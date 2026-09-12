package util;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;

public class ListUtil {

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
	public static List<List<Integer>> completar(List<List<Integer>> lists, int tam, int maxNum) {
		return completarExcluirIncluir(lists, tam, maxNum, null, null);
	}

	/**
	 *
	 * @param lists
	 * @param tam
	 * @param maxNum
	 * @param clean
	 * @return
	 */
	public static List<List<Integer>> completar(List<List<Integer>> lists, int tam, int maxNum,
			boolean clean) {
		return completarExcluirIncluir(lists, tam, maxNum, null, null, clean);
	}

	public static List<List<Integer>> completarExcluirIncluir(List<List<Integer>> lists, int tam,
			int maxNum, List<Integer> excluir, List<Integer> incluir) {
		return completarExcluirIncluir(lists, tam, maxNum, excluir, incluir, false);
	}

	public static List<List<Integer>> completarExcluirIncluir(List<List<Integer>> lists, int tam,
															  int maxNum, List<Integer> excluir, List<Integer> incluir, boolean clean) {

		// 1. Otimização de Log: Removemos logs excessivos que travam o I/O
		System.out.println("Iniciando otimização... Total listas: " + lists.size());

		// 2. Preparação dos números permitidos (Pool)
		// Usamos HashSet para busca rápida O(1) e ArrayList para sorteio indexado O(1)
		Set<Integer> excluidosSet = (excluir != null) ? new HashSet<>(excluir) : new HashSet<>();

		// Cria lista de números disponíveis para sorteio (1 até maxNum, pulando os excluídos)
		List<Integer> poolNumerosValidos = new ArrayList<>(maxNum);
		for (int i = 1; i <= maxNum; i++) {
			if (!excluidosSet.contains(i)) {
				poolNumerosValidos.add(i);
			}
		}

		// Se não houver números suficientes para completar, retornamos vazio ou tratamos erro
		if (poolNumerosValidos.isEmpty()) {
			return new ArrayList<>();
		}

		// 3. Otimização de Armazenamento
		// Usamos um Set para garantir unicidade automaticamente sem varrer lista (O(1))
		// LinkedHashSet mantém a ordem de inserção (opcional, mas bom para debug)
		Set<List<Integer>> resultadoSet = new LinkedHashSet<>(lists.size());
		int removidos = 0;

		for (List<Integer> listaOriginal : lists) {

			// Se a lista já é maior que o tamanho, passa direto (mantendo lógica original)
			if (listaOriginal.size() > tam) {
				resultadoSet.add(listaOriginal);
				continue;
			}

			// Criamos um Set temporário para evitar duplicatas dentro do próprio jogo
			// E iniciamos com os números da lista original
			Set<Integer> jogoEmConstrucao = new HashSet<>(listaOriginal);

			// Se o jogo já tem o tamanho ou mais, apenas adicionamos
			if (jogoEmConstrucao.size() >= tam) {
				List<Integer> pronto = new ArrayList<>(jogoEmConstrucao);
				Collections.sort(pronto);
				if (!resultadoSet.add(pronto)) {
					removidos++;
				}
				continue;
			}

			// --- LÓGICA DE PREENCHIMENTO OTIMIZADA ---
			// Em vez de "tentar e falhar" (while tentativas < 7),
			// sorteamos diretamente da lista de válidos.

			int tentativasSeguranca = 0;
			while (jogoEmConstrucao.size() < tam && tentativasSeguranca < 50) {
				// Sorteia um índice da lista de permitidos
				int index = ThreadLocalRandom.current().nextInt(poolNumerosValidos.size());
				Integer numeroSorteado = poolNumerosValidos.get(index);

				jogoEmConstrucao.add(numeroSorteado);
				tentativasSeguranca++;
			}

			// Finalização do jogo
			if (jogoEmConstrucao.size() == tam) {
				List<Integer> jogoFinal = new ArrayList<>(jogoEmConstrucao);
				Collections.sort(jogoFinal); // Ordenar é rápido para listas pequenas

				// O método .add() do Set retorna false se já existir,
				// eliminando a necessidade do .contains() manual
				if (!resultadoSet.add(jogoFinal)) {
					removidos++;
				}
			} else {
				// Caso raro onde não conseguiu preencher (ex: pool muito pequeno)
				removidos++;
			}
		}

		System.out.println("Removidos (Duplicados/Falhas): " + removidos);

		// Converte o Set de volta para List para respeitar o retorno
		return new ArrayList<>(resultadoSet);
	}

	/**
	 *
	 * @param listas
	 * @param tamanhoLista
	 * @param maiorNumero
	 * @return
	 */
	public static List<List<Integer>> completarExclusao(List<List<Integer>> lists, int tam,
			int maxNum, List<Integer> remover) {
		return completarExcluirIncluir(lists, tam, maxNum, remover, null);
	}

	/**
	 *
	 * @param listas
	 * @param tamanhoLista
	 * @param maiorNumero
	 * @return
	 */
	public static List<List<Integer>> completarIncluir(List<List<Integer>> lists, int tam,
			int maxNum, List<Integer> incluir) {
		return completarExcluirIncluir(lists, tam, maxNum, null, incluir);
	}

	public static List<List<Integer>> completarPorLista(final List<List<Integer>> lists,
			final int tam, final List<Integer> lista) {
		final List<List<Integer>> retorno = new ArrayList<>();
		final Set<Integer> set = new HashSet<>(lista);
		lists.forEach(l -> {
			int rest = tam - l.size();
			List<Integer> localList = new ArrayList<>(l);
			List<Integer> restList = new ArrayList<>(lista);
			restList.removeAll(localList);
			Collections.shuffle(restList);
			localList.addAll(restList.subList(0, rest));
			retorno.add(localList);
		});
		return retorno;
	}

	public static List<List<Integer>> gerarListasMinMaxRemove(int min, int max, int sizeList,
			int sizeLists) {

		final List<List<Integer>> retorno = new ArrayList<>();

		for (int i = 0; i < sizeLists; i++) {
			List<Integer> lista = getListaSimplesMimMaxRemove(min, max, sizeList);
			retorno.add(lista);
		}
		return retorno;
	}

	public static List<List<Integer>> gerarPorLista(final int tam, final List<Integer> lista,
			final int quantidade) {
		final List<List<Integer>> retorno = new ArrayList<>();
		final Set<Integer> set = new HashSet<>(lista);

		for (int i = 0; i < quantidade; i++) {
			List<Integer> local = new ArrayList<>(set);
			Collections.shuffle(local);
			retorno.add(local.subList(0, tam));
		}

		return new ArrayList<>(new HashSet<>(retorno));
	}

	private static List<Integer> getListaFromMinToMax(int min, int max) {
		return IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
	}

	private static List<Integer> getListaSimplesMimMaxRemove(int min, int max, int size) {
		List<Integer> lista = getListaFromMinToMax(min, max);
		int diff = lista.size() - size;

		for (int i = 0; i < diff; i++) {
			Collections.shuffle(lista);
			lista.remove(0);
		}
		return lista;
	}

	public static Boolean isListOfInteger(Object o) {
		return (Boolean) isListOfInteger.transform(o);
	}


	public static List<Integer> iterateStream(int from, int step, int limit) {
		return IntStream.iterate(from, i -> i + step) // next int
				.limit(limit / step) // only numbers in range
				.boxed().collect(Collectors.toList());
	}

}
