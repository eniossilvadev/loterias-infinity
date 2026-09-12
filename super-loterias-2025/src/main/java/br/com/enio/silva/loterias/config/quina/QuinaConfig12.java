package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina12;

public class QuinaConfig12 extends QuinaConfigAb {

	public QuinaConfig12() {

		super();

		setNrosApostados(12);
		setQttInicial(10000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina12());
		setPrefixo("QUI");
	}
}
