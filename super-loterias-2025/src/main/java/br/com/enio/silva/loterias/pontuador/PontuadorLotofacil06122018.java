package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil06122018 extends PontuadorBasico {

	public PontuadorLotofacil06122018() {
		super();
		mapPontos.put(5, -10);
		mapPontos.put(6, -5);
		mapPontos.put(7, -4);
		mapPontos.put(8, -3);
		mapPontos.put(9, -2);
		mapPontos.put(10, 1);
		mapPontos.put(11, 2);
		mapPontos.put(12, 4);
		mapPontos.put(13, 8);
		mapPontos.put(14, 16);

		inicializaMapConta();

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
