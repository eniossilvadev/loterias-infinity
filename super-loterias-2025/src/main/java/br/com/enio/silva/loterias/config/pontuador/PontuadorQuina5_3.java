package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorQuina5_3 extends PontuadorBasico {

	public PontuadorQuina5_3() {
		super();
		// mapPontos.put(0, -1);
		// mapPontos.put(5, -1000);
		// mapPontos.put(1, 2);
		mapPontos.put(2, 1);
		mapPontos.put(3, 10);
	}

	@Override
	public void inicializaMapConta() {
		mapConta.put(0, 0);
		mapConta.put(1, 0);
		mapConta.put(2, 0);
		mapConta.put(3, 0);
		mapConta.put(4, 0);
		mapConta.put(5, 0);
	}
}
