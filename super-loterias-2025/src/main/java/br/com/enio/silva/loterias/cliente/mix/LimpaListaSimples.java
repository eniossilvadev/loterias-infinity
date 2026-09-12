package br.com.enio.silva.loterias.cliente.mix;

import java.util.List;

import br.com.enio.silva.loterias.config.ListOfListComparator;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LimpaListaSimples {

	public static void main(String[] args) {
		saveListSimples("C:\\loterias\\gerador-apostas\\lotofacil\\curr\\B03_ELL.txt");
	}

	/**
	 * Não mexer
	 */
	public static void saveListSimples() {
		saveListSimples("C:\\loterias\\python-utils\\full-results\\data\\lotofacil.txt");
	}

	/**
	 * Não mexer
	 */
	public static void saveListSimples(String f) {
		saveListSimples(f, ",");
	}

	/**
	 * Não mexer
	 */
	public static void saveListSimples(String f, String sep) {
		final String out = f;
		final List<List<Integer>> listas = ArquivoUtil.obterLinhasComoListas(f);
		ArquivoUtil.safeSaveLists(listas, out, sep, 2);
	}

	public static void saveListSimplesUnique() {
		final String f = "D:\\Meus Documentos\\Downloads\\3060_15.txt";
		final String out = f;
		final String sep = ",";

		final List<List<Integer>> listas = ArquivoUtil.obterLinhasComoListasUnique(f);
		listas.sort(new ListOfListComparator());
		ArquivoUtil.safeSaveLists(listas, out, sep, 2);
	}

}
