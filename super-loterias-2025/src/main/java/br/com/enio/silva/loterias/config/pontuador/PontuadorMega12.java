package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorMega12 extends PontuadorBasico {

	public PontuadorMega12() {
		super();

		mapPontos.put(0, -5);
		mapPontos.put(1, 0);
		mapPontos.put(2, 1);
		mapPontos.put(3, (int) Math.pow(2, 2));
		mapPontos.put(4, (int) Math.pow(2, 5));
		mapPontos.put(5, (int) Math.pow(2, 10));
		mapPontos.put(6, 1000);
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
