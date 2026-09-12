package br.com.enio.silva.loterias.duplasena;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorDuplaSenaPF extends PontuadorBasico {

	public PontuadorDuplaSenaPF() {
		super();
		mapPontos.put(1, 1);
		mapPontos.put(2, 4);
		mapPontos.put(3, 16);
		mapPontos.put(4, 64);
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
