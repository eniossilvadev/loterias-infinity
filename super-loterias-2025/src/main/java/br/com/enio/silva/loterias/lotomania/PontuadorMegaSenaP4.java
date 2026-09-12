package br.com.enio.silva.loterias.lotomania;

public class PontuadorMegaSenaP4 extends PontuadorBasico {

	public PontuadorMegaSenaP4() {
		super();
		mapPontos.put(0, -3);
		mapPontos.put(1, 1);
		mapPontos.put(2, 50);
		mapPontos.put(3, 100);
		mapPontos.put(4, 200);
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
