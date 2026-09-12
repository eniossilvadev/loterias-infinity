package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.diversos.EsquemasLotofacilUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ClienteLofofacilMaisCinco {

	public static void main(String[] args) throws IOException {
		String input = "D:\\Meus Documentos\\�rea de Trabalho\\teste.txt";

		List<List<Integer>> listas = ArquivoUtil.obterLinhasComoListasUnique(input);

		List<List<Integer>> listasFull = EsquemasLotofacilUtil.gerarEsquemaMaisCincoLista(listas);

		// String result = listasFull.stream().map(el -> el.toString())
		// .collect(Collectors.joining("\n"));

		String result = listasFull.stream()
		        .map(el -> el.stream().map(e -> StringUtils.leftPad(e.toString(), 2, "0"))
		                .collect(Collectors.joining("\t")))
		        .collect(Collectors.joining("\n"));

		ArquivoUtil.save(result, input);
	}

}
