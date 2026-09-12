package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConverterParaListas3 {

	public static void main(String[] args) throws IOException {

		String input = "E:\\loterias\\dupla_sena\\config\\novos_jogos.txt";
		List<List<Integer>> listaIn = ArquivoUtil.obterLinhasComoListas(input);
		List<List<Integer>> listaOut = new ArrayList<List<Integer>>(new HashSet<>(listaIn));
		Collections.sort(listaOut, new Comparator<List<Integer>>() {

			@Override
			public int compare(List<Integer> o1, List<Integer> o2) {
				int s1 = o1.size();
				int s2 = o2.size();
				if (s1 == s2) {
					Collections.sort(o1);
					Collections.sort(o2);
					for (int i = 0; i < s1; i++) {
						int el1 = o1.get(i);
						int el2 = o2.get(i);
						if (el1 != el2) {
							return el1 - el2;
						}
					}
				}
				return s1 - s2;
			}
		});
		ArquivoUtil.saveLists(listaOut, input, ",");
	}
}
