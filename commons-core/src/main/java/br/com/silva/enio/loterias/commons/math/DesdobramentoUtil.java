package br.com.silva.enio.loterias.commons.math;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DesdobramentoUtil {

	/**
	 * Retorna uma lista de todas as combina��es (sem duplicatas) dos elementos
	 * da lista especificada.
	 *
	 * Exemplo: Suponhamos que a lista dada � "[a, b, c]", ent�o oresultado �
	 * uma lista de listas como se segue: "[[], [a], [a, b], [a, c],[b], [b, c],
	 * [c]]".
	 *
	 * @param <T>
	 *            O tipo de elementos
	 * @param elemento
	 *            Os elementos
	 * @return todas as combina��es dos elementos
	 */
	public static <T extends Comparable<? super T>> List<List<T>> encontrarCombinacoes(
	        Collection<T> elements) {
		List<List<T>> result = new ArrayList<List<T>>();

		for (int i = 0; i <= elements.size(); i++) {
			result.addAll(encontrarCombinacoes(elements, i));
		}

		return result;
	}

	/**
	 * Retorna uma lista de todas as combina��es (sem duplicatas) dos elementos
	 * da Lista espec�fica, compreendendo um certo número.
	 *
	 * Exemplo 1: Suponha-se que a lista dada � "[a, b, c, d]" e o Número � 2,
	 * ent�o o resultado wre uma lista de listas como se segue: "[[a, b], [a,
	 * c], [a, d], [b, c], [b, d], [], [c, d]]".
	 *
	 * @param <T>
	 *            O tipo de elementos
	 * @param elemento
	 *            Os elementos
	 * @param n
	 *            O número de elementos
	 * @return todas as combina��es dos elementos
	 */
	public static <T extends Comparable<? super T>> List<List<T>> encontrarCombinacoes(
	        Collection<T> elements, int n) {
		List<List<T>> result = new ArrayList<List<T>>();

		if (n == 0) {
			result.add(new ArrayList<T>());

			return result;
		}

		List<List<T>> combinations = encontrarCombinacoes(elements, n - 1);
		for (List<T> combination : combinations) {
			for (T element : elements) {
				if (combination.contains(element)) {
					continue;
				}

				List<T> list = new ArrayList<T>();

				list.addAll(combination);

				if (list.contains(element)) {
					continue;
				}

				list.add(element);
				Collections.sort(list);

				if (result.contains(list)) {
					continue;
				}

				result.add(list);
			}
		}

		return result;
	}

	public static List<List<Integer>> encontrarDesdobramentos(List<List<Integer>> entradas) {
		List<List<Integer>> out = encontrarDesdobramentos2(entradas);
		for (List<Integer> o : out) {
			Collections.sort(o);
		}
		return out;
	}

	@SuppressWarnings("unchecked")
	public static List<List<Integer>> encontrarDesdobramentos2(List<List<Integer>> entradas) {
		List<List<Integer>> out = new ArrayList<List<Integer>>();

		if (entradas == null || entradas.size() == 0 || entradas.isEmpty()) {
			out.add(new ArrayList<Integer>());
			return out;
		}

		List<Integer> firstList = entradas.get(0);
		List<List<Integer>> e = (List<List<Integer>>) ((ArrayList<List<Integer>>) entradas).clone();
		e.remove(firstList);

		List<List<Integer>> retorno = null;
		// List<Integer> ret = null;
		// Iterator<List<Integer>> it;
		for (Integer elem : firstList) {
			retorno = encontrarDesdobramentos2(e);
			// it = retorno.iterator();
			// ret = null;
			// for( ; it.hasNext(); ret = it.next()){
			for (List<Integer> ret : retorno) {
				ret.add(elem);
				out.add(ret);
			}
		}

		List<List<Integer>> saida = new ArrayList<List<Integer>>();
		Integer anterior = null;
		boolean include = false;
		for (List<Integer> elems : out) {
			anterior = Integer.MAX_VALUE;
			include = true;
			for (Integer atual : elems) {
				if (atual >= anterior) {

					include = false;
					break;
				}
				anterior = atual;
			}
			if (include) {
				// Collections.sort(elems);
				saida.add(elems);
			}
		}

		return saida;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		List<List<Integer>> entradas = testeLotofacil();

		for (List<Integer> e : entradas) {
			System.out.println(e);
		}
		System.out.println("\n");

		List<List<Integer>> saidas = encontrarDesdobramentos(entradas);
		System.out.println("Total de combina��es:\t" + saidas.size() + "\n");
		for (List<Integer> s : saidas) {
			System.out.println(s);
		}
		// ArquivoUtil.saveLists(saidas, ConstantesUtil.CAMINHO_PADRAO +
		// ConstantesUtil.LOTOFACIL_TEXTO);
		System.out.println("Total de combina��es:\t" + saidas.size() + "\n");
	}

	public static List<List<Integer>> testeDuplaSena1() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e1 = new ArrayList<Integer>();
		e1.add(1);
		e1.add(4);
		List<Integer> e2 = new ArrayList<Integer>();
		e2.add(9);
		e2.add(11);
		List<Integer> e3 = new ArrayList<Integer>();
		e3.add(20);
		e3.add(29);
		List<Integer> e4 = new ArrayList<Integer>();
		e4.add(26);
		e4.add(34);
		List<Integer> e5 = new ArrayList<Integer>();
		e5.add(38);
		e5.add(42);
		List<Integer> e6 = new ArrayList<Integer>();
		e6.add(47);
		e6.add(48);
		entradas.add(e1);
		entradas.add(e2);
		entradas.add(e3);
		entradas.add(e4);
		entradas.add(e5);
		entradas.add(e6);

		return entradas;
	}

	public static List<List<Integer>> testeDuplaSena2() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e1 = new ArrayList<Integer>();
		e1.add(7); // e1.add(0); //e1.add(0);
		List<Integer> e2 = new ArrayList<Integer>();
		e2.add(9);
		e2.add(16);
		e2.add(22);
		List<Integer> e3 = new ArrayList<Integer>();
		e3.add(19);
		e3.add(27);
		e3.add(34);
		List<Integer> e4 = new ArrayList<Integer>();
		e4.add(31);
		e4.add(36);
		e4.add(40);
		List<Integer> e5 = new ArrayList<Integer>();
		e5.add(38);
		e5.add(42);
		e5.add(45);
		List<Integer> e6 = new ArrayList<Integer>();
		e6.add(44);
		e6.add(48);
		e6.add(50);
		entradas.add(e1);
		entradas.add(e2);
		entradas.add(e3);
		entradas.add(e4);
		entradas.add(e5);
		entradas.add(e6);

		return entradas;

	}

	public static List<List<Integer>> testeDuplaSena3() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e1 = new ArrayList<Integer>();
		e1.add(7); // e1.add(0); e1.add(0);
		List<Integer> e2 = new ArrayList<Integer>();
		e2.add(9);
		e2.add(16);
		e2.add(19);
		List<Integer> e3 = new ArrayList<Integer>();
		e3.add(22);
		e3.add(27);
		e3.add(31);
		List<Integer> e4 = new ArrayList<Integer>();
		e4.add(34);
		e4.add(36);
		e4.add(38);
		List<Integer> e5 = new ArrayList<Integer>();
		e5.add(40);
		e5.add(42);
		e5.add(44);
		List<Integer> e6 = new ArrayList<Integer>();
		e6.add(45);
		e6.add(48);
		e6.add(50);
		entradas.add(e1);
		entradas.add(e2);
		entradas.add(e3);
		entradas.add(e4);
		entradas.add(e5);
		entradas.add(e6);

		return entradas;
	}

	public static List<List<Integer>> testeDuplaSena4() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e1 = new ArrayList<Integer>();
		e1.add(7); // e1.add(0); e1.add(0);
		List<Integer> e2 = new ArrayList<Integer>();
		e2.add(9);
		e2.add(16);
		e2.add(19);
		e2.add(22);
		List<Integer> e3 = new ArrayList<Integer>();
		e3.add(19);
		e3.add(22);
		e3.add(31);
		e3.add(34);
		List<Integer> e4 = new ArrayList<Integer>();
		e4.add(27);
		e4.add(34);
		e4.add(38);
		e4.add(40);
		List<Integer> e5 = new ArrayList<Integer>();
		e5.add(36);
		e5.add(40);
		e5.add(42);
		e5.add(45);
		List<Integer> e6 = new ArrayList<Integer>();
		e6.add(42);
		e6.add(44);
		e6.add(48);
		e6.add(50);
		entradas.add(e1);
		entradas.add(e2);
		entradas.add(e3);
		entradas.add(e4);
		entradas.add(e5);
		entradas.add(e6);

		return entradas;
	}

	public static List<List<Integer>> testeLotofacil() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e01 = new ArrayList<Integer>();
		e01.add(1);
		e01.add(2);
		List<Integer> e02 = new ArrayList<Integer>();
		e02.add(2);
		e02.add(3);
		e02.add(4);
		List<Integer> e03 = new ArrayList<Integer>();
		e03.add(3);
		e03.add(4);
		e03.add(5);
		List<Integer> e04 = new ArrayList<Integer>();
		e04.add(4);
		e04.add(5);
		e04.add(6);
		e04.add(7);
		List<Integer> e05 = new ArrayList<Integer>();
		e05.add(6);
		e05.add(7);
		e05.add(8);
		e05.add(9);
		List<Integer> e06 = new ArrayList<Integer>();
		e06.add(8);
		e06.add(9);
		e06.add(10);
		e06.add(11);
		List<Integer> e07 = new ArrayList<Integer>();
		e07.add(10);
		e07.add(11);
		e07.add(12);
		e07.add(13);
		List<Integer> e08 = new ArrayList<Integer>();
		e08.add(11);
		e08.add(12);
		e08.add(13);
		e08.add(14);
		e08.add(15);
		List<Integer> e09 = new ArrayList<Integer>();
		e09.add(13);
		e09.add(14);
		e09.add(15);
		e09.add(16);
		e09.add(17);

		List<Integer> e10 = new ArrayList<Integer>();
		e10.add(15);
		e10.add(16);
		e10.add(17);
		e10.add(18);
		List<Integer> e11 = new ArrayList<Integer>();
		e11.add(17);
		e11.add(18);
		e11.add(20);
		e11.add(21);
		List<Integer> e12 = new ArrayList<Integer>();
		e12.add(18);
		e12.add(19);
		e12.add(30);
		e12.add(34);
		List<Integer> e13 = new ArrayList<Integer>();
		e13.add(20);
		e13.add(21);
		e13.add(22);
		e13.add(23);
		List<Integer> e14 = new ArrayList<Integer>();
		e14.add(22);
		e14.add(23);
		e14.add(24);
		List<Integer> e15 = new ArrayList<Integer>();
		e15.add(24);
		e15.add(25);
		entradas.add(e01);
		entradas.add(e02);
		entradas.add(e03);
		entradas.add(e04);
		entradas.add(e05);
		entradas.add(e06);
		entradas.add(e07);
		entradas.add(e08);
		entradas.add(e09);
		entradas.add(e10);
		entradas.add(e11);
		entradas.add(e12);
		entradas.add(e13);
		entradas.add(e14);
		entradas.add(e15);

		return entradas;
	}

	public static List<List<Integer>> testeMegaSena() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e1 = new ArrayList<Integer>();
		e1.add(4); // e1.add(0); e1.add(0);
		List<Integer> e2 = new ArrayList<Integer>();
		e2.add(11);
		e2.add(17);
		e2.add(30);
		e2.add(34);
		e2.add(36);
		List<Integer> e3 = new ArrayList<Integer>();
		e3.add(24);
		e3.add(28);
		e3.add(48);
		e3.add(50);
		e3.add(51);
		List<Integer> e4 = new ArrayList<Integer>();
		e4.add(42);
		e4.add(45);
		e4.add(61);
		e4.add(65);
		e4.add(67);
		List<Integer> e5 = new ArrayList<Integer>();
		e5.add(54);
		e5.add(57);
		e5.add(72);
		e5.add(75);
		e5.add(78);
		entradas.add(e1);
		entradas.add(e2);
		entradas.add(e3);
		entradas.add(e4);
		entradas.add(e5);

		return entradas;
	}

	public static List<List<Integer>> testeQuina() {
		List<List<Integer>> entradas = new ArrayList<List<Integer>>();

		List<Integer> e1 = new ArrayList<Integer>();
		e1.add(4); // e1.add(0); e1.add(0);
		List<Integer> e2 = new ArrayList<Integer>();
		e2.add(11);
		e2.add(17);
		e2.add(30);
		e2.add(34);
		e2.add(36);
		List<Integer> e3 = new ArrayList<Integer>();
		e3.add(24);
		e3.add(28);
		e3.add(48);
		e3.add(50);
		e3.add(51);
		List<Integer> e4 = new ArrayList<Integer>();
		e4.add(42);
		e4.add(45);
		e4.add(61);
		e4.add(65);
		e4.add(67);
		List<Integer> e5 = new ArrayList<Integer>();
		e5.add(54);
		e5.add(57);
		e5.add(72);
		e5.add(75);
		e5.add(78);
		entradas.add(e1);
		entradas.add(e2);
		entradas.add(e3);
		entradas.add(e4);
		entradas.add(e5);

		return entradas;
	}

}
