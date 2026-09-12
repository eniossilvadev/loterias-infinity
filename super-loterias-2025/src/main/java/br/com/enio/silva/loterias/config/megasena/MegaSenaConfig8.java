package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega8;

public class MegaSenaConfig8 extends MegaSenaConfigAb {

	public MegaSenaConfig8() {

		super();

		setNrosApostados(8);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorMega8());
		setPrefixo("MS");
	}
}
