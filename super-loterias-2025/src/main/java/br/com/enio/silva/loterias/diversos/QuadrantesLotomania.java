package br.com.enio.silva.loterias.diversos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuadrantesLotomania {

	private static final List<Integer> full = IntStream.rangeClosed(1, 100).boxed()
			.collect(Collectors.toList());

	private static final List<Integer> q1 = getQ1(full);

	private static final List<Integer> q2 = getQ2(full);

	private static final List<Integer> q3 = getQ3(full);

	private static final List<Integer> q4 = getQ4(full);

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

	public static List<List<Integer>> generateList(final int quantidade, final int size,
			final List<Integer> excluir) {
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

	public static List<List<Integer>> generateList(final int quantidade, final int size,
			final List<Integer> excluir, final List<Integer> incluir) {

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

	private static List<Integer> getQ1(final List<Integer> full) {
		List<Integer> curr = new ArrayList<>(full);
		curr.removeIf(el -> el > 50 || el % 10 > 5 || el % 10 == 0);
		Collections.sort(curr);
		System.out.println("getQ1: " + curr);
		return curr;
	}

	private static List<Integer> getQ2(final List<Integer> full) {
		List<Integer> curr = new ArrayList<>(full);
		curr.removeIf(el -> el > 50 || (el % 10 <= 5 && el % 10 != 0));
		Collections.sort(curr);
		System.out.println("getQ2: " + curr);
		return curr;
	}

	private static List<Integer> getQ3(final List<Integer> full) {
		List<Integer> curr = new ArrayList<>(full);
		curr.removeIf(el -> el <= 50 || el % 10 > 5 || el % 10 == 0);
		Collections.sort(curr);
		System.out.println("getQ3: " + curr);
		return curr;
	}

	private static List<Integer> getQ4(final List<Integer> full) {
		List<Integer> curr = new ArrayList<>(full);
		curr.removeIf(el -> el <= 50 || (el % 10 <= 5 && el % 10 != 0));
		Collections.sort(curr);
		System.out.println("getQ4: " + curr);
		return curr;
	}

	public static void main(String[] args) {

	}

}
