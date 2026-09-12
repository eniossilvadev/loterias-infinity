package br.com.enio.silva.loterias.util;

import java.util.List;

public class IdList {

	private int id;

	private List<Integer> lista;

	public IdList(int id, List<Integer> lista) {
		super();
		this.id = id;
		this.lista = lista;
	}

	public int getId() {
		return id;
	}

	public List<Integer> getLista() {
		return lista;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLista(List<Integer> lista) {
		this.lista = lista;
	}

}
