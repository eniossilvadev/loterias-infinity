package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.MegaSenaMapper;

public class MegaSenalRN implements LoteriaRNAb {

	private static MegaSenalRN instance = null;

	public static final int MAIOR_NUMERO = 60;

	public static MegaSenalRN getInstance() {
		if (instance == null) {
			instance = new MegaSenalRN();
		}
		return instance;
	}

	private MegaSenalRN() {

	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {
		return new MegaSenaMapper().getNumerosSorteados(fileName);
	}

	@Override
	public List<List<String>> parseToList(String fileName) throws IOException {
		return new MegaSenaMapper().getLists(fileName);
	}

}
