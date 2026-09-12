package br.com.enio.silva.loterias.config;

import br.com.enio.silva.loterias.config.pontuador.PontuadorTimemania;

public class TimemaniaConfig extends TimemaniaConfigAb {

	public TimemaniaConfig() {

		super();

		setNrosApostados(10);
		setQttInicial(10000);
		setMaxAnterior(1);
		setPontuador(new PontuadorTimemania());
		setPrefixo("TM");
	}
}
