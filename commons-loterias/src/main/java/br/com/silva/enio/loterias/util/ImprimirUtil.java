package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ImprimirUtil {

	public static ImprimirUtil getInstancia() {
		return imprimirUtil;
	}

	public static String printLista(List<? extends Object> lista) {
		if (lista instanceof List<?>) {
			;
		}
		return StringUtils.join(lista, "\t");
	}

	public static String printListas(List<List<Integer>> sorteios) {
		str = new StringBuilder();

		Iterator<List<Integer>> it = sorteios.iterator();
		while (it.hasNext()) {
			lista = it.next();
			str.append(printLista(lista)).append("\n");
		}

		return str.toString();
	}

	private static ImprimirUtil imprimirUtil = new ImprimirUtil();

	static StringBuilder str = null;

	static List<Integer> lista = new ArrayList<Integer>();
}
