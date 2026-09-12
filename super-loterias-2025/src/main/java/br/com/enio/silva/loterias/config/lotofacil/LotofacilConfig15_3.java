package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15_13;

public class LotofacilConfig15_3 extends LotofacilConfig {

	public LotofacilConfig15_3() {

		super();
		setNrosApostados(15);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15_13());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
