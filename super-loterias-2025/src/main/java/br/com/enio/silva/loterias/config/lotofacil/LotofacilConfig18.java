package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil18;

public class LotofacilConfig18 extends LotofacilConfig {

	public LotofacilConfig18() {

		super();
		setNrosApostados(18);
		setQttInicial(200000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil18());
		setPrefixo("LF");

	}
}
