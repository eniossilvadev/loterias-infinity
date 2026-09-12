package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.diversos.EsquemasLotofacilUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarEsquemaX {

	public static void main(String[] args) throws IOException {
		String input = "D:\\Meus Documentos\\�rea de Trabalho\\x.txt";
		// String input = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJA.txt";
		String output = "D:\\Meus Documentos\\�rea de Trabalho\\y.txt";

		List<List<Integer>> listas = ArquivoUtil.obterLinhasComoListasUnique(input);

		// List<List<Integer>> listasFull =
		// EsquemasLotofacilUtil.gerarEsquemaXLista(listas);
		List<List<Integer>> listasFull = EsquemasLotofacilUtil.gerarEsquemaXLista(listas, 5);

		String result = listasFull.stream()
		        .map(el -> el.stream().map(e -> StringUtils.leftPad(e.toString(), 2, "0"))
		                .collect(Collectors.joining("\t")))
		        .collect(Collectors.joining("\n"));

		ArquivoUtil.save(result, output);
		ArquivoUtil.saveLists(listas, input, "\t", 2);
	}

}
