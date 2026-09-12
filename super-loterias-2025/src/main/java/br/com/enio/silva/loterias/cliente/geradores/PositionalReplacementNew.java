package br.com.enio.silva.loterias.cliente.geradores;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PositionalReplacementNew {

	public static void gerar() {
		final String sep = ",";
		final int max = 25;

		final String basePath = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\20230821221816147";

		final String path = basePath + ".txt";
		final String out = basePath + "_OUT2.txt";
		//		final String full = basePath + "_FULL2.txt";

		final List<List<Integer>> postions = ArquivoUtil.obterLinhasComoListas(path);

		List<List<Integer>> retorno = getPositionalReplacement(postions, max);

		Collections.sort(postions, new ListOfListComparator());
		Collections.sort(retorno, new ListOfListComparator());

		List<List<Integer>> fullList = new ArrayList<>(postions);
		fullList.addAll(retorno);

		ArquivoUtil.safeSaveLists(postions, path, sep, 2);
		ArquivoUtil.safeSaveLists(retorno, out, sep, 2);
		//		ArquivoUtil.safeSaveLists(fullList, full, sep, 2);
	}

	public static List<List<Integer>> getPositionalReplacement(List<List<Integer>> input,
			final int max) {

		List<Integer> numeros = IntStream.rangeClosed(1, max).boxed().collect(Collectors.toList());

		Collections.shuffle(numeros);

		List<List<Integer>> retorno = new ArrayList<>();
		input.forEach(line -> {
			List<Integer> curr = new ArrayList<>();
			line.forEach(p -> {
				final int pos = p - 1;
				curr.add(numeros.get(pos));
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
