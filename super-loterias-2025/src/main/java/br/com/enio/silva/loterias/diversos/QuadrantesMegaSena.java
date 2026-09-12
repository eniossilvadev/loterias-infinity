package br.com.enio.silva.loterias.diversos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadrantesMegaSena {

	private static final List<Integer> q1 = Arrays.asList(1, 2, 3, 4, 5, 11, 12, 13, 14, 15, 21, 22,
			23, 24, 25);

	private static final List<Integer> q2 = Arrays.asList(6, 7, 8, 9, 10, 16, 17, 18, 19, 20, 26,
			27, 28, 29, 30);

	private static final List<Integer> q3 = Arrays.asList(31, 32, 33, 34, 35, 41, 42, 43, 44, 45,
			51, 52, 53, 54, 55);

	private static final List<Integer> q4 = Arrays.asList(36, 37, 38, 39, 40, 46, 47, 48, 49, 50,
			56, 57, 58, 59, 60);

	private static final List<List<Integer>> quadrantes = Arrays.asList(q1, q2, q3, q4);

	public static List<List<Integer>> generateList(final int quantidade, final int size) {
		final List<List<Integer>> listas = new ArrayList<>();
		for (int i = 0; i < quantidade; i++) {
			List<Integer> lista = Rotacao.getList(quadrantes, size);
			Collections.sort(lista);
			listas.add(lista);
		}
		final Set<List<Integer>> set = new HashSet<>(listas);
		final List<List<Integer>> retorno = new ArrayList<>(set);
		return retorno;
	}

	public static List<List<Integer>> generateList(final int quantidade, final int size, final List<Integer> excluir) {
		final List<List<Integer>> listas = new ArrayList<>();

		final List<List<Integer>> quads = new ArrayList<>();
		quadrantes.forEach(q -> {
			List<Integer> curr = new ArrayList<>(q);
			curr.removeAll(excluir);
			quads.add(curr);
		});

		for (int i = 0; i < quantidade; i++) {
			List<Integer> lista = Rotacao.getList(quads, size);
			Collections.sort(lista);
			listas.add(lista);
		}
		final Set<List<Integer>> set = new HashSet<>(listas);
		final List<List<Integer>> retorno = new ArrayList<>(set);
		return retorno;
	}

	public static List<List<Integer>> generateList(final int quantidade, final int size, final List<Integer> excluir, final List<Integer> incluir) {

		System.out.println("QuadrantesMegaSena.generateList#excluir: " + excluir != null? excluir: "");
		System.out.println("QuadrantesMegaSena.generateList#incluir: " + incluir != null? incluir: "");

		final List<List<Integer>> listas = new ArrayList<>();

		final List<List<Integer>> quads = new ArrayList<>();
		quadrantes.forEach(q -> {
			List<Integer> curr = new ArrayList<>(q);
			curr.removeAll(excluir);
			quads.add(curr);
		});

		for (int i = 0; i < quantidade; i++) {
			List<Integer> lista = Rotacao.getList(quads, size, excluir, incluir);
			Collections.sort(lista);
			listas.add(lista);
		}
		final Set<List<Integer>> set = new HashSet<>(listas);
		final List<List<Integer>> retorno = new ArrayList<>(set);
		return retorno;
	}

}
