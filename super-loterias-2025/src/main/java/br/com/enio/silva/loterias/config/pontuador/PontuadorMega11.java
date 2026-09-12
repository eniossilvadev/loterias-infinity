package br.com.enio.silva.loterias.config.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorMega11 extends PontuadorBasico {

	public PontuadorMega11() {
		super();

		mapPontos.put(0, -1);
		mapPontos.put(1, 1);
		mapPontos.put(2, 4);
		mapPontos.put(3, (int) Math.pow(2, 4));
		mapPontos.put(4, (int) Math.pow(2, 7));
		mapPontos.put(5, (int) Math.pow(2, 12));
		mapPontos.put(6, 10000);
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
