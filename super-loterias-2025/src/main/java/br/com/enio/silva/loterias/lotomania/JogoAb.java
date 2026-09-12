package br.com.enio.silva.loterias.lotomania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JogoAb {

	protected Long id;

	protected Map<Integer, Integer> mapConta = new HashMap<>();

	protected Integer[] numeros;

	protected Integer pontuacao = 0;

	protected List<List<Integer>> listaDeJogos = new ArrayList<List<Integer>>();

	public JogoAb(int size, Integer[] numeros) {
		numeros = new Integer[size];
		this.numeros = numeros;
	}

	public JogoAb(int size, List<Integer> lista) {
		numeros = new Integer[size];
		int c = 0;
		for (Integer l : lista) {
			this.numeros[c++] = l;
		}
	}

	public void addPontuacao(Integer pontuacao) {
		this.pontuacao += pontuacao;
	}

	public Long getId() {
		return id;
	}

	public List<List<Integer>> getListaDeJogos() {
		return listaDeJogos;
	}

	public Map<Integer, Integer> getMapConta() {
		return mapConta;
	}

	public Integer[] getNumeros() {
		return numeros;
	}

	public List<Integer> getNumerosAsList() {
		List<Integer> nros = Arrays.asList(numeros);
		Collections.sort(nros);
		return nros;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setListaDeJogos(List<List<Integer>> listaDeJogos) {
		this.listaDeJogos = listaDeJogos;
	}

	public void setMapConta(Map<Integer, Integer> mapConta) {
		this.mapConta = mapConta;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	@Override
	public String toString() {
		return getPontuacao() + "\t: " + getNumerosAsList();
	}
}
