package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.util.Arrays;
import java.util.List;

public class _Teste {

	public static void main(String[] args) {
		final List<Integer> lista1 = Arrays.asList(1, 2, 3, 4);
		final List<Integer> lista2 = Arrays.asList(1, 1, 1, 2, 2, 3, 4, 5, 5, 6);
		final List<Integer> lista3 = Arrays.asList(1, 1, 1, 2, 2, 3, 4, 4, 4);

		print(lista1, lista2, lista3);

		// final List<Integer> r1 = getRest(lista1, 6)
		//
		// MapUtil

	}

	public static void print(List<Integer>... l) {
		for (List<Integer> lista : l) {
			System.out.println(lista);
		}
	}

}
