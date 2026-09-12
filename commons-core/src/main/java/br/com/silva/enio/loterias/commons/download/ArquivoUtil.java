package br.com.silva.enio.loterias.commons.download;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArquivoUtil {

	/**
	 *
	 * @param content
	 * @param caminhoDestino
	 * @throws IOException
	 */
	public static void append(String content, String caminhoDestino) throws IOException {

		File file = new File(caminhoDestino);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fileWritter = new FileWriter(file.getAbsolutePath(), true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(content);
		bufferWritter.close();
	}

	/**
	 * Efetua a c�pia de arquivo
	 *
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static final void copytInputStream(InputStream in, OutputStream out) throws IOException {

		int umByte = 0;
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(out);

		while ((umByte = bis.read()) != -1) {
			bos.write(umByte);
		}

		bis.close();
		in.close();
		bos.close();
		out.close();
	}

	public static void criarCaminhoCompletoSeNaoExistir(String name) throws IOException {

		if (name != null && StringUtils.isNotEmpty(name)) {
			File file = new File(name);
			if (!file.exists()) {
				System.out.print("Criando... " + file.getAbsolutePath());
				file.getParentFile().mkdirs();
				file.createNewFile();
				System.out.println("... done!");
			}
		}
	}

	/**
	 * Obt�m o conte�do do arquivo e retorna uma String.
	 *
	 * @param fileName
	 * @return
	 */
	public static String getContents(String fileName) {
		File aFile = new File(fileName);

		try {
			if (aFile.exists()) {
				// ...checks on aFile are elided
				StringBuilder contents = new StringBuilder();

				// use buffering, reading one line at a time
				// FileReader always assumes default encoding is OK!
				BufferedReader input = new BufferedReader(new FileReader(aFile));
				try {
					String line = null; // not declared within while loop
					/*
					 * readLine is a bit quirky : it returns the content of a
					 * line MINUS the newline. it returns null only for the END
					 * of the stream. it returns an empty String if two newlines
					 * appear in a row.
					 */
					while ((line = input.readLine()) != null) {
						contents.append(line);
						contents.append(System.getProperty("line.separator"));
					}
				} finally {
					input.close();
				}

				return contents.toString();
			} else {
				aFile.createNewFile();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return StringUtils.EMPTY;
	}

	/***********************************************************************************************************************************/

	// TODO Mover para um local adequado.
	public static List<List<Integer>> obterLinhasComoListas(String fileName) {

		// return obterLinhasComoListas(fileName, "\t");
		return obterLinhasComoListas(fileName, "\\t|;| |-|,");
	}

	public static List<List<Integer>> obterLinhasComoListas(String fileName, String separator) {
		List<List<Integer>> sorteios = new ArrayList<List<Integer>>();

		String str = getContents(fileName);
		String[] linhas = str.split(System.getProperty("line.separator"));
		for (String linha : linhas) {
			List<Integer> sorteio = new ArrayList<Integer>();
			String[] numeros = linha.split(separator);
			for (String numero : numeros) {
				if (!StringUtils.isEmpty(numero)) {
					sorteio.add(Integer.valueOf(numero.replace("^[0-9]", "")));
				}
			}
			sorteios.add(sorteio);
		}

		return sorteios;
	}

	// TODO Mover para um local adequado.
	public static List<List<String>> obterLinhasComoListasStr(String fileName) {

		return obterLinhasComoListasStr(fileName, "\\t|;| |-|,");
	}

	public static List<List<String>> obterLinhasComoListasStr(String fileName, String separator) {
		List<List<String>> sorteios = new ArrayList<List<String>>();

		String str = getContents(fileName);
		String[] linhas = str.split(System.getProperty("line.separator"));
		for (String linha : linhas) {
			List<String> sorteio = new ArrayList<String>();
			String[] numeros = linha.split(separator);
			for (String spl : numeros) {
				if (!StringUtils.isEmpty(spl)) {
					sorteio.add(spl);
				}
			}
			sorteios.add(sorteio);
		}
		return sorteios;
	}

	public static List<List<Integer>> obterLinhasComoListasUnique(String fileName) {

		// return obterLinhasComoListas(fileName, "\t");
		return obterLinhasComoListasUnique(fileName, "\\t|;| |-|,");
	}

	public static List<List<Integer>> obterLinhasComoListasUnique(String fileName,
	        String separator) {

		List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

		String str = getContents(fileName);
		String[] linhas = str.split(System.getProperty("line.separator"));
		for (String linha : linhas) {
			List<Integer> sorteio = new ArrayList<Integer>();
			String[] numeros = linha.split(separator);
			for (String numero : numeros) {
				if (!StringUtils.isEmpty(numero)) {
					sorteio.add(Integer.valueOf(numero.replace("^[0-9]", "")));
				}
			}

			Collections.sort(sorteio);

			if (!jogosUnique.contains(sorteio)) {
				jogosUnique.add(sorteio);
			}
		}

		return new ArrayList<List<Integer>>(jogosUnique);
	}

	/**
	 * Salva o conte�do no caminho indicado.
	 *
	 * @param content
	 * @param caminhoDestino
	 * @throws IOException
	 */
	public static void save(String content, String caminhoDestino) throws IOException {

		criarCaminhoCompletoSeNaoExistir(caminhoDestino);

		File arquivo = new File(caminhoDestino);

		FileOutputStream fos = new FileOutputStream(arquivo);
		fos.write(content.getBytes());
		fos.close();
	}

	public static void saveLists(List<List<Integer>> listas, String caminhoDestimo)
	        throws IOException {
		saveLists(listas, caminhoDestimo, "\t");
	}

	/**
	 * Salva a lista indicada em arquivo. Cada lista ser� salva em uma linha.
	 * Cada linha ser� separada por uma tabula��o.
	 *
	 * @param listas
	 * @param caminhoDestimo
	 * @throws IOException
	 */
	public static void saveLists(List<List<Integer>> listas, String caminhoDestimo, String sep)
	        throws IOException {

		StringBuilder str = new StringBuilder();
		for (List<Integer> linha : listas) {
			if (linha != null && linha.size() > 0) {
				str.append(StringUtils.join(linha, sep) + "\r\n");
			}
		}
		if (str.length() > 0) {
			save(str.toString().substring(0, str.length() - 1), caminhoDestimo);
		}
	}

	/**
	 * Salva a lista indicada em arquivo. Cada lista ser� salva em uma linha.
	 * Cada linha ser� separada por uma tabula��o.
	 *
	 * @param listas
	 * @param caminhoDestimo
	 * @throws IOException
	 */
	public static void saveLists(List<List<Integer>> listas, String caminhoDestimo, String sep,
	        Integer pad) throws IOException {

		List<List<String>> newList = new ArrayList<List<String>>();
		for (List<Integer> linha : listas) {
			List<String> l = new ArrayList<String>();
			for (Integer i : linha) {
				l.add(StringUtils.leftPad(i.toString(), pad, "0"));
			}
			newList.add(l);
		}

		StringBuilder str = new StringBuilder();
		for (List<String> linha : newList) {
			str.append(StringUtils.join(linha, sep) + "\r\n");
		}
		if (str.length() > 0) {
			save(str.toString().substring(0, str.length() - 1), caminhoDestimo);
		}
	}

	/**
	 * Salva a lista indicada em arquivo. Cada lista ser� salva em uma linha.
	 * Cada linha ser� separada por uma tabula��o.
	 *
	 * @param listas
	 * @param caminhoDestimo
	 * @throws IOException
	 */
	public static void saveLists(List<List<Integer>> listas, String caminhoDestimo, String sep,
	        Map<String, String> replace) throws IOException {

		StringBuilder str = new StringBuilder();
		for (List<Integer> linha : listas) {
			if (linha != null && linha.size() > 0) {
				str.append(StringUtils.join(linha, sep) + "\r\n");
			}
		}

		if (str.length() > 0) {

			String resultado = str.toString().substring(0, str.length() - 1);

			for (Map.Entry<String, String> entry : replace.entrySet()) {
				String key = entry.getKey();
				resultado = resultado.replaceAll(key, replace.get(key));
			}

			save(resultado, caminhoDestimo);
		}
	}

}
