package br.com.enio.silva.loterias.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.config.quina.QuinaConfig5;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroDiffMinimaGeralEntreNumeros;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosQuina {

	public static void main(String[] args) throws IOException {

		QuinaConfigAb config = new QuinaConfig5();

		config.setPre(new Integer[] {});
		config.setExcluir(Arrays.asList());

		List<Integer> temUm = new ArrayList<Integer>(config.getExcluir());

		System.out.println("Filtro TemUm (tamanho): " + temUm.size());

		String output = config.getCaminhoDefaultOutput();

		String path = config.getCaminhoResultados();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);
		// int ultimos = resultados.size();
		int ultimos = resultados.size();
		// int ultimos = 26;

		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
		        .getMapContaResultados(resultados, ultimos);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);

		Map<Integer, Integer> mapAtraso = ContaAtrasosResultados.getInstance(config.getMaxNum())
		        .getMapContaResultados(resultados);
		System.out.println(mapAtraso);

		mapAtraso = MapUtil.sortByValueDesc(mapAtraso);
		System.out.println(mapAtraso);

		List<List<Integer>> preJogos = config.getPreJogos();

		preJogos = ListUtil.completarExclusao(preJogos, config.getNrosApostados(),
		        config.getMaxNum(), config.getExcluir());

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		String remove = config.getCaminhoJogoAtual();
		List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
		ArquivoUtil.saveLists(remover, remove, "\t");
		remover.addAll(resultados);

		filtro = new FiltroRemoverIntersecao(remover);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroParImpar(3);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMaximoLinhas(8, 10, 3);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMaxConsecutivos(3);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroDivide(3, 80);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroDiffMinimaGeralEntreNumeros(55);
		preJogos = filtro.filtrarListas(preJogos);

		// filtro = new FiltroDiffMinimaGeralEntreNumerosAlt(30);
		// preJogos = filtro.filtrarListas(preJogos);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (preJogos != null && preJogos.size() > 0) {
			// System.out.println(ImprimirUtil.printListas(preJogos));

			List<JogoQuina> jogosLM = new ArrayList<>();
			JogoQuina jlm = null;
			for (List<Integer> pj : preJogos) {
				jlm = new JogoQuina(pj);
				jogosLM.add(jlm);
			}

			System.out.println("\n");

			for (JogoAb meuJogo : jogosLM) {
				config.getPontuador().pontuar(resultados, meuJogo);
				// System.out.println(meuJogo.getPontuacao() + ": " +
				// meuJogo.getNumerosAsList());

			}

			for (JogoAb meuJogo : jogosLM) {
				// pontuador.pontuarMap(meuJogo, mapResultado, maxNum, 3);
				// System.out.println(meuJogo.getPontuacao() + ": " +
				// meuJogo.getNumerosAsList());
				// config.getPontuador().pontuarMap(meuJogo, mapAtraso, 60, 25);
			}

			Collections.sort(jogosLM);

			// imprimirJogosPontuados(jogosLM);

			filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());
			// jogosLM = (List<JogoQuina >) filtro.filtrarJogos(jogosLM);

			jogosLM = jogosLM.subList(0, config.getNrosJogos());
			int c = 0;
			for (JogoAb meuJogo : jogosLM) {
				System.out.println(
				        meuJogo.getPontuacao() + "[" + c++ + "]: " + meuJogo.getNumerosAsList());
			}

			List<List<Integer>> jogos = new ArrayList<List<Integer>>();
			for (JogoAb jj : jogosLM) {
				jogos.add(jj.getNumerosAsList());
				System.out.println(jj.getNumerosAsList() + "\t" + jj.getMapConta());
			}

			System.out.println("Original");
			for (List<Integer> jogo : jogos) {
				System.out.println(jogo);
				Collections.replaceAll(jogo, 0, 0);
				Collections.sort(jogo);
			}

			System.out.println("Ordenado");
			for (List<Integer> jogo : jogos) {
				System.out.println(jogo);
			}

			ArquivoUtil.saveLists(jogos, output, "\t");

		} else {

			System.out.println("Não há resultados com os filtros aplicados");
		}

	}

	/**
	 * @param mapResultado
	 * @param last
	 */
	@SuppressWarnings("unused")
	private static void obterTopLast(Map<Integer, Integer> mapResultado, List<Integer> last) {
		List<Integer> top10 = new ArrayList<Integer>();
		for (Map.Entry<Integer, Integer> entry : mapResultado.entrySet()) {
			Integer key = entry.getKey();
			if (last.contains(key)) {
				top10.add(key);
				if (top10.size() == 1) {
					break;
				}
			}
		}
		System.out.println(top10);
	}
}
