package br.com.enio.silva.loterias.lotomania;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Retorna um map contendo o atraso de um número.
 *
 * @author Enio
 */
public class ContaAtrasosResultados {

	private static ContaAtrasosResultados instance = null;

	public static ContaAtrasosResultados getInstance(Integer nro) {
		if (instance == null) {
			instance = new ContaAtrasosResultados(nro);
		}
		return instance;
	}

	private final Map<Integer, Integer> retorno = new HashMap<Integer, Integer>();

	protected ContaAtrasosResultados(Integer nro) {

		for (int i = 0; i < nro; i++) {
			retorno.put(i + 1, 0);
		}
	}

	public Map<Integer, Integer> getMapContaResultados(List<List<Integer>> resultados) {

		for (List<Integer> resultado : resultados) {

			for (Map.Entry<Integer, Integer> entry : retorno.entrySet()) {

				Integer key = entry.getKey();

				if (resultado.contains(key)) {

					retorno.put(key, 0);
				} else {

					Integer r = retorno.get(key);
					int qtt = (r == null ? 0 : r) + 1;
					retorno.put(key, qtt);
				}
			}
		}
		return retorno;
	}
}
