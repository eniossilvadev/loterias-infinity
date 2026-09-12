package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina7;

public class QuinaConfig7 extends QuinaConfigAb {

	public QuinaConfig7() {

		super();

		setNrosApostados(7);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina7());
		setPrefixo("QUI");
	}
}
