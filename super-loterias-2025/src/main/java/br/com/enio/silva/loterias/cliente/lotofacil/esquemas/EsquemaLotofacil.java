package br.com.enio.silva.loterias.cliente.lotofacil.esquemas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class EsquemaLotofacil {

	/*
	 * http://www.comoganharnaloteria.com.br/forum/topico/2115-13-pontos-
	 * garantido -em-qualquer-sorteio-com-apenas-11-jogos/
	 */
	public static List<List<Integer>> getEsquema19n11a(List<Integer> lista) {

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		int[] pos1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 18 };
		int[] pos2 = { 1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 14, 16, 17, 19 };
		int[] pos3 = { 1, 2, 3, 4, 5, 7, 9, 11, 12, 13, 14, 16, 17, 18, 19 };
		int[] pos4 = { 1, 2, 3, 4, 5, 8, 9, 11, 12, 13, 14, 15, 16, 17, 19 };
		int[] pos5 = { 1, 2, 4, 5, 6, 7, 8, 9, 10, 13, 15, 16, 17, 18, 19 };
		int[] pos6 = { 1, 2, 4, 6, 7, 8, 10, 12, 13, 14, 15, 16, 17, 18, 19 };
		int[] pos7 = { 1, 3, 4, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17, 18, 19 };
		int[] pos8 = { 1, 3, 4, 6, 7, 8, 10, 11, 13, 14, 15, 16, 17, 18, 19 };
		int[] pos9 = { 1, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 16, 17, 18, 19 };
		int[] pos10 = { 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 18, 19 };
		int[] pos11 = { 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18 };

		retorno.add(EsquemaUtil.getByPosition(lista, pos1));
		retorno.add(EsquemaUtil.getByPosition(lista, pos2));
		retorno.add(EsquemaUtil.getByPosition(lista, pos3));
		retorno.add(EsquemaUtil.getByPosition(lista, pos4));
		retorno.add(EsquemaUtil.getByPosition(lista, pos5));
		retorno.add(EsquemaUtil.getByPosition(lista, pos6));
		retorno.add(EsquemaUtil.getByPosition(lista, pos7));
		retorno.add(EsquemaUtil.getByPosition(lista, pos8));
		retorno.add(EsquemaUtil.getByPosition(lista, pos9));
		retorno.add(EsquemaUtil.getByPosition(lista, pos10));
		retorno.add(EsquemaUtil.getByPosition(lista, pos11));

		return retorno;
	}

	public static List<List<Integer>> getEsquema19n24j(List<Integer> lista) {

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		int[] pos1 = { 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 15, 16, 17 };
		int[] pos2 = { 1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 16, 18, 19 };
		int[] pos3 = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 18, 19 };
		int[] pos4 = { 1, 2, 3, 4, 5, 6, 8, 10, 11, 12, 14, 15, 16, 18, 19 };
		int[] pos5 = { 1, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15, 16, 18 };
		int[] pos6 = { 1, 2, 4, 5, 7, 8, 10, 11, 12, 13, 14, 15, 16, 18, 19 };
		int[] pos7 = { 1, 3, 4, 5, 6, 7, 8, 10, 11, 13, 14, 15, 16, 18, 19 };
		int[] pos8 = { 1, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 16, 17, 19 };
		int[] pos9 = { 1, 3, 4, 5, 6, 7, 9, 10, 11, 13, 14, 15, 17, 18, 19 };
		int[] pos10 = { 1, 3, 4, 5, 6, 7, 9, 11, 12, 13, 14, 15, 16, 18, 19 };
		int[] pos11 = { 1, 3, 4, 5, 6, 7, 10, 11, 12, 13, 15, 16, 17, 18, 19 };
		int[] pos12 = { 1, 3, 4, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		int[] pos13 = { 1, 3, 5, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
		int[] pos14 = { 1, 4, 5, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19 };
		int[] pos15 = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 16, 17, 19 };
		int[] pos16 = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 15, 17, 18, 19 };
		int[] pos17 = { 2, 3, 4, 5, 6, 7, 8, 10, 11, 13, 14, 15, 16, 18, 19 };
		int[] pos18 = { 2, 3, 4, 5, 6, 7, 9, 10, 11, 13, 15, 16, 17, 18, 19 };
		int[] pos19 = { 2, 3, 4, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18 };
		int[] pos20 = { 2, 3, 4, 6, 7, 8, 10, 11, 12, 13, 14, 15, 16, 18, 19 };
		int[] pos21 = { 2, 3, 5, 6, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 19 };
		int[] pos22 = { 2, 4, 5, 6, 7, 8, 9, 10, 11, 14, 15, 16, 17, 18, 19 };
		int[] pos23 = { 3, 4, 5, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 19 };
		int[] pos24 = { 3, 4, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };

		retorno.add(EsquemaUtil.getByPosition(lista, pos1));
		retorno.add(EsquemaUtil.getByPosition(lista, pos2));
		retorno.add(EsquemaUtil.getByPosition(lista, pos3));
		retorno.add(EsquemaUtil.getByPosition(lista, pos4));
		retorno.add(EsquemaUtil.getByPosition(lista, pos5));
		retorno.add(EsquemaUtil.getByPosition(lista, pos6));
		retorno.add(EsquemaUtil.getByPosition(lista, pos7));
		retorno.add(EsquemaUtil.getByPosition(lista, pos8));
		retorno.add(EsquemaUtil.getByPosition(lista, pos9));
		retorno.add(EsquemaUtil.getByPosition(lista, pos10));
		retorno.add(EsquemaUtil.getByPosition(lista, pos11));
		retorno.add(EsquemaUtil.getByPosition(lista, pos12));
		retorno.add(EsquemaUtil.getByPosition(lista, pos13));
		retorno.add(EsquemaUtil.getByPosition(lista, pos14));
		retorno.add(EsquemaUtil.getByPosition(lista, pos15));
		retorno.add(EsquemaUtil.getByPosition(lista, pos16));
		retorno.add(EsquemaUtil.getByPosition(lista, pos17));
		retorno.add(EsquemaUtil.getByPosition(lista, pos18));
		retorno.add(EsquemaUtil.getByPosition(lista, pos19));
		retorno.add(EsquemaUtil.getByPosition(lista, pos20));
		retorno.add(EsquemaUtil.getByPosition(lista, pos21));
		retorno.add(EsquemaUtil.getByPosition(lista, pos22));
		retorno.add(EsquemaUtil.getByPosition(lista, pos23));
		retorno.add(EsquemaUtil.getByPosition(lista, pos24));

		return retorno;

	}

	public static List<List<Integer>> getEsquema21n21a(List<Integer> lista) {

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		Collections.shuffle(lista);

		int[] pos1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		int[] pos2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 16, 17, 18 };
		int[] pos3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 19, 20, 21 };
		int[] pos4 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18 };
		int[] pos5 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 19, 20, 21 };
		int[] pos6 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 16, 17, 18, 19, 20, 21 };
		int[] pos7 = { 1, 2, 3, 4, 5, 6, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		int[] pos8 = { 1, 2, 3, 4, 5, 6, 10, 11, 12, 13, 14, 15, 19, 20, 21 };
		int[] pos9 = { 1, 2, 3, 4, 5, 6, 10, 11, 12, 16, 17, 18, 19, 20, 21 };
		int[] pos10 = { 1, 2, 3, 4, 5, 6, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int[] pos11 = { 1, 2, 3, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		int[] pos12 = { 1, 2, 3, 7, 8, 9, 10, 11, 12, 13, 14, 15, 19, 20, 21 };
		int[] pos13 = { 1, 2, 3, 7, 8, 9, 10, 11, 12, 16, 17, 18, 19, 20, 21 };
		int[] pos14 = { 1, 2, 3, 7, 8, 9, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int[] pos15 = { 1, 2, 3, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int[] pos16 = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
		int[] pos17 = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 19, 20, 21 };
		int[] pos18 = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 16, 17, 18, 19, 20, 21 };
		int[] pos19 = { 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int[] pos20 = { 4, 5, 6, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };
		int[] pos21 = { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 };

		retorno.add(EsquemaUtil.getByPosition(lista, pos1));
		retorno.add(EsquemaUtil.getByPosition(lista, pos2));
		retorno.add(EsquemaUtil.getByPosition(lista, pos3));
		retorno.add(EsquemaUtil.getByPosition(lista, pos4));
		retorno.add(EsquemaUtil.getByPosition(lista, pos5));
		retorno.add(EsquemaUtil.getByPosition(lista, pos6));
		retorno.add(EsquemaUtil.getByPosition(lista, pos7));
		retorno.add(EsquemaUtil.getByPosition(lista, pos8));
		retorno.add(EsquemaUtil.getByPosition(lista, pos9));
		retorno.add(EsquemaUtil.getByPosition(lista, pos10));
		retorno.add(EsquemaUtil.getByPosition(lista, pos11));
		retorno.add(EsquemaUtil.getByPosition(lista, pos12));
		retorno.add(EsquemaUtil.getByPosition(lista, pos13));
		retorno.add(EsquemaUtil.getByPosition(lista, pos14));
		retorno.add(EsquemaUtil.getByPosition(lista, pos15));
		retorno.add(EsquemaUtil.getByPosition(lista, pos16));
		retorno.add(EsquemaUtil.getByPosition(lista, pos17));
		retorno.add(EsquemaUtil.getByPosition(lista, pos18));
		retorno.add(EsquemaUtil.getByPosition(lista, pos19));
		retorno.add(EsquemaUtil.getByPosition(lista, pos20));
		retorno.add(EsquemaUtil.getByPosition(lista, pos21));

		return retorno;
	}

	// Esquema Jacqueline
	public static void getEsquemaJacqueline() {
		// https://www.youtube.com/watch?v=6c5NauzrO40
	}

	public static void main(String[] args) throws IOException {
		String pathIn = "C:\\loterias\\gerador-apostas\\lotofacil\\out00.txt";
		String pathOut = "C:\\loterias\\gerador-apostas\\lotofacil\\out10.txt";

		List<List<Integer>> input = ArquivoUtil.obterLinhasComoListas(pathIn);

		List<List<Integer>> output = new ArrayList<List<Integer>>();
		for (List<Integer> in : input) {
			output.addAll(getEsquema19n24j(in));
		}
		ArquivoUtil.saveLists(output, pathOut);

	}

}
