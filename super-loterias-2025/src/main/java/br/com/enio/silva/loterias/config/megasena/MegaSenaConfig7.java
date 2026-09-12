package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega7;

public class MegaSenaConfig7 extends MegaSenaConfigAb {

	public MegaSenaConfig7() {

		super();

		setNrosApostados(7);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorMega7());
		setPrefixo("MS");
	}

}
