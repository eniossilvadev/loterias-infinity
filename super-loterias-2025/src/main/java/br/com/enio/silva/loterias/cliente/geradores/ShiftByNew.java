package br.com.enio.silva.loterias.cliente.geradores;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShiftByNew {

	public static void gerar() {
		final String sep = ",";
		final int max = 60;

		final String basePath = "D:\\Meus Documentos\\Área de Trabalho\\01";

		final String path = basePath + ".txt";
		final String out = basePath + "_OUT1.txt";
		//		final String full = basePath + "_FULL1.txt";

		final List<List<Integer>> lines = ArquivoUtil.obterLinhasComoListas(path);

		List<List<Integer>> retorno = getShifted(lines, max);

		Collections.sort(lines, new ListOfListComparator());
		Collections.sort(retorno, new ListOfListComparator());

		List<List<Integer>> fullList = new ArrayList<>(lines);
		fullList.addAll(retorno);

		ArquivoUtil.safeSaveLists(lines, path, sep, 2);
		ArquivoUtil.safeSaveLists(retorno, out, sep, 2);
		//		ArquivoUtil.safeSaveLists(fullList, full, sep, 2);
	}

	public static List<List<Integer>> getShifted(final List<List<Integer>> input, final int max) {
		List<List<Integer>> retorno = new ArrayList<>();

		int random = new Random().nextInt(max);
		final int shift = random == 0 ? 26 : random;

		System.out.println("Shifting..." + (shift + 1));

		input.forEach(line -> {
			List<Integer> curr = new ArrayList<>();
			line.forEach(p -> {
				int cv = (p + shift) % max + 1;
				curr.add(cv);
			});
			Collections.sort(curr);
			retorno.add(curr);
		});
		return retorno;
	}

	public static void main(String[] args) {
		gerar();
	}
}
