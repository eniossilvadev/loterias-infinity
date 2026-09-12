package br.com.enio.silva.loterias.lotomania;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Retorna um map contendo o número de vezes que um número saiu como resultado.
 *
 * @author Enio
 */
public class ContaNumerosResultados {

	private static ContaNumerosResultados instance = null;

	public static ContaNumerosResultados getInstance() {
		if (instance == null) {
			instance = new ContaNumerosResultados();
		}
		return instance;
	}

	protected ContaNumerosResultados() {
	}

	public Map<Integer, Integer> getMapContaResultados(List<List<Integer>> resultados) {
		Map<Integer, Integer> retorno = new HashMap<Integer, Integer>();
		for (List<Integer> resultado : resultados) {
			for (Integer result : resultado) {
				Integer r = retorno.get(result);
				int qtt = (r == null ? 0 : r) + 1;
				retorno.put(result, qtt);
			}
		}
		return retorno;
	}

	public Map<Integer, Integer> getMapContaResultados(List<List<Integer>> resultados,
	        Integer ultimosJogos) {

		Integer max = resultados.size();
		if (max.intValue() < ultimosJogos.intValue()) {
			return getMapContaResultados(resultados);
		}

		List<List<Integer>> ultimos = resultados.subList(max - ultimosJogos, max - 1);
		return getMapContaResultados(ultimos);
	}

}
