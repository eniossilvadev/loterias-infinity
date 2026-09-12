package br.com.enio.silva.loterias.diversos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.ListOfListComparator2;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LotoUtils {

	private final static String defaultSep = ",";

	public static List<Integer> gaps(int min, int max, String path) {

		List<Integer> lista = ArquivoUtil.obterLinhasComoLista(path);
		lista = new ArrayList<>(new HashSet<>(lista));
		Collections.sort(lista);
		final List<Integer> list = new ArrayList<>(lista);
		List<Integer> all = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());

		List<Integer> restante = all.stream().filter(el -> !list.contains(el))
				.collect(Collectors.toList());
		return restante;
	}

	public static List<List<Integer>> gc(List<List<Integer>> theList) {
		theList = CollectionsUtils.removeDuplicated(theList);
		theList.sort(new ListOfListComparator());
		return theList;
	}

	public static List<List<Integer>> gc(List<List<Integer>> theList, int size) {
		theList = CombinationUtils.gerarCombinacoes(theList, size);
		theList = CollectionsUtils.removeDuplicated(theList);
		theList.sort(new ListOfListComparator());
		return theList;
	}

	public static List<List<Integer>> gcc2(List<List<Integer>> theList, int size) {
		theList = CombinationUtils.gerarCombinacoes(theList, size);
		theList = CollectionsUtils.removeDuplicated(theList);
		theList.sort(new ListOfListComparator2());
		return theList;
	}

	public static List<List<Integer>> getAll(String dir) {

		System.out.println("Início.");

		final List<File> listaDeArquivos = ArquivoUtil.listaTodosArquivosDiretorio(dir);

		final List<List<Integer>> jogosIn = new ArrayList<>();
		listaDeArquivos.forEach(f -> {
			final String fileName = f.getAbsolutePath();
			final List<List<Integer>> curr = ArquivoUtil.obterLinhasComoListasUnique(fileName);

			if (curr != null && !curr.isEmpty()) {
				curr.forEach(c -> {
					if (c != null && !c.isEmpty()) {
						jogosIn.add(c);
					}
				});

			}
		});

		final List<List<Integer>> jogosFinal = new ArrayList<>(jogosIn);

		System.out.println("Fim.");
		return jogosFinal;
	}

	public static List<List<Integer>> getAll(String dir, int defaultSize) {

		return getAll(dir, defaultSize, false);
	}

	public static List<List<Integer>> getAll(String dir, int defaultSize, boolean isCombination) {

		File file = new File(dir);
		if (file == null || !file.exists() || !file.isDirectory()) {
			return new ArrayList<>();
		}

		System.out.println("Início.");

		final List<File> listaDeArquivos = ArquivoUtil.listaTodosArquivosDiretorio(dir);

		final List<List<Integer>> jogosIn = new ArrayList<>();
		listaDeArquivos.forEach(f -> {
			final String fileName = f.getAbsolutePath();
			final List<List<Integer>> curr = ArquivoUtil.obterLinhasComoListasUnique(fileName);

			final List<List<Integer>> currOrdered = isCombination ? LotoUtils.gc(curr, defaultSize)
					: LotoUtils.gc(curr);
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

		System.out.println("Fim.");
		return jogosFinal;
	}

	public static List<Integer> getAllInSingleList(String dir, int defaultSize){
		List<List<Integer>> lista = getAll(dir, defaultSize);
		List<Integer> retorno = new ArrayList<>();
		if(lista != null && !lista.isEmpty()) {
			lista.forEach(l -> retorno.addAll(l));
		}
		return retorno;
	}

	/**
	 * Remove linhas excedentes
	 *
	 * @param path
	 * @throws IOException
	 */
	public static void limpaLinhas(String path) throws IOException {
		final List<List<Integer>> lista = ArquivoUtil.obterLinhasComoListasUniqueFromWeb(path);
		ArquivoUtil.saveLists(lista, path, "\t", 2);
	}

	public static void limparLoto(String path, int groupBy, String sep) throws IOException {
		final String content = ArquivoUtil.getContents(path, groupBy, sep);
		ArquivoUtil.save(content, path);
	}

	public static void limparLoto(String path, String sep) throws IOException {
		final String content = ArquivoUtil.getContents(path, sep);
		ArquivoUtil.save(content, path);
	}

	public static List<Integer> mix(int size, String path) throws IOException {

		List<Integer> lista = ArquivoUtil.obterLinhasComoLista(path);

		lista = new ArrayList<>(new HashSet<>(lista));

		Collections.shuffle(lista);

		lista = lista.subList(0, size);

		Collections.sort(lista);

		// List<List<Integer>> listas =
		// ArquivoUtil.obterLinhasComoListasUnique(path);
		//
		// listas.add(lista);
		//
		// listas = new ArrayList<>(new HashSet<>(listas));
		//
		// ArquivoUtil.saveLists(listas, path, ", ", 2);

		return lista;
	}

	public static List<Integer> obterNumerosQueMaisSairam(List<List<Integer>> input, int size) {

		final ContaNumerosResultados contador = ContaNumerosResultados.getInstance();

		Map<Integer, Integer> mapResultado = contador.getMapContaResultados(input);

		Map<Integer, Integer> mapTmp = MapUtil.sortByValueDescShuffe(mapResultado);

		Map<Integer, Integer> map = new HashMap<>(mapTmp);

		map = MapUtil.sortByValueDescShuffe(map);

		List<Integer> lista = MapUtil.getElements(map, size);

		return lista;
	}

	public static List<Integer> obterNumerosQueMenosSairam(List<List<Integer>> input, int size) {

		final ContaNumerosResultados contador = ContaNumerosResultados.getInstance();

		Map<Integer, Integer> mapResultado = contador.getMapContaResultados(input);

		Map<Integer, Integer> mapTmp = MapUtil.sortByValueShuffe(mapResultado);

		Map<Integer, Integer> map = new HashMap<>(mapTmp);

		map = MapUtil.sortByValueShuffe(map);

		List<Integer> lista = MapUtil.getElements(map, size);

		return lista;
	}

	public static void removerDuplicados(String path, String sep) throws IOException {
		removerDuplicados(path, sep, false);
	}

	public static void removerDuplicados(String path, String sep, boolean sortAndClean) throws IOException {
		final List<List<Integer>> lists = ArquivoUtil.obterLinhasComoListas(path);
		if(sortAndClean) {
			final List<List<Integer>> cleanLists = gc(lists);
			ArquivoUtil.saveLists(cleanLists, path, sep, 2);
		} else {
			final List<List<Integer>> cleanLists = new ArrayList<>(new HashSet<>(lists));
			ArquivoUtil.saveLists(cleanLists, path, sep, 2);
		}

	}

	public static void removerPorTamanho(String path, String sep, int tam) throws IOException {
		final List<List<Integer>> lists = ArquivoUtil.obterLinhasComoListas(path);
		List<List<Integer>> lista = new ArrayList<>(lists);
		lista.removeIf(el -> el.size() != tam);
		ArquivoUtil.saveLists(lista, path, sep, 2);
	}

	public static List<List<Integer>> union(List<List<Integer>>... theList) {

		if (theList == null || theList.length == 0) {
			return Collections.emptyList();
		}

		if (theList.length == 1) {
			return theList[0];
		}

		final List<List<Integer>> all = new ArrayList<>();
		for (List<List<Integer>> tl : theList) {
			tl.forEach(l -> all.add(l));
		}

		List<List<Integer>> union = gc(all);
		return union;
	}

	public static List<List<Integer>> unionLists(List<List<Integer>> lista1,
			List<List<Integer>> lista2) {

		if ((lista1 == null && lista2 == null) || (lista1.isEmpty() && lista2.isEmpty())) {
			return Collections.emptyList();
		}

		if (lista1 == null || lista1.isEmpty()) {
			return lista2;
		}

		if (lista2 == null || lista2.isEmpty()) {
			return lista1;
		}

		final List<List<Integer>> all = new ArrayList<>(lista1);
		all.addAll(lista2);

		List<List<Integer>> union = gc(all);
		return union;
	}
}
