package br.com.enio.silva.loterias.filtro;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class FiltroMaxLista extends FiltroAb {

	private List<List<Integer>> listaMax = null;

	private final int max;

	public FiltroMaxLista(List<List<Integer>> listaMaximoRepetidos, int maximo) {
		this.listaMax = listaMaximoRepetidos;
		this.max = maximo;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		if (max <= 0) {
			return Collections.emptyList();
		}

		for (List<Integer> l : listaMax) {
			int equals = ListUtils.intersection(lista, l).size();

			if (equals > max) {
				return Collections.emptyList();
			}
		}

		return lista;
	}

}
