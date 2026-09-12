package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.silva.enio.loterias.model.mapper.LotofacilMapper;

public class CopyOfLotofacilRN implements LoteriaRNAb {
	
	public static final int MAIOR_NUMERO = 25;

	@Override
	public List<List<String>> parseToList(String fileName) throws IOException {
		return new LotofacilMapper().getLists(fileName);		
	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {
		return new LotofacilMapper().getNumerosSorteados(fileName);
	}
	
	@SuppressWarnings("unchecked")
	public List<List<Integer>> aplicarFiltro(List<List<Integer>> input, List<Integer> ultimoSorteio){
		List<List<Integer>> output = new ArrayList<List<Integer>>();
		
		// Trocar
		int[] repeticoes = {9, 10, 11};
		
		List<List<Integer>> todosInput = (ArrayList<List<Integer>>)((ArrayList<List<Integer>>)input).clone();
		for(List<Integer> in: todosInput){
			int rep = ConferirRN.conferir(in, ultimoSorteio);
			for(int r: repeticoes){
				if(rep == r){
					output.add(in);
					break;
				}
			}
		}
				
		return output;
	}
	
	public List<List<Integer>> aplicarFiltro2(List<List<Integer>> jogos, List<Integer> sorteios){
		List<List<Integer>> output = new ArrayList<List<Integer>>();
		
		
		return output;
	}

}
