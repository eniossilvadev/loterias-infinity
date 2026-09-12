package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_2;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_3;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_4;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_5;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoInicio;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoFim;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;


public abstract class GerarJogosLotofacil2020Ab extends GerarJogosLotofacilAb {

	protected static List<List<Integer>> listaJogosCorrentes = new ArrayList<>();

	protected static List<List<Integer>> listaJogosAtuais = new ArrayList<>();

	protected static LotofacilConfig config = new LotofacilConfig15();

	/**
	 * @param preJogos
	 * @param sequenciaMaxima
	 * @return
	 */
	private static List<List<Integer>> aplicarFiltroMaximaSequencia(List<List<Integer>> preJogos,
			int sequenciaMaxima) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroMaximaSequencia(sequenciaMaxima);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @param maximoInicio
	 * @return
	 */
	private static List<List<Integer>> aplicarFiltroMaximoInicio(List<List<Integer>> preJogos,
			int maximoInicio) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroMaximoInicio(maximoInicio);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroMaximoLinhas(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroMaximoLinhas(5, 5, 4);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param maximoRepetidosLista
	 * @param pathListaMax
	 * @param listaMax
	 * @param filtrarMaximoRepetidosLista
	 * @param preJogos
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> aplicarFiltroMaxLista(int maximoRepetidosLista,
			String pathListaMax, List<List<Integer>> listaMax, boolean filtrarMaximoRepetidosLista,
			List<List<Integer>> preJogos) throws IOException {
		FiltroIf filtro;
		if (filtrarMaximoRepetidosLista && !listaMax.isEmpty()) {
			filtro = new FiltroMaxLista(listaMax, maximoRepetidosLista);
			preJogos = filtro.filtrarListas(preJogos);
			ArquivoUtil.saveLists(listaMax, pathListaMax, "\t");
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroMinimoColunas(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroMinimoColunas(5, 1);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @param minimoFim
	 * @return
	 */
	private static List<List<Integer>> aplicarFiltroMinimoFim(List<List<Integer>> preJogos,
			int minimoFim) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroMinimoFim(minimoFim);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroMinimoLinhas(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroMinimoLinhas(5, 5, 1);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroParImpar(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroParImpar(5);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param config
	 * @param qtt
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroQuantidadeDaLista(LotofacilConfig config,
			List<Integer> qtt, List<List<Integer>> preJogos) {

		if (isNotEmpty(preJogos)) {

			boolean filtrarQtt = qtt != null && qtt.size() > 0;

			if (filtrarQtt) {
				int mxQtt = config.getNrosApostados() - (config.getMaxNum() - qtt.size());
				FiltroIf filtro = new FiltroQuantidadeDaLista(mxQtt, qtt);
				preJogos = filtro.filtrarListas(preJogos);
			}
		}
		return preJogos;
	}

	/**
	 * @param listaJogosAtuais
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroRemoverIntersecao(
			List<List<Integer>> listaJogosAtuais, List<List<Integer>> preJogos) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroRemoverIntersecao(listaJogosAtuais);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param maximoRepetidosLista
	 * @param listaMax
	 * @param count
	 * @param filtrarMaximoRepetidosLista
	 * @param preJogos
	 * @param minimoFim
	 * @param maximoInicio
	 * @param sequenciaMaxima
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> aplicarFiltros(int maximoRepetidosLista,
			List<List<Integer>> listaMax, int count, boolean filtrarMaximoRepetidosLista,
			List<List<Integer>> preJogos, int sequenciaMaxima, int maximoInicio, int minimoFim)
					throws IOException {
		boolean filtrar = (count % 5) != 0;

		if (filtrar && isNotEmpty(preJogos)) {

			System.out.println("Antes: " + preJogos.size());

			filtrarMaximoRepetidosLista = filtrarMaximoRepetidosLista && isNotEmpty(preJogos);

			preJogos = aplicarFiltroMaxLista(maximoRepetidosLista,
					LotofacilConfig.getPathListaMax(), listaMax, filtrarMaximoRepetidosLista,
					preJogos);

			preJogos = aplicarFiltroMaximaSequencia(preJogos, sequenciaMaxima);

			preJogos = aplicarFiltroMinimoFim(preJogos, minimoFim);

			preJogos = aplicarFiltroMinimoColunas(preJogos);

			preJogos = aplicarFiltroParImpar(preJogos);

			preJogos = aplicarFiltroMaximoInicio(preJogos, maximoInicio);

			preJogos = aplicarFiltroMinimoLinhas(preJogos);

			preJogos = aplicarFiltroMaximoLinhas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param last
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroUltimoSorteio(List<Integer> last,
			List<List<Integer>> preJogos) {
		if (isNotEmpty(preJogos) && isNotEmpty(last)) {
			FiltroIf filtro = new FiltroUltimoSorteio(6, 10, last);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	protected static List<List<Integer>> aplicarFiltroUltimoSorteio(List<Integer> last,
			List<List<Integer>> preJogos, int excl) {
		if (isNotEmpty(preJogos) && isNotEmpty(last)) {
			int min = 5 + excl;
			int max = min + 2;
			FiltroIf filtro = new FiltroUltimoSorteio(min, max, last);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	protected static List<List<Integer>> aplicarFiltroUltimoSorteio(List<Integer> last,
			List<List<Integer>> preJogos, int min, int max) {

		if (isNotEmpty(preJogos) && isNotEmpty(last)) {
			FiltroIf filtro = new FiltroUltimoSorteio(min, max, last);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	public static void beforeLotofacil(String base, List<List<Integer>> listaSN, int defaultSize,
			String currDate, String currDateTime) {
		try {
			String sn = base + "\\LSN.txt";
			String snBkp = base + "novos/" + currDateTime + "_nlf.txt";
			String pathListaDeJogos = config.getCaminhoJogoAtual();
			String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();

			saveDefault2(listaSN, sn);
			saveDefault2(listaSN, snBkp);

			saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
			saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

			List<List<Integer>> statC1 = gc(listaJogosCorrentes, defaultSize);
			String mc1 = config.getFrequencia(statC1);
			ArquivoUtil.save(mc1, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

			List<List<Integer>> statA1 = gc(listaJogosAtuais, defaultSize);
			String ma1 = config.getFrequencia(statA1);
			ArquivoUtil.save(ma1, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static List<List<Integer>> clear(List<List<Integer>> lista) {
		List<List<Integer>> retorno = new ArrayList<>();

		for (List<Integer> l : lista) {
			if (!retorno.contains(l)) {
				retorno.add(l);
			}
		}

		return retorno;
	}

	protected static LotofacilConfig getConfig(int count) {

		int mod = count % 6;

		if (mod == 5 || mod == 1) {
			return new LotofacilConfig15();
		}

		if (mod == 2) {
			return new LotofacilConfig15_4();
		}

		if (mod == 3) {
			return new LotofacilConfig15_2();
		}

		if (mod == 4) {
			return new LotofacilConfig15_3();
		}

		if (mod == 0) {
			return new LotofacilConfig15_5();
		}

		return null;
	}

	/**
	 * @param jogosAtuais
	 * @param jogosCorrentes
	 * @param count
	 * @return
	 */
	protected static List<Integer> getFlat(String jogosAtuais, String jogosCorrentes, int count) {

		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);
		if (count % 4 == 0) {
			jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosCorrentes);
		} else if (count % 4 == 1 || count % 4 == 3) {
			jogosAtuaisFlat.addAll(ArquivoUtil.obterLinhasComoLista(jogosCorrentes));
		}
		return jogosAtuaisFlat;
	}

	/**
	 * @param config
	 * @param listaJogosAtuais
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> getJogosAtuais(LotofacilConfig config,
			List<List<Integer>> listaJogosAtuais) throws IOException {
		String m = "1. Arquivo %s, tamanho %s";
		m = String.format(m, config.getCaminhoJogoAtual(), listaJogosAtuais.size());
		System.out.println(m);

		listaJogosAtuais
		.addAll(ArquivoUtil.obterLinhasComoListasUnique(config.getCaminhoJogoAtual()));
		m = "2. Arquivo %s, tamanho %s";
		m = String.format(m, config.getCaminhoJogoAtual(), listaJogosAtuais.size());
		System.out.println(m);

		listaJogosAtuais = new ArrayList<>(new HashSet<>(listaJogosAtuais));

		m = "3. Arquivo %s, tamanho %s";
		m = String.format(m, config.getCaminhoJogoAtual(), listaJogosAtuais.size());
		System.out.println(m);

		ArquivoUtil.saveLists(listaJogosAtuais, config.getCaminhoJogoAtual(), "\t", 2);
		return listaJogosAtuais;
	}

	protected static void getJogosCorrentes(LotofacilConfig config,
			List<List<Integer>> listaJogosAtuais, List<List<Integer>> listaJogosCorrentes)
					throws IOException {

		List<List<Integer>> novos = new LinkedList<>();
		List<List<Integer>> repetidos = new LinkedList<>();

		String pastaAtual = config.getCaminhoPastaAtual();

		File f = new File(pastaAtual);
		if (f != null && f.exists() && f.isDirectory()) {
			File[] files = f.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith("txt");
				}
			});
			String m = null;
			if (files != null && files.length > 0) {
				for (File currFile : files) {
					List<List<Integer>> curr = ArquivoUtil
							.obterLinhasComoListasUnique(currFile.getAbsolutePath());
					if (curr != null && !curr.isEmpty()) {

						curr = new ArrayList<>(new HashSet<>(curr));
						ArquivoUtil.saveLists(curr, currFile.getAbsolutePath(), "\t", 2);

						m = ">>>> Arquivo %s, tamanho %s";
						m = String.format(m, currFile.getAbsolutePath(), curr.size());
						System.out.println(m);
						listaJogosAtuais.addAll(curr);
						m = "<<<< Lista Jogos atuais tamanho %s";
						m = String.format(m, listaJogosAtuais.size());
						System.out.println(m);

						if (!currFile.getName().startsWith("_")) {
							for (List<Integer> c : curr) {
								if (!listaJogosCorrentes.contains(c)) {
									listaJogosCorrentes.add(c);
									novos.add(c);
								} else {
									repetidos.add(c);
								}
							}
						}
					}
				}
			}
		}

		ArquivoUtil.saveLists(novos, config.getBasePath() + "novos.txt", "\t", 2);
		ArquivoUtil.saveLists(repetidos, config.getBasePath() + "repetidos.txt", "\t", 2);
	}

	protected static List<Integer> getLast(List<List<Integer>> curr) {
		if (isNotEmpty(curr)) {
			return curr.get(curr.size() - 1);
		}
		return Collections.emptyList();
	}

	protected static List<List<Integer>> getListaMax() {
		String pathListaMax = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LISTA_MAX.txt";
		return ArquivoUtil.obterLinhasComoListasUnique(pathListaMax);
	}

	protected static int getMaximoRepetidosLista(int count, LotofacilConfig config) {
		int qttNumerosApostados = config.getNrosApostados();
		int n1 = qttNumerosApostados - 4;
		int n2 = n1 + 1;
		int n3 = n1 + 2;
		int n4 = n1 + 3;

		count = count % 6;

		if (count == 1) {
			return n1;
		}

		if (count == 3) {
			return n3;
		}

		if (count == 5) {
			return n4;
		}

		return n2;
	}

	/**
	 * @param config
	 * @param topN
	 * @return
	 */
	protected static int getTopN(LotofacilConfig config, int topN) {
		try {
			topN = Math.min(new Random().nextInt(topN * 3) + 1000, config.getQttInicial() / 5);
		} catch (Exception e) {
			System.out.println("Top N recalculado: " + e.getMessage());
			topN = 100;
		}
		return topN;
	}

	public static void init() {
		String pathCorr = config.getCaminhoJogoCorrente();
		String pathAtual = config.getCaminhoJogoAtual();

		listaJogosCorrentes = new LinkedList<>(ArquivoUtil.obterLinhasComoListasUnique(pathCorr));

		listaJogosAtuais = new LinkedList<>(ArquivoUtil.obterLinhasComoListas(pathAtual));
	}

	protected static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	/**
	 * @param jogos
	 * @return
	 */
	protected static StringBuilder printOrdenado(List<List<Integer>> jogos) {
		System.out.println("Ordenado");
		Collector<CharSequence, ?, String> clt = Collectors.joining("\t");
		StringBuilder str = new StringBuilder();
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo);
			String atual = jogo.stream().map(Object::toString).collect(clt);
			System.out.println(atual);
			str.append(atual);
		}
		return str;
	}

	protected static void save(List<List<Integer>> lista, String path) throws IOException {
		if (lista != null && path != null) {
			ArquivoUtil.saveLists(lista, path, ", ", 2);
		}
	}


	protected static void saveAll(LotofacilConfig config, List<List<Integer>> somenteNovos,
			List<List<Integer>> listaJogosAtuais, List<List<Integer>> listaJogosCorrentes,
			String sn, int count, Set<Integer> listaExcl, StringBuilder str,
			List<List<Integer>> jogosOut) throws IOException {

		String jogosAtuais = config.getCaminhoJogoAtual();
		String jogosCorrentes = config.getCaminhoJogoCorrente();
		String destino = config.getFullPath();

		save(jogosOut, destino);
		save(listaJogosCorrentes, jogosCorrentes);
		save(listaJogosAtuais, jogosAtuais);

		String jc = String.valueOf(count + 1);
		if (str != null) {
			String sf = "Concurso: %s, ";
			System.out.println(String.format(sf + "Jogos Correntes: ", jc, jogosCorrentes));
			System.out.println(
					String.format(sf + "Não participaram dessa rodada: %s", jc, listaExcl));
			System.out.println(String.format(sf + "Meu Jogo Milion�rio: %s", jc, str.toString()));
		}

		System.out.println("\n\n\n");

		try {
			String mc = config.getFrequencia(listaJogosCorrentes);
			ArquivoUtil.save(mc, config.getBasePath() + "correntes.txt");
			String ma = config.getFrequencia(listaJogosAtuais);
			ArquivoUtil.save(ma, config.getBasePath() + "atuais.txt");
			List<List<Integer>> mixLista = new ArrayList<>();
			mixLista.addAll(listaJogosCorrentes);
			mixLista.addAll(listaJogosAtuais);
			String mix = config.getFrequencia(mixLista);
			ArquivoUtil.save(mix, config.getBasePath() + "mix.txt");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		ArquivoUtil.saveLists(somenteNovos, sn, ",", 2);
	}

	/**
	 * @param config
	 * @param resultados
	 * @throws IOException
	 */
	protected static void saveMapAtraso(LotofacilConfig config, List<List<Integer>> resultados)
			throws IOException {
		Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
		ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");
	}

	/**
	 * @param config
	 * @param resultados
	 * @param ultimos
	 * @throws IOException
	 */
	protected static void saveMapResultado(LotofacilConfig config, List<List<Integer>> resultados,
			int ultimos) throws IOException {
		if (ultimos <= 10) {
			ultimos = resultados.size();
		}

		Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
		ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");
	}

}
