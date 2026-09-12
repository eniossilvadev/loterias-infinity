package br.com.enio.silva.loterias.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.config.ListOfListComparator;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ListaUtils {

	public static Map<Integer, Integer> countListRangeList(List<Integer> lista, int max) {
		List<Integer> range = getListaRange(1, max);
		final Map<Integer, Integer> map = new HashMap<>();

		range.forEach(el -> {
			map.put(el, Integer.valueOf(Collections.frequency(lista, el)));
		});

		return map;
	}

	public static Map<Integer, Integer> countListRangeLists(List<List<Integer>> lista, int max) {
		return countListRangeList(getPlainFullList(lista), max);
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

	public static List<List<Integer>> getAll(String dir){

		File file = new File(dir);
		if(file == null || !file.exists() || !file.isDirectory()) {
			return new ArrayList<>();
		}

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

		return jogosIn;
	}

	public static List<List<Integer>> getAll(String dir, int defaultSize){
		File file = new File(dir);
		if(file == null || !file.exists() || !file.isDirectory()) {
			return new ArrayList<>();
		}

		final List<List<Integer>> jogosIn = getAll(dir);

		final List<List<Integer>> jogosout = new ArrayList<>();
		jogosIn.forEach(j -> {
			final List<List<Integer>> combinacao = CombinationUtils.gerarCombinacao(j, defaultSize);
			if (combinacao != null && !combinacao.isEmpty()) {
				jogosout.addAll(combinacao);
			}
		});

		final List<List<Integer>> jogosFinal = gc(jogosout, defaultSize);
		return jogosFinal;
	}

	public static <T> String getAsString(List<T> lista) {
		return lista.stream().map(e -> e.toString()).collect(Collectors.joining(","));
	}

	public static <T> String getAsString(List<T> lista, int pad) {
		return lista.stream().map(e -> StringUtils.leftPad(e.toString(), pad, "0")).collect(Collectors.joining(","));
	}

	public static List<Integer> getElements(int min, int max, int numberOfElements) {
		if(numberOfElements < 1) {
			return new ArrayList<>();
		}

		List<Integer> lista = getListaRange(min, max);
		Collections.shuffle(lista);
		return lista.subList(0, numberOfElements);
	}

	public static <T> Set<T> getFromList(List<T> lista, int size) {
		Set<T> listaRetorno = new HashSet<>();
		for (int i = 0; i < size; i++) {
			Collections.shuffle(lista);
			listaRetorno.add(lista.remove(0));
		}
		return listaRetorno;
	}

	public static List<Integer> getListaOrdenadaMinimos(String path, int max, int elements) {
		return getListaOrdenadaMinimos(path, max, elements, null);
	}

	public static List<Integer> getListaOrdenadaMinimos(String path, int max, int elements,
			List<Integer> excluir) {
		List<Integer> plainList = ArquivoUtil.obterLinhasComoLista(path);
		List<Integer> listaOrdenada = MapUtil.getListaOrdenada(plainList, 1, max, excluir); // do
		// menor
		// para
		// o
		// maior
		List<Integer> subList = listaOrdenada.subList(0, elements);

		System.out.println("getListaOrdenadaMinimos (plainList): " + plainList);
		System.out.println("getListaOrdenadaMinimos (listaOrdenada): " + listaOrdenada);
		System.out.println("getListaOrdenadaMinimos (subList): " + subList);

		return subList;
	}

	public static List<Integer> getListaRange(int min, int max) {
		return IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
	}

	public static <T> List<T> getListOneMinusListTwo(List<T> listOne, List<T> listTwo){
		List<T> listOneCopy = new ArrayList<>(listOne);
		List<T> listTwoCopy = new ArrayList<>(listTwo);
		listOneCopy.removeAll(listTwoCopy);
		return listOneCopy;
	}

	public static <T> List<T> getPlainFullList(List<List<T>> fullList) {

		if (fullList != null && !fullList.isEmpty()) {

			return fullList.stream().flatMap(List::stream).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	public static <T> boolean isNullOrEmpty(List<List<T>> lista){
		return lista != null && !lista.isEmpty();
	}

	public static List<Integer> iterateStream(int from, int step, int limit) {
		return IntStream.iterate(from, i -> i + step) // next int
				.limit(limit) // only numbers in range
				//				.limit(limit / step) // only numbers in range
				.boxed().collect(Collectors.toList());
	}

	public static void main(String[] args) throws IOException {
		String origem = "E:\\loterias\\mega_sena\\info\\L0.txt";
		String destino = "E:\\loterias\\mega_sena\\info\\L0_S%s.txt";
		List<List<Integer>> input = ArquivoUtil.obterLinhasComoListas(origem);
		List<List<List<Integer>>> output = split(input);

		ArquivoUtil.saveLists(output.get(0), String.format(destino, "01"));
		ArquivoUtil.saveLists(output.get(1), String.format(destino, "02"));
	}

	public static List<List<Integer>> regerarSemRepetidos(final List<List<Integer>> in) {
		final Set<List<Integer>> set = new HashSet<>(in);
		final List<List<Integer>> out = new ArrayList<>(set);
		return out;
	}

	public static <T> List<List<T>> removerDaLista(final List<List<T>> principal, final List<List<T>> remover){

		if(isNullOrEmpty(principal) || isNullOrEmpty(remover)) {
			return principal;
		}

		List<List<T>> listaRetorno = new ArrayList<>(principal);
		listaRetorno.removeAll(remover);

		return listaRetorno;
	}

	public static List<List<Integer>> removerRepetidos(List<List<Integer>> in) {

		List<List<Integer>> out = new ArrayList<>();

		int count = 0;
		for (List<Integer> l : in) {
			count++;
			if (!out.contains(l)) {
				Collections.sort(l);
				out.add(l);
			} else {
				System.out.println(String.format("Linha repetida %s: %s", count, l));
			}
		}

		return out;
	}

	public static <T> List<List<List<T>>> split(List<List<T>> lOriginal) {

		List<List<T>> l1 = new ArrayList<>();
		List<List<T>> l2 = new ArrayList<>();
		int count = 0;
		for (List<T> l : lOriginal) {
			if (count++ % 2 == 0) {
				l1.add(l);
			} else {
				l2.add(l);
			}
		}

		List<List<List<T>>> retorno = new ArrayList<>();
		retorno.add(l1);
		retorno.add(l2);

		return retorno;
	}

}
