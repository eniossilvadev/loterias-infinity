package br.com.silva.enio.loterias.commons.arquivo;

import static java.util.stream.Collectors.joining;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.silva.enio.loterias.commons.exceptions.FileFormatException;
import br.com.silva.enio.loterias.commons.exceptions.GeneralRuntimeException;
import br.com.silva.enio.loterias.commons.math.CombinationUtils;
import org.apache.commons.lang3.StringUtils;


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

				Path pathToFile = Paths.get(aFile.getAbsolutePath());
				Files.createDirectories(pathToFile.getParent());
				Files.createFile(pathToFile);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return StringUtils.EMPTY;
	}

	public static String getContents(String fileName, int groupBy, String sep) {
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
					StringBuilder strLine = new StringBuilder();
					int count = 1;
					while ((line = input.readLine()) != null) {

						if (StringUtils.isNotBlank(line)) {
							if (line.length() == 2 && isNumeric(line)) {
								strLine.append(line).append(sep);

								int controle = count++ % groupBy;
								if (controle == 0) {
									strLine.append(System.getProperty("line.separator"));
								}
							}
						}
					}
					contents.append(strLine.toString().trim());
					contents.append(System.getProperty("line.separator"));
				} finally {
					input.close();
				}

				return contents.toString();
			} else {

				Path pathToFile = Paths.get(aFile.getAbsolutePath());
				Files.createDirectories(pathToFile.getParent());
				Files.createFile(pathToFile);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return StringUtils.EMPTY;
	}

	public static String getContents(String fileName, String sep) {
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
					StringBuilder strLine = new StringBuilder();
					while ((line = input.readLine()) != null) {

						if (StringUtils.isNotBlank(line) && line.length() == 2 && isNumeric(line)) {
							strLine.append(line).append(sep);
						} else {
							strLine.append(System.getProperty("line.separator"));
						}
					}
					contents.append(strLine.toString().trim());
					contents.append(System.getProperty("line.separator"));
				} finally {
					input.close();
				}

				return contents.toString();
			} else {

				Path pathToFile = Paths.get(aFile.getAbsolutePath());
				Files.createDirectories(pathToFile.getParent());
				Files.createFile(pathToFile);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return StringUtils.EMPTY;
	}

	public static List<String> getContentsAsList(String fileName) {



		File aFile = new File(fileName);

		try {
			if (aFile.exists()) {
				// ...checks on aFile are elided
				List<String> contents = new ArrayList<>();

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
						contents.add(line);
					}
				} finally {
					input.close();
				}

				return contents;
			} else {

				Path pathToFile = Paths.get(aFile.getAbsolutePath());
				Files.createDirectories(pathToFile.getParent());
				Files.createFile(pathToFile);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return Collections.emptyList();
	}

	public static Path getNewNameCurrentFolder(String fullPath, String newName) throws IOException {

		File f = new File(fullPath);

		Path newFile = Paths.get(f.getParentFile().getAbsolutePath(), newName);

		return Files.createFile(newFile);
	}

	public static Path getNewNameCurrentFolderWithSuffix(String fullPath, String suffix)
			throws IOException {

		File f = new File(fullPath);

		String parent = f.getParent();
		String fullFileName = f.getName();
		String[] fileNameWithExt = fullFileName.split("\\.");

		Path newFile = Paths.get(parent, fileNameWithExt[0] + suffix + "." + fileNameWithExt[1]);

		System.out.println(newFile.toString());

		if (newFile.toFile().exists()) {
			return newFile;
		}

		return newFile;
	}

	private static boolean isNumeric(String s) {
		try {
			Integer.parseInt(s.trim());
		} catch (NumberFormatException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static List<File> listaTodosArquivosDiretorio(String directory) {
		// Reading only files in the directory
		try {
			List<File> files = Files.list(Paths.get(directory)).map(Path::toFile)
					.filter(File::isFile).collect(Collectors.toList());

			return files;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public static List<Integer> obterLinhasComoLista(String fileName) {

		// return obterLinhasComoListas(fileName, "\t");
		return obterLinhasComoLista(fileName, "\\t|;| |-|,|\\.|/");
	}

	public static List<Integer> obterLinhasComoLista(String fileName, String separator) {

		List<Integer> todosNumeros = new ArrayList<>();

		String str = getContents(fileName);
		String[] linhas = str.split(System.getProperty("line.separator"));
		for (String linha : linhas) {
			String[] numeros = linha.split(separator);
			for (String numero : numeros) {
				if (!StringUtils.isEmpty(numero)) {
					todosNumeros.add(Integer.valueOf(numero.replace("^[0-9]", "")));
				}
			}
		}

		return todosNumeros;
	}

	/***********************************************************************************************************************************/

	// TODO Mover para um local adequado.
	public static List<List<Integer>> obterLinhasComoListas(String fileName) {

		// return obterLinhasComoListas(fileName, "\t");
		return obterLinhasComoListas(fileName, "\\t|;| |-|,|[.]");
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
		return obterLinhasComoListasUnique(fileName, "\\t|;| |-|,|\\.|/");
	}

	public static List<List<Integer>> obterLinhasComoListasUnique(String fileName, int size) {
		List<List<Integer>> retorno = new ArrayList<>();

		List<List<Integer>> temp = obterLinhasComoListasUnique(fileName, "\\t|;| |-|,|\\.|/");
		for (List<Integer> t : temp) {
			t = new ArrayList<>(new HashSet<>(t));
			if (t != null && t.size() == size) {
				retorno.add(t);
			} else if (t != null && t.size() != size) {
				System.out.println("A linha contem um jogo com erro: " + t);
			}
		}

		return retorno;
	}

	public static List<List<Integer>> obterLinhasComoListasUnique(String fileName,
			String separator) {

		try {
			List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

			String str = getContents(fileName);
			String[] linhas = str.split(System.lineSeparator());
			int countRepetido = 0;
			int linhaCorrente = 0;
			for (String linha : linhas) {
				linhaCorrente++;
				List<Integer> sorteio = new ArrayList<Integer>();
				String[] numbers = linha.split(separator);
				for (String numero : numbers) {
					if (!StringUtils.isEmpty(numero)) {
						String replace = numero.replace("^[0-9]", "");
						if (!replace.isEmpty()) {

							try {
								sorteio.add(Integer.valueOf(replace));
							} catch (NumberFormatException e) {
								throw new GeneralRuntimeException("Falha ao converter valor '" + replace + "'", e);
							}
						}
					}
				}

				Collections.sort(sorteio);

				if (!jogosUnique.contains(sorteio)) {
					jogosUnique.add(sorteio);
				} else {
					String format = ">>> Linha Repetida (%s, %s):\t%s";
					String contador = StringUtils.leftPad("" + ++countRepetido, 4, "0");
					String contadorLinhaCorrente = StringUtils.leftPad("" + linhaCorrente, 4, "0");
					String linhaRepetida = sorteio.stream().map(Object::toString)
							.collect(joining(","));
					System.out.printf((format) + "%n", contador, contadorLinhaCorrente, linhaRepetida);
				}
			}

			return new ArrayList<List<Integer>>(jogosUnique);
		} catch(Exception e){
			throw new FileFormatException("Falha ao processar arquivo '" + fileName + "'", e);
		}
	}

	public static List<List<Integer>> obterLinhasComoListasUniqueDesdobrado(String fileName,
			int size) {
		List<List<Integer>> jogosUnique = obterLinhasComoListasUnique(fileName);
		List<List<Integer>> retorno = new ArrayList<>();

		jogosUnique.forEach(j -> {
			if (j.size() > size) {
				List<List<Integer>> aux = CombinationUtils.gerarCombinacao(j, size);
				retorno.addAll(aux);
			} else {
				retorno.add(j);
			}
		});

		return retorno;
	}

	public static List<List<Integer>> obterLinhasComoListasUniqueFromWeb(String fileName) {

		List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

		String str = getContents(fileName);
		System.out.println(fileName);
		String[] linhas = str.split(System.getProperty("line.separator"));
		for (String linha : linhas) {
			List<Integer> sorteio = new ArrayList<Integer>();
			String[] els = linha.split("\\t|;| |-|,|\\.|/");
			for (String el : els) {
				try {
					Integer num = Integer.parseInt(el.trim());
					sorteio.add(num);
				} catch (NumberFormatException nfe) {
					System.out.println(nfe.getMessage());
				}
			}
			Collections.sort(sorteio);
			if (!jogosUnique.contains(sorteio)) {
				jogosUnique.add(sorteio);
			}
		}

		return new ArrayList<List<Integer>>(jogosUnique);
	}

	public static List<List<Integer>> obterLinhasComoListasUniqueFromWeb(String fileName,
			int size) {

		List<List<Integer>> jogosUnique = new LinkedList<List<Integer>>();

		String str = getContents(fileName);
		System.out.println(fileName);
		String[] linhas = str.split(System.getProperty("line.separator"));
		for (String linha : linhas) {
			List<Integer> sorteio = new ArrayList<Integer>();
			String[] els = linha.split("\\t|;| |-|,|\\.|/");
			for (String el : els) {
				try {
					Integer num = Integer.parseInt(el.trim());
					sorteio.add(num);
				} catch (NumberFormatException nfe) {
					System.out.println(nfe.getMessage());
				}
			}
			Collections.sort(sorteio);
			if (!jogosUnique.contains(sorteio)) {
				jogosUnique.add(sorteio);
			}
		}

		return new ArrayList<List<Integer>>(jogosUnique);
	}

	public static void safeSave(String content, String caminhoDestino) {
		try {
			save(content, caminhoDestino);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean safeSaveLists(List<List<Integer>> listas, String caminhoDestimo,
			String sep, Integer pad) {
		try {
			saveLists(listas, caminhoDestimo, sep, pad);
			return true;
		} catch (IOException e) {
			return false;
		}
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

		System.out.println("Caminho Destino: " + caminhoDestino);
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
			if (linha != null && !linha.isEmpty()) {
				List<String> l = new ArrayList<String>();
				for (Integer i : linha) {
					l.add(StringUtils.leftPad(i.toString(), pad, "0"));
				}
				newList.add(l);
			}
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

	public static void saveLists(List<List<Integer>> listas, String caminhoDestimo, String sep,
			Map<String, String> replace, Integer pad) throws IOException {

		List<List<String>> newList = new ArrayList<List<String>>();
		for (List<Integer> linha : listas) {
			if (linha != null && !linha.isEmpty()) {
				List<String> l = new ArrayList<String>();
				for (Integer i : linha) {
					l.add(StringUtils.leftPad(i.toString(), pad, "0"));
				}
				Collections.sort(l);
				newList.add(l);
			}
		}

		StringBuilder str = new StringBuilder();
		for (List<String> linha : newList) {
			if (linha != null && linha.size() > 0) {
				str.append(StringUtils.join(linha, sep) + "\r\n");
			}
		}

		if (str.length() > 0) {

			String resultado = str.toString().substring(0, str.length() - 1);

			if (replace != null && !replace.isEmpty()) {
				for (Map.Entry<String, String> entry : replace.entrySet()) {
					String key = entry.getKey();
					resultado = resultado.replaceAll(key, replace.get(key));
				}
			}

			save(resultado, caminhoDestimo);
		}
	}

}
