package br.com.enio.silva.loterias.filtro;

import java.util.Collections;
import java.util.List;

public class FiltroMinimoFim extends FiltroAb {

	private int maxx = 0;

	public FiltroMinimoFim() {
	}

	public FiltroMinimoFim(int minFim) {
		this.maxx = minFim;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		int max = Collections.max(lista);

		if (max < maxx) {
			return Collections.emptyList();
		}
		return lista;
	}
}
