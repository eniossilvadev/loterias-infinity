package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorMega8 extends PontuadorBasico {

	public PontuadorMega8() {
		super();
		mapPontos.put(0, -1);
		mapPontos.put(1, 1);
		mapPontos.put(2, 2);
		mapPontos.put(3, 500);
		mapPontos.put(4, 750);
		mapPontos.put(5, 6000);
		mapPontos.put(6, 1500);
	}

	@Override
	public void inicializaMapConta() {
		mapConta.put(0, 0);
		mapConta.put(1, 0);
		mapConta.put(2, 0);
		mapConta.put(3, 0);
		mapConta.put(4, 0);
		mapConta.put(5, 0);
		mapConta.put(6, 0);
	}
}
