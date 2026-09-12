package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega16;

public class MegaSenaConfig16 extends MegaSenaConfigAb {

	public MegaSenaConfig16() {

		super();

		setNrosApostados(16);
		setQttInicial(200000);
		setMaxAnterior(1);
		setPontuador(new PontuadorMega16());
		setPrefixo("MS");
	}

}
