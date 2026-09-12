package br.com.enio.silva.loterias.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GeradorClienteCombination {

	public static void main(String[] args) throws IOException {

		String input = "C:\\loterias\\gerador-apostas\\quina\\config\\qjs\\2022062501.txt";
		String output = input;
		int tamanho = 5;

		List<List<Integer>> listaIn = ArquivoUtil.obterLinhasComoListasUnique(input);
		List<List<Integer>> listaOut = new ArrayList<List<Integer>>();

		for (List<Integer> lista : listaIn) {
			if (lista != null && !lista.isEmpty() && tamanho <= lista.size()) {
				listaOut.addAll(CombinationUtils.gerarCombinacao(lista, tamanho));
			}
		}

		listaOut = new ArrayList<>(new HashSet<>(listaOut));
		ArquivoUtil.saveLists(listaOut, output);
	}

}
