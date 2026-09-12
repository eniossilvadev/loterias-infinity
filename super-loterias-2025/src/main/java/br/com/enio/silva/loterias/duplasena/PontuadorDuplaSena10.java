package br.com.enio.silva.loterias.duplasena;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorDuplaSena10 extends PontuadorBasico {

	public PontuadorDuplaSena10() {
		super();
		mapPontos.put(1, 1);
		mapPontos.put(2, 5);
		mapPontos.put(3, 25);
		mapPontos.put(4, 125);
		mapPontos.put(5, 525);
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
