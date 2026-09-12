package br.com.enio.silva.loterias.cliente.mix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ReadAll {

	private final static int defaultSize = 6;

	private final static String defaultSep = "\t";

	private final static String base = "E:\\loterias\\mega_sena\\jogos\\";

	private final static String dir = base;

	private final static String out = base + "out.txt";

	public static void main(String[] args) throws IOException {
		runAll();
	}

	public static void runAll() {

		try {
			System.out.println("Início -> runAll.");

			final List<File> listaDeArquivos = ArquivoUtil.listaTodosArquivosDiretorio(dir);

			final List<List<Integer>> jogosIn = new ArrayList<>();
			listaDeArquivos.forEach(f -> {
				final String fileName = f.getAbsolutePath();
				final List<List<Integer>> curr = ArquivoUtil.obterLinhasComoListasUnique(fileName);

				final List<List<Integer>> currOrdered = LotoUtils.gc(curr);
				ArquivoUtil.safeSaveLists(currOrdered, fileName, defaultSep, 2);

				if (curr != null && !curr.isEmpty()) {
					curr.forEach(c -> {
						if (c != null && !c.isEmpty()) {
							jogosIn.add(c);
						}
					});

				}
			});

			final List<List<Integer>> jogosout = new ArrayList<>();
			jogosIn.forEach(j -> {
				final List<List<Integer>> combinacao = CombinationUtils.gerarCombinacao(j, defaultSize);
				if (combinacao != null && !combinacao.isEmpty()) {
					jogosout.addAll(combinacao);
				} else {
					System.err.println("J: " + j);
				}
			});

			final List<List<Integer>> jogosFinal = LotoUtils.gc(jogosout, defaultSize);

			ArquivoUtil.saveLists(jogosFinal, out, defaultSep, 2);
			saveOthers(jogosFinal);
			System.out.println("Fim -> runAll.");
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	protected static void saveOthers(final List<List<Integer>> jogosFinal) throws IOException {
		final String out1 = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\novos_jogos.txt";
		final String out2 = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\todos_jogos.txt";
		List<String> outs = Stream.of(out1, out2).collect(Collectors.toList());
		outs.forEach(o -> ArquivoUtil.safeSaveLists(jogosFinal, o, defaultSep, 2));
	}

}
