package br.com.enio.silva.loterias.lotomania;

public class PontuadorMegaSenaP2 extends PontuadorBasico {

	public PontuadorMegaSenaP2() {
		super();
		mapPontos.put(1, 1);
		mapPontos.put(2, 20);
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
