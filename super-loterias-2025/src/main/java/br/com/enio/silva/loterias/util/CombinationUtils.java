package br.com.enio.silva.loterias.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.util.CombinatoricsUtils;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

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

		try {

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
		} catch (Exception e) {
			return null;
		}
	}

	public static List<List<Integer>> gerarCombinacao(Integer[] elements, int K) {

		try {

			List<List<Integer>> retorno = new ArrayList<>();

			Iterator<int[]> list = CombinatoricsUtils.combinationsIterator(elements.length, K);

			int[] el = null;
			while (list.hasNext()) {

				el = list.next();

				List<Integer> linha = new ArrayList<>();
				for (int i = 0; i < el.length; i++) {
					linha.add(elements[el[i]]);
				}
				retorno.add(linha);
			}
			return retorno;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<List<Integer>> gerarCombinacao(List<Integer> elements, int K) {
		Integer[] myArray = new Integer[elements.size()];
		elements.toArray(myArray);
		return gerarCombinacao(myArray, K);
	}

	public static List<List<Integer>> gerarCombinacaoComFixos(List<Integer> fixos, List<Integer> variaveis, int K){
		final List<List<Integer>> comb = gerarCombinacao(variaveis, K);
		final List<List<Integer>> retorno = new ArrayList<>();

		comb.forEach(l -> {
			List<Integer> curr = new ArrayList<>(l);
			curr.addAll(fixos);
			Collections.sort(curr);
			retorno.add(curr);
		});

		return retorno;
	}

	public static List<List<Integer>> gerarCombinacoes(List<List<Integer>> listaIn, int K) {
		List<List<Integer>> retorno = new ArrayList<>();
		for (List<Integer> lista : listaIn) {
			if (lista != null && !lista.isEmpty() && K <= lista.size()) {
				retorno.addAll(CombinationUtils.gerarCombinacao(lista, K));
			}
		}
		return retorno;
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
		int size = 15;
		String jogo7 = "C:\\loterias\\gerador-apostas\\lotofacil\\tmp.txt";
		List<List<Integer>> l0 = ArquivoUtil.obterLinhasComoListasUnique(jogo7);

		List<List<Integer>> l1 = getCombinations(size, 15, 9, 10, 3, 24, 11, 2, 18, 22, 23, 12, 6,
				8, 16, 21, 7);
		List<List<Integer>> l2 = getCombinations(size, 5, 9, 10, 3, 24, 11, 2, 18, 22, 23, 12, 6, 8,
				16, 21, 7);

		List<List<Integer>> l3 = getCombinations(size, 17, 9, 10, 3, 24, 11, 2, 18, 22, 23, 12, 6,
				8, 16, 21, 7);
		Set<List<Integer>> set = new HashSet<>(l0);
		set.addAll(l1);
		set.addAll(l2);
		set.addAll(l3);

		System.out.println("Set size " + set.size());
		List<List<Integer>> listaOut = new ArrayList<>(set);

		LotofacilConfig config = new LotofacilConfig15();
		List<List<Integer>> resultados = config.getTodosResultados();

		// Removendo
		listaOut.removeAll(resultados);

		String corrente = "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\pessoal.txt";
		resultados = ArquivoUtil.obterLinhasComoListasUnique(corrente);
		listaOut.removeAll(resultados);

		corrente = "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\bolao_sef.txt";
		resultados = ArquivoUtil.obterLinhasComoListasUnique(corrente);
		listaOut.removeAll(resultados);

		corrente = "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\ELL.txt";
		resultados = ArquivoUtil.obterLinhasComoListasUnique(corrente);
		listaOut.removeAll(resultados);

		String path = "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\combinacao.txt";
		ArquivoUtil.saveLists(listaOut, path, "\t", 2);
	}
}
