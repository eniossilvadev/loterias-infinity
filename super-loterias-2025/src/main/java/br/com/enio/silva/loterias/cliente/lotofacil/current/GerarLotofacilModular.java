package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;

public abstract class GerarLotofacilModular extends GerarJogosLotofacil2020Ab {

	protected static int getUltimos(List<List<Integer>> resultados, int[] ultimosJogos, int count) {
		int ultimos = ultimosJogos[count % ultimosJogos.length];
		ultimos = ultimosJogos[count % ultimosJogos.length];
		ultimos = ultimos < 10 || ultimos > resultados.size() - 1 ? resultados.size() : ultimos;
		return ultimos;
	}

	protected static List<List<Integer>> getUltimosResultadosCorrentes(int ultimos) {
		List<List<Integer>> ultimosResultados = new LotofacilConfig15().obterTodosResultados();
		Collections.reverse(ultimosResultados);
		ultimosResultados = ultimosResultados.subList(0, ultimos);
		return ultimosResultados;
	}

	protected static List<List<Integer>> getUltimosResultadosCorrentes(
			List<List<Integer>> resultados, int[] ultimosJogos, int count) {
		int ultimos = getUltimos(resultados, ultimosJogos, count);
		return getUltimosResultadosCorrentes(ultimos);
	}
}
