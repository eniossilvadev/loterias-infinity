package br.com.enio.silva.loterias.filtro;

import java.util.Collections;
import java.util.List;

public class FiltroDiffMaximaEntreNumerosConsecutivos extends FiltroAb {

	private int diff = 0;

	public FiltroDiffMaximaEntreNumerosConsecutivos() {
	}

	public FiltroDiffMaximaEntreNumerosConsecutivos(int diferenca) {
		this.diff = diferenca;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		Collections.sort(lista);

		int last = lista.get(0);
		for (Integer i : lista) {
			if (i - last > diff) {
				return Collections.emptyList();
			}
			last = i;
		}
		return lista;
	}
}
