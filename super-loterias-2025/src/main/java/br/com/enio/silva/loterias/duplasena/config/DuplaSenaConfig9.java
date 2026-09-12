package br.com.enio.silva.loterias.duplasena.config;

import br.com.enio.silva.loterias.duplasena.PontuadorDuplaSena9;

public class DuplaSenaConfig9 extends DuplaSenaConfigAb {

	public DuplaSenaConfig9() {

		super();
		setNrosApostados(9);
		setPontuador(new PontuadorDuplaSena9());
	}

}
