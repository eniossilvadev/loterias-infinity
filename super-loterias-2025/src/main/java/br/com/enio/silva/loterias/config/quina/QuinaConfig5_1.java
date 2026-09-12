package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina5_1;

public class QuinaConfig5_1 extends QuinaConfigAb {

	public QuinaConfig5_1() {

		super();

		setNrosApostados(5);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina5_1());
		setPrefixo("QUI");
	}

}
