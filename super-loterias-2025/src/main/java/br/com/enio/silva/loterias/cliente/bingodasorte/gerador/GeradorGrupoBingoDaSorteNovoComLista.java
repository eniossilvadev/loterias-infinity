package br.com.enio.silva.loterias.cliente.bingodasorte.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.cliente.quina.GerarJogosQuinaLoopExcluirIncluirGenerico10;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

public class GeradorGrupoBingoDaSorteNovoComLista extends GeradorGrupoBingoDaSorteNovo {

	public GeradorGrupoBingoDaSorteNovoComLista(int size, String path) {
		super(size, path);
	}

	public void gerar(List<List<Integer>> listaBase, int K, int qtt) throws IOException {
		final List<List<Integer>> superLista = gerarSuperLista(listaBase, K);
		for (int i = 0; i < qtt; i++) {
			List<List<Integer>> listaDeJogosCorrentes = getCorrentes();
			List<Integer> excluirLista = this.toPlainList();
			final List<Integer> lista = GerarJogosQuinaLoopExcluirIncluirGenerico10
					.getNumerosDaSorte(getResultados(), listaDeJogosCorrentes, excluirLista, 0, superLista);
			gerados.addJogoTradicional(lista);
		}
		gerados.setMix(gerarMix());
	}

	public List<List<Integer>> gerarSuperLista(List<List<Integer>> listaBase, int K){

		listaBase = ListaUtils.removerRepetidos(listaBase);

		final List<List<Integer>> retorno = new ArrayList<>();
		List<List<Integer>> l = CombinationUtils.gerarCombinacoes(listaBase, K);
		l.forEach(el -> {
			List<Integer> listaRange = ListaUtils.getListaRange(1, 80);
			listaRange.removeAll(el);
			List<List<Integer>> restante = CombinationUtils.gerarCombinacao(listaRange, 10 - el.size());

			final List<List<Integer>> combinacao = new ArrayList<>();
			restante.forEach(r -> {
				r.addAll(el);
				combinacao.add(r);
			});
			retorno.addAll(combinacao);
		});
		int maxSize = 200000;
		if(retorno.size() > maxSize) {
			Collections.shuffle(retorno);
			final List<List<Integer>> retornoAux = retorno.subList(0, maxSize);
			final List<List<Integer>> retornoSemRepetidos = ListaUtils.regerarSemRepetidos(retornoAux);
			return retornoSemRepetidos;
		}
		final List<List<Integer>> retornoSemRepetidos = ListaUtils.removerRepetidos(retorno);
		return retornoSemRepetidos;
	}
}
