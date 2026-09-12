package br.com.enio.silva.loterias.cliente.bingodasorte.newp;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public abstract class GerarJogosQuinaBingoAb extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	protected static int varMin = 0;

	protected static int varMax = 0;

	/**
	 * @param c
	 * @param perc
	 * @param tamExcluir
	 * @param jogosAtuais
	 * @return
	 */
	protected static Set<Integer> gerarExcluirAcimaMax(QuinaConfigAb c, int perc, int tamExcluir,
			List<Integer> jogosAtuais) {
		int tamExcl = 0;
		Set<Integer> listaExcl = new HashSet<>(Arrays.asList());

		int parMax = (jogosAtuais.size() / c.getNrosApostados() * perc / 100) + varMax;

		if (parMax > 0) {
			for (int i = 1; i <= 25; i++) {
				int qttEl = Collections.frequency(jogosAtuais, i);
				if (qttEl > parMax) {
					listaExcl.add(i);
				}
			}

			tamExcl = listaExcl.size();
			System.out.println(">>> Números excluídos(" + varMax + "):" + listaExcl);
			if (tamExcl > tamExcluir) {
				List<Integer> excl = new ArrayList<>(listaExcl);
				Collections.shuffle(excl);
				excl = excl.subList(0, tamExcluir);
				listaExcl = new HashSet<>(excl);
				varMax = Math.min(varMax++, 0);
			} else {
				varMax = varMax - 2;
			}
			System.out.println("");
		}
		return listaExcl;
	}

	protected static Set<Integer> gerarExcluirMax(QuinaConfigAb config, int tamPadraoIncluir,
			List<Integer> jogosAtuaisFlat) {

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValueDesc(map);

		return getSublistaMap(map, tamPadraoIncluir);
	}

	protected static Set<Integer> gerarExcluirMaximo(QuinaConfigAb config, int tamPadraoExcluir,
			List<Integer> jogosAtuaisFlat) {

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValueDesc(map);

		return getSublistaMap(map, tamPadraoExcluir);
	}

	/**
	 * @param config
	 * @param minPercent
	 * @param tamPadraoIncluir
	 * @param jogosAtuaisFlat
	 * @return
	 */
	protected static Set<Integer> gerarIncluirAbaixoMinimo(QuinaConfigAb config, int minPercent,
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

	protected static Set<Integer> gerarIncluirMinimo(QuinaConfigAb config, int tamPadraoIncluir,
			List<Integer> jogosAtuaisFlat) {

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValue(map);

		return getSublistaMap(map, tamPadraoIncluir);
	}

	/**
	 * @param pathListaJogosCorrentes
	 * @param count
	 * @param jogosAtuais
	 * @return
	 */
	protected static List<Integer> getFlatQuina(String pathListaJogosCorrentes, int count,
			String jogosAtuais) {

		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);
		if(count % 2 == 0) {
			jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(pathListaJogosCorrentes);
		}
		return jogosAtuaisFlat;
	}

	/**
	 * @param config
	 * @param listaJogosAtuais
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> getJogosAtuais(QuinaConfigAb config,
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

		ArquivoUtil.saveLists(listaJogosAtuais, config.getCaminhoJogoAtual(), "\t");
		return listaJogosAtuais;
	}

	/**
	 * @param config
	 * @param listaJogosAtuais
	 * @param listaJogosCorrentes
	 * @param novos
	 * @param repetidos
	 * @throws IOException
	 */
	protected static void getJogosCorrentes(QuinaConfigAb config,
			List<List<Integer>> listaJogosAtuais, List<List<Integer>> listaJogosCorrentes)
					throws IOException {

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
						ArquivoUtil.saveLists(curr, currFile.getAbsolutePath(), "\t");

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
								}
							}
						}
					}
				}
			}
		}
	}

	protected static int getMrmj(QuinaConfigAb config) {
		int max = config.getNrosApostados() - 2;

		List<Integer> lista = new ArrayList<>();
		for (int i = 1; i < max; i++) {
			int incluir = i + 1;
			lista.add(incluir);
			for (int j = i; j < max; j++) {
				lista.add(i);
			}
		}
		Collections.shuffle(lista);
		Collections.shuffle(lista);
		Collections.shuffle(lista);
		System.out.println("getMrmj: " + lista);
		return lista.get(0);
	}

	protected static int getMrmj(QuinaConfigAb config, int mrmj) {
		int max = config.getNrosApostados() - 1;
		int min = (int) Math.ceil((max + mrmj + 1) / 2);
		String msg = String.format("getMrmj(%s, %s, %s)", mrmj, max, min);
		System.out.println("Máximo Repetidos: " + msg);
		return min;
	}

	protected static Set<Integer> getSublistaMap(Map<Integer, Integer> map, int size) {
		Set<Integer> resultado = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			resultado.add(entry.getKey());
			if (resultado.size() >= size) {
				return resultado;
			}
		}
		return resultado;
	}

}
