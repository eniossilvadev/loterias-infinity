package br.com.enio.silva.loterias.cliente.geradores.mega20;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.cliente.megasena.GerarJogosMegaLoopAb;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigSuper20;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class MegaLoopExcluirIncluirTopezaSuper20 extends GerarJogosMegaLoopAb {

	public static final int DEFAULT_SIZE = 20;

	public static final String BASE_PATH = "E:\\loterias\\bingo-da-sorte\\_mega_20_\\";

	public static List<List<Integer>> execute(final int totalDeJogos) throws IOException {

		int qttInicialFixo = 50000;
		final int qttOriginal = qttInicialFixo;

		int tamPadraoExcluir = 1;
		int tamPadraoIncluir = 5;

		final int defaultSize = 20;

		return execute(totalDeJogos, qttInicialFixo, qttOriginal, tamPadraoExcluir,
				tamPadraoIncluir, defaultSize);
	}

	public static List<List<Integer>> execute(final int totalDeJogos, final int qttInicialFixo,
			final int qttOriginal, final int tamPadraoExcluir, final int tamPadraoIncluir,
			final int defaultSize) throws IOException {

		List<List<Integer>> retorno = new ArrayList<>();

		MegaSenaConfigAb config = new MegaSenaConfigSuper20();
		Random rand = new Random();

		List<Integer> theList = Arrays.asList();
		Collections.shuffle(theList);

		String sep = ",";

		List<List<Integer>> listaJogosAtuais = new ArrayList<>();

		List<List<Integer>> listaJogosCorrentes = new ArrayList<>();

		List<List<Integer>> full = LotoUtils.getAll("E:\\loterias\\mega_sena\\curr2", defaultSize);
		listaJogosAtuais.addAll(full);
		listaJogosCorrentes.addAll(full);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais, defaultSize);
		listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosCorrentes, defaultSize);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = BASE_PATH + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		String mcb = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		int magicNumber = rand.nextInt(1000);

		theSep = sep;

		for (int count = 0; count < totalDeJogos; count++) {

			config.setQttInicial(qttOriginal);

			try {

				System.out.println("Magic Number: " + magicNumber);
				magicNumber++;

				config.setQttInicial(qttInicialFixo);

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				int tamanhoIncluirLista = tamPadraoIncluir;
				Set<Integer> listaIncl = new HashSet<>();

				String jogosAtuais = config.getCaminhoJogoAtual();

				List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);

				List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1,
						config.getMaxNum());

				listaIncl.addAll(listaOrdenada.subList(0, tamanhoIncluirLista));

				Collections.reverse(listaOrdenada);
				Set<Integer> listaExcl = new HashSet<>(listaOrdenada.subList(0, tamPadraoExcluir));

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (last != null) {
					Collections.shuffle(lasts);
					lasts = lasts != null && lasts.size() > 11 ? lasts.subList(0, 8) : lasts;
					lasts.addAll(last);
					lasts = new ArrayList<>(new HashSet<>(lasts));
					listaExcl.addAll(lasts);
					listaIncl.removeAll(lasts);
				}

				System.out.println(repeated50);

				System.out.println("Last: " + last);
				System.out.println("Lasts: " + lasts);

				lasts = new ArrayList<>(new HashSet<>(lasts));

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				int n = config.getMaxNum() - tamPadraoExcluir;
				int r = config.getNrosApostados() - tamPadraoIncluir;
				int totalCombinacoes = CombinationUtils.calcNumberOfCombinations(n, r).intValue();
				if (config.getQttInicial() > totalCombinacoes && totalCombinacoes > 0) {
					System.out.println("Novo máximo número de jogos: " + totalCombinacoes);
					config.setQttInicial(totalCombinacoes);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				List<List<Integer>> fullList = new ArrayList<>();
				fullList.addAll(somenteNovos);

				preJogos.removeAll(fullList);

				FiltroIf filtro = null;

				filtro = new FiltroRemoverIntersecao(fullList);
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && !preJogos.isEmpty()) {

					List<JogoMega> jogosLM = new ArrayList<JogoMega>();
					JogoMega jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoMega(pj);
						jogosLM.add(jlm);
					}

					System.out.println("Pontuando...");
					final Pontuador pontuador = config.getPontuador();
					final List<List<Integer>> results = new ArrayList<>(resultados);
					for (final JogoAb meuJogo : jogosLM) {
						pontuador.pontuar(results, meuJogo);
					}
					System.out.println("Fim da pontuação!!!");

					Collections.sort(jogosLM);

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
						listaJogosAtuais.add(novo);
						listaJogosCorrentes.add(novo);
						somenteNovos.add(novo);
						last = new ArrayList<>(novo);
						lasts.addAll(last);
					}

					for (List<Integer> jogo : jogos) {
						Collections.replaceAll(jogo, 0, 0);
						Collections.sort(jogo);
					}

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo + "\t" + listaIncl + "\t" + listaExcl);
					}

					List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
					String mc = config.getFrequencia(statC);
					ArquivoUtil.save(mc,
							CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");

					List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
					String ma = config.getFrequencia(statA);
					ArquivoUtil.save(ma, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

					retorno = new ArrayList<>(somenteNovos);

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
				ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
			}
		}
		return retorno;

	}

	public static void main(String[] args) throws IOException {

		final int totalDeJogos = 1;

		final int quantidade = totalDeJogos; //

		final int size = DEFAULT_SIZE;
		final int max = 60;
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = BASE_PATH;
		String basePathName = basePath + "01_" + currDate + "_mega_sorte_20_";
		String finalListPath = basePathName + currDateTime + "_final.txt";
		String finalCleanListPath = basePathName + currDateTime + ".txt";

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		List<List<Integer>> mainList = execute(quantidade);
		print(mainList, "Main");

		// List<List<Integer>> positionalList = PositionalReplacement
		// .getPositionalReplacement(mainList, max);
		// print(positionalList, "Positional");

		List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, max);
		print(shiftedList, "Shifed");

		List<List<Integer>> finalList = new ArrayList<>();
		finalList.addAll(mainList);
		// finalList.addAll(positionalList);
		finalList.addAll(shiftedList);

		print(finalList, "Final -> Before");
		Collections.sort(finalList, new ListOfListComparator());
		print(finalList, "Final -> After");

		List<List<Integer>> cleanList = new ArrayList<>(finalList);
		cleanList = ListaUtils.removerDaLista(cleanList, listaRemover);
		print(cleanList, "Clean List");

		saveDefault(finalList, finalListPath, size);
		saveDefault(cleanList, finalCleanListPath, size);

	}

}
