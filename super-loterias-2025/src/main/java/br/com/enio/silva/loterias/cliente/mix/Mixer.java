package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.diversos.LotoUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Mixer {

	public static void main(String[] args) throws IOException {

		int size = 6;

		int times = 2;

		String input = "E:\\loterias\\mega_sena\\jogos\\ell.txt";

		String output = "E:\\loterias\\mega_sena\\jogos\\ell2.txt";

		List<List<Integer>> listas = new ArrayList<>();

		for(int i = 0; i < times; i++) {

			List<Integer> mix = LotoUtils.mix(size, input);
			listas.add(mix);

			System.out.println(mix);
		}

		listas = new ArrayList<>(new HashSet<>(listas));

		ArquivoUtil.saveLists(listas, output, ",", 2);

	}

}
