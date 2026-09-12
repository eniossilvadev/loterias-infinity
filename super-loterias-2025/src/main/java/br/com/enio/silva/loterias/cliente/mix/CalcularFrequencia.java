package br.com.enio.silva.loterias.cliente.mix;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class CalcularFrequencia {

	public static List<Integer> getBottom(String path, int quantidade, int maxNumber) {
		System.out.println("getBottom");
		final List<Integer> listaOrdenada = getList(path, maxNumber);
		System.out.println(listaOrdenada);
		final List<Integer> lista = getSubList(listaOrdenada, quantidade, false);
		System.out.println(lista);
		return lista;
	}

	private static List<Integer> getList(String path, int maxNumber) {
		System.out.println("getList");
		final List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(path);
		System.out.println(jogosAtuaisFlat);
		final List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1, maxNumber);
		System.out.println(listaOrdenada);
		return listaOrdenada;
	}

	private static List<Integer> getSubList(List<Integer> listaOrdenada, int quantidade,
			boolean reverse) {
		System.out.println("getSubList");
		if (reverse) {
			Collections.reverse(listaOrdenada);
		}
		final List<Integer> lista = listaOrdenada.subList(0, quantidade);
		System.out.println(listaOrdenada);
		Collections.sort(lista);
		System.out.println(lista);
		return lista;
	}

	public static List<Integer> getTop(String path, int quantidade, int maxNumber) {
		System.out.println("getTop");
		final List<Integer> listaOrdenada = getList(path, maxNumber);
		System.out.println(listaOrdenada);
		final List<Integer> lista = getSubList(listaOrdenada, quantidade, true);
		System.out.println(lista);

		return lista;
	}

	public static void main(String[] args) {
		String path = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\SNMS.txt";
		int quantidade = 24;
		int maxNumber = 60;

		//		List<Integer> listaTop = getTop(path, quantidade, maxNumber);
		//		System.out.println(listaTop);

		List<Integer> listaBottom = getBottom(path, quantidade, maxNumber);
		System.out.println(listaBottom);
	}

}
