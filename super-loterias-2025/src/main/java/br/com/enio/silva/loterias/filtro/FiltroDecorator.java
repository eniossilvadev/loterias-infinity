package br.com.enio.silva.loterias.filtro;

import java.util.List;

public abstract class FiltroDecorator implements FiltroIf {

	protected FiltroIf filtroDecorator;

	public FiltroDecorator(FiltroIf filtro) {
		this.filtroDecorator = filtro;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		return filtroDecorator.filtrarLista(lista);
	}

	@Override
	public List<List<Integer>> filtrarListas(List<List<Integer>> listas) {

		return filtroDecorator.filtrarListas(listas);
	}

}
