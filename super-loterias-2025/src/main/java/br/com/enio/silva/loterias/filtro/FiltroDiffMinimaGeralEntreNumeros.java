package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiltroDiffMinimaGeralEntreNumeros extends FiltroAb {

	private int diff = 0;

	public FiltroDiffMinimaGeralEntreNumeros() {
	}

	public FiltroDiffMinimaGeralEntreNumeros(int diferenca) {
		this.diff = diferenca;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		Collections.sort(lista);

		int primeiro = lista.get(0);
		int ultimo = lista.get(lista.size() - 1);

		int diferencaGeral = ultimo - primeiro;

		if (diferencaGeral <= diff) {
			return new ArrayList<Integer>();
		}

		return lista;
	}

}
