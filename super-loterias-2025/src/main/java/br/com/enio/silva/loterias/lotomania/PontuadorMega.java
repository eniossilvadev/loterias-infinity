package br.com.enio.silva.loterias.lotomania;

public class PontuadorMega extends PontuadorBasico {

	public PontuadorMega() {
		super();
		mapPontos.put(0, -100);
		mapPontos.put(1, 1);
		mapPontos.put(2, 10);
		mapPontos.put(3, 10000);
		mapPontos.put(4, 1000);
		mapPontos.put(5, 1);
		mapPontos.put(6, -10000);
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
