package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiltroMaximaSequencia extends FiltroAb {

	private int maxx = 0;

	public FiltroMaximaSequencia() {
	}

	public FiltroMaximaSequencia(int max) {
		this.maxx = max;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		Collections.sort(lista);

		int countSeq = 1;
		int current = lista.get(0);

		for (Integer elem : lista) {

			if (current + 1 == elem.intValue()) {
				countSeq++;
				if (countSeq == maxx) {
					// System.out.println("Removendo: " + lista);
					return new ArrayList<Integer>();
				}
			} else {
				countSeq = 1;
			}
			current = elem;
		}

		return lista;
	}

}
