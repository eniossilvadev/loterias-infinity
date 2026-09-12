package br.com.enio.silva.loterias.cliente.geradores.duplasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesDuplaSena;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfigAb;
import br.com.enio.silva.loterias.gerador.GerarJogosDuplaBase;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoDuplaSena;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosDuplaSena2024 extends GerarJogosDuplaBase {

	public static final int QUANTIDADE_DE_JOGOS = 10;

	public static final int shif = 1;

	public static final int positional = 2;

	public static int ultimos = -1;

	private static final int DEFAULT_SIZE = 6;

	private static final int CURRENT_SIZE = 6;

	private static final int QUANTIDADE = 100000;

	private static final int MAX_NUMBER = 50;

	public static String sep = ",";

	public static List<List<Integer>> getAll(int times) throws IOException {

		final int TIMES = times;

		List<List<Integer>> retorno = new ArrayList<>();

		int[] ultimosJogos = { 0, 0, 26, 0, 0, 50, 0, 0, 100, 0, 0, 500, 0, 0, 0 };

		DuplaSenaConfigAb config = getConfigBySize(CURRENT_SIZE);

		List<List<Integer>> resultados = config.getTodosResultados();

		String f = config.getCaminhoJogoAtual();
		List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

		System.out.println("Tamanho da lista de origem (sem repetiçoes): " + atual.size());

		String lm = config.getCaminhoJogoCorrente();

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int count = 0;

		int magicNumber = random.nextInt(26) + 1;

		String base = "E:\\loterias\\dupla_sena\\config\\";
		String sn = base + "\\LSN.txt";

		List<List<Integer>> full = LotoUtils.getAll("E:\\loterias\\dupla_sena\\curr", DEFAULT_SIZE,
				false);

		List<List<Integer>> inicio = new ArrayList<>(full);
		Collections.shuffle(inicio);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";

		List<Integer> ultimoSorteio = resultados.get(resultados.size() - 1);

		for (int i = 0; i < TIMES; i++) {

			String dt = DateUtils.getCurrentDefaultDateTime();

			int num = Math.abs(random.nextInt());

			num = num % 20;

			count++;

			List<Integer> la = new ArrayList<>(ultimoSorteio);
			Collections.shuffle(la);
			List<Integer> listaBaseNovosJogos = new ArrayList<>();

			List<Integer> all = ListaUtils.getPlainFullList(full);

			Map<Integer, Integer> mapAtuais = MapUtil.getMapFrequencia(all, 1, MAX_NUMBER);
			mapAtuais = MapUtil.sortByValue2(mapAtuais);
			System.out.println("Map Atuais: " + mapAtuais);
			mapAtuais = MapUtil.removeAll(mapAtuais, listaBaseNovosJogos);
			List<Integer> elements = new ArrayList<>();
			List<Integer> listaRange = ListaUtils.getListaRange(1, MAX_NUMBER);
			Collections.shuffle(listaRange);
			elements.add(listaRange.get(0));

			int completar = config.getNrosApostados() - listaBaseNovosJogos.size()
					- elements.size();

			List<List<Integer>> preJogos = new ArrayList<>();

			System.out.println("completar (size): " + completar);
			System.out.println("Lista Base: " + listaBaseNovosJogos);
			System.out.println("Lista elements: " + elements);

			System.out.println("Gerando...");
			int listSize = config.getNrosApostados() - elements.size();
			List<List<Integer>> pjs = QuadrantesDuplaSena.generateList(QUANTIDADE, listSize,
					elements, listaBaseNovosJogos);
			try {

				for (List<Integer> pj : pjs) {
					pj.addAll(elements);
					if (config.getNrosApostados() < pj.size()) {
						throw new Exception("Unespected size");
					}
					preJogos.add(pj);
				}

				List<List<Integer>> fullList = new ArrayList<>();
				fullList.addAll(resultados);
				fullList.addAll(full);
				Set<List<Integer>> fullSet = new HashSet<>(fullList);
				fullList = new ArrayList<>(fullSet);

				preJogos.removeAll(fullList);

				ultimos = ultimosJogos[(count + magicNumber) % ultimosJogos.length];

				int ultimos = ultimosJogos[count % ultimosJogos.length];
				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
						: ultimos;

				String asterics = StringUtils.repeat('*', 20);
				String spaces = StringUtils.repeat(' ', 20);
				System.out.println(asterics + spaces + "Últimos: " + ultimos + spaces + asterics);

				List<List<Integer>> ultimosResultados = config.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> rem = ArquivoUtil.obterLinhasComoListasUnique(remove);
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				remover.addAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				List<JogoDuplaSena> jogosLM = new ArrayList<JogoDuplaSena>();

				JogoDuplaSena jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoDuplaSena(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();

				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(ultimosResultados, meuJogo);
				}

				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(meuJogo.getPontuacao() + "[" + c++ + "]: "
							+ meuJogo.getNumerosAsList());
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (

						JogoAb jj : jogosLM) {
					jogos.add(jj.getNumerosAsList());
					System.out.println(jj.getNumerosAsList() + "\t" + jj.getMapConta());
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					todos.addAll(jogo);
					System.out.println(jogo);
				}

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				rem.addAll(jogosOut);
				ArquivoUtil.saveLists(rem, remove, sep);

				somenteNovos.addAll(jogos);

				int size = somenteNovos.size();
				somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
				if (somenteNovos.size() < size) {
					count = count - (size - somenteNovos.size());
				}

				Collections.sort(jogosLM);

				String output = base + "ind/" + currDate + "/LFI" + dt + ".txt";
				saveDefault2(jogos, output);
				saveDefault2(somenteNovos, sn);
				saveDefault2(somenteNovos, snBkp);

				retorno = new ArrayList<>(somenteNovos);

			} catch (Exception e) {
				e.printStackTrace();
				count--;
			}
			config = getConfigBySize(CURRENT_SIZE, i);
		}

		if (!erro.isEmpty()) {
			System.out.println("Favor reprocessar :" + erro);
		}

		return retorno;

	}

	public static void main(String[] args) throws IOException {
		final int quantidade = QUANTIDADE_DE_JOGOS; // vezes 3

		final int max = MAX_NUMBER;
		final int size = DEFAULT_SIZE;
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = "E:\\loterias\\dupla_sena\\curr\\";
		String basePathName = basePath + "01_" + currDate + "_";
		String finalCleanListPath = basePathName + currDateTime + ".txt";
		List<List<Integer>> finalList = new ArrayList<>();

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		List<List<Integer>> mainList = getAll(quantidade);
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
		cleanList = LotoUtils.gc(cleanList);
		print(cleanList, "Clean List");

		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);
	}

}
