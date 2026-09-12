package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina7;

public class QuinaConfig07 extends QuinaConfigAb {

	public QuinaConfig07() {

		super();

		setNrosApostados(8);
		setQttInicial(10000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina7());
		setPrefixo("QUI");
	}
}
