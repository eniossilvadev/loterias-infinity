package br.com.enio.silva.loterias.conferator;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.duplasena.GerarListaDuplaSena;

public class ConferatorDuplaSena1 extends ConferatorDuplaSenaAb {

	@Override
	public String getDs() {
		return "ds1_";
	}

	@Override
	public List<List<Integer>> getResultados() {
		try {
			List<List<Integer>> resultados = GerarListaDuplaSena.getInstance()
					.gerarArquivoResultado1();
			return resultados;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}
