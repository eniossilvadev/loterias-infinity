package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConverterParaJogos {

	public static void main(String[] args) throws IOException {
		String input = "D:\\Meus Documentos\\�rea de Trabalho\\apostas-20220709102424.csv";
		List<List<Integer>> lista = ArquivoUtil.obterLinhasComoListasUniqueFromWeb(input, 15);
		// ArquivoUtil.saveLists(lista, String.format(input, "02"), ",", 2);
		System.out.println(lista);
		ArquivoUtil.saveLists(lista, input, "\t", 2);
	}

}
