package br.com.enio.silva.loterias.pontuador.lotomania;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotomaniaWinnerSimple01 extends PontuadorBasico {

	public PontuadorLotomaniaWinnerSimple01() {
		super();

		mapPontos.put(1, -2);
		mapPontos.put(1, -2);
		mapPontos.put(2, -2);
		mapPontos.put(3, -2);
		mapPontos.put(4, -2);

		mapPontos.put(5, -1);
		mapPontos.put(6, -1);
		mapPontos.put(7, -1);
		mapPontos.put(8, -1);
		mapPontos.put(9, -1);

		mapPontos.put(10, 0);
		mapPontos.put(11, 1);
		mapPontos.put(12, 2);
		mapPontos.put(13, 3);
		mapPontos.put(14, 4);

		mapPontos.put(15, 8);
		mapPontos.put(16, 16);
		mapPontos.put(17, 32);
		mapPontos.put(18, 1024);
		mapPontos.put(19, 2048);
		mapPontos.put(20, 4096);
	}

}
