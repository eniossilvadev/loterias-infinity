package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil20;

public class LotofacilConfig20 extends LotofacilConfig {

	public LotofacilConfig20() {

		super();
		setNrosApostados(20);
		setQttInicial(70000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil20());
		setPrefixo("LF");

	}
}
