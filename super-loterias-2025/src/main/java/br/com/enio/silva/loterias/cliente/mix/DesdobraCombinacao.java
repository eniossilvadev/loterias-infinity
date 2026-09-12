package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class DesdobraCombinacao {

	public static void desdobraCombinacao(String input, String output, int tamanho)
			throws IOException {
		desdobraCombinacao(input, output, tamanho, true);
	}

	public static void desdobraCombinacao(String input, String output, int tamanho,
			boolean removerDuplicados) throws IOException {
		List<List<Integer>> listaOut = gerarDesbobramentos(input, tamanho);

		if (removerDuplicados) {
			listaOut = new ArrayList<>(new HashSet<>(listaOut));
		}
		ArquivoUtil.saveLists(listaOut, output, ",", 2);
	}

	/**
	 * @param input
	 * @param tamanho
	 * @return
	 */
	private static List<List<Integer>> gerarDesbobramentos(String input, int tamanho) {
		List<List<Integer>> listaIn = ArquivoUtil.obterLinhasComoListasUnique(input);
		List<List<Integer>> listaOut = new ArrayList<List<Integer>>();

		for (List<Integer> lista : listaIn) {
			if (lista != null && !lista.isEmpty() && tamanho <= lista.size()) {
				listaOut.addAll(CombinationUtils.gerarCombinacao(lista, tamanho));
			}
		}
		return listaOut;
	}

	public static void main(String[] args) throws IOException {
		String input = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\BSEF_20230907_independencia_20230907023501273.txt";
		String output = input;
		int tamanho = 15;

		desdobraCombinacao(input, output, tamanho);
	}

}
