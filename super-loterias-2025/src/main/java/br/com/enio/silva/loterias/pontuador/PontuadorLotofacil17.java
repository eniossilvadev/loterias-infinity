package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil17 extends PontuadorBasico {

	public PontuadorLotofacil17() {
		mapPontos.put(7, -8);
		mapPontos.put(8, -4);
		mapPontos.put(9, -2);
		mapPontos.put(10, -1);
		mapPontos.put(11, 1);
		mapPontos.put(12, 5);
		mapPontos.put(13, 250);
		mapPontos.put(14, 1250);
	}

	@Override
	public void inicializaMapConta() {
		mapConta.put(5, 0);
		mapConta.put(6, 0);
		mapConta.put(7, 0);
		mapConta.put(8, 0);
		mapConta.put(9, 0);
		mapConta.put(10, 0);
		mapConta.put(11, 0);
		mapConta.put(12, 0);
		mapConta.put(13, 0);
		mapConta.put(14, 0);
		mapConta.put(15, 0);
	}
}
