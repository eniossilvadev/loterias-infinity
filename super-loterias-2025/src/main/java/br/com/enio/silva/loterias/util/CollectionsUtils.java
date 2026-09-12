package br.com.enio.silva.loterias.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsUtils {

	public static <T> List<T> removeDuplicated(final List<T> lista) {
		if(lista == null) {
			return Collections.emptyList();
		}
		final Set<T> set = new HashSet<>(lista);
		final List<T> listaRetorno = new ArrayList<>(set);
		return listaRetorno;
	}

}
