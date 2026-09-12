package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.List;

public interface LoteriaRNAb {
	
	public List<List<String>> parseToList(String fileName) throws IOException;
	
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException;
}
