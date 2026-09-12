package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.Transformer;

public class ListUtil2 {

	private static Transformer isListOfInteger = new Transformer() {

		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			if (input instanceof List) {
				List<Object> lista = (List<Object>) input;
				for (Object o : lista) {
					if (!(o instanceof Integer)) {
						return Boolean.FALSE;
					}
				}
			} else {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}

	};

	/**
	 *
	 * @param listas
	 * @param tamanhoLista
	 * @param maiorNumero
	 * @return
	 */
	public static List<List<Integer>> completar(List<List<Integer>> listas, int tamanhoLista,
	        int maiorNumero) {
		System.out.println("Completar...");
		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		Integer par = new Integer(listas.size()) / 200;
		int removidos = 0;
		for (List<Integer> lista : listas) {
			if (lista.size() <= tamanhoLista) {
				Set<Integer> l = new TreeSet<Integer>(lista);
				while (l.size() < tamanhoLista) {
					int i = (int) (Math.random() * maiorNumero) + 1;
					l.add(i);
				}
				List<Integer> ret = new ArrayList<Integer>(l);
				if (!retorno.contains(ret)) {
					retorno.add(ret);
					if ((retorno.size() % par) == 0) {
						System.out.print(retorno.size() + "...");
						if ((retorno.size() % (par * 10)) == 0) {
							System.out.println();
						}
					}
				} else {
					removidos++;
				}
			} else {
				System.out.println(lista);
				retorno.add(lista);
			}
		}
		System.out.println("\n\nRemovidos " + removidos + "\n");
		System.out.println(retorno.size() + "\tFim!!!");
		return retorno;
	}

	/**
	 *
	 * @param lists
	 * @param tam
	 * @param maxNum
	 * @return
	 */
	public static List<List<Integer>> completarExcluirIncluir(List<List<Integer>> lists, int tam,
	        int maxNum, List<Integer> excluir, List<Integer> incluir) {

		List<Integer> todosNumeros = new ArrayList<>();
		for (int i = 1; i <= maxNum; i++) {

		}

		System.out.println("Completar...");
		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		Integer par = new Integer(lists.size()) / 200;
		int removidos = 0;
		for (List<Integer> lista : lists) {
			if (lista.size() <= tam) {
				Set<Integer> l = new TreeSet<Integer>(lista);
				while (l.size() < tam) {
					int i = (int) (Math.random() * maxNum) + 1;
					if (incluir.contains(i)) {
						l.add(i);
					}
				}
				List<Integer> ret = new ArrayList<Integer>(l);
				if (!retorno.contains(ret)) {
					retorno.add(ret);
					if ((retorno.size() % par) == 0) {
						System.out.print(retorno.size() + "...");
						if ((retorno.size() % (par * 10)) == 0) {
							System.out.println();
						}
					}
				} else {
					removidos++;
				}
			} else {
				System.out.println(lista);
				retorno.add(lista);
			}
		}
		System.out.println("\n\nRemovidos " + removidos + "\n");
		System.out.println(retorno.size() + "\tFim!!!");
		return retorno;
	}

	/**
	 *
	 * @param listas
	 * @param tamanhoLista
	 * @param maiorNumero
	 * @return
	 */
	public static List<List<Integer>> completarExclusao(List<List<Integer>> listas,
	        int tamanhoLista, int maiorNumero, List<Integer> remover) {
		System.out.println("Completar...");
		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		Integer par = new Integer(listas.size()) / 200;
		int removidos = 0;
		for (List<Integer> lista : listas) {
			if (lista.size() <= tamanhoLista) {
				Set<Integer> l = new TreeSet<Integer>(lista);
				while (l.size() < tamanhoLista) {
					int i = (int) (Math.random() * maiorNumero) + 1;
					if (!remover.contains(i)) {
						l.add(i);
					}
				}
				List<Integer> ret = new ArrayList<Integer>(l);
				if (!retorno.contains(ret)) {
					retorno.add(ret);
					if ((retorno.size() % par) == 0) {
						System.out.print(retorno.size() + "...");
						if ((retorno.size() % (par * 10)) == 0) {
							System.out.println();
						}
					}
				} else {
					removidos++;
				}
			} else {
				System.out.println(lista);
				retorno.add(lista);
			}
		}
		System.out.println("\n\nRemovidos " + removidos + "\n");
		System.out.println(retorno.size() + "\tFim!!!");
		return retorno;
	}

	/**
	 *
	 * @param listas
	 * @param tamanhoLista
	 * @param maiorNumero
	 * @return
	 */
	public static List<List<Integer>> completarIncluir(List<List<Integer>> listas, int tamanhoLista,
	        int maiorNumero, List<Integer> incluir) {
		System.out.println("Completar...");
		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		Integer par = new Integer(listas.size()) / 200;
		int removidos = 0;
		for (List<Integer> lista : listas) {
			if (lista.size() <= tamanhoLista) {
				Set<Integer> l = new TreeSet<Integer>(lista);
				while (l.size() < tamanhoLista) {
					int i = (int) (Math.random() * maiorNumero) + 1;
					if (incluir.contains(i)) {
						l.add(i);
					}
				}
				List<Integer> ret = new ArrayList<Integer>(l);
				if (!retorno.contains(ret)) {
					retorno.add(ret);
					if ((retorno.size() % par) == 0) {
						System.out.print(retorno.size() + "...");
						if ((retorno.size() % (par * 10)) == 0) {
							System.out.println();
						}
					}
				} else {
					removidos++;
				}
			} else {
				System.out.println(lista);
				retorno.add(lista);
			}
		}
		System.out.println("\n\nRemovidos " + removidos + "\n");
		System.out.println(retorno.size() + "\tFim!!!");
		return retorno;
	}

	public static Boolean isListOfInteger(Object o) {
		return (Boolean) isListOfInteger.transform(o);
	}

}
