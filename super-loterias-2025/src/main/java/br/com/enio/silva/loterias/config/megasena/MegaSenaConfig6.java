package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega;

public class MegaSenaConfig6 extends MegaSenaConfigAb {

	public MegaSenaConfig6() {

		super();

		setNrosApostados(6);
		setQttInicial(150000);
		setMaxAnterior(1);
		setPontuador(new PontuadorMega());
		setPrefixo("MS");
	}

}
