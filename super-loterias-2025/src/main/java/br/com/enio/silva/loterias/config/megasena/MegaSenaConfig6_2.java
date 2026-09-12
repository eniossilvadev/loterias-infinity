package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega6_2;

public class MegaSenaConfig6_2 extends MegaSenaConfigAb {

	public MegaSenaConfig6_2() {

		super();

		setNrosApostados(6);
		setQttInicial(50000);
		setMaxAnterior(1);
		setPontuador(new PontuadorMega6_2());
		setPrefixo("MS");
	}

}
