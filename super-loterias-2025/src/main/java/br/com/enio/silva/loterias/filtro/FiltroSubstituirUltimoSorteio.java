package br.com.enio.silva.loterias.filtro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.util.LoteriaUtil;

public class FiltroSubstituirUltimoSorteio extends FiltroAb {

	private static List<Integer> todos = new ArrayList<Integer>();

	private static List<Integer> last = new ArrayList<Integer>();

	private static int maiorNumero;

	private static int maxEquals = 0;

	private static List<Integer> getTodos() {
		List<Integer> tmp = LoteriaUtil.getArrayCompleto(maiorNumero);
		tmp.removeAll(last);
		Collections.shuffle(tmp);
		return tmp;
	}

	public FiltroSubstituirUltimoSorteio(List<Integer> last, int maiorNumero) {
		FiltroSubstituirUltimoSorteio.last = last;
		FiltroSubstituirUltimoSorteio.maiorNumero = maiorNumero;
	}

	public FiltroSubstituirUltimoSorteio(List<Integer> last, int maiorNumero, int maxEquals) {
		FiltroSubstituirUltimoSorteio.last = last;
		FiltroSubstituirUltimoSorteio.maiorNumero = maiorNumero;
		FiltroSubstituirUltimoSorteio.maxEquals = maxEquals;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {

		if (ListUtils.intersection(lista, last).size() <= FiltroSubstituirUltimoSorteio.maxEquals) {
			return lista;
		}

		int size = lista.size();
		lista.removeAll(last);

		todos = getTodos();
		todos.removeAll(lista);

		for (Integer i : todos) {
			lista.add(i);
			if (lista.size() == size) {
				return lista;
			}
		}
		return lista;
	}
}
