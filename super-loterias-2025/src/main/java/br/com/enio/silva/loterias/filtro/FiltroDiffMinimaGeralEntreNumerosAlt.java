package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiltroDiffMinimaGeralEntreNumerosAlt extends FiltroAb {

	private int diff = 0;

	public FiltroDiffMinimaGeralEntreNumerosAlt() {
	}

	public FiltroDiffMinimaGeralEntreNumerosAlt(int diferenca) {
		this.diff = diferenca;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		Collections.sort(lista);

		int diferencaGeral = 0;
		int current = lista.get(0);
		int maxDiff = 0;
		int diffTemporaria = 0;
		for (Integer elem : lista) {

			diffTemporaria = elem.intValue() - current;
			diferencaGeral += diffTemporaria;
			maxDiff = Math.max(maxDiff, diffTemporaria);
			current = elem;
		}

		diferencaGeral = diferencaGeral - maxDiff / 2;
		int removidos = 0;
		if (diferencaGeral <= diff && maxDiff >= diff * 1.5) {
			// System.out.println("Removendo: " + lista);
			removidos++;
			return new ArrayList<Integer>();
		}
		System.out.println("Total de removidos: " + removidos);
		return lista;
	}

}
