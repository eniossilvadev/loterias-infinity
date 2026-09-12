package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil19 extends PontuadorBasico {

	public PontuadorLotofacil19() {
		super();
		mapPontos.put(8, -1);
		mapPontos.put(9, -1);
		mapPontos.put(10, -1);
		mapPontos.put(12, 10);
		mapPontos.put(13, 100);
		mapPontos.put(14, 1000);
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
