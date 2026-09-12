package br.com.enio.silva.loterias.cliente.mix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class MegaDaVirada {

	public static List<File> getFiles(String dir) {
		String directory = "E:\\loterias\\mega-da-virada";

		try {
			List<File> files = Files.list(Paths.get(directory)).map(Path::toFile)
					.filter(File::isFile).collect(Collectors.toList());

			files.forEach(System.out::println);

			return files;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public static void main(String[] args) throws IOException {
		String directory = "E:\\loterias\\mega-da-virada";
		String pathNovo = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\novos_jogos.txt";
		String pathTodos = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\todos_jogos.txt";
		final String sep = "\t";
		final Integer pad = 2;
		List<File> files = getFiles(directory);
		List<List<Integer>> listaTodos = new ArrayList<>();
		List<List<Integer>> listaNovos = new ArrayList<>();
		files.forEach(f -> {
			String path = f.getAbsolutePath();
			List<List<Integer>> arquivoAtual = ArquivoUtil.obterLinhasComoListasUnique(path);
			try {
				ArquivoUtil.saveLists(arquivoAtual, path, sep, pad);
			} catch (IOException e) {
				e.printStackTrace();
			}
			listaTodos.addAll(arquivoAtual);
		});
		listaTodos.forEach(ll -> {
			if(ll.size() == 6) {
				listaNovos.add(ll);
			}
		});
		ArquivoUtil.saveLists(listaNovos, pathNovo, sep, pad);
		ArquivoUtil.saveLists(listaTodos, pathTodos, sep, pad);

		DesdobraCombinacao.desdobraCombinacao(pathTodos, pathTodos, 6);
	}

}


