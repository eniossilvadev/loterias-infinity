package br.com.enio.silva.loterias.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderedMap {

	private final Map<Integer, Integer> map;

	private final boolean crescente;

	private final List<IdList> lista = new ArrayList<>();

	public OrderedMap(Map<Integer, Integer> paramMap, boolean crescente) {
		this.map = paramMap;
		this.crescente = crescente;
		this.organize();
	}

	private void add(int id, int value) {
		if(lista.isEmpty()) {
			List<Integer> l = new ArrayList<>();
			l.add(value);
			lista.add(new IdList(id, l));
		} else {
			//			for()
		}
	}

	private void organize() {
		if (crescente) {
			MapUtil.sortByValue(map);
		} else {
			MapUtil.sortByValueDesc(map);
		}

		int currValue = 0;
		int id = 0;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int key = entry.getKey().intValue();
			int value = entry.getValue().intValue();
		}
	}

}
