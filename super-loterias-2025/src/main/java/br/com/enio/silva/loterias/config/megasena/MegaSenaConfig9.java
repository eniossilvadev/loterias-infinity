package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega9;

public class MegaSenaConfig9 extends MegaSenaConfigAb {

	public MegaSenaConfig9() {

		super();

		setNrosApostados(9);
		setQttInicial(200000);
		setMaxAnterior(2);
		setPontuador(new PontuadorMega9());
		setPrefixo("MS");
		setMaxIgualAnteriores(3);
	}
}
