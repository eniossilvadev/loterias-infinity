package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConverterParaListasClean {

	/**
	 * Converter uma lista em arquivo para uma lista no formato HTLOTO
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String input = "E:\\loterias\\lotomania\\info\\HOJE.txt";

		String base = input.split("[.]")[0];
		String output = base + "_%s.txt";

		System.out.println(output);

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		List<List<Integer>> jogosUnique = new LinkedList<>();

		System.out.println("Original");
		int linha = 1;
		for (List<Integer> jogo : jogos) {
			// System.out.println(jogo.size() + "\t" + jogo);
			Collections.replaceAll(jogo, 100, 0);
			jogo = new ArrayList<Integer>(new HashSet<Integer>(jogo));
			Collections.sort(jogo);
			if (!jogosUnique.contains(jogo)) {
				jogosUnique.add(jogo);
			} else {
				System.out.println(String.format("Linha repetida %s: %s", linha, jogo));
			}
			linha++;
		}

		jogos = new ArrayList<List<Integer>>(jogosUnique);

		Collections.shuffle(jogos);

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
		}

		// ArquivoUtil.saveLists(jogos, String.format(output, "01"), "\t", 2);
		ArquivoUtil.saveLists(jogos, String.format(output, "02"), ",", 2);
		// ArquivoUtil.saveLists(jogos, String.format(output, "03"), " ");
		// ArquivoUtil.saveLists(jogos, String.format(output, "04"), "-");
		// ArquivoUtil.saveLists(jogos, input, "\t");
		List<List<Integer>> js = new ArrayList<List<Integer>>();
		for (List<Integer> jogo : jogos) {
			List<Integer> j = new ArrayList<>(jogo);
			Collections.shuffle(j);
			Collections.shuffle(j);
			js.add(j);
		}
		// ArquivoUtil.saveLists(js, String.format(output, "05"), "\t");

		Set<Integer> all = new TreeSet<Integer>();
		for (List<Integer> j : jogos) {
			all.addAll(j);
		}
		List<List<Integer>> allJ = new ArrayList<List<Integer>>();
		allJ.add(new ArrayList<Integer>(all));
		// ArquivoUtil.saveLists(allJ, String.format(output, "00"), ", ");
		String listaStr = all.toString();

		System.out.println(all.size() + " ->\t" + listaStr);
	}
}
