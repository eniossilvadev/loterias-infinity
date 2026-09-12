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

public class ConverterParaListas {

	/**
	 * Converter uma lista em arquivo para uma lista no formato HTLOTO
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String input = "D:\\Meus Documentos\\�rea de Trabalho\\x.txt";

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListasUnique(input);

		List<List<Integer>> jogosUnique = new LinkedList<>();

		System.out.println("Original");
		int linha = 1;
		for (List<Integer> jogo : jogos) {
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

		ArquivoUtil.saveLists(jogos, String.format(input, "02"), ",", 2);

		List<List<Integer>> js = new ArrayList<List<Integer>>();
		for (List<Integer> jogo : jogos) {
			List<Integer> j = new ArrayList<>(jogo);
			Collections.shuffle(j);
			Collections.shuffle(j);
			js.add(j);
		}

		Set<Integer> all = new TreeSet<Integer>();
		for (List<Integer> j : jogos) {
			all.addAll(j);
		}
		List<List<Integer>> allJ = new ArrayList<List<Integer>>();
		allJ.add(new ArrayList<Integer>(all));
		String listaStr = all.toString();

		System.out.println(all.size() + " ->\t" + listaStr);
	}
}
