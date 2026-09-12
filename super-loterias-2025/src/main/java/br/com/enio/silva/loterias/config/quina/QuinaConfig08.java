package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina8;

public class QuinaConfig08 extends QuinaConfigAb {

	public QuinaConfig08() {

		super();

		setNrosApostados(8);
		setQttInicial(10000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina8());
		setPrefixo("QUI");
	}
}
