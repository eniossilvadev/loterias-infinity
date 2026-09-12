package br.com.enio.silva.loterias.diversos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuadrantesLotofacil {

	private static final List<Integer> q1 = Arrays.asList(1, 2, 3, 4, 5);

	private static final List<Integer> q2 = Arrays.asList(6, 7, 8, 9, 10);

	private static final List<Integer> q3 = Arrays.asList(11, 12, 13, 14, 15);

	private static final List<Integer> q4 = Arrays.asList(16, 17, 18, 19, 20);

	private static final List<Integer> q5 = Arrays.asList(21, 22, 23, 24, 25);

	private static final List<List<Integer>> quadrantes = Arrays.asList(q1, q2, q3, q4, q5);

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

		System.out.println(
				"QuadrantesLotofacil.generateList#excluir: " + excluir != null ? excluir : "");
		System.out.println(
				"QuadrantesLotofacil.generateList#incluir: " + incluir != null ? incluir : "");

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

	public static List<List<Integer>> generateList(final int quantidade, final int size,
			final List<Integer> excluir, final List<Integer> incluir, int qtt) {

		System.out.println(
				"QuadrantesLotofacil.generateList#excluir: " + excluir != null ? excluir : "");
		System.out.println(
				"QuadrantesLotofacil.generateList#incluir: " + incluir != null ? incluir : "");

		final List<List<Integer>> listas = new ArrayList<>();

		final List<List<Integer>> quads = new ArrayList<>();
		quadrantes.forEach(q -> {
			List<Integer> curr = new ArrayList<>(q);
			curr.removeAll(excluir);
			quads.add(curr);
		});

		for (int i = 0; i < quantidade; i++) {
			List<Integer> curr = new ArrayList<>(incluir);
			Collections.shuffle(curr);
			curr = curr.subList(0, qtt);
			List<Integer> lista = Rotacao.getList(quads, size, excluir, curr);
			Collections.sort(lista);
			listas.add(lista);
		}
		final Set<List<Integer>> set = new HashSet<>(listas);
		final List<List<Integer>> retorno = new ArrayList<>(set);
		return retorno;
	}

}
