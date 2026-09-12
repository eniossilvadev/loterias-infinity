package br.com.enio.silva.loterias.config.quina;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina10;
import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina10_2;
import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina11;

import java.util.Random;

public class QuinaConfig11 extends QuinaConfigAb {

	public QuinaConfig11() {

		super();

		setNrosApostados(11);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorQuina11());
		setPrefixo("QUI");
	}
}
