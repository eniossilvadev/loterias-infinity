package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorMega6_2 extends PontuadorBasico {

	public PontuadorMega6_2() {
		super();
		mapPontos.put(0, -5);
		mapPontos.put(1, 1);
		mapPontos.put(2, 50);
		mapPontos.put(3, 100);
		mapPontos.put(4, -1000);
		mapPontos.put(5, -10000);
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
