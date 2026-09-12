package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina6_1;

public class QuinaConfig6_1 extends QuinaConfigAb {

	public QuinaConfig6_1() {

		super();

		setNrosApostados(6);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina6_1());
		setPrefixo("QUI");
	}

}
