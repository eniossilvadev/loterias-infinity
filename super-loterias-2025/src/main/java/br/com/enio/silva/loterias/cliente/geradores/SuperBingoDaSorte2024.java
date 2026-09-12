package br.com.enio.silva.loterias.cliente.geradores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.quina.QuinaConfig10;
//import br.com.enio.silva.loterias.config.quina.QuinaConfig5;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesQuina;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

/**
 * QuinaQuadranteBaseN
 *
 * @author enios
 *
 */
public class SuperBingoDaSorte2024 extends Base {

	public static final int QUANTIDADE_DE_JOGOS = 4; // vezes 3

	public static final int DEFAULT_SIZE = 10;

	public static final int shif = 1;

	public static final int positional = 1;

	public static int maxTries = 50;

	public static final String CONFIG_PATH = "E:\\loterias\\bingo-da-sorte\\config\\";

	public static final String CURR_PATH = "E:\\loterias\\bingo-da-sorte\\curr\\";

	public static final QuinaConfigAb quinaConfig = new QuinaConfig10();

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static List<List<Integer>> execute(final int totalDeJogos) throws IOException {
		int maxListaBase = 2;
		int defaultSize = 10;
		int qttInicialFixo = 50000;
		return execute(totalDeJogos, maxListaBase, defaultSize, qttInicialFixo);
	}

