package br.com.enio.silva.loterias.config;

public class LotomaniaConfig extends LotomaniaConfigAb {

	public LotomaniaConfig() {
		super();
		setNrosApostados(50);
		setQttInicial(50000);
		setMaxAnterior(1);
		setPrefixo("LM");
	}
}
