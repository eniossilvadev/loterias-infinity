package br.com.enio.silva.loterias.pontuador.lotomania;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotomania extends PontuadorBasico {

	public PontuadorLotomania() {
		super();
		/*
		 * mapPontos.put(1, -9); mapPontos.put(2, -8); mapPontos.put(3, -7);
		 * mapPontos.put(4, -6); mapPontos.put(5, -5); mapPontos.put(6, -4);
		 * mapPontos.put(7, -3); mapPontos.put(8, -2); mapPontos.put(9, -1);
		 * mapPontos.put(10, 0); mapPontos.put(11, 1); mapPontos.put(12, 5);
		 * mapPontos.put(13, 10); mapPontos.put(14, 50); mapPontos.put(15, 100);
		 * mapPontos.put(16, 200); mapPontos.put(17, 100); mapPontos.put(18,
		 * 50); mapPontos.put(19, 10); mapPontos.put(20, -1000);
		 */

		mapPontos.put(0, 16 * 16 * 4);
		mapPontos.put(15, 1);
		mapPontos.put(16, 4);
		mapPontos.put(17, 16);
		mapPontos.put(18, 16 * 4);
		mapPontos.put(19, 16 * 16);
		mapPontos.put(20, 16 * 16 * 4);
	}

}
