package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.TimemaniaMapper;

public class TimemaniaRN implements LoteriaRNAb {

	private static TimemaniaRN instance = null;

	public static final int MAIOR_NUMERO = 80;

	public static TimemaniaRN getInstance() {
		if (instance == null) {
			instance = new TimemaniaRN();
		}
		return instance;
	}

	private TimemaniaRN() {

	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {
		return new TimemaniaMapper().getNumerosSorteados(fileName);
	}

	@Override
	public List<List<String>> parseToList(String fileName) throws IOException {
		return new TimemaniaMapper().getLists(fileName);
	}

}
