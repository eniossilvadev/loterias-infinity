package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina6_2;

public class QuinaConfig6_2 extends QuinaConfigAb {

	public QuinaConfig6_2() {

		super();

		setNrosApostados(6);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina6_2());
		setPrefixo("QUI");
	}

}
