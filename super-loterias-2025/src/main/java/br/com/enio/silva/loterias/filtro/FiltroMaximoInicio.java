package br.com.enio.silva.loterias.filtro;

import java.util.Collections;
import java.util.List;

public class FiltroMaximoInicio extends FiltroAb {

	private int minn = 0;

	public FiltroMaximoInicio() {
	}

	public FiltroMaximoInicio(int maxInicio) {
		this.minn = maxInicio;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		int min = Collections.min(lista);

		if (min > minn) {
			return Collections.emptyList();
		}
		return lista;
	}
}
