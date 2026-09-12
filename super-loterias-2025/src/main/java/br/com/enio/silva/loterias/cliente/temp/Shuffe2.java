package br.com.enio.silva.loterias.cliente.temp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class Shuffe2 {

	public static int lines = 1;

	// public static int partes = 2;
	public static int numbers = 7;

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

			int linhas = 0;
			List<Integer> lista = new ArrayList<Integer>();
			while (line != null) {
				linhas++;
				sb.append(line);
				sb.append(System.getProperty("line.separator"));

				System.out.println(line);
				String[] nroStr = line.split(" ");
				for (String s : nroStr) {
					Integer i = new Integer(0);
					try {
						i = new Integer(s);
						lista.add(i);
					} catch (NumberFormatException n) {

					}
				}
				line = br.readLine();
			}

			Set<Integer> list = new TreeSet<Integer>(lista);

			lista = new ArrayList<Integer>(list);

			List<Integer> sublist1 = new ArrayList<Integer>();
			List<Integer> sublist2 = new ArrayList<Integer>();
			List<Integer> sublist3 = new ArrayList<Integer>();

			for (int i = 0; i < lista.size(); i++) {
				int terco = lista.size() / 3;
				int elem = lista.get(i);
				if (i < terco) {
					sublist1.add(elem);
				} else if (i < terco * 2) {
					sublist2.add(elem);
				} else {
					sublist3.add(elem);
				}
			}

			System.out.println(lista);
			System.out.println("Sublistas");
			System.out.println(sublist1);
			System.out.println(sublist2);
			System.out.println(sublist3);

			System.out.println();
			// for(int i = 0; i < linhas; i++) {
			for (int i = 0; i < lines; i++) {

				Collections.shuffle(sublist1);
				Collections.shuffle(sublist2);
				Collections.shuffle(sublist3);

				Set<Integer> l = new TreeSet<Integer>();
				// Set<Integer> l = new TreeSet<Integer>(lista.subList(0,
				// numbers));

				for (int j = 0; l.size() < numbers; j++) {

					Collections.shuffle(sublist1);
					Collections.shuffle(sublist2);
					Collections.shuffle(sublist3);

					if (i % 2 == 0) {
						int s = l.size();

						if (s % 3 == 1) {
							l.add(sublist2.get(0));
						} else if (s % 3 == 0) {
							l.add(sublist1.get(0));
						} else {
							l.add(sublist3.get(0));
						}
					} else {
						if (j % 3 == 1) {
							l.add(sublist1.get(0));
						} else if (j % 3 == 0) {
							l.add(sublist3.get(0));
						} else {
							l.add(sublist2.get(0));
						}
					}
				}

				System.out.println(StringUtils.join(l.toArray(), "\t"));
			}

		} finally {
			br.close();
		}

		// String value =
		// "- :::::: ::::/*-+++!@#$%�&*()_+=INCOPRE INDUSTRIA E COMERCIO S/A
		// 123";

		System.out.println("");
		// System.out.println(value.replaceAll("\\W", " "));

		// System.out.println(value.replaceAll("\\P{Alnum}", " "));

	}

}
