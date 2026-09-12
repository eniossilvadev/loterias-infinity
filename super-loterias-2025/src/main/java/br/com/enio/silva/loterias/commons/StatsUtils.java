package br.com.enio.silva.loterias.commons;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.util.MapUtil;

public class StatsUtils {

	public static String getFrequencia(List<List<Integer>> resultados) {

		if(resultados == null || resultados.isEmpty()) {
			return StringUtils.EMPTY;
		}

		DecimalFormat df2 = new DecimalFormat("#0.0000");

		StringBuilder str = new StringBuilder();

		int total = resultados.size();

		Map<Integer, Integer> map = getMapResultado(resultados, total);
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int key = entry.getKey();
			int val = entry.getValue();
			if (val > max) {
				max = val;
			}
			if (val < min) {
				min = val;
			}
			double percent = (double) val / total;

			String sKey = String.format("%02d", key);
			str.append(sKey).append("\t").append(val).append("\t").append(df2.format(percent))
			.append("\n");
		}
		str.append("\n");
		str.append("Max: ").append(max).append("\n");
		str.append("Min: ").append(min).append("\n");
		str.append("Dif: ").append(max - min).append("\n");
		return str.toString();
	}

	public static String getFrequenciaSimples(List<List<Integer>> resultados) {
		StringBuilder str = new StringBuilder();

		int total = resultados.size();

		Map<Integer, Integer> map = getMapResultado(resultados, total);
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int key = entry.getKey();
			int val = entry.getValue();
			if (val > max) {
				max = val;
			}
			if (val < min) {
				min = val;
			}
			String sKey = String.format("%02d", key);
			str.append(sKey).append(": ").append(val).append(", ");
		}
		return str.toString();
	}

	public static Map<Integer, Integer> getMapResultado(List<List<Integer>> resultados, int ultimos) {
		ContaNumerosResultados instance = ContaNumerosResultados.getInstance();
		Map<Integer, Integer> map = instance.getMapContaResultados(resultados, ultimos);
		return MapUtil.sortByValueDesc(map);
	}

}
