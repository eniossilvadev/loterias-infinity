package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Teste {

	public static void main(String[] args) throws IOException {

		List<Integer> lista = new ArrayList<Integer>(Arrays.asList(17, 18, 29, 33, 37, 42, 49, 52,
		        8, 11, 27, 38, 40, 47, 50, 60, 5, 18, 29, 30, 41, 43, 53, 54, 2, 4, 12, 23, 42, 49,
		        51, 52, 2, 6, 15, 18, 23, 25, 49, 53, 4, 10, 24, 32, 41, 51, 53, 5, 7, 13, 28, 30,
		        31, 54, 1, 12, 23, 34, 36, 46, 56, 2, 8, 19, 38, 43, 50, 58, 3, 14, 20, 26, 44, 48,
		        57, 6, 15, 21, 35, 45, 55, 59, 2, 11, 15, 18, 19, 39, 49, 2, 9, 10, 16, 18, 22, 43));

		Collections.shuffle(lista);
		Set<Integer> set = new TreeSet<Integer>(lista);
		System.out.println("Tamanho: " + set.size() + set);

		List<Integer> lista1 = new ArrayList<Integer>();
		List<Integer> lista2 = new ArrayList<Integer>();
		List<Integer> lista3 = new ArrayList<Integer>();

		int count = 0;
		/*
		 * for (Integer i : set) { if (count % 2 == 0) { lista1.add(i); } else {
		 * lista2.add(i); } count++; }
		 */

		for (Integer i : set) {
			if (count % 3 == 0) {
				lista1.add(i);
			} else if (count % 2 == 0) {
				lista2.add(i);
			} else {
				lista3.add(i);
			}
			count++;
		}

		System.out.println("Tamanho: " + lista1.size() + lista1);

		System.out.println("Tamanho: " + lista2.size() + lista2);

		System.out.println("Tamanho: " + lista3.size() + lista3);

		List<Integer> temUm = new ArrayList<Integer>();

		for (int i = 1; i < 60; i++) {
			temUm.add(i);
		}

		temUm.removeAll(set);

		System.out.println("Inverso: " + temUm.size() + temUm);
	}

}
