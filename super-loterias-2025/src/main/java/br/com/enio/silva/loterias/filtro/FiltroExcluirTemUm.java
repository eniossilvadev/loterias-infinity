package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class FiltroExcluirTemUm extends FiltroAb {

	List<Integer> listaExcluirUm = null;

	public FiltroExcluirTemUm(List<Integer> lista) {
		this.listaExcluirUm = lista;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		int equals = ListUtils.intersection(lista, listaExcluirUm).size();

		if (equals == 0) {
			return lista;
		}
		return new ArrayList<Integer>();
	}

}
