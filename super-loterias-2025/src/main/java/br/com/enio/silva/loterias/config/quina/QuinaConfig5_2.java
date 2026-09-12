package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina5_2;

public class QuinaConfig5_2 extends QuinaConfigAb {

	public QuinaConfig5_2() {

		super();

		setNrosApostados(5);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina5_2());
		setPrefixo("QUI");
	}

}
