package br.com.enio.silva.loterias.config;

import java.util.Comparator;
import java.util.List;

public class ListOfListComparator2 implements Comparator<List<Integer>> {

	@Override
	public int compare(List<Integer> lista1, List<Integer> lista2) {
		int comp = Integer.compare(lista2.size(), lista1.size());
		if (comp != 0) {
			return comp;
		}
		int min = Math.min(lista1.size(), lista2.size()) - 1;
		for (int i = 0; i < min; i++) {
			int el = min - i;
			int comp2 = Integer.compare(lista1.get(el), lista2.get(el));
			if (comp2 != 0) {
				return comp2;
			}
		}
		return 0;
	}

}