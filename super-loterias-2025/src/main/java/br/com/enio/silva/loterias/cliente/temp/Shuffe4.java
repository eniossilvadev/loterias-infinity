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

public class Shuffe4 {

	public static int lines = 1;

	// public static int partes = 2;
	public static int numbers = 7;

	public static int max_number = 60;

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

			List<Integer> lista = new ArrayList<Integer>();
			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));

				System.out.println(line);
				String[] nroStr = line.split("\t");
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

			System.out.println();
			// for(int i = 0; i < linhas; i++) {
			for (int i = 0; i < lines; i++) {

				Collections.shuffle(lista);

				Set<Integer> l = new TreeSet<Integer>(lista.subList(0, numbers));

				System.out.println(StringUtils.join(l.toArray(), "\t"));
			}

		} finally {
			br.close();
		}

		System.out.println("");
	}

}
