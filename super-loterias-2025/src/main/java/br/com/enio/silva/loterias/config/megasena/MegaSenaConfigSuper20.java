package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.lotomania.PontuadorMegaSenaSuper20;

public class MegaSenaConfigSuper20 extends MegaSenaConfigAb {

	public MegaSenaConfigSuper20() {

		super();
		setPontuador(new PontuadorMegaSenaSuper20());
		setNrosApostados(20);
	}

}
