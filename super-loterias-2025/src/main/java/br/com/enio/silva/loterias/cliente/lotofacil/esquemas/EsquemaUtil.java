package br.com.enio.silva.loterias.cliente.lotofacil.esquemas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EsquemaUtil {

	public static List<Integer> getByPosition(List<Integer> lista, int[] pos) {
		List<Integer> retorno = new ArrayList<Integer>();
		for (int i = 0; i < pos.length; i++) {
			retorno.add(lista.get(pos[i] - 1));
		}
		Collections.sort(retorno);
		return retorno;
	}

	public static List<Integer> getFromMap(List<Integer> lista, Map<Integer, Integer> mapa, int n) {

		List<Integer> retorno = new ArrayList<Integer>();

		for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
			Integer key = entry.getKey();
			if (lista.contains(key)) {
				retorno.add(key);
				if (retorno.size() == n) {
					return retorno;
				}
			}
		}
		return retorno;
	}

	@SafeVarargs
	public static List<Integer> getMergedList(List<Integer>... listas) {
		Set<Integer> set = new HashSet<Integer>();

		int tamElem = 0;
		for (List<Integer> l : listas) {
			// tamElem += l.size();
			set.addAll(l);
		}
		List<Integer> retorno = new ArrayList<Integer>(set);

		if (retorno.size() < tamElem) {
			System.out.println("Os elementos repetidos forma removidos da lista");
		}

		return retorno;

	}

}
