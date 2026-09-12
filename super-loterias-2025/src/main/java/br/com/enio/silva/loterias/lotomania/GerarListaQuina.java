package br.com.enio.silva.loterias.lotomania;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;

public class GerarListaQuina extends GerarLista {

	private static GerarListaQuina instance;

	public static GerarListaQuina getInstance() {
		if (instance == null) {
			instance = new GerarListaQuina();
		}
		return instance;
	}

	protected GerarListaQuina() {
	}

	@Override
	public List<List<Integer>> gerarArquivoResultado() {

		String pathResultados = CaminhoResultados.QUINA.getPath();
		try {
			return gerarArquivoResultado(pathResultados);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
