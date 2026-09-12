package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.lotomania.PontuadorMegaSenaSuper08;

public class MegaSenaConfigSuper08 extends MegaSenaConfigAb {

	public MegaSenaConfigSuper08() {

		super();
		setPontuador(new PontuadorMegaSenaSuper08());
		setNrosApostados(8);
	}

}
