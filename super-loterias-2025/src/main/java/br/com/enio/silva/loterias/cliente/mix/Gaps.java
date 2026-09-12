package br.com.enio.silva.loterias.cliente.mix;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.diversos.LotoUtils;

public class Gaps {

	public static void main(String[] args) {

		int min = 1;
		int max = 80;

		String path = "C:\\loterias\\gerador-apostas\\quina\\config\\SNQ.txt";

		List<Integer> lista = LotoUtils.gaps(min, max, path);

		Collections.sort(lista);
		System.out.println(lista);

	}

}
