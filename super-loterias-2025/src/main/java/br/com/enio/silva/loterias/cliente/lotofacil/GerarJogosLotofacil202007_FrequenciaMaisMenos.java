package br.com.enio.silva.loterias.cliente.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotofacil202007_FrequenciaMaisMenos extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 50000;

	public static int total = 10;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 10;

	public static int maximoInicio = 6;

	public static int minimoFim = 19;

	public static int topN = 2;

	public static int minPercent = 20;

	public static int maxPercent = 35;

	public static int tamPadraoExcluir = 1;

	public static int tamPadraoIncluir = 8;

	private static int varMin = 0;

	private static int varMax = 0;

	/**
	 * @param c
	 * @param perc
	 * @param tamExcluir
	 * @param jogosAtuais
	 * @return
	 */
	private static Set<Integer> gerarExcluirAcimaMax(LotofacilConfig c, int perc, int tamExcluir,
			List<Integer> jogosAtuais) {
		int tamExcl = 0;
		Set<Integer> listaExcl = new HashSet<>(Arrays.asList());

		int parMax = (jogosAtuais.size() / c.getNrosApostados() * perc / 100) + varMax;

		if (parMax > 0) {
			// StringBuilder norm = new StringBuilder();
			for (int i = 1; i <= 25; i++) {
				int qttEl = Collections.frequency(jogosAtuais, i);
				// norm.append(String.format("(%s, %s) ", i, qttEl));
				if (qttEl > parMax) {
					// System.out.println("Normalizando: " + i);
					listaExcl.add(i);
				}
			}

			// System.out.println(norm.toString());
			tamExcl = listaExcl.size();
			System.out.println(">>> Números excluídos(" + varMax + "):" + listaExcl);
			if (tamExcl > tamExcluir) {
				List<Integer> excl = new ArrayList<>(listaExcl);
				Collections.shuffle(excl);
				excl = excl.subList(0, tamExcluir);
				listaExcl = new HashSet<>(excl);
				varMax = Math.min(varMax++, 0);
			} else {
				varMax = varMax - 3;
			}
			System.out.println("");
		}
		return listaExcl;
	}

	/**
	 * @param config
	 * @param minPercent
	 * @param tamPadraoIncluir
	 * @param jogosAtuaisFlat
	 * @return
	 */
	private static Set<Integer> gerarIncluirAbaixoMinimo(LotofacilConfig config, int minPercent,
			int tamPadraoIncluir, List<Integer> jogosAtuaisFlat) {
		int tamExcl;

		int parMin = (jogosAtuaisFlat.size() / config.getNrosApostados() * minPercent / 100)
				+ varMin;
		Set<Integer> listaIncl = new HashSet<>();
		if (parMin > 0) {
			// StringBuilder norm = new StringBuilder();
			for (int i = 1; i <= 25; i++) {
				int qttEl = Collections.frequency(jogosAtuaisFlat, i);
				// norm.append(String.format("(%s, %s) ", i, qttEl));
				if (qttEl < parMin) {
					// System.out.println("Normalizando: " + i);
					listaIncl.add(i);
				}
			}

			// System.out.println(norm.toString());
			tamExcl = listaIncl.size();
			System.out.println(">>> Números incluídos (" + varMin + "): " + listaIncl);
			if (tamExcl > tamPadraoIncluir) {
				List<Integer> incl = new ArrayList<>(listaIncl);
				Collections.shuffle(incl);
				incl = incl.subList(0, tamPadraoIncluir);
				listaIncl = new HashSet<>(incl);
				varMin = Math.max(varMin--, 0);
			} else {
				varMin = varMin + 3;
			}
			System.out.println("");
		}
		return listaIncl;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		int[] ultimosJogos = { 0, 50, 75, 100, 150, 0, 260, 500, 1000, 20 };

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		int maximoRepetidosLista = 12;

		List<List<Integer>> somenteNovos = new ArrayList<>();

		List<List<Integer>> listaMax = getListaMax();

		List<List<Integer>> listaJogosCorrentes = new LinkedList<>(
				ArquivoUtil.obterLinhasComoListasUnique(config.getCaminhoJogoCorrente(),
						config.getNrosApostados()));

		List<List<Integer>> listaJogosAtuais = new ArrayList<>(listaJogosCorrentes);
		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);
		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);
		List<Integer> last = getLast(listaJogosCorrentes);

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		int curr = 0;

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		int defaultSize = 15;
		String output = base + "ind/" + currDate + "/" + config.getDefaultName() + DateUtils.getCurrentDefaultDateTime() + ".txt";
		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";
		String pathListaDeJogos = config.getCaminhoJogoAtual();
		String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();

		saveDefault2(somenteNovos, sn);
		saveDefault2(somenteNovos, snBkp);

		saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
		saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

		List<List<Integer>> statC1 = gc(listaJogosCorrentes, defaultSize);
		String mc1 = config.getFrequencia(statC1);
		ArquivoUtil.save(mc1, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

		List<List<Integer>> statA1 = gc(listaJogosAtuais, defaultSize);
		String ma1 = config.getFrequencia(statA1);
		ArquivoUtil.save(ma1, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");

		for (int count = 0; count < total; count++) {

			tamPadraoExcluir = 2 + curr % 4;
			tamPadraoIncluir = 6 - tamPadraoExcluir;
			curr++;

			try {

				topN = getTopN(config, topN);

				config = getConfig(count);

				maximoRepetidosLista = getMaximoRepetidosLista(count, config);

				config.setQttInicial(qttInicial);

				List<Integer> jogosAtuaisFlat = getFlat(config.getCaminhoJogoAtual(),
						config.getCaminhoJogoCorrente(), count);

				Set<Integer> listaIncl = gerarIncluirAbaixoMinimo(config, minPercent,
						tamPadraoIncluir, jogosAtuaisFlat);

				int exc = new Random().nextInt(100);
				exc = exc % 25 + 1;

				listaIncl.remove(exc);

				int tmpTamPadraoExcluir = tamPadraoExcluir + tamPadraoIncluir - listaIncl.size()
				- 1;

				Set<Integer> listaExcl = gerarExcluirAcimaMax(config, maxPercent,
						tmpTamPadraoExcluir, jogosAtuaisFlat);

				listaExcl.add(exc);

				if (listaExcl.size() == tmpTamPadraoExcluir) {
					varMax = varMax + 2;
				} else {
					varMax--;
				}

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				listaIncl.removeAll(listaExcl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				boolean filtrarMaximoRepetidosLista = true && maximoRepetidosLista > 0;

				List<List<Integer>> resultados = config.getTodosResultados();

				listaJogosCorrentes.removeAll(resultados);

				int ultimos = ultimosJogos[count % ultimosJogos.length];
				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
						: ultimos;
				List<List<Integer>> ultimosResultados = config.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

				saveMapResultado(config, ultimosResultados, ultimos);

				saveMapAtraso(config, ultimosResultados);

				System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = Arrays.asList(config.getPre());

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = preJogo;

				int totalIncluirExcluir = excluir.size() + incluir.size();
				if (totalIncluirExcluir >= 4) {
					preJogos = CombinationUtils.gerarCombinacao(tam, 1, maxNum, excluir, incluir);
				} else {
					for (int i = 0; i < config.getQttInicial(); i++) {
						preJogos.add(preJogo);
					}
					preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
							incluir);
				}

				List<Integer> listaPre = Arrays.asList(config.getPre());
				boolean validarPre = listaPre != null && !listaPre.isEmpty();
				for (List<Integer> t : top) {
					List<Integer> inter = ListUtils.intersection(t, excluir);

					if (inter == null || inter.isEmpty()) {
						if (validarPre) {
							inter = ListUtils.intersection(t, listaPre);
							if (inter != null && inter.size() == listaPre.size()) {
								preJogos.add(new ArrayList<>(t));
							}
						} else {
							preJogos.add(new ArrayList<>(t));
						}
					}
				}

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				// preJogos = aplicarFiltroQuantidadeDaLista(config, qtt,
				// preJogos);

				// Filtro �ltimo sorteado
				preJogos = aplicarFiltroUltimoSorteio(last, preJogos, tamPadraoExcluir);

				if (count >= total / 2) {
					preJogos = aplicarFiltros(maximoRepetidosLista, listaMax, count,
							filtrarMaximoRepetidosLista, preJogos, sequenciaMaxima, maximoInicio,
							minimoFim);
				}

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(ultimosResultados, meuJogo);
				}
				Collections.sort(jogosLM);

				topN = Math.min(topN, jogosLM.size() - 1);
				List<JogoLotofacil> jogosTop = jogosLM.subList(0, topN);
				for (JogoLotofacil jj : jogosTop) {
					top.add(jj.getNumerosAsList());
				}

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(c++ + "\t" + meuJogo);
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {

					List<Integer> novo = jj.getNumerosAsList();

					System.out.println(novo + "\t" + jj.getMapConta());

					jogos.add(novo);
					if (listaJogosAtuais.contains(novo) || listaJogosCorrentes.contains(novo)) {
						throw new Exception("J� tem esse jogo: " + novo);
					}
					listaJogosAtuais.add(novo);
					listaJogosCorrentes.add(novo);
					last = new ArrayList<>(novo);

					somenteNovos.add(novo);
				}

				saveDefault2(jogos, output);
				saveDefault2(somenteNovos, sn);
				saveDefault2(somenteNovos, snBkp);

				saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
				saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

				List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
				String mc = config.getFrequencia(statC);
				ArquivoUtil.save(mc, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

				List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
				String ma = config.getFrequencia(statA);
				ArquivoUtil.save(ma, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");

			} catch (Exception e) {
				e.printStackTrace();
				count--;
				save(listaJogosAtuais, config.getCaminhoJogoAtual());
				save(listaJogosCorrentes, config.getCaminhoJogoCorrente());
				save(somenteNovos, sn);
			}
		}
	}
}
