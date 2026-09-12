package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.config.Config;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LotoStatsUtil {

	public static Map<Integer, Integer> getDezenasQueMaisSairam(Config config) {
		List<List<Integer>> todosResultados = config.getTodosResultados();

		Map<Integer, Integer> map = ContaNumerosResultados.getInstance()
				.getMapContaResultados(todosResultados, todosResultados.size());

		return MapUtil.sortByValueDesc(map);

	}

	public static void main(String[] args) throws IOException {
		String path = "D:\\Meus Documentos\\�rea de Trabalho\\x.txt";
		Map<Integer, Integer> dezenasQueMaisSairam = getDezenasQueMaisSairam(
				new LotofacilConfig15());
		final String msg = "[Número, Quantidade de vezes] ";
		String map = dezenasQueMaisSairam.keySet().stream()
				.map(key -> msg + " = [" + key + ",\t" + dezenasQueMaisSairam.get(key) + "]")
				.collect(Collectors.joining("\n"));
		ArquivoUtil.save(map, path);
	}


}
