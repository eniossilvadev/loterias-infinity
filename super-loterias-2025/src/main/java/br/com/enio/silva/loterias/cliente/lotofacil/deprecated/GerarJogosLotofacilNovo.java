package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoInicio;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoFim;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotofacilNovo {

	private static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	public static void main(String[] args) throws IOException {

		LotofacilConfig config = new LotofacilConfig15();

		config.setPre(new Integer[] { 5, 8, 10, 18, 7 });
		config.setExcluir(Arrays.asList());
		config.setIncluir(Arrays.asList());

		List<Integer> qtt = Arrays.asList();

		int maximoRepetidosLista = 11;

		LotofacilConfig.Esquema esquema = LotofacilConfig.Esquema.PADRAO;
		if (config.getNrosApostados() == 19) {
			esquema = LotofacilConfig.Esquema.E19;
		} else if (config.getNrosApostados() == 21) {
			esquema = LotofacilConfig.Esquema.E21;
		}

		String destino = config.getFullPath();
		String params = config.getFullPathParams();

		int maxAnterior = 15;

		List<List<Integer>> resultados = config.getTodosResultados();

		int ultimos = resultados.size() / 20;

		Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
		ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");

		Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
		ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");

		System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

		List<Integer> last = resultados.get(resultados.size() - 1);

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = new ArrayList<>(Arrays.asList(config.getPre()));

		for (int i = 0; i < config.getQttInicial(); i++) {
			preJogos.add(preJogo);
		}

		int tam = config.getNrosApostados();
		int maxNum = config.getMaxNum();
		List<Integer> excluir = config.getExcluir();
		List<Integer> incluir = config.getIncluir();

		preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir, incluir);

		String remove = config.getCaminhoJogoAtual();
		List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
		ArquivoUtil.saveLists(remover, remove, "\t");
		remover.addAll(resultados);
		FiltroIf filtro;

		if (isNotEmpty(preJogos)) {
			filtro = new FiltroRemoverIntersecao(remover);
			if (esquema.equals(LotofacilConfig.Esquema.PADRAO)) {
				preJogos = filtro.filtrarListas(preJogos);
			}
		}

		if (isNotEmpty(preJogos)) {
			if (qtt != null && qtt.size() > 0) {
				int mxQtt = config.getNrosApostados() - (config.getMaxNum() - qtt.size());
				filtro = new FiltroQuantidadeDaLista(mxQtt, qtt);
				preJogos = filtro.filtrarListas(preJogos);
			} else if (esquema.equals(LotofacilConfig.Esquema.PADRAO)) {
				filtro = new FiltroUltimoSorteio(8, 11, last);
				// preJogos = filtro.filtrarListas(preJogos);
			} else {
				filtro = new FiltroUltimoSorteio(11, 13, last);
				// preJogos = filtro.filtrarListas(preJogos);
			}
		}

		boolean filtrar = true; // new Random().nextBoolean();

		String pathListaMax = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LISTA_MAX.txt";

		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(pathListaMax);

		if (filtrar && isNotEmpty(preJogos)) {
			System.out.println("Antes: " + preJogos.size());

			if (isNotEmpty(preJogos)) {
				if (esquema.equals(LotofacilConfig.Esquema.PADRAO)) {
					filtro = new FiltroMaximaSequencia(11);
					preJogos = filtro.filtrarListas(preJogos);
				}
			}

			if (isNotEmpty(preJogos)) {
				if (esquema.equals(LotofacilConfig.Esquema.PADRAO)) {
					// filtro = new FiltroDivide(3, config.getMaxNum());
					// preJogos = filtro.filtrarListas(preJogos);
				} else {
					filtro = new FiltroDivide(1, config.getMaxNum());
					preJogos = filtro.filtrarListas(preJogos);
				}

			}

			if (isNotEmpty(preJogos)) {
				filtro = new FiltroMaximoInicio(2);
				preJogos = filtro.filtrarListas(preJogos);
			}

			if (isNotEmpty(preJogos)) {
				filtro = new FiltroMinimoFim(23);
				preJogos = filtro.filtrarListas(preJogos);
			}

			if (isNotEmpty(preJogos)) {
				if (esquema.equals(LotofacilConfig.Esquema.PADRAO)) {
					filtro = new FiltroParImpar(5);
					preJogos = filtro.filtrarListas(preJogos);
				} else {
					filtro = new FiltroParImpar(5);
					preJogos = filtro.filtrarListas(preJogos);
				}
			}

			if (isNotEmpty(preJogos)) {
				filtro = new FiltroMinimoLinhas(5, 5, 1);
				preJogos = filtro.filtrarListas(preJogos);
			}

			if (isNotEmpty(preJogos)) {
				filtro = new FiltroMinimoColunas(5, 1);
				preJogos = filtro.filtrarListas(preJogos);
			}

			if (isNotEmpty(preJogos)) {
				if (esquema.equals(LotofacilConfig.Esquema.PADRAO)) {
					filtro = new FiltroMaximoLinhas(5, 5, 4);
					// preJogos = filtro.filtrarListas(preJogos);
				}
			}

			if (isNotEmpty(preJogos) && maximoRepetidosLista > 0) {
				filtro = new FiltroMaxLista(listaMax, maximoRepetidosLista);
				// preJogos = filtro.filtrarListas(preJogos);
				ArquivoUtil.saveLists(listaMax, pathListaMax, "\t");
			}
		}

		List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

		JogoLotofacil jlm = null;
		for (

		List<Integer> pj : preJogos) {
			jlm = new JogoLotofacil(pj);
			jogosLM.add(jlm);
		}

		System.out.println("\n");

		Pontuador pontuador = config.getPontuador();
		int cont = 0;
		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			pontuador.pontuar(resultados, meuJogo);
			pontuador.pontuar(resultados, meuJogo);
			// Integer before = meuJogo.getPontuacao();
			// // meuJogo.setPontuacao(meuJogo.getPontuacao() * 1);
			// ((PontuadorBasico) pontuador).pontuarPorPosicao(resultados,
			// meuJogo, 1);
			// Integer after = meuJogo.getPontuacao();
			// List<Integer> lista = meuJogo.getNumerosAsList();
			//
			// cont++;
			// if (cont % 500 == 0) {
			// String msg = String.format("[%s, %s] %s", before, after, lista);
			// System.out.println(msg);
			// }
		}

		if (config.isPontuacaoExtra()) {
			for (JogoAb meuJogo : jogosLM) {

				Integer before = meuJogo.getPontuacao();
				// pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);
				// pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);
				// pontuador.pontuarMap(meuJogo, mapResultado, 15, 1);
				pontuador.pontuarMap(meuJogo, mapAtraso, 10, 1);
				Integer after = meuJogo.getPontuacao();
				List<Integer> lista = meuJogo.getNumerosAsList();

				cont++;
				if (cont % 500 == 0) {
					String msg = String.format("[%s, %s] %s", before, after, lista);
					System.out.println(msg);
				}
			}
		}

		Collections.sort(jogosLM);

		filtro = new FiltroMaxmoIgualAnterior(maxAnterior);
		// preJogos = filtro.filtrarListas(preJogos);

		jogosLM = jogosLM.subList(0, config.getNrosJogos());

		int c = 0;
		for (

		JogoAb meuJogo : jogosLM) {
			System.out.println(
			        meuJogo.getPontuacao() + "[" + c++ + "]: " + meuJogo.getNumerosAsList());
		}

		List<List<Integer>> jogos = new ArrayList<List<Integer>>();
		for (

		JogoAb jj : jogosLM) {
			jogos.add(jj.getNumerosAsList());
			System.out.println(jj.getNumerosAsList() + "\t" + jj.getMapConta());
		}

		System.out.println("Ordenado");
		for (

		List<Integer> jogo : jogos) {
			System.out.println(jogo);
			System.out
			        .println(jogo.stream().map(Object::toString).collect(Collectors.joining("\t")));
		}

		List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();

		if (esquema.equals(LotofacilConfig.Esquema.E19)) {
			for (List<Integer> j : jogos) {
				System.out.println("19");
				jogosOut.addAll(EsquemaLotofacil.getEsquema19n11a(j));
			}
		} else if (esquema.equals(LotofacilConfig.Esquema.E21)) {
			for (List<Integer> j : jogos) {
				jogosOut.addAll(EsquemaLotofacil.getEsquema21n21a(j));
			}
		} else {
			jogosOut.addAll(jogos);
		}

		ArquivoUtil.saveLists(jogosOut, destino, "\t");

		ArquivoUtil.save(config.toString(), params);

		config.setSufixo("_shuffle");
		Collections.shuffle(jogosOut);
		// SArquivoUtil.saveLists(jogosOut, config.getFullPath(), "\t");

	}

}
