package br.com.enio.silva.loterias.lotomania;

public class PontuadorMegaSenaSuper20 extends PontuadorBasico {

	public PontuadorMegaSenaSuper20() {
		super();
		mapPontos.put(1, 1);
		mapPontos.put(2, 10);
		mapPontos.put(3, 100);
		mapPontos.put(4, 110);
		mapPontos.put(5, 120);
		mapPontos.put(6, 150);
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