	public static List<List<Integer>> execute(final int totalDeJogos, final int maxListaBase,
			final int defaultSize, final int qttInicialFixo) throws IOException {

		List<List<Integer>> retorno = new ArrayList<>();

		int[] ultimosJogos = { 0, 50, 0, 100, 0, 260, 0, 500, 0, 1000 };

		final String sep = ",";

		final int tamanhoIncluirLista = 4;
		// final int tamPadraoExcluir = 3;

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

		String base = CONFIG_PATH;

		List<List<Integer>> full = LotoUtils.getAll(CURR_PATH, defaultSize);
		List<List<Integer>> listaJogosAtuais = new ArrayList<>(full);
		List<List<Integer>> listaJogosCorrentes = new ArrayList<>(full);

		String fileName = "E:\\loterias\\bingo-da-sorte\\curr\\vip.txt";
		List<List<Integer>> listaDaSorte = ArquivoUtil.obterLinhasComoListasUnique(fileName);
		Collections.shuffle(listaDaSorte);

		System.out.println(listaJogosAtuais);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNQ.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random r = new Random();

		int currTry = 0;

		for (int count = 0; count < totalDeJogos && currTry < maxTries; count++) {

			QuinaConfigAb config = quinaConfig;

			List<List<Integer>> ultimosResultados = config.getTodosResultados();
			Collections.reverse(ultimosResultados);

			int ultimos = ultimosJogos[count % ultimosJogos.length];
			ultimos = ultimos <= 10 || ultimos > ultimosResultados.size() - 1
					? ultimosResultados.size()
							: ultimos;

					ultimosResultados = ultimosResultados.subList(0, ultimos);

					try {

						Set<Integer> listaIncl = new HashSet<>(listaInclFixa);

						config.setQttInicial(qttInicialFixo);

						if (qttInicialFixo > 0) {
							config.setQttInicial(qttInicialFixo);
						}

						String output = base + "individual\\" + config.getDefaultName() + ".txt";

						String remove = config.getCaminhoJogoAtual();
						List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
						if (count == 0) {
							ArquivoUtil.saveLists(remover, remove, sep);
						}

						System.out.println(repeated50);

						List<Integer> listaAtual = listaDaSorte
								.get(Math.abs(r.nextInt()) % listaDaSorte.size());
						Collections.shuffle(listaAtual);

						int currIncluir = Math.min(listaAtual.size(), tamanhoIncluirLista);

						listaIncl.addAll(new ArrayList<>(listaAtual.subList(0, currIncluir)));

						Collections.reverse(listaAtual);

						listaAtual.removeAll(listaIncl);

						// Set<Integer> listaExcl = new HashSet<>(listaAtual.subList(0,
						// tamPadraoExcluir));
						Set<Integer> listaExcl = new HashSet<>(listaAtual);

						listaExcl.removeAll(listaIncl);

						System.out.println("Números excluídos: " + listaIncl);
						System.out.println("Números incluídos: " + listaExcl);

						if (listaIncl != null && !listaIncl.isEmpty()) {
							Integer[] myArray = new Integer[listaIncl.size()];
							listaIncl.toArray(myArray);
							config.setPre(myArray);
						}
						List<List<Integer>> preJogos = config.getPreJogos();

						List<Integer> listaIncluirFinal = new ArrayList<>(listaIncl);

						List<Integer> listaExcluirFinal = new ArrayList<>(listaExcl);

						int tamanhoDoJogo = config.getNrosApostados();
						preJogos = QuadrantesQuina.generateList(qttInicialFixo, tamanhoDoJogo,
								listaExcluirFinal, listaIncluirFinal, currIncluir);

						preJogos.removeAll(listaJogosAtuais);


						FiltroIf filtro = null;

						filtro = new FiltroRemoverIntersecao(remover);
						preJogos = filtro.filtrarListas(preJogos);

						if (!preJogos.isEmpty()) {
							filtro = new FiltroMaxConsecutivos(3);
							preJogos = filtro.filtrarListas(preJogos);
						}

						if (!preJogos.isEmpty()) {
							filtro = new FiltroMaximoLinhas(5, 10, 4);
							preJogos = filtro.filtrarListas(preJogos);
						}

						if (preJogos != null && preJogos.size() > 0) {

							List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
							JogoQuina jlm = null;
							for (List<Integer> pj : preJogos) {
								jlm = new JogoQuina(pj);
								jogosLM.add(jlm);
							}

							for (JogoAb meuJogo : jogosLM) {
								config.getPontuador().pontuar(ultimosResultados, meuJogo);
							}

							Collections.sort(jogosLM);
							if (random.nextBoolean() && random.nextBoolean()) {
								Collections.reverse(jogosLM);
							}

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

							ArquivoUtil.saveLists(jogos, output, sep, 2);
							ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);

							String mc = config.getFrequencia(listaJogosCorrentes);
							ArquivoUtil.save(mc, base + "correntes.txt");
							String ma = config.getFrequencia(listaJogosAtuais);
							ArquivoUtil.save(ma, base + "atuais.txt");

							retorno = new ArrayList<>(somenteNovos);

						} else {

							System.out.println("Não há resultados com os filtros aplicados");
							Thread.sleep(2000);
							count--;
							currTry++;
						}
					} catch (Exception e) {
						e.printStackTrace();
						count--;
						ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
						currTry++;
					}
		}
		return retorno;
	}

	public static void main(String[] args) throws IOException {

		final int quantidade = QUANTIDADE_DE_JOGOS; // vezes 3

		final int max = 80;
		final int size = DEFAULT_SIZE;
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = CURR_PATH;
		String basePathName = basePath + "01_" + currDate + "_quina_da_sorte_";
		String finalCleanListPath = basePathName + currDateTime + ".txt";
		List<List<Integer>> finalList = new ArrayList<>();

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		List<List<Integer>> mainList = execute(quantidade);
		print(mainList, "Main");
		finalList.addAll(mainList);

		for (int i = 0; i < positional; i++) {
			List<List<Integer>> positionalList = PositionalReplacement
					.getPositionalReplacement(mainList, max);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		for (int i = 0; i < shif; i++) {
			List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, max);
			print(shiftedList, "Shifed");
			finalList.addAll(shiftedList);
		}

		print(finalList, "Final -> Before");
		Collections.sort(finalList, new ListOfListComparator());
		print(finalList, "Final -> After");

		List<List<Integer>> cleanList = new ArrayList<>(finalList);
		cleanList = ListaUtils.removerDaLista(cleanList, listaRemover);
		cleanList = CollectionsUtils.removeDuplicated(cleanList);
		print(cleanList, "Clean List");

		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);
	}

}
