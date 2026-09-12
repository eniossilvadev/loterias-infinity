package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarEspelhosLotomanis {

	static int max = 80;

	private static List<Integer> getTodos(int max) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 1; i <= max; i++) {
			tmp.add(i);
		}
		return tmp;
	}

	public static void main(String[] args) throws IOException {
		String nome = "file.txt";

		String base = ""; // "C:\\loterias\\lotomania\\1644\\SEF\\";

		boolean divide = true;

		String input = base + nome;
		String newName = "fileOut.txt";
		String output = base + (StringUtils.isNotEmpty(newName) ? newName : nome);

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

		System.out.println("Original");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
			// Collections.replaceAll(jogo, 0, max);
			Collections.sort(jogo);
			if (!jogosUnique.contains(jogo)) {
				jogosUnique.add(jogo);

				List<Integer> espelho = getTodos(max);
				espelho.removeAll(jogo);
				jogosUnique.add(espelho);

			}
		}

		jogos = new ArrayList<List<Integer>>(jogosUnique);

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
		}

		if (divide) {
			List<List<Integer>> jogoDividido = new ArrayList<List<Integer>>();
			for (List<Integer> j : jogos) {
				jogoDividido.add(j.subList(0, j.size() / 2));
				jogoDividido.add(j.subList(j.size() / 2, j.size()));
			}

			jogos = jogoDividido;
		}

		ArquivoUtil.saveLists(jogos, output, "\t");

	}

}
