package br.com.enio.silva.loterias.cliente.geradores.quina;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.quina.*;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesQuina;
import br.com.enio.silva.loterias.filtro.*;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

import java.io.IOException;
import java.util.*;

public class QuinaQuadranteBase2025N extends Base {

	public static final int QUANTIDADE_DE_JOGOS = 50; // vezes 3
	public static final int shift = 1;
	public static final int positional = 1;

//	public static final QuinaConfigAb quinaConfig = new QuinaConfig7();
	public static final QuinaConfigAb quinaConfig = new QuinaConfig5();


	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static String basePath = "C:\\loterias\\gerador-apostas\\quina\\meus-jogos\\";

	public static List<List<Integer>> execute(final int totalDeJogos) throws IOException {
		int maxListaBase = 2;
		int defaultSize = quinaConfig.getDefaultSize();
		int qttInicialFixo = 26_000;
		int tamPadraoExcluir = 15;
		return execute(totalDeJogos, maxListaBase, defaultSize, qttInicialFixo, tamPadraoExcluir);
	}

	public static List<List<Integer>> execute(final int totalDeJogos, final int maxListaBase,
			final int defaultSize, final int qttInicialFixo, final int tamPadraoExcluir)
					throws IOException {

		List<List<Integer>> retorno = new ArrayList<>();

		int[] ultimosJogos = { 0, 50, 0, 100, 0, 260, 0, 500, 0, 1000 };

		List<Integer> theList = Arrays.asList();
		final String sep = ",";

		int tamanhoIncluirLista = theList.size();

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

		String base = "C:\\loterias\\gerador-apostas\\quina\\config\\";

		String pathListaDeJogos = base + "meu_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		List<List<Integer>> full = LotoUtils.getAll(basePath, defaultSize);
		listaJogosAtuais.addAll(full);
		listaJogosCorrentes.addAll(full);

		System.out.println(listaJogosAtuais);

		String path = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String pathBkpCorrentes = base + "/bkp/" + currDate + "/" + "bkp_" + currDateTime
				+ "_c.txt";
		String pathBkpAtuais = base + "/bkp/" + currDate + "/" + "bkp_" + currDateTime + "_a.txt";

		saveDefault(listaJogosCorrentes, pathBkpCorrentes, defaultSize);
		saveDefault(listaJogosAtuais, pathBkpAtuais, defaultSize);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNQ.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();
		Set<List<Integer>> top = new HashSet<>();

		Random rand = new Random();
		int magicNumber = rand.nextInt(1000);

		saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
		saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);




		for (int count = 0; count < totalDeJogos; count++) {

			List<Integer> listaBase = ListaUtils.getElements(1, 80, maxListaBase);

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
						if (theList != null && !theList.isEmpty()) {
							int el = magicNumber % theList.size();
							int sorte = theList.get(el);
							listaIncl.add(sorte);

							System.out.println("el: " + el + "\t" + sorte);
						}

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

						String jogosAtuais = config.getCaminhoJogoAtual();

						List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);

						List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1,
								config.getMaxNum());

						listaIncl.addAll(listaOrdenada.subList(0, tamanhoIncluirLista));

						Collections.reverse(listaOrdenada);

						listaOrdenada.removeAll(listaIncl);
						listaOrdenada.removeAll(listaBase);

						Set<Integer> listaExcl = new HashSet<>(listaOrdenada.subList(0, tamPadraoExcluir));

						listaExcl.removeAll(listaIncl);

						System.out.println("Números excluídos: " + listaExcl);
						System.out.println("Números incluídos: " + listaIncl);

						if (!listaIncl.isEmpty()) {
							Integer[] myArray = new Integer[listaIncl.size()];
							listaIncl.toArray(myArray);
							config.setPre(myArray);
						}
						List<List<Integer>> preJogos = config.getPreJogos();

						List<Integer> excluir = config.getExcluir();

						int tamanhoDoJogo = config.getNrosApostados();
						preJogos = QuadrantesQuina.generateList(qttInicialFixo, tamanhoDoJogo, excluir,
								listaBase, maxListaBase);
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

						filtro = new FiltroQuantidadeDaLista(maxListaBase, listaBase);
						preJogos = filtro.filtrarListas(preJogos);

						if (preJogos != null && !preJogos.isEmpty()) {

							List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
							JogoQuina jlm = null;
							for (List<Integer> pj : preJogos) {
								jlm = new JogoQuina(pj);
								jogosLM.add(jlm);
							}


							Pontuador pontuador = config.getPontuador();
							for (JogoAb meuJogo : jogosLM) {
								pontuador.pontuar(ultimosResultados, meuJogo);
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

							saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
							saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

							ArquivoUtil.saveLists(jogos, output, sep, 2);
							ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);

//							String mc = config.getFrequencia(listaJogosCorrentes);
//							ArquivoUtil.save(mc, base + "correntes.txt");
//							String ma = config.getFrequencia(listaJogosAtuais);
//							ArquivoUtil.save(ma, base + "atuais.txt");

							retorno = new ArrayList<>(somenteNovos);

						} else {

							System.out.println("Não há resultados com os filtros aplicados");
							Thread.sleep(2000);
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
		return retorno;
	}

	public static void main(String[] args) throws IOException {

		final int quantidade = QUANTIDADE_DE_JOGOS; // vezes 3

		final int max = 80;
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String basePathName = basePath + "01_" + currDate + "_quina_da_sorte_";
		String finalListPath = basePathName + currDateTime + "_final_temp.txt";
		String finalCleanListPath = basePathName + currDateTime + ".txt";
		List<List<Integer>> finalList = new ArrayList<>();

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, quinaConfig.getDefaultSize());

		List<List<Integer>> mainList = execute(quantidade);
		print(mainList, "Main");
		finalList.addAll(mainList);

		for(int i = 0; i < positional; i++) {
			List<List<Integer>> positionalList = PositionalReplacement
					.getPositionalReplacement(mainList, max);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		for(int i = 0; i < shift; i++) {
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

		ArquivoUtil.saveLists(finalList, finalListPath, theSep, 2);
		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);
	}

}
