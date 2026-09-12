package br.com.enio.silva.loterias.lotomania;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;

public class GerarListaMega extends GerarLista {

	private static GerarListaMega instance;

	public static GerarListaMega getInstance() {
		if (instance == null) {
			instance = new GerarListaMega();
		}
		return instance;
	}

	protected GerarListaMega() {
	}

	@Override
	public List<List<Integer>> gerarArquivoResultado() throws IOException {
		return gerarArquivoResultado(CaminhoResultados.MEGA_SENA.getPath());
	}
}
