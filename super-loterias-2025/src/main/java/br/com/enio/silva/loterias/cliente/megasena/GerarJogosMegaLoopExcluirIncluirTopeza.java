package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosMegaLoopExcluirIncluirTopeza extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 13;

		int[] ultimosJogos = { 0, 0, 50, 100, 0, 20, 75, 150, 0, 260, 1000, 0 };

		//		int[] ultimosJogos = {0, 10, 13, 26};

		boolean includeTop = false;
		int qttInicialFixo = 50000;

		int tamPadraoExcluir = 0;
		int tamPadraoIncluir = 1;

		int seedExcluir = 15;
		int seedIncluir = 3;

		MegaSenaConfigAb config = getConfig(0);

		String sep = ", ";

		// int minPercent = 10;
		// int maxPercent = 20;

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "todos_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUniqueDesdobrado(pathListaDeJogos, config.getNrosApostados());

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil.obterLinhasComoListasUniqueDesdobrado(
				pathListaJogosCorrentes, config.getNrosApostados());

		listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais, 6);
		listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosCorrentes, 6);

		System.out.println(listaJogosAtuais);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
		ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, sep, 2);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();
		Set<List<Integer>> top = new HashSet<>();

		String mcb = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		List<Integer> last = null;

		int magicNumber = random.nextInt(100);

		listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais,
				config.getNrosApostados());

		listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosCorrentes,
				config.getNrosApostados());

		for (int count = 0; count < totalDeJogos; count++) {

			includeTop = includeTop && new Random().nextBoolean();

			config = getConfig(count);

			int ultimos = ultimosJogos[(count + magicNumber) % ultimosJogos.length];
			ultimos = ultimos < 10 || ultimos > resultados.size() - 1 ? resultados.size()
					: ultimos;
			List<List<Integer>> ultimosResultados = config.getTodosResultados();
			Collections.reverse(ultimosResultados);
			ultimosResultados = ultimosResultados.subList(0, ultimos);

			try {
				// int maxRepeditosMeusJogos = getMrmj(config);
				// int maxRepeditosResultados = getMrmj(config,
				// maxRepeditosMeusJogos) + 1;

				int var1 = magicNumber + count;
				int var2 = magicNumber + count * 3;
				tamPadraoIncluir = seedIncluir - var1 % seedIncluir;
				tamPadraoExcluir = seedExcluir + var2 % seedIncluir;

				int maxRepeditosMeusJogos = 5 - var2 % 3;
				int maxRepeditosResultados = 5 - var1 % 2;

				config.setQttInicial(qttInicialFixo);

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove);
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				String jogosAtuais = config.getCaminhoJogoAtual();

				List<Integer> jogosAtuaisFlat = getFlatMega(pathListaJogosCorrentes, count,
						jogosAtuais);

				Set<Integer> listaExcl = new HashSet<>();
				Set<Integer> listaIncl = new HashSet<>();

				Map<Integer, Integer> mapFrequencia = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
						60);
				Map<Integer, Integer> map = MapUtil.sortByValueDesc(mapFrequencia);
				int lastValue = 0;
				int currValue = 0;
				for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
					if (listaExcl == null) {
						listaExcl = new HashSet<>();
						lastValue = entry.getValue();
					}
					currValue = entry.getValue();
					if (listaExcl.size() <= tamPadraoExcluir || lastValue == currValue) {
						listaExcl.add(entry.getKey());
						lastValue = currValue;
					}
				}
				listaIncl = gerarIncluirMinimo(config, tamPadraoIncluir, jogosAtuaisFlat);
				if (count % 2 == 1) {
					listaExcl = gerarExcluirMaximo(config, tamPadraoExcluir, jogosAtuaisFlat);
				}

				if (last != null && last.isEmpty()) {
					listaExcl.addAll(last);
					listaIncl.removeAll(last);
				}

				System.out.println(repeated50);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				listaIncl.removeAll(listaExcl);
				listaExcl.removeAll(listaIncl);

				if (listaExcl.size() > 30) {
					List<Integer> tmp = new ArrayList<>(listaExcl);
					Collections.shuffle(tmp);
					tmp = tmp.subList(0, 13);
					listaExcl = new HashSet<>(tmp);
					listaIncl = new HashSet<>();
				}

				int max = 0;
				int min = 0;
				for (Integer i : listaIncl) {
					int val = i.intValue();
					if (max == 0) {
						min = val;
						max = val;
					} else if (val > max) {
						max = val;
					} else if (val < min) {
						min = val;
					}
				}

				int diff = max - min;
				int listDiff = (listaIncl.size() - 1) * 4;
				if (diff > 0 && diff < listDiff) {
					System.out.println("dist: " + diff + "\t" + listDiff);
					listaIncl = new HashSet<>();
				}

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				if (includeTop) {
					for (List<Integer> t : top) {
						@SuppressWarnings("unchecked")
						List<Integer> inter = ListUtils.intersection(t, excluir);
						if (inter == null || inter.isEmpty()) {
							preJogos.add(new ArrayList<>(t));
						}
					}
				}

				List<List<Integer>> fullList = new ArrayList<>();
				fullList.addAll(listaJogosAtuais);
				fullList.addAll(listaJogosCorrentes);
				fullList.addAll(somenteNovos);
				fullList.addAll(resultados);

				preJogos.removeAll(fullList);

				FiltroIf filtro = null;

				if (isFiltrarAtual(preJogos)) {
					filtro = new FiltroRemoverIntersecao(remover);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos)) {
					if (maxRepeditosMeusJogos < 5) {
						// Filtrar pela lista de jogos correntes
						filtro = new FiltroMaxLista(listaJogosAtuais, maxRepeditosMeusJogos);
						preJogos = filtro.filtrarListas(preJogos);
					}
				}

				if (isFiltrarAtual(preJogos) && new Random().nextBoolean()) {
					if (maxRepeditosResultados < 5) {
						// Filtrar por resultados anteriores
						filtro = new FiltroMaxLista(resultados, maxRepeditosResultados);
						preJogos = filtro.filtrarListas(preJogos);
					}
				}

				if (isFiltrarAtual(preJogos)) {
					int maxRep = 2 + (count % 4 == 0 ? 1 : 0);
					filtro = new FiltroMaxConsecutivos(maxRep);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos)) {
					int maxLinhas = 2 + count % 3;
					filtro = new FiltroMaximoLinhas(6, 10, maxLinhas);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos) && isNotEmpty(last)) {
					filtro = new FiltroUltimoSorteio(0, 1, last);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (preJogos != null && !preJogos.isEmpty()) {

					List<JogoMega> jogosLM = new ArrayList<JogoMega>();
					JogoMega jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoMega(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(ultimosResultados, meuJogo);
					}

					if (random.nextBoolean() && random.nextBoolean()) {
						Collections.shuffle(jogosLM);
					} else {
						Collections.sort(jogosLM);
					}

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
						listaJogosAtuais.add(novo);
						listaJogosCorrentes.add(novo);
						somenteNovos.add(novo);
					}

					for (List<Integer> jogo : jogos) {
						Collections.replaceAll(jogo, 0, 0);
						Collections.sort(jogo);
					}

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo);
					}

					listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais,
							config.getNrosApostados());

					listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosCorrentes,
							config.getNrosApostados());

					listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

					int size = somenteNovos.size();
					somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
					if (somenteNovos.size() < size) {
						count = count - (size - somenteNovos.size());
					}

					ArquivoUtil.saveLists(jogos, output, sep, 2);
					ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, sep, 2);
					ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
					ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);

					String mc = config.getFrequencia(listaJogosCorrentes);
					ArquivoUtil.save(mc,
							CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
					String ma = config.getFrequencia(listaJogosAtuais);
					ArquivoUtil.save(ma, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

					saveOrdered(pathListaDeJogos, listaJogosAtuais, pathListaJogosCorrentes,
							listaJogosCorrentes);

				} else {

					for (int i = 0; i < 6; i++) {
						System.out.println("Não há resultados com os filtros aplicados");
						Thread.sleep(500);
					}
					count--;
				}
			} catch (Exception e) {
				e.printStackTrace();
				count--;
				ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, sep, 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
				ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
			}
		}

	}

	/**
	 * @param pathListaDeJogos
	 * @param listaJogosAtuais
	 * @param pathListaJogosCorrentes
	 * @param listaJogosCorrentes
	 * @throws IOException
	 */
	private static void saveOrdered(String pathListaDeJogos, List<List<Integer>> listaJogosAtuais,
			String pathListaJogosCorrentes, List<List<Integer>> listaJogosCorrentes)
					throws IOException {
		listaJogosAtuais.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, ", ", 2);
		listaJogosCorrentes.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, ", ", 2);
	}

}
