package br.com.enio.silva.loterias.pontuador;

import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

public class PontuadorLotofacil extends PontuadorBasico {

	public PontuadorLotofacil() {
		super();
		// TODO Auto-generated constructor stub

		mapPontos.put(5, -100);
		mapPontos.put(6, -10);
		mapPontos.put(7, -1);
		mapPontos.put(8, 0);
		mapPontos.put(9, 1);
		mapPontos.put(10, 1);
		mapPontos.put(11, 5);
		mapPontos.put(12, 4);
		mapPontos.put(13, 1);
		mapPontos.put(14, -100);

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
