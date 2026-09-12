package br.com.enio.silva.loterias.lotomania;

public class PontuadorMega8 extends PontuadorBasico {

	public PontuadorMega8() {
		super();
		// mapPontos.put(0, -75);
		// mapPontos.put(1, 0);
		// mapPontos.put(2, 5);
		// mapPontos.put(3, 100);
		// mapPontos.put(4, 1000);
		// mapPontos.put(5, 150);
		// mapPontos.put(6, -10000);
		int pow = 3;
		mapPontos.put(0, 0);
		mapPontos.put(1, 1);
		mapPontos.put(2, (int) Math.pow(2, pow));
		mapPontos.put(3, (int) Math.pow(6, pow));
		mapPontos.put(4, (int) Math.pow(4, pow));
		mapPontos.put(5, -(int) Math.pow(5, pow));
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
