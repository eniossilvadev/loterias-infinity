package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorTimemania extends PontuadorBasico {

	public PontuadorTimemania() {
		super();
		mapPontos.put(0, -10);
		mapPontos.put(1, 1);
		mapPontos.put(2, (int) Math.pow(2, 2));
		mapPontos.put(3, (int) Math.pow(2, 4));
		mapPontos.put(4, (int) Math.pow(2, 6));
		mapPontos.put(5, (int) Math.pow(2, 8));
		mapPontos.put(6, (int) Math.pow(2, 10));
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
