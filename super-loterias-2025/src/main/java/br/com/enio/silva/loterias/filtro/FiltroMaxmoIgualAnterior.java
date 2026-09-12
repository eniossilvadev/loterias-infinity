package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class FiltroMaxmoIgualAnterior extends FiltroAb {

	private int maximo = 0;

	List<List<Integer>> anteriores = new ArrayList<>();

	public FiltroMaxmoIgualAnterior(int max) {
		this.maximo = max;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		if (anteriores == null || anteriores.size() == 0) {
			anteriores.add(lista);
			return lista;
		}

		for (List<Integer> ant : anteriores) {
			int equals = ListUtils.intersection(lista, ant).size();

			if (equals > maximo) {
				return new ArrayList<Integer>();
			}
		}

		anteriores.add(lista);
		return lista;
	}

}
