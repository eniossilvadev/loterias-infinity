package br.com.enio.silva.loterias.duplasena.config;

import br.com.enio.silva.loterias.duplasena.PontuadorDuplaSena8;

public class DuplaSenaConfig8 extends DuplaSenaConfigAb {

	public DuplaSenaConfig8() {

		super();
		setNrosApostados(8);
		setPontuador(new PontuadorDuplaSena8());
	}

}
