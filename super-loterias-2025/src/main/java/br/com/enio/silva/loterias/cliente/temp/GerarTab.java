package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarTab {

	public static void main(String[] args) throws IOException {
		String input = "D:\\Meus Documentos\\�rea de Trabalho\\apostas-20220628071229.csv";
		List<List<Integer>> lista = ArquivoUtil.obterLinhasComoListasUnique(input, 15);
		System.out.println(lista);
		ArquivoUtil.saveLists(lista, input, ", ", 2);
	}

}
