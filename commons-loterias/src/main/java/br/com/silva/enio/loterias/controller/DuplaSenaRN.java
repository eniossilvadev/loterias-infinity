package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.DuplaSenaMapper;
import br.com.silva.enio.loterias.model.mapper.LotofacilMapper;

public class DuplaSenaRN implements LoteriaRNAb {

	private static DuplaSenaRN instance = null;

	public static final int MAIOR_NUMERO = 50;

	public static DuplaSenaRN getInstance() {
		if (instance == null) {
			instance = new DuplaSenaRN();
		}
		return instance;
	}

	private DuplaSenaRN() {

	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {

		List<List<Integer>> r1 = new DuplaSenaMapper().getNumerosSorteio1(fileName);
		List<List<Integer>> r2 = new DuplaSenaMapper().getNumerosSorteio2(fileName);
		List<List<Integer>> resultado = new ArrayList<>();

		Iterator<List<Integer>> i1 = r1.iterator();
		Iterator<List<Integer>> i2 = r2.iterator();

		while (i1.hasNext() && i2.hasNext()) {
			List<Integer> n1 = i1.next();
			resultado.add(n1);
			resultado.add(i2.next());
			resultado.add(n1);
		}
		return resultado;
	}

	public List<List<Integer>> getNumerosSorteio1(String fileName) throws IOException {
		return new DuplaSenaMapper().getNumerosSorteio1(fileName);
	}

	public List<List<Integer>> getNumerosSorteio2(String fileName) throws IOException {
		return new DuplaSenaMapper().getNumerosSorteio2(fileName);
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
