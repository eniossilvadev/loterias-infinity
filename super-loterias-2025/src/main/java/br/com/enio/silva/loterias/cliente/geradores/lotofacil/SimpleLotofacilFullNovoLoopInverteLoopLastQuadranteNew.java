package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class SimpleLotofacilFullNovoLoopInverteLoopLastQuadranteNew {

	public static int ultimos = -1;

	public static final int QUANTIDADE_DE_JOGOS = 1;

	public static final int modulador = 1;

	private static final int DEFAULT_SIZE = 15;

	public static String sep = ",";

	public static final Boolean shif = true;

	public static final Boolean positional = true;

	public static void main(String[] args) throws IOException {

		final int quantidade = 30; // vezes 3

		final int max = 25;
		final int size = DEFAULT_SIZE;
		final String theSep = "\t";
		final String input = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\01_20240307_20240307223909925.txt";

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\";
		String basePathName = basePath + "01_" + currDate + "_";
		String finalCleanListPath = basePathName + currDateTime + ".txt";
		List<List<Integer>> finalList = new ArrayList<>();

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		final List<List<Integer>> initialList = ArquivoUtil.obterLinhasComoListasUnique(input);
		print(initialList, "Main");
		finalList.addAll(initialList);

		List<List<Integer>> mainList = new ArrayList<>(initialList);
		Collections.shuffle(mainList);
		mainList = new ArrayList<>(mainList.subList(0, quantidade));

		if (positional) {
			List<List<Integer>> positionalList = PositionalReplacement
					.getPositionalReplacement(mainList, max);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		if (shif) {
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
		cleanList = LotoUtils.gc(cleanList, 15);
		print(cleanList, "Clean List");

		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);
	}

	public static void print(List<List<Integer>> lista, String text) {
		System.out.println(text + "\t" + lista.size());
		lista.forEach(System.out::println);
		System.out.println("\n");
	}
}
