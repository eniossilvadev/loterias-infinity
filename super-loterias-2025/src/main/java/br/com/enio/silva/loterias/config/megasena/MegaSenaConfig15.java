package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega15;

public class MegaSenaConfig15 extends MegaSenaConfigAb {

	public MegaSenaConfig15() {

		super();

		setNrosApostados(15);
		setQttInicial(200000);
		setMaxAnterior(4);
		setPontuador(new PontuadorMega15());
		setPrefixo("MS");
		setMaxIgualAnteriores(3);
	}
}
