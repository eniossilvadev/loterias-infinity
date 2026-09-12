package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil18 extends PontuadorBasico {

	public PontuadorLotofacil18() {
		super();
		mapPontos.put(6, -1000);
		mapPontos.put(7, -100);
		mapPontos.put(8, -10);
		mapPontos.put(9, 0);
		mapPontos.put(10, 1);
		mapPontos.put(11, (int) Math.pow(2, 2));
		mapPontos.put(12, (int) Math.pow(2, 4));
		mapPontos.put(13, (int) Math.pow(2, 6));
		mapPontos.put(14, (int) Math.pow(2, 8));
		mapPontos.put(15, (int) Math.pow(2, 12));
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
