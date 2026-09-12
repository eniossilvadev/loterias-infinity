package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig16;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroDiffMinimaGeralEntreNumeros;
import br.com.enio.silva.loterias.filtro.FiltroDiffMinimaGeralEntreNumerosAlt;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ImprimirUtil;
import util.ListUtil;

public class GerarJogosMega {

	public static void main(String[] args) throws IOException {

		MegaSenaConfigAb config = new MegaSenaConfig16();

		config.setPre(new Integer[] {});
		config.setExcluir(Arrays.asList());
		config.setIncluir(Arrays.asList());

		List<Integer> temUm = new ArrayList<Integer>(config.getExcluir());

		System.out.println("Filtro TemUm (tamanho): " + temUm.size());

		String output = "E:\\loterias\\mega_sena\\" + config.getDefaultName() + ".txt";

		String path = config.getCaminhoTodosResultados();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);
		int ultimos = resultados.size();
		// int ultimos = 100;

		String remove = config.getCaminhoJogoAtual();
		List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
		ArquivoUtil.saveLists(remover, remove, "\t");

		String pathListaMax = "E:\\loterias\\mega_sena\\mensal\\atual.txt";
		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(pathListaMax);
		int maximoRepetidosLista = 3;

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

		int tam = config.getNrosApostados();
		int max = config.getMaxNum();
		List<Integer> exc = config.getExcluir();
		List<Integer> inc = config.getIncluir();
		preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, max, exc, inc);

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		remover.addAll(resultados);

		filtro = new FiltroRemoverIntersecao(remover);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMaxConsecutivos(7);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroDivide(4, config.getMaxNum());
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroDiffMinimaGeralEntreNumeros(42);
		// preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroDiffMinimaGeralEntreNumerosAlt(23);
		// preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroParImpar(4);
		// preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMinimoLinhas(6, 10, 1);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMaximoLinhas(6, 10, 4);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroExcluirTemUm(temUm);
		// preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMaxLista(listaMax, maximoRepetidosLista);
		// preJogos = filtro.filtrarListas(preJogos);

		if (preJogos != null && preJogos.size() > 0) {
			System.out.println(ImprimirUtil.printListas(preJogos));

			List<JogoMega> jogosLM = new ArrayList<JogoMega>();
			JogoMega jlm = null;
			for (List<Integer> pj : preJogos) {
				jlm = new JogoMega(pj);
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
				config.getPontuador().pontuarMap(meuJogo, mapAtraso, 60, 30);
			}

			Collections.sort(jogosLM);

			// imprimirJogosPontuados(jogosLM);

			filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());
			// jogosLM = (List<JogoMega>) filtro.filtrarJogos(jogosLM);

			List<JogoMega> jogosLMFinal = new ArrayList<>();
			List<Integer> tmp = new ArrayList<>();
			List<List<Integer>> iguais;
			for (int i = 0; i < config.getNrosJogos(); i++) {

			}

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
