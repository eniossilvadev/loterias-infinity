package br.com.enio.silva.loterias.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtil {

	private static DecimalFormat df2 = new DecimalFormat("#0.0000");

	public static <K, V> String getAllKeyValues(Map<K, V> map, String sep) {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			str.append(String.format("%s:\t%s%s", entry.getKey(), entry.getValue(), sep));
		}
		return str.toString();
	}

	public static <K, V extends Comparable<? super V>> List<K> getElements(final Map<K, V> map,
			int n) {
		if (map == null || map.isEmpty() || n < 1) {
			return new ArrayList<>();
		}
		final List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		final List<K> retorno = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			retorno.add(list.get(i % list.size()).getKey());
		}
		return retorno;
	}

	public static String getFrequenciaStr(List<Integer> lista, int minValue, int maxValue) {

		Map<Integer, Integer> mapFrequencia = getMapFrequencia(lista, minValue, maxValue);
		Map<Integer, Integer> map = sortByValueDescShuffe(mapFrequencia);

		StringBuilder str = new StringBuilder();

		int total = lista.size();

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

	public static String getFrequenciaString(List<List<Integer>> lista, int min, int max) {
		final List<Integer> flatList = new ArrayList<>();
		lista.forEach(l -> flatList.addAll(l));
		return getFrequenciaStr(flatList, min, max);
	}

	/**
	 * TODO
	 *
	 * @param lista
	 * @param min
	 * @param max
	 * @return
	 */
	public static List<Integer> getListaOrdenada(List<Integer> lista, int min, int max) {
		return getListaOrdenada(lista, min, max, null);
	}

	/**
	 * TODO
	 *
	 * @param lista
	 * @param min
	 * @param max
	 * @return
	 */
	public static List<Integer> getListaOrdenada(List<Integer> lista, int min, int max, List<Integer> listaExluir) {
		Collections.sort(lista);
		final Map<Integer, Integer> freq = getMapFrequencia(lista, min, max);
		System.err.println(freq);
		final Map<Integer, Integer> sorted = sortByValue(freq);
		final List<Integer> retorno = new ArrayList<>();
		System.err.println(sorted);
		List<Integer> subLista = new ArrayList<>();
		int quantidadeAtual = -1;
		if(listaExluir == null) {
			listaExluir = new ArrayList<>();
		}

		for (Map.Entry<Integer, Integer> entry : sorted.entrySet()) {

			if(!listaExluir.contains(entry.getKey())) {
				int quantidade = entry.getValue();

				if (quantidadeAtual != quantidade && quantidadeAtual >= 0) {
					quantidadeAtual = quantidade;

					Set<Integer> set = new HashSet<>(subLista);
					List<Integer> listaCurr = new ArrayList<>(set);
					Collections.shuffle(listaCurr);
					listaCurr.forEach(l -> retorno.add(l));
					subLista = new ArrayList<>();
					subLista.add(entry.getKey());
				} else {
					quantidadeAtual = quantidade;
					subLista.add(entry.getKey());
				}
			}
		}

		if (subLista != null && !subLista.isEmpty()) {
			Set<Integer> set = new HashSet<>(subLista);
			List<Integer> listaCurr = new ArrayList<>(set);
			Collections.shuffle(listaCurr);
			listaCurr.forEach(l -> retorno.add(l));
		}
		return retorno;
	}

	public static List<Integer> getListaOrdenadaListas(List<List<Integer>> lista, int min,
			int max) {
		final List<Integer> flatList = new ArrayList<>();
		lista.forEach(l -> flatList.addAll(l));
		return getListaOrdenada(flatList, min, max);
	}

	public static Map<Integer, Integer> getMapFrequencia(List<Integer> lista, int min, int max) {
		Map<Integer, Integer> map = new HashMap<>();

		for (int i = min; i <= max; i++) {
			int qttEl = Collections.frequency(lista, i);
			map.put(i, qttEl);
		}

		List<Integer> list = new ArrayList<>(map.keySet());
		Collections.shuffle(list);

		Map<Integer, Integer> shuffleMap = new LinkedHashMap<>();
		list.forEach(k -> shuffleMap.put(k, map.get(k)));

		return map;
	}

	public static Map<Integer, Integer> getMapInvertido(List<List<Integer>> listaCompleta, int max){
		List<Integer> plainFullList = ListaUtils.getPlainFullList(listaCompleta);
		int size = plainFullList.size() / 10;
		Map<Integer, Integer> mapa = new HashMap<>();
		for(int i = 0; i < max; i++) {
			final int curr = i + 1;
			int count = (int) plainFullList.stream().filter(el -> el.equals(Integer.valueOf(curr))).count();
			mapa.put(Integer.valueOf(i), Integer.valueOf(size - count));
		}
		return mapa;
	}

	public static int getMaximo(Map<Integer, Integer> map) {
		Integer max = Integer.MIN_VALUE;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if(entry.getKey().intValue() > max.intValue()) {
				max = entry.getKey();
			}
		}
		return max;
	}

	public static int getMinimo(Map<Integer, Integer> map) {
		Integer min = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if(entry.getKey().intValue() < min.intValue()) {
				min = entry.getKey();
			}
		}
		return min;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> removeAll(Map<K, V> map,
			List<K> lista) {

		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			if (!lista.contains(entry.getKey())) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> removeFromList(final Map<K, V> map,
			final List<K> lista) {
		final Map<K, V> retorno = new HashMap<>(map);

		lista.forEach(l -> retorno.remove(l));

		return retorno;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue2(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());

		Collections.shuffle(list);

		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescShuffe(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.shuffle(list);
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueShuffe(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.shuffle(list);
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {

			@Override
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}
