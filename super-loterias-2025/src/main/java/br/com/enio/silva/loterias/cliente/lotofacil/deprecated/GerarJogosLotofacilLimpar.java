package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosLotofacilLimpar {

	public static void main(String[] args) throws IOException {

		// Input
		String in = "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\combinacao.txt";
		List<List<Integer>> input = ArquivoUtil.obterLinhasComoListasUnique(in);

		// Todos os Resultados
		LotofacilConfig config = new LotofacilConfig15();
		List<List<Integer>> resultados = config.getTodosResultados();

		// Removendo
		input.removeAll(resultados);

		input = new ArrayList<>(new HashSet<>(input));

		String out = "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\depois.txt";
		ArquivoUtil.saveLists(input, out, "\t", 2);
	}
}
