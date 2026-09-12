package br.com.enio.silva.loterias.filtro;

import java.util.*;

import org.apache.commons.collections.ListUtils;

public class FiltroRemoverIntersecao extends FiltroAb {

	private final List<List<Integer>> remove;

	public FiltroRemoverIntersecao(List<List<Integer>> remover) {
		this.remove = remover;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		if (this.remove == null || this.remove.isEmpty()) {
			return lista;
		}

		Set<Integer> conjuntoAtual = new HashSet<>(lista);

		boolean jaFoiJogado = this.remove.parallelStream().anyMatch(jogadaAnterior -> {
			if (jogadaAnterior == null || jogadaAnterior.isEmpty()) {
				return false;
			}

			if (lista.size() > jogadaAnterior.size()) {
				return false;
			}

			return conjuntoAtual.containsAll(jogadaAnterior);
		});

		return jaFoiJogado ? Collections.emptyList() : lista;
	}

}
