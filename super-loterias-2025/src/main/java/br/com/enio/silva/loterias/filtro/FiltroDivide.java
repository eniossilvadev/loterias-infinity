package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.ListUtils;

public class FiltroDivide extends FiltroAb {

	private int diff = 2;

	private int maxNumber = 100;

	public FiltroDivide(int diferenca, int maxNumber) {
		this.diff = diferenca;
		this.maxNumber = maxNumber;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		int divisor = maxNumber / 2;

		List<Integer> menor = new ArrayList<Integer>();
		List<Integer> maior = new ArrayList<Integer>();

		int i = 1;

		while (i <= divisor) {
			menor.add(i++);
		}

		while (i <= maxNumber) {
			maior.add(i++);
		}

		int contaMenor = ListUtils.intersection(lista, menor).size();
		int contaMaior = ListUtils.intersection(lista, maior).size();

		if (Math.abs(contaMaior - contaMenor) <= diff) {
			return lista;
		}

		return new ArrayList<Integer>();
	}

	public static Predicate<List<Integer>> filtroDivisaoOtimizado(int diff, int maxNumber) {
		int divisor = maxNumber / 2;
		return lista -> {
			int contaMenor = 0;
			// Loop simples é mais rápido que Streams para listas pequenas (6 a 20 itens)
			for (Integer n : lista) {
				if (n <= divisor) contaMenor++;
			}
			int contaMaior = lista.size() - contaMenor;
			return Math.abs(contaMaior - contaMenor) <= diff;
		};
	}
}
