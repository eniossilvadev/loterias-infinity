package br.com.enio.silva.loterias.config.quina;

import java.util.Random;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina10;
import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina10_2;
import br.com.enio.silva.loterias.lotomania.Pontuador;

public class QuinaConfig10 extends QuinaConfigAb {

	public QuinaConfig10() {

		super();

		setNrosApostados(10);
		setQttInicial(10000);
		setMaxAnterior(1);
		setPontuador(new Random().nextBoolean() ? new PontuadorQuina10() : new PontuadorQuina10_2());
		setPrefixo("QUI");
	}

	@Override
	public Pontuador getPontuador() {
		return new Random().nextBoolean() ? new PontuadorQuina10() : new PontuadorQuina10_2();
	}
}
