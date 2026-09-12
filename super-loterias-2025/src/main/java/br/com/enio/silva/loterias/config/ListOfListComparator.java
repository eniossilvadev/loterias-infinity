package br.com.enio.silva.loterias.config;

import java.util.Comparator;
import java.util.List;

public class ListOfListComparator implements Comparator<List<Integer>> {

	@Override
	public int compare(List<Integer> lista1, List<Integer> lista2) {
		int comp = Integer.compare(lista2.size(), lista1.size());
		if (comp != 0) {
			return comp;
		}
		int max = Math.max(lista1.size(), lista2.size());
		for (int i = 0; i < max; i++) {
			int comp2 = Integer.compare(lista1.get(i % lista1.size()),
			        lista2.get(i % lista2.size()));
			if (comp2 != 0) {
				return comp2;
			}
		}
		return 0;
	}

}