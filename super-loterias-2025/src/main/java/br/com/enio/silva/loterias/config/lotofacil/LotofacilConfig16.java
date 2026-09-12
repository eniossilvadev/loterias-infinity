package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil16Simples;

public class LotofacilConfig16 extends LotofacilConfig {

	public LotofacilConfig16() {

		super();
		setNrosApostados(16);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil16Simples());
		setPrefixo("LF");

	}
}
