package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorMega extends PontuadorBasico {

	public PontuadorMega() {
		super();
		mapPontos.put(0, -1);
		mapPontos.put(1, 1);
		mapPontos.put(2, 750);
		mapPontos.put(3, 1000);
		mapPontos.put(4, 100);
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
