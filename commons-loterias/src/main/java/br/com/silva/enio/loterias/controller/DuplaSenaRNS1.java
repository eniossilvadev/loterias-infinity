package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.DuplaSenaMapper;
import br.com.silva.enio.loterias.model.mapper.LotofacilMapper;

public class DuplaSenaRNS1 implements LoteriaRNAb {

	private static DuplaSenaRNS1 instance = null;

	public static final int MAIOR_NUMERO = 50;

	public static DuplaSenaRNS1 getInstance() {
		if (instance == null) {
			instance = new DuplaSenaRNS1();
		}
		return instance;
	}

	private DuplaSenaRNS1() {

	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {

		return new DuplaSenaMapper().getNumerosSorteio1(fileName);
	}

	public List<List<Integer>> getNumerosSorteio1(String fileName) throws IOException {
		return getNumerosSorteados(fileName);
	}

	public List<List<Integer>> getNumerosSorteio2(String fileName) throws IOException {
		return getNumerosSorteados(fileName);
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
