package br.com.silva.enio.loterias.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import org.apache.commons.lang3.ArrayUtils;

//import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Estatisticas {

	/**
	 *
	 * @param resultados
	 * @param posicoes
	 *            quantidade de números sorteados em cada sorteio. (não precisa)
	 * @param numeros
	 *            número máximo sorteado.
	 * @return
	 */
	public List<List<Integer>> calcularEstatisticasNumerosPorPosicao(List<List<Integer>> resultados,
	        int posicoes, int numeros) {

		Integer[][] est = obterArrayPosicoes(resultados, posicoes, numeros);

		List<List<Integer>> estatisticas = new ArrayList<List<Integer>>();
		for (int i = 0; i < numeros; i++) {
			// for(int j = 0; j < numeros; j++){
			estatisticas.add(Arrays.asList(est[i]));
			// System.out.print(est[i]);
			// }
		}
		return estatisticas;
	}

	/**
	 *
	 * @param resultados
	 * @param max
	 * @return
	 */
	public List<Integer> calcularNumerosMaisSaidos(List<List<Integer>> resultados, int max) {

		Integer[] total = new Integer[max];
		for (int i = 0; i < total.length; i++) {
			total[i] = 0;
		}

		for (List<Integer> resultado : resultados) {
			for (Integer r : resultado) {
				total[r.intValue() - 1]++;
			}
		}

		return Arrays.asList(total);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> calcularNumerosRepetidosSorteioAnterior(List<List<Integer>> resultados,
	        int maxRepeticoes) {
		int[] repeticoes = new int[maxRepeticoes];
		Arrays.fill(repeticoes, 0);
		List<List<Integer>> todosResultados = (ArrayList<List<Integer>>) ((ArrayList<List<Integer>>) resultados)
		        .clone();
		List<Integer> atual = todosResultados.remove(0);
		for (List<Integer> todos : todosResultados) {
			int rep = ConferirRN.conferir(todos, atual);
			repeticoes[rep]++;
			atual = todos;
		}

		return Arrays.asList(ArrayUtils.toObject(repeticoes));
	}

	/**
	 * @param resultados
	 * @param posicoes
	 * @param numeros
	 * @return
	 */
	private Integer[][] obterArrayPosicoes(List<List<Integer>> resultados, int posicoes,
	        int numeros) {
		Integer[][] est = new Integer[numeros][posicoes];
		for (int i = 0; i < numeros; i++) {
			for (int j = 0; j < posicoes; j++) {
				est[i][j] = 0;
			}
		}

		for (List<Integer> lista : resultados) {
			for (int i = 0; i < lista.size(); i++) {
				est[lista.get(i) - 1][i]++;
			}
		}
		return est;
	}

	/**
	 *
	 * @param fileName
	 * @return
	 */
	public List<List<Integer>> obterJogos(String fileName) {
		return ArquivoUtil.obterLinhasComoListas(fileName);
	}

	public List<List<Integer>> obterListasPre(List<List<Integer>> resultados, int posicoes,
	        int numeros, int filtro) {
		Integer[][] estat = obterArrayPosicoes(resultados, posicoes, numeros);

		int count = 1;
		for (int i = 0; i < estat.length; i++) {
			Integer[] integers = estat[i];

			for (int j = 0; j < integers.length; j++) {
				Integer integer = integers[j];
				if (integer < filtro) {
					estat[i][j] = 0;
				} else {
					estat[i][j] = count;
				}
			}
			count++;
		}

		int C = estat[0].length;
		int R = estat.length;
		Integer[][] result = new Integer[C][R];
		for (int c = 0; c != C; c++) {
			Integer[] row = new Integer[R];
			int cnt = 0;
			for (int r = 0; r < R; r++) {
				if (estat[r][c] != 0) {
					row[cnt++] = estat[r][c];
				}
			}
			result[c] = row;
		}

		List<List<Integer>> estatisticas = new ArrayList<List<Integer>>();
		for (int i = 0; i < result.length; i++) {
			// for(int j = 0; j < numeros; j++){
			estatisticas.add(Arrays.asList(result[i]));
			// }
		}

		return estatisticas;
	}

	/**
	 *
	 * @param listas
	 * @return
	 */
	public Integer obterTotalDeSorteios(List<List<Integer>> listas) {
		Integer qttSorteios = listas.size();
		return qttSorteios;
	}

	/**
	 * Ordena os resultados. A primeira coluna � a pontua��o do jogo.
	 * 
	 * @param resultados
	 * @return
	 */
	public List<List<Integer>> ordernarResultados(List<List<Integer>> resultados, boolean remover) {
		Collections.sort(resultados, new Comparator<List<Integer>>() {

			@Override
			public int compare(List<Integer> o1, List<Integer> o2) {
				if (o1 != null && o2 != null) {
					int i1 = o1.get(0);
					int i2 = o2.get(0);
					if (i1 < i2) {
						return -1;
					}
					if (i1 > i2) {
						return 1;
					}
				}
				return 0;
			}
		});

		if (remover) {
			for (List<Integer> r : resultados) {
				r.remove(0);
			}
		}
		return resultados;
	}

	/**
	 *
	 * @param resultados
	 * @param max
	 * @return
	 */
	public String printCalcularNumerosMaisSaidos(List<List<Integer>> resultados, int max) {
		List<Integer> lista = calcularNumerosMaisSaidos(resultados, max);
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < lista.size(); i++) {
			str.append(lista.get(i) + "\t");
		}
		return str.toString();
	}

}
