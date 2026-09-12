package br.com.enio.silva.loterias.duplasena.config;

import br.com.enio.silva.loterias.duplasena.PontuadorDuplaSena10;

public class DuplaSenaConfig10 extends DuplaSenaConfigAb {

	public DuplaSenaConfig10() {

		super();
		setNrosApostados(10);
		setPontuador(new PontuadorDuplaSena10());
	}

}
