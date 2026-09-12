package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina5_3;

public class QuinaConfig5_3 extends QuinaConfigAb {

	public QuinaConfig5_3() {

		super();

		setNrosApostados(5);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina5_3());
		setPrefixo("QUI");
	}

}
