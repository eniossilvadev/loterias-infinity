package br.com.enio.silva.loterias.cliente.mix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class RandomFilterList {

	public static List<List<Integer>> getListas(String path, int quantidade) {
		List<List<Integer>> listas = ArquivoUtil.obterLinhasComoListasUnique(path);
		Collections.shuffle(listas);

		List<Integer> positions = IntStream.rangeClosed(1, listas.size()).boxed()
				.collect(Collectors.toList());
		Collections.shuffle(positions);

		final List<List<Integer>> retorno = new ArrayList<>();
		for (int i = 0; i < quantidade; i++) {
			retorno.add(listas.get(positions.get(i)));
		}
		return retorno;
	}

	public static void main(String[] args) {
		String path = "E:\\loterias\\bingo-da-sorte\\20230201\\results\\1677653130469_ngt_0.txt";
		int quantidade = 7;

		List<List<Integer>> listas =  getListas(path, quantidade);
		listas.forEach(System.out::println);
	}

}
