package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarCombByList {

	public static void main(String[] args) throws IOException {

		int size = 16;
		String path = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJA.txt";
		String out = "D:\\Meus Documentos\\�rea de Trabalho\\y.txt";
		String x = "D:\\Meus Documentos\\�rea de Trabalho\\x.txt";

		LotofacilConfig config = new LotofacilConfig15();
		List<List<Integer>> resultados = config.getTodosResultados();

		Map<Integer, Integer> map = config.getMapResultado(resultados, resultados.size());
		Map<Integer, Integer> sortedMap = MapUtil.sortByValue(map);

		List<Integer> elements = MapUtil.getElements(sortedMap, size);

		List<List<Integer>> combinacao = CombinationUtils.gerarCombinacao(elements, size - 1);
		System.out.println(combinacao.size());
		combinacao.forEach(System.out::println);

		combinacao.removeAll(resultados);
		System.out.println(combinacao.size());
		combinacao.forEach(System.out::println);

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListasUnique(path);
		combinacao.removeAll(jogos);
		System.out.println(combinacao.size());
		combinacao.forEach(System.out::println);

		List<List<Integer>> curr = ArquivoUtil.obterLinhasComoListasUnique(x);
		combinacao.removeAll(curr);
		System.out.println(combinacao.size());
		combinacao.forEach(System.out::println);

		final List<List<Integer>> outList = new ArrayList<List<Integer>>();
		combinacao.forEach(el -> {
			Collections.sort(el);
			outList.add(el);
		});

		ArquivoUtil.saveLists(outList, out, ", ", 2);
	}

}
