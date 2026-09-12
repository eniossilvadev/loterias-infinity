package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil17;

public class LotofacilConfig17 extends LotofacilConfig {

	public LotofacilConfig17() {

		super();
		setNrosApostados(17);
		setQttInicial(200000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil17());
		setPrefixo("LF");

	}
}
