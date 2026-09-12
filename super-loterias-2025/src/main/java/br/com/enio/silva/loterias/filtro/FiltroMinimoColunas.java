package br.com.enio.silva.loterias.filtro;

import java.util.Collections;
import java.util.List;

public class FiltroMinimoColunas extends FiltroAb {

	int numeroDeColunas;

	int minimoPorLinha;

	public FiltroMinimoColunas(int ndc, int mpc) {
		this.numeroDeColunas = ndc;
		this.minimoPorLinha = mpc;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		Collections.sort(lista);

		int[] totalColunas = new int[numeroDeColunas];

		int col = 0;
		for (Integer el : lista) {
			col = el % numeroDeColunas;
			totalColunas[col] += totalColunas[col] + 1;
		}

		for (int c : totalColunas) {
			if (c < minimoPorLinha) {
				return Collections.emptyList();
			}
		}
		return lista;
	}
}
