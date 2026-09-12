package br.com.enio.silva.loterias.config.megasena;

import br.com.enio.silva.loterias.config.pontuador.PontuadorMega11;

public class MegaSenaConfig11 extends MegaSenaConfigAb {

	public MegaSenaConfig11() {

		super();

		setNrosApostados(11);
		setQttInicial(200000);
		setMaxAnterior(2);
		setPontuador(new PontuadorMega11());
		setPrefixo("MS");
		setMaxIgualAnteriores(3);
	}
}
