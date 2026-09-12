package br.com.silva.enio.loterias.commons.math;

import java.util.Iterator;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class Combination {

	public static void combination(Object[] elements, int K) {
		Iterator<int[]> list = CombinatoricsUtils.combinationsIterator(9, 8);

		int[] el = null;
		int count = 0;
		while (list.hasNext()) {
			el = list.next();
			System.out.print(++count + " => ");
			for (int i = 0; i < el.length; i++) {
				System.out.print(el[i] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Object[] elements = new Object[] { 'A', 'B', 'C', 'D', 'E' };

		Combination.combination(elements, 3);
	}

}
