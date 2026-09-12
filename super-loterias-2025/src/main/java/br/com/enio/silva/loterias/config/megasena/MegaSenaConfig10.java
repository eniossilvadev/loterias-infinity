package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega10;

public class MegaSenaConfig10 extends MegaSenaConfigAb {

	public MegaSenaConfig10() {

		super();

		setNrosApostados(10);
		//		setQttInicial(200000);
		setMaxAnterior(2);
		setPontuador(new PontuadorMega10());
		setPrefixo("MS");
		setMaxIgualAnteriores(3);
	}
}
