package br.com.silva.enio.loterias.commons.util;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ListaUtils {

	public static <T> Set<T> getFromList(List<T> lista, int size) {
		Set<T> listaRetorno = new HashSet<>();
		for (int i = 0; i < size; i++) {
			Collections.shuffle(lista);
			listaRetorno.add(lista.remove(0));
		}
		return listaRetorno;
	}

	public static List<Integer> iterateStream(int from, int step, int limit) {
		return IntStream.iterate(from, i -> i + step) // next int
		        .limit(limit / step) // only numbers in range
		        .boxed().collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		String origem = "E:\\loterias\\mega_sena\\info\\L0.txt";
		String destino = "E:\\loterias\\mega_sena\\info\\L0_S%s.txt";
		List<List<Integer>> input = ArquivoUtil.obterLinhasComoListas(origem);
		List<List<List<Integer>>> output = split(input);

		ArquivoUtil.saveLists(output.get(0), String.format(destino, "01"));
		ArquivoUtil.saveLists(output.get(1), String.format(destino, "02"));
	}

	public static List<List<Integer>> removerRepetidos(List<List<Integer>> in) {

		List<List<Integer>> out = new ArrayList<>();

		int count = 0;
		for (List<Integer> l : in) {
			count++;
			if (!out.contains(l)) {
				Collections.sort(l);
				out.add(l);
			} else {
				System.out.println(String.format("Linha repetida %s: %s", count, l));
			}
		}

		return out;
	}

	public static <T> List<List<List<T>>> split(List<List<T>> lOriginal) {

		List<List<T>> l1 = new ArrayList<>();
		List<List<T>> l2 = new ArrayList<>();
		int count = 0;
		for (List<T> l : lOriginal) {
			if (count++ % 2 == 0) {
				l1.add(l);
			} else {
				l2.add(l);
			}
		}

		List<List<List<T>>> retorno = new ArrayList<>();
		retorno.add(l1);
		retorno.add(l2);

		return retorno;
	}

}
