package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil15Simples3 extends PontuadorBasico {

	public PontuadorLotofacil15Simples3() {
		mapPontos.put(11, 1);
		mapPontos.put(12, 2);
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
