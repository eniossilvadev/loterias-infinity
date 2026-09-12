package br.com.enio.silva.loterias.cliente.bingodasorte.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.bingodasorte.ModelBingoGerador;
import br.com.enio.silva.loterias.cliente.bingodasorte.newp.GerarJogosQuinaLoopQuadrante;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GeradorGrupoBingoDaSorteQuadrante {

	protected static final int excluir = 30;

	protected static List<List<Integer>> resultados = new ArrayList<>();

	protected static List<List<Integer>> getResultados() throws IOException {
		if (resultados == null && resultados.isEmpty()) {
			String pathResultado = CaminhoResultados.QUINA.getPath();
			resultados = GerarListaQuina.getInstance().gerarArquivoResultado(pathResultado);
		}
		return resultados;
	}

	protected final String pathCorrente;

	protected int size = 0;

	protected final ModelBingoGerador gerados = new ModelBingoGerador();

	public GeradorGrupoBingoDaSorteQuadrante(int size, String path) {
		this.size = size;
		this.pathCorrente = path;
	}

	public void gerar() throws IOException {
		for (int i = 0; i < this.size; i++) {
			List<List<Integer>> listaDeJogosCorrentes = getCorrentes();
			List<Integer> excluirLista = this.toPlainList();
			final List<Integer> lista = GerarJogosQuinaLoopQuadrante
					.getNumerosDaSorte(getResultados(), listaDeJogosCorrentes, excluirLista);
			gerados.addJogoTradicional(lista);
		}
		//		gerados.setMix(gerarMix());
	}

	protected List<Integer> gerarMix() {
		List<Integer> theList = this.toPlainList();
		Collections.shuffle(theList);
		return theList.subList(0, 10);
	}

	protected List<List<Integer>> getCorrentes() {
		List<List<Integer>> correntes = gerados.getAll();

		List<List<Integer>> listaCorrentes = ArquivoUtil
				.obterLinhasComoListas(this.pathCorrente);

		correntes.addAll(listaCorrentes);

		return correntes;
	}

	public ModelBingoGerador getGerados() {
		return gerados;
	}

	protected List<Integer> toPlainList() {
		List<List<Integer>> all = gerados.getAll();
		if (all == null || all.isEmpty()) {
			return Collections.emptyList();
		}
		final Set<Integer> retorno = new HashSet<>();
		all.forEach(el -> retorno.addAll(el));
		List<Integer> listaRetorno = new ArrayList<>(retorno);
		if (listaRetorno.size() > excluir) {
			listaRetorno = listaRetorno.subList(0, excluir);
		}
		return listaRetorno;
	}
}
