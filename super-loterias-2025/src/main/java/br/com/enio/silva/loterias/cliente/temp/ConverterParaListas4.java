package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConverterParaListas4 {

	private static String getFileName(String input, String complemento) {
		Path path = Paths.get(input);
		String parent = path.getParent().toString();
		String filename = path.getFileName().toString();
		String nameWithoutDot = filename.split("\\.")[0];
		String extWithDot = "." + filename.split("\\.")[1];

		String novoNome = nameWithoutDot + complemento + extWithDot;
		Path file = Paths.get(parent, novoNome);

		return file.toString();
	}

	/**
	 * Converter uma lista em arquivo para uma lista no formato HTLOTO
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String input = "E:\\loterias\\mega_sena\\x21.txt";

		int esperado = 6;

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		List<List<Integer>> jogosUnique = new LinkedList<>();

		int linha = 1;
		for (List<Integer> jogo : jogos) {
			Collections.replaceAll(jogo, 100, 0);
			// jogo = new ArrayList<Integer>(new HashSet<Integer>(jogo));
			Collections.sort(jogo);
			if (jogo.size() != esperado) {
				System.out.println(">>>> " + jogo);
			}
			if (!jogosUnique.contains(jogo)) {
				jogosUnique.add(jogo);
			} else {
				System.out.println(String.format("Linha repetida %s: %s", linha, jogo));
			}
			linha++;
		}

		// jogos = new ArrayList<List<Integer>>(jogosUnique);

		// Collections.shuffle(jogos);

		// System.out.println("Ordenado");
		// for (List<Integer> jogo : jogos) {
		// System.out.println(jogo.size() + "\t" + jogo);
		// }

		ArquivoUtil.saveLists(jogosUnique, getFileName(input, "01"), "\t");
		ArquivoUtil.saveLists(jogosUnique, getFileName(input, "02"), ",", 2);
		// ArquivoUtil.saveLists(jogos, getFileName(input, "03"), " ");
		// ArquivoUtil.saveLists(jogos, getFileName(input, "04"), "-");
		ArquivoUtil.saveLists(jogos, input, "\t");
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
		// ArquivoUtil.saveLists(allJ, getFileName(input, "00"), ", ");
		String listaStr = all.toString();

		System.out.println(all.size() + " ->\t" + listaStr);

	}
}
