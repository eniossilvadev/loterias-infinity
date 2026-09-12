package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FiltroLinhaColunaAb extends FiltroAb {

	int numeroDeLinhas;

	int numerosPorLinhas;

	int qttPorLinha;

	public FiltroLinhaColunaAb(int ndl, int npl, int mpl) {
		this.numeroDeLinhas = ndl;
		this.numerosPorLinhas = npl;
		this.qttPorLinha = mpl;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		List<Integer> ordenada = new ArrayList<>(lista);
		Collections.sort(ordenada);

		for (int i = 0; i < numeroDeLinhas; i++) {
			int inicioLinha = 1 + i * numerosPorLinhas;
			int fimLinha = (1 + i) * numerosPorLinhas;

			int contar = 0;
			for (Integer n : ordenada) {
				if (n >= inicioLinha && n <= fimLinha) {
					contar++;
				}
			}

			if (validar(contar)) {
				return Collections.emptyList();
			}
		}

		return lista;
	}


	protected abstract boolean validar(int contador);

}
