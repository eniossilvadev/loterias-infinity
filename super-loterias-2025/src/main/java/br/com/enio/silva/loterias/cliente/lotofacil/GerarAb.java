package br.com.enio.silva.loterias.cliente.lotofacil;

import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.lotomania.JogoLotofacil;

public abstract class GerarAb {

	public static List<JogoLotofacil> filtroSkip(List<JogoLotofacil> jogos, int sk) {

		System.out.println("Skipping... " + sk + "!");
		List<JogoLotofacil> retorno = new ArrayList<JogoLotofacil>();
		int skip = sk;
		for (JogoLotofacil lista : jogos) {
			if (skip++ % sk == 0) {
				retorno.add(lista);
			}
		}
		return retorno;
	}

}
