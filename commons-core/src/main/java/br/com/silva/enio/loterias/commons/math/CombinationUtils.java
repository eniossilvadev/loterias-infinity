package br.com.silva.enio.loterias.commons.math;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

// import br.com.silva.enio.loterias.arquivo.ListaUtils;
import br.com.silva.enio.loterias.commons.util.ListaUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class CombinationUtils {

	public static BigInteger calcNumberOfCombinations(int n, int r) {
		BigInteger factn = fact(n);
		BigInteger factr = fact(r);
		BigInteger factnr = fact(n - r);
		return factn.divide(factr).divide(factnr);
	}

	public static void combination(Object[] elements, int K) {
		Iterator<int[]> list = CombinatoricsUtils.combinationsIterator(elements.length, K);

		int[] el = null;
		int count = 0;
		while (list.hasNext()) {
			el = list.next();
			System.out.print(++count + " => ");
			for (int i = 0; i < el.length; i++) {
				System.out.print(el[i] + " ");
			}
			System.out.println();
		}
	}

	private static BigInteger fact(int n) {
		BigInteger res = BigInteger.valueOf(1l);
		for (int i = 2; i <= n; i++) {
			res = res.multiply(BigInteger.valueOf(i));
		}
		return res;
	}

	public static List<List<Integer>> gerarCombinacao(int K, int min, int max,
	        List<Integer> excluir, List<Integer> incluir) {
		return gerarCombinacao(K, min, max, excluir, incluir, false);
	}

	public static List<List<Integer>> gerarCombinacao(int K, int min, int max,
	        List<Integer> excluir, List<Integer> incluir, boolean removerRepetidos) {

		excluir = new ArrayList<>(new HashSet<>(excluir));

		List<Integer> base = new ArrayList<>();
		for (int i = min; i <= max; i++) {
			if (!excluir.contains(i) && !incluir.contains(i)) {
				base.add(i);
			}
		}

		K = K - incluir.size();

		Integer[] baseArr = new Integer[base.size()];
		baseArr = base.toArray(baseArr);

		System.out.println("***********************************************************");
		System.out.println("Excluir: " + excluir);
		System.out.println("Incluir: " + incluir);
		System.out.println("baseArr: " + Arrays.asList(baseArr));
		System.out.println("***********************************************************");

		List<List<Integer>> ret = gerarCombinacao(baseArr, K);
		List<List<Integer>> retorno = new ArrayList<>();
		for (List<Integer> r : ret) {
			r.addAll(incluir);
			int size = r.size();
			r = new ArrayList<>(new HashSet<>(r));
			if (r.size() == size) {
				retorno.add(r);
			} else {
				System.out.println("Ops!\t" + r);
			}
		}

		if (removerRepetidos) {
			retorno = new ArrayList<>(new HashSet<>(retorno));
		}

		return retorno;
	}

	public static List<List<Integer>> gerarCombinacao(Integer[] elements, int K) {

		List<List<Integer>> retorno = new ArrayList<>();

		Iterator<int[]> list = CombinatoricsUtils.combinationsIterator(elements.length, K);

		int[] el = null;
		int count = 0;
		while (list.hasNext()) {

			el = list.next();

			List<Integer> linha = new ArrayList<>();
			for (int i = 0; i < el.length; i++) {
				linha.add(elements[el[i]]);
			}
			retorno.add(linha);
			if (count++ % 10000 == 0) {
				Collector<CharSequence, ?, String> joining = Collectors.joining(", ");
				System.out.println(linha.stream().map(Object::toString).collect(joining));
			}
		}
		return retorno;
	}

	public static List<List<Integer>> gerarCombinacao(List<Integer> elements, int K) {
		Integer[] myArray = new Integer[elements.size()];
		elements.toArray(myArray);
		return gerarCombinacao(myArray, K);
	}

	/**
	 *
	 * @param min
	 *            M�nimo
	 * @param max
	 *            Máximo
	 * @param m
	 *            M em Combina��o
	 * @return
	 */
	public static List<List<Integer>> gerarListaCombinacao(int min, int max, int m) {
		return gerarListaCombinacao(min, max, 1, m);
	}

	// public static void main(String[] args) {
	// // Integer[] base = new Integer[] { 5, 6, 7, 12, 13, 14, 19, 20, 21 };
	// List<Integer> lista = ListaUtils.iterateStream(1, 1, 50);
	// List<List<Integer>> inicial = gerarCombinacao(lista.toArray(new
	// Integer[lista.size()]), 4);
	// System.out.println("Tamanho da lista: " + inicial.size());
	// List<Integer> fixa = Arrays.asList(new Integer[] { 10, 11, 24, 2, 1, 3,
	// 25, 22 });
	//
	// List<List<Integer>> listaFinal = new ArrayList<>();
	//
	// for (List<Integer> list : inicial) {
	// List<Integer> auxiliar = new ArrayList<>(fixa);
	// auxiliar.addAll(list);
	// Collections.sort(auxiliar);
	// listaFinal.add(auxiliar);
	// }
	//
	// System.out.println("Ver");
	// for (List<Integer> l : listaFinal) {
	// //
	// System.out.println(l.stream().map(Object::toString).collect(Collectors.joining("\t")));
	// }
	//
	// }

	/**
	 *
	 * @param min
	 *            M�nimo
	 * @param max
	 *            Máximo
	 * @param p
	 *            Passo
	 * @param m
	 *            M em Combina��o
	 * @return
	 */
	public static List<List<Integer>> gerarListaCombinacao(int min, int max, int p, int m) {
		List<Integer> lista = ListaUtils.iterateStream(min, p, max);
		List<List<Integer>> inicial = gerarCombinacao(lista.toArray(new Integer[lista.size()]), m);
		return inicial;
	}

	public static List<Integer> gerarListaSimples(int min, int max) {
		List<Integer> lista = new ArrayList<>();
		if (min > max) {
			int aux = min;
			min = max;
			max = aux;
		}
		for (int i = min; i <= max; i++) {
			lista.add(i);
		}
		return lista;
	}

	private static List<List<Integer>> getCombinations(int i, Integer... listao) {
		List<List<Integer>> lista = CombinationUtils.gerarCombinacao(listao, 15);
		List<List<Integer>> listaOut = new ArrayList<>();
		for (List<Integer> l : lista) {
			Collections.sort(l);
			listaOut.add(l);
		}
		return listaOut;
	}

	public static void main(String[] args) throws IOException {
	}
}
