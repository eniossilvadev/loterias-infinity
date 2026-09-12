package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosLotofacilNovoLoop2 {

	public static List<List<Integer>> filtroDivide(int filtroDivideDiferenca, int maxNum,
			List<List<Integer>> fullList) {
		FiltroIf filtro = new FiltroDivide(filtroDivideDiferenca, maxNum);
		return filtro.filtrarListas(fullList);
	}

	private static List<List<Integer>> filtroMaximoLinhas(int i, int j, int k,
			List<List<Integer>> fullList) {
		FiltroIf filtro = new FiltroMaximoLinhas(i, j, k);
		return filtro.filtrarListas(fullList);
	}

	private static List<List<Integer>> filtroMinimoColunas(int i, int j,
			List<List<Integer>> fullList) {
		FiltroIf filtro = new FiltroMinimoColunas(i, j);
		return filtro.filtrarListas(fullList);
	}

	public static List<List<Integer>> filtroMinimoLinhas(int i, int j, int k,
			List<List<Integer>> fullList) {
		FiltroIf filtro = new FiltroMinimoLinhas(i, j, k);
		return filtro.filtrarListas(fullList);
	}

	public static List<List<Integer>> filtroParImpar(int filtroParImparDiff,
			List<List<Integer>> fullList) {
		FiltroIf filtro = new FiltroParImpar(filtroParImparDiff);
		return filtro.filtrarListas(fullList);
	}

	public static List<List<Integer>> filtroUltimoSorteio(int minUltimoSorteio,
			int maxUltimoSorteio, List<List<Integer>> full, List<Integer> last) {
		FiltroIf filtro = new FiltroUltimoSorteio(minUltimoSorteio, maxUltimoSorteio, last);
		return filtro.filtrarListas(full);
	}

	public static List<List<Integer>> filtroUltimoSorteio(int filtroMaximaSequenciaMax,
			List<List<Integer>> full) {
		FiltroIf filtro = new FiltroMaximaSequencia(filtroMaximaSequenciaMax);
		return filtro.filtrarListas(full);
	}

	private static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	public static void main(String[] args) throws IOException {

		List<List<Integer>> erro = new ArrayList<>();

		int nroJogos = 1;

		LotofacilConfig config = new LotofacilConfig15();
		config.setPre(new Integer[] {});

		config.setExcluir(Arrays.asList(5, 8, 10, 18, 7));
		config.setIncluir(Arrays.asList());

		List<Integer> elements = ListaUtils.iterateStream(1, 1, 25);
		elements.removeAll(config.getExcluir());

		Integer[] listTemp = elements.toArray(new Integer[elements.size()]);
		List<List<Integer>> fullList = CombinationUtils.gerarCombinacao(listTemp, 15);

		List<List<Integer>> resultados = config.getTodosResultados();

		List<Integer> last = resultados.get(resultados.size() - 1);

		int minUltimoSorteio = 8;
		int maxUltimoSorteio = 11;
		fullList = filtroUltimoSorteio(minUltimoSorteio, maxUltimoSorteio, fullList, last);

		int filtroMaximaSequenciaMax = 11;
		fullList = filtroUltimoSorteio(filtroMaximaSequenciaMax, fullList);

		int filtroDivideDiferenca = 5;
		fullList = filtroDivide(filtroDivideDiferenca, config.getMaxNum(), fullList);

		int filtroParImparDiff = 5;
		fullList = filtroParImpar(filtroParImparDiff, fullList);

		fullList = filtroMinimoLinhas(5, 5, 1, fullList);

		fullList = filtroMaximoLinhas(5, 5, 4, fullList);

		fullList = filtroMinimoColunas(5, 1, fullList);

		for (int n = 0; n < nroJogos; n++) {

			try {

				LotofacilConfig.Esquema esquema = LotofacilConfig.Esquema.PADRAO;
				if (config.getNrosApostados() == 19) {
					esquema = LotofacilConfig.Esquema.E19;
				} else if (config.getNrosApostados() == 21) {
					esquema = LotofacilConfig.Esquema.E21;
				}

				String destino = config.getFullPath();
				String params = config.getFullPathParams();

				System.out.println(config.toJson());

				int ultimos;
				if (n % 3 == 0) {
					ultimos = 100;
				} else if (n % 3 == 1) {
					ultimos = resultados.size() / 3;
				} else {
					ultimos = resultados.size();
				}

				Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
				ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");

				Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
				ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");

				System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = new ArrayList<>(Arrays.asList(config.getPre()));

				for (int i = 0; i < config.getQttInicial(); i++) {
					preJogos.add(preJogo);
				}
				preJogos = new ArrayList<>(fullList);

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> rem = ArquivoUtil.obterLinhasComoListasUnique(remove);
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				remover.addAll(resultados);
				FiltroIf filtro;

				preJogos.removeAll(remover);

				if (isNotEmpty(preJogos)) {
					filtro = new FiltroRemoverIntersecao(remover);
					preJogos = filtro.filtrarListas(preJogos);
				}

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				pontuar(resultados, jogosLM, pontuador);

				// if (new Random().nextBoolean()) {
				// pontuarExtra(config, mapResultado, jogosLM, pontuador);
				// }

				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {

					if (c % 1000 == 0) {
						int pontuacao = meuJogo.getPontuacao();
						List<Integer> atual = meuJogo.getNumerosAsList();
						System.out.println(pontuacao + "[" + c + "]: " + atual);
					}
					c++;
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {
					jogos.add(jj.getNumerosAsList());
					System.out.println(jj.getNumerosAsList() + "\t" + jj.getMapConta());
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					System.out.println(jogo);
					Collector<CharSequence, ?, String> joining = Collectors.joining("\t");
					System.out.println(jogo.stream().map(Object::toString).collect(joining));
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

				rem.addAll(jogosOut);
				ArquivoUtil.saveLists(rem, remove, "\t");

				config.setSufixo("_shuffle");
				Collections.shuffle(jogosOut);
				// SArquivoUtil.saveLists(jogosOut, config.getFullPath(), "\t");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!erro.isEmpty()) {
			System.out.println("Favor reprocessar :" + erro);
		}
	}

	/**
	 * @param resultados
	 * @param jogosLM
	 * @param pontuador
	 */
	private static void pontuar(List<List<Integer>> resultados, List<JogoLotofacil> jogosLM,
			Pontuador pontuador) {
		List<JogoLotofacil> jogosRetorno = new ArrayList<>();
		// int cont = 0;
		// Integer before;
		// Integer after;
		// List<Integer> lista;
		// PontuadorBasico pb = ((PontuadorBasico) pontuador);
		int max = 0;
		int atual = 0;

		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			atual = meuJogo.getPontuacao();
			if (atual > max) {
				jogosRetorno.clear();
				jogosRetorno.add((JogoLotofacil) meuJogo);
				max = atual;
			} else if (atual == max) {

			}
			// pontuador.pontuar(resultados, meuJogo);
			// before = meuJogo.getPontuacao();
			// pb.pontuarPorPosicao(resultados, meuJogo, 1);
			// after = meuJogo.getPontuacao();
			// lista = meuJogo.getNumerosAsList();
			//
			// cont++;
			// if (cont % 10000 == 0) {
			// String msg = String.format("[%s, %s] %s", before, after, lista);
			// System.out.println(msg);
			// }
		}
	}

	/**
	 * @param config
	 * @param mapResultado
	 * @param jogosLM
	 * @param pontuador
	 */
	public static void pontuarExtra(LotofacilConfig config, Map<Integer, Integer> mapResultado,
			List<JogoLotofacil> jogosLM, Pontuador pontuador) {
		int cont;
		cont = 0;
		if (config.isPontuacaoExtra()) {
			for (JogoAb meuJogo : jogosLM) {

				Integer before = meuJogo.getPontuacao();
				pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);

				Integer after = meuJogo.getPontuacao();
				List<Integer> lista = meuJogo.getNumerosAsList();

				cont++;
				if (cont % 500 == 0) {
					String msg = String.format("[%s, %s] %s", before, after, lista);
					System.out.println(msg);
				}
			}
		}
	}
}
