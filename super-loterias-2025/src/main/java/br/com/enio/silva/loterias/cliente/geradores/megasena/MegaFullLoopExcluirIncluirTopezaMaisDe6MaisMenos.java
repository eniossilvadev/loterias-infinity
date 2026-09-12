package br.com.enio.silva.loterias.cliente.geradores.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.cliente.megasena.GerarJogosMegaLoopAb;
import br.com.enio.silva.loterias.commons.SaveMegaSenaEspecial;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class MegaFullLoopExcluirIncluirTopezaMaisDe6MaisMenos extends GerarJogosMegaLoopAb {

	//	private final static SaveMegaSena saveMegaSenaEspecial = new SaveMegaSena();
	private final static SaveMegaSenaEspecial saveMegaSenaEspecial = new SaveMegaSenaEspecial();

	private final static int QUANTIDADE_DE_JOGOS = 1; // vezes 3

	private final static int DEFAULT_SIZE = 6;

	private final static int TAMANHO_DO_JOGO = 6;

	private final static int SHIFT = 1;

	private final static int POSITIONAL = 1;

	private final static int QTT_INICIAL_FIXO = 50000;

	private final static int QTT_ORIGINAL = QTT_INICIAL_FIXO;

	private final static int TAM_PADRAO_EXCLUIR = 10;

	private final static int TAMANHO_INCLUIR_LISTA = 2;

	public static List<List<Integer>> execute(final int totalDeJogos, final int tamanhoDoJogo,
			final int defaultSize) throws IOException {

		int qttInicialFixo = QTT_INICIAL_FIXO;
		int qttOriginal = QTT_ORIGINAL;

		int tamPadraoExcluir = TAM_PADRAO_EXCLUIR;
		int tamanhoIncluirLista = TAMANHO_INCLUIR_LISTA;

		List<List<Integer>> retorno = new ArrayList<>();

		// https://www.mazusoft.com.br/mega/tabela-sequencia-atraso.php
		List<Integer> theList = Arrays.asList();

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

		String sep = ",";

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		final List<List<Integer>> fullOriginal = LotoUtils.getAll(saveMegaSenaEspecial.getCurr(),
				defaultSize);

		final List<List<Integer>> full = LotoUtils.gc(fullOriginal, defaultSize);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		MegaSenaConfigAb config = getConfigBySize(tamanhoDoJogo);
		List<List<Integer>> resultados = config.getTodosResultados();

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		String snBkp = base + "novos/" + currDateTime + "_snms.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		int magicNumber = rand.nextInt(1000);

		theSep = sep;

		int variable = TAMANHO_DO_JOGO / 3;

		for (int count = 0; count < totalDeJogos; count++) {

			tamanhoIncluirLista = 1 + (tamanhoIncluirLista + count) % variable;

			config = getConfigBySize(tamanhoDoJogo);

			config.setQttInicial(qttOriginal);

			try {

				System.out.println("Magic Number: " + magicNumber);
				magicNumber++;

				Set<Integer> listaIncl = new HashSet<>(listaInclFixa);
				if (theList != null && !theList.isEmpty()) {
					int el = magicNumber % theList.size();
					int sorte = theList.get(el);
					listaIncl.add(sorte);

					System.out.println("el: " + el + "\t" + sorte);
				}

				config.setQttInicial(qttInicialFixo);

				String output = base + "ind/" + currDate + "/" + config.getDefaultName()
				+ DateUtils.getCurrentDefaultDateTime() + ".txt";

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				List<Integer> jogosAtuaisFlat = ListaUtils.getPlainFullList(full);

				List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1,
						config.getMaxNum());

				listaIncl.addAll(listaOrdenada.subList(0, tamanhoIncluirLista));

				Collections.reverse(listaOrdenada);
				Set<Integer> listaExcl = new HashSet<>(listaOrdenada.subList(0, tamPadraoExcluir));

				listaExcl.removeAll(listaIncl);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (last != null) {
					Collections.shuffle(lasts);
					lasts = lasts != null && lasts.size() > 11 ? lasts.subList(0, 8) : lasts;
					lasts.addAll(last);
					lasts = new ArrayList<>(new HashSet<>(lasts));
					lasts.removeAll(listaIncl);
					listaExcl.addAll(lasts);
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
				int r = config.getNrosApostados() - listaIncl.size();
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
				fullList.addAll(resultados);
				fullList.addAll(full);

				preJogos.removeAll(fullList);

				FiltroIf filtro = null;

				filtro = new FiltroRemoverIntersecao(fullList);
				preJogos = filtro.filtrarListas(preJogos);

				int maxRep = 3;
				filtro = new FiltroMaxConsecutivos(maxRep);
				preJogos = filtro.filtrarListas(preJogos);

				int maxLinhas = 3 + count % 3;
				filtro = new FiltroMaximoLinhas(6, 10, maxLinhas);
				preJogos = filtro.filtrarListas(preJogos);

				filtro = new FiltroDivide(4, config.getMaxNum());
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && !preJogos.isEmpty()) {

					List<JogoMega> jogosLM = new ArrayList<JogoMega>();
					JogoMega jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoMega(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(resultados, meuJogo);
					}

					Collections.sort(jogosLM);

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
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

					saveDefault2(jogos, output);
					saveDefault2(somenteNovos, sn);
					saveDefault2(somenteNovos, snBkp);

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

		final int quantidade = QUANTIDADE_DE_JOGOS; // vezes 3

		final int max = 60;
		final int size = DEFAULT_SIZE;
		final int tamanhoDoJogo = TAMANHO_DO_JOGO;
		final List<List<Integer>> finalList = new ArrayList<>();

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = saveMegaSenaEspecial.getCurr();
		String basePathName = basePath + "01_" + currDate + "_mega_sorte_";
		String finalCleanListPath = basePathName + currDateTime + ".txt";

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		List<List<Integer>> mainList = execute(quantidade, tamanhoDoJogo, size);
		print(mainList, "Main");
		finalList.addAll(mainList);

		for (int i = 0; i < POSITIONAL; i++) {
			List<List<Integer>> positionalList = PositionalReplacement
					.getPositionalReplacement(mainList, max);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		for (int i = 0; i < SHIFT; i++) {
			List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, max);
			print(shiftedList, "Shifed");
			finalList.addAll(shiftedList);
		}

		print(finalList, "Final -> Before");
		Collections.sort(finalList, new ListOfListComparator());
		print(finalList, "Final -> After");

		List<List<Integer>> cleanList = new ArrayList<>(finalList);
		cleanList = ListaUtils.removerDaLista(cleanList, listaRemover);
		print(cleanList, "Clean List");

		saveDefault2(cleanList, finalCleanListPath);
	}

}
