package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.List;

public class FiltroParImpar extends FiltroAb {

	private int diff = 0;

	public FiltroParImpar() {
	}

	public FiltroParImpar(int diff) {
		this.diff = diff;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		List<Integer> even = new ArrayList<Integer>(); // pares

		for (Integer l : lista) {
			if ((l & 1) == 0) {
				even.add(l);
			}
		}

		int nPar = even.size();
		int nImpar = lista.size() - even.size();

		if (Math.abs(nImpar - nPar) <= diff) {
			return lista;
		}
		return new ArrayList<Integer>();
	}

}
