package br.com.enio.silva.loterias.filtro;

import java.util.List;
import java.util.function.Predicate;

public class FiltroMaximoLinhas extends FiltroLinhaColunaAb {

	public FiltroMaximoLinhas(int ndl, int npl, int mpl) {
		super(ndl, npl, mpl);
	}

	@Override
	protected boolean validar(int contador) {
		return contador > qttPorLinha;
	}

	/**
	 * Verifica se o jogo excede o limite máximo de números permitidos por linha.
	 * * @param totalLinhas      Número total de linhas no volante (ex: 5 na Lotofácil).
	 * @param numerosPorLinha  Quantos números existem em cada linha (ex: 5 na Lotofácil).
	 * @param maximoPermitido  O limite máximo aceitável de números marcados em uma linha.
	 * @return Predicate true se o jogo for válido.
	 */
	public static Predicate<List<Integer>> filtroMaximoLinhas(int totalLinhas, int numerosPorLinha, int maximoPermitido) {
		return lista -> {
			// Validação de segurança
			if (lista == null || lista.isEmpty()) return false;

			// Cria um "balde" para cada linha.
			// int[] é alocado na Stack (muito rápido) e zerado automaticamente.
			int[] contagemPorLinha = new int[totalLinhas];

			for (Integer numero : lista) {
				// FÓRMULA MÁGICA: Descobre o índice da linha com matemática simples (divisão inteira).
				// Exemplo (Linhas de 5):
				// Num 1 -> (0)/5 = índice 0.
				// Num 6 -> (5)/5 = índice 1.
				int linhaIndex = (numero - 1) / numerosPorLinha;

				// Verificação de segurança para números fora do volante (opcional)
				if (linhaIndex >= 0 && linhaIndex < totalLinhas) {

					// Incrementa o contador daquela linha
					contagemPorLinha[linhaIndex]++;

					// FAIL FAST: A otimização crucial.
					// Se essa linha acabou de estourar o limite, retorna false IMEDIATAMENTE.
					// Não precisamos terminar de ler o resto da lista nem conferir as outras linhas.
					if (contagemPorLinha[linhaIndex] > maximoPermitido) {
						return false;
					}
				}
			}

			// Se percorreu todos os números e nenhum balde transbordou, o jogo é válido.
			return true;
		};
	}
}
