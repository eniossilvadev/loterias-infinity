package br.com.enio.silva.loterias.cliente.lotofacil.esquemas;

import java.util.ArrayList;
import java.util.List;

public class EsquemaVencendoLotofacil {

	public static List<List<Integer>> obterPadraoOito16Numeros(List<Integer> lista) {

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		int[] pos1 = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] pos2 = { 1, 2, 3, 4, 5, 6, 7, 9 };
		int[] pos3 = { 1, 2, 3, 4, 5, 6, 8, 9 };
		int[] pos4 = { 1, 2, 3, 4, 5, 7, 8, 9 };
		int[] pos5 = { 1, 2, 3, 4, 6, 7, 8, 9 };
		int[] pos6 = { 1, 2, 3, 5, 6, 7, 8, 9 };
		int[] pos7 = { 1, 2, 4, 5, 6, 7, 8, 9 };
		int[] pos8 = { 1, 3, 4, 5, 6, 7, 8, 9 };
		int[] pos9 = { 2, 3, 4, 5, 6, 7, 8, 9 };

		retorno.add(EsquemaUtil.getByPosition(lista, pos1));
		retorno.add(EsquemaUtil.getByPosition(lista, pos2));
		retorno.add(EsquemaUtil.getByPosition(lista, pos3));
		retorno.add(EsquemaUtil.getByPosition(lista, pos4));
		retorno.add(EsquemaUtil.getByPosition(lista, pos5));
		retorno.add(EsquemaUtil.getByPosition(lista, pos6));
		retorno.add(EsquemaUtil.getByPosition(lista, pos7));
		retorno.add(EsquemaUtil.getByPosition(lista, pos8));
		retorno.add(EsquemaUtil.getByPosition(lista, pos9));

		return retorno;
	}
}
