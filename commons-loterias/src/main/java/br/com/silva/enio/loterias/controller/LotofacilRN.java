package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.LotofacilMapper;

public class LotofacilRN implements LoteriaRNAb {

	private static LotofacilRN instance = null;

	public static final int MAIOR_NUMERO = 25;

	public static LotofacilRN getInstance() {
		if (instance == null) {
			instance = new LotofacilRN();
		}
		return instance;
	}

	private LotofacilRN() {

	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {
		return new LotofacilMapper().getNumerosSorteados(fileName);
	}

	public List<Integer> obterTodosNumeros() {
		List<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < MAIOR_NUMERO; i++) {
			numeros.add(i + 1);
		}
		return numeros;
	}

	@Override
	public List<List<String>> parseToList(String fileName) throws IOException {
		return new LotofacilMapper().getLists(fileName);
	}

}
