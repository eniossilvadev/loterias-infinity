package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConverterParaListas2 {

	public static String countDupl(List<Integer> lista, Set<Integer> set) {

		List<Integer> listaOriginal = new ArrayList<>(lista);
		Map<Integer, Integer> mapa = new HashMap<>();
		List<Integer> listaAtual = new ArrayList<>();
		for (Integer s : set) {
			int qtt = listaOriginal.size();
			listaAtual.add(s);
			listaOriginal.removeAll(listaAtual);
			qtt = qtt - listaOriginal.size();
			mapa.put(s, qtt);
		}

		mapa = MapUtil.sortByValueDesc(mapa);

		StringBuilder str = new StringBuilder();
		int primeiro = -1;
		int ultimo = -1;
		for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
			Integer value = entry.getValue();
			if (primeiro < 0) {
				primeiro = value;
			}
			ultimo = value;
			str.append(entry.getKey() + ":\t" + value + "\n");
		}
		str.append("Diferen�a:\t" + Math.abs(primeiro - ultimo));

		return str.toString();
	}

	/**
	 * Converter uma lista em arquivo para uma lista no formato HTLOTO
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String nome = "t0.txt";

		String base = "C:\\loterias\\gerador-apostas\\quina\\config\\temp\\";

		String newName = nome;
		String input = base + nome;
		String output = base + (StringUtils.isNotEmpty(newName) ? newName : nome);
		// String outputsp = StringUtils.replace(output, ".txt", "") + "_1.txt";
		String outputtab = StringUtils.replace(output, ".txt", "") + "_2.txt";
		// String outhif = StringUtils.replace(output, ".txt", "") + "_3.txt";
		// String outvirg = StringUtils.replace(output, ".txt", "") + "_4.txt";
		String todos = StringUtils.replace(output, ".txt", "") + "_5.txt";
		String shuffle = StringUtils.replace(output, ".txt", "") + "_6.txt";
		String contador = StringUtils.replace(output, ".txt", "") + "_7.txt";

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		// Set<List<Integer>> jogosUnique = new HashSet<List<Integer>>();
		List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

		System.out.println("Original");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
			Collections.replaceAll(jogo, 100, 0);
			jogo = new ArrayList<Integer>(new HashSet<Integer>(jogo));
			Collections.sort(jogo);
			if (!jogosUnique.contains(jogo)) {
				jogosUnique.add(jogo);
			}
		}

		jogos = new ArrayList<List<Integer>>(jogosUnique);

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
		}

		// ArquivoUtil.saveLists(jogos, outputsp, " ");
		ArquivoUtil.saveLists(jogos, outputtab, "\t");
		// ArquivoUtil.saveLists(jogos, outputthif, "-");
		// ArquivoUtil.saveLists(jogos, outputtvirgula, ",", 2);

		List<List<Integer>> jogosShuffle = new ArrayList<List<Integer>>();
		for (List<Integer> jogo : jogos) {
			List<Integer> j = new ArrayList<>(jogo);
			Collections.shuffle(j);
			Collections.shuffle(j);
			jogosShuffle.add(j);
		}
		ArquivoUtil.saveLists(jogosShuffle, shuffle, "\t");

		Set<Integer> all = new TreeSet<>();
		List<Integer> allLists = new ArrayList<>();
		for (List<Integer> j : jogos) {
			all.addAll(j);
			allLists.addAll(j);
		}
		Collections.sort(allLists);

		List<List<Integer>> allJ = new ArrayList<List<Integer>>();
		allJ.add(new ArrayList<Integer>(all));
		ArquivoUtil.saveLists(allJ, todos, ", ");
		String listaStr = all.toString();
		System.out.println(all.size() + " ->\t" + listaStr);
		String duplicados = countDupl(allLists, all);
		System.out.println(duplicados);
		ArquivoUtil.save(duplicados, contador);
		// ArquivoUtil.save(listaStr, todos);
	}
}
