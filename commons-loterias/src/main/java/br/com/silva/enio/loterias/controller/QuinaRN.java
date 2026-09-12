package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.QuinaMapper;

public class QuinaRN implements LoteriaRNAb {

	private static QuinaRN instance = null;

	public static final int MAIOR_NUMERO = 80;

	public static QuinaRN getInstance() {
		if (instance == null) {
			instance = new QuinaRN();
		}
		return instance;
	}

	private QuinaRN() {

	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {
		return new QuinaMapper().getNumerosSorteados(fileName);
	}

	@Override
	public List<List<String>> parseToList(String fileName) throws IOException {
		return new QuinaMapper().getLists(fileName);
	}

}
