package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class FiltroUltimoSorteio extends FiltroAb {

	private int mininoIguais = 0;

	private int maximoIguais = 0;

	private final List<Integer> ultimo;

	public FiltroUltimoSorteio(int min, int max, List<Integer> ultimoSorteio) {
		this.mininoIguais = min;
		this.maximoIguais = max;
		this.ultimo = ultimoSorteio;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		int equals = ListUtils.intersection(lista, ultimo).size();

		if (equals >= mininoIguais && equals <= maximoIguais) {
			return lista;
		}
		return new ArrayList<Integer>();
	}

}
