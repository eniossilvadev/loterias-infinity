package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil21;

public class LotofacilConfig21 extends LotofacilConfig {

	public LotofacilConfig21() {

		super();
		setNrosApostados(21);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil21());
		setPrefixo("LF");

	}
}
