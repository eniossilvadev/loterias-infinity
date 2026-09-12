package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil20 extends PontuadorBasico {

	public PontuadorLotofacil20() {
		super();
		mapPontos.put(5, -100000);
		mapPontos.put(6, -10000);
		mapPontos.put(7, -10000);
		mapPontos.put(8, -1000);
		mapPontos.put(9, -100);
		mapPontos.put(10, 1);
		mapPontos.put(11, 10);
		mapPontos.put(12, 100);
		mapPontos.put(13, 1000);
		mapPontos.put(14, 100000);
		mapPontos.put(15, 100000);
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
