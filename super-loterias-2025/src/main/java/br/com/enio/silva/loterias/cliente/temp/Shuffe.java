package br.com.enio.silva.loterias.cliente.temp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Shuffe {

	public static int lines = 1;

	public static int numbers = 6;

	public static boolean usesSet = true;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		System.out.println("New Shuffle");
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			List<List<Integer>> linhas = ArquivoUtil.obterLinhasComoListas("file.txt");

			List<Integer> lista = new ArrayList<Integer>();
			for (List<Integer> li : linhas) {
				lista.addAll(li);
			}

			Collections.sort(lista);
			System.out.println("Lista" + lista);
			if (usesSet) {
				Set<Integer> list = new TreeSet<Integer>(lista);
				lista = new ArrayList<Integer>(list);
			}

			System.out.println();
			for (int i = 0; i < lines; i++) {
				Collections.shuffle(lista);
				Set<Integer> l = new TreeSet<>();

				Iterator<Integer> itLista = lista.iterator();
				while (l.size() < numbers) {
					l.add(itLista.next());
				}

				System.out.println(StringUtils.join(l.toArray(), "\t"));
			}

		} finally {
			br.close();
		}

		System.out.println("");
	}

}
