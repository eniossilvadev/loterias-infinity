package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega12;

public class MegaSenaConfig12 extends MegaSenaConfigAb {

	public MegaSenaConfig12() {

		super();

		setNrosApostados(12);
		setQttInicial(200000);
		setMaxAnterior(2);
		setPontuador(new PontuadorMega12());
		setPrefixo("MS");
		setMaxIgualAnteriores(3);
	}
}
