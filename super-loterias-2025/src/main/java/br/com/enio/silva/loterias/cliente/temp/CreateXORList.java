package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class CreateXORList {

	/**
	 *
	 */
	private static List<List<Integer>> getListUnique(String input) {
		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		// Set<List<Integer>> jogosUnique = new HashSet<List<Integer>>();
		List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

		System.out.println("Original");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
			// Collections.replaceAll(jogo, 100, 0);
			Collections.sort(jogo);
			if (!jogosUnique.contains(jogo)) {
				jogosUnique.add(jogo);
			}
		}

		return new ArrayList<List<Integer>>(jogosUnique);
	}

	public static void main(String[] args) throws IOException {
		String base = "arquivos\\";
		String lista1 = "lista01.txt";
		String lista2 = "lista02.txt";
		String newName = "XOR.txt";
		String input1 = base + lista1;
		String input2 = base + lista2;
		String output = base + (StringUtils.isNotEmpty(newName) ? newName : lista1);
		String outputsp = StringUtils.replace(output, ".txt", "") + "_1.txt";
		String outputtab = StringUtils.replace(output, ".txt", "") + "_2.txt";
		String outputthif = StringUtils.replace(output, ".txt", "") + "_3.txt";
		String outputtvirgula = StringUtils.replace(output, ".txt", "") + "_4.txt";
		String todos = StringUtils.replace(output, ".txt", "") + "_5.txt";

		List<List<Integer>> l1 = getListUnique(input1);
		List<List<Integer>> l2 = getListUnique(input2);

		List<List<Integer>> jogos = ListUtils.union(l1, l2);
		List<List<Integer>> tmp2 = ListUtils.intersection(l1, l2);

		jogos.removeAll(tmp2);

		ArquivoUtil.saveLists(jogos, outputsp, " ");
		ArquivoUtil.saveLists(jogos, outputtab, "\t");
		ArquivoUtil.saveLists(jogos, outputthif, "-");
		ArquivoUtil.saveLists(jogos, outputtvirgula, ", ");

		Set<Integer> all = new TreeSet<Integer>();
		for (List<Integer> j : jogos) {
			all.addAll(j);
		}
		List<List<Integer>> allJ = new ArrayList<List<Integer>>();
		allJ.add(new ArrayList<Integer>(all));
		ArquivoUtil.saveLists(allJ, todos, ", ");
		String listaStr = all.toString();
		System.out.println(all.size() + " ->\t" + listaStr);

	}

}
