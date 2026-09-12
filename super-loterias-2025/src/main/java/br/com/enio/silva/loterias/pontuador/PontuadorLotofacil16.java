package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil16 extends PontuadorBasico {

	public PontuadorLotofacil16() {
		super();

		System.out.println(PontuadorLotofacil16.class.getName());
		// mapPontos.put(7, -1000);
		// mapPontos.put(8, -100);
		// mapPontos.put(9, -10);
		// mapPontos.put(10, -10);
		// mapPontos.put(11, 1000);
		// mapPontos.put(12, 1000);
		// mapPontos.put(13, 250);
		// mapPontos.put(14, 10);
		// mapPontos.put(15, -1000);
		// mapPontos.put(11, 1);
		// mapPontos.put(12, 1);
		mapPontos.put(13, 1);
		mapPontos.put(14, 100);
		mapPontos.put(15, -10000);
	}

	@Override
	public void inicializaMapConta() {
		mapConta.put(5, 0);
		mapConta.put(6, 0);
		mapConta.put(7, 0);
		mapConta.put(8, 0);
		mapConta.put(9, 0);
		mapConta.put(10, 0);
		mapConta.put(11, 0);
		mapConta.put(12, 0);
		mapConta.put(13, 0);
		mapConta.put(14, 0);
		mapConta.put(15, 0);
	}
}
