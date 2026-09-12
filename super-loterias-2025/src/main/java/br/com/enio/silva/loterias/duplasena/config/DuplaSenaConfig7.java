package br.com.enio.silva.loterias.duplasena.config;

import br.com.enio.silva.loterias.duplasena.PontuadorDuplaSena7;

public class DuplaSenaConfig7 extends DuplaSenaConfigAb {

	public DuplaSenaConfig7() {

		super();
		setNrosApostados(7);
		setPontuador(new PontuadorDuplaSena7());
	}

}
