package br.com.enio.silva.loterias.filtro;

import br.com.enio.silva.loterias.util.CollectionsUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Predicate;

public class FiltroMaxConsecutivos extends FiltroAb {

	private int maxx = 0;

	public FiltroMaxConsecutivos() {
	}

	public FiltroMaxConsecutivos(int max) {
		this.maxx = max;
	}

	@Override
	public List<Integer> filtrarLista(List<Integer> lista) {
		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}

		Set<Integer> set = new TreeSet<>(lista); // Ordena automaticamente e remove duplicatas
		int countSeq = 1;
		Integer prev = null;

		for (Integer num : set) {
			if (prev != null && num == prev + 1) {
				countSeq++;
				if (countSeq >= maxx) {
					return Collections.emptyList();
				}
			} else {
				countSeq = 1;
			}
			prev = num;
		}

		return lista;
	}

	public static Predicate<List<Integer>> filtroMaxConsecutivos(int max) {
		return lista -> {
			// Validações básicas (segurança)
			if (lista == null || lista.isEmpty()) return true;

			int sequenciaAtual = 1;

			// Iteramos da segunda posição até o fim
			// Assumimos que a lista JÁ ESTÁ ORDENADA (padrão em loterias)
			for (int i = 1; i < lista.size(); i++) {
				int anterior = lista.get(i - 1);
				int atual = lista.get(i);

				// Verifica se são consecutivos (ex: 4 e 5)
				if (atual == anterior + 1) {
					sequenciaAtual++;

					// Fail Fast: Se atingiu o limite, para agora.
					// Não precisa ler o resto da lista.
					if (sequenciaAtual >= max) {
						return false;
					}
				} else {
					// Reinicia a contagem se quebrou a sequência
					sequenciaAtual = 1;
				}
			}

			// Se passou pelo loop sem retornar false, o jogo é válido
			return true;
		};
	}
}
