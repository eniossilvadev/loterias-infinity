package br.com.enio.silva.loterias.config;

import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomania80;

public class LotomaniaConfig80 extends LotomaniaConfigAb {

	public LotomaniaConfig80() {
		super();
		setNrosApostados(80);
		setQttInicial(200000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotomania80());
		setPrefixo("LM");
	}

}
