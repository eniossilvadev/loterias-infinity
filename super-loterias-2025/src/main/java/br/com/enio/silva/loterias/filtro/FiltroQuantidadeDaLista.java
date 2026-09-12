package br.com.enio.silva.loterias.filtro;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

public class FiltroQuantidadeDaLista extends FiltroAb {

	private int iguais = 0;

	private final List<Integer> referencia;

	public FiltroQuantidadeDaLista(int quantidade, List<Integer> listaReferencia) {
		this.iguais = quantidade;
		this.referencia = listaReferencia;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		Set<Integer> conjuntoEntrada = new HashSet<>(lista);
		Set<Integer> conjuntoReferencia = new HashSet<>(referencia);

		conjuntoEntrada.retainAll(conjuntoReferencia); // interseção
		if (conjuntoEntrada.size() == iguais) {
			return lista;
		}

		return Collections.emptyList();
	}

}
