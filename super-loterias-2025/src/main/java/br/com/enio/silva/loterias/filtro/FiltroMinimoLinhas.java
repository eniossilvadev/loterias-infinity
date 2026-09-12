package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiltroMinimoLinhas extends FiltroAb {

	int numeroDeLinhas;

	int numerosPorLinhas;

	int minimoPorLinha;

	public FiltroMinimoLinhas(int ndl, int npl, int mpl) {
		this.numeroDeLinhas = ndl;
		this.numerosPorLinhas = npl;
		this.minimoPorLinha = mpl;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		Collections.sort(lista);
		@SuppressWarnings("unchecked")
		List<Integer> cloneLista = (ArrayList<Integer>) ((ArrayList<Integer>) (lista)).clone();

		int inicioLinha, fimLinha;

		for (int i = 0; i < numeroDeLinhas; i++) {

			inicioLinha = 1 + i * numerosPorLinhas;
			fimLinha = (1 + i) * numerosPorLinhas;

			int contar = 0;
			for (Integer n : lista) {
				int atual = n;
				cloneLista.remove(n);
				if (atual >= inicioLinha && atual <= fimLinha) {
					contar++;
				}
			}

			if (contar < minimoPorLinha) {
				return new ArrayList<Integer>();
			}

		}

		return lista;
	}
}
