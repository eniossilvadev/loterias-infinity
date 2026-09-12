package br.com.enio.silva.loterias.diversos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Rotacao {

	public static List<Integer> getList(List<List<Integer>> listas, int size) {

		Set<Integer> set = new HashSet<>();

		do {
			int index = ThreadLocalRandom.current().nextInt(listas.size());
			List<Integer> curr = listas.get(index);
			Collections.shuffle(curr);
			set.add(curr.get(0));
		} while (set.size() < size);

		return new ArrayList<>(set);
	}

	public static List<Integer> getList(final List<List<Integer>> l1, int size, final List<Integer> excluir) {

		Set<Integer> set = new HashSet<>();

		final List<List<Integer>> listas = new ArrayList<>();

		l1.forEach(l -> {
			l.removeAll(excluir);
			listas.add(l);
		});

		do {

			int index = ThreadLocalRandom.current().nextInt(listas.size());
			List<Integer> curr = listas.get(index);
			Collections.shuffle(curr);
			set.add(curr.get(0));

		} while (set.size() < size);

		return new ArrayList<>(set);
	}

	public static List<Integer> getList(final List<List<Integer>> l1, int size, final List<Integer> excluir, final List<Integer> incluir) {


		Set<Integer> set = incluir != null? new HashSet<>(incluir) : new HashSet<>();

		final List<List<Integer>> listas = new ArrayList<>();

		l1.forEach(l -> {
			l.removeAll(excluir);
			listas.add(l);
		});

		do {

			int index = ThreadLocalRandom.current().nextInt(listas.size());
			List<Integer> curr = listas.get(index);
			Collections.shuffle(curr);
			set.add(curr.get(0));

		} while (set.size() < size);

		return new ArrayList<>(set);
	}

}
