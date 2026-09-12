package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15_14;

public class LotofacilConfig15_5 extends LotofacilConfig {

	public LotofacilConfig15_5() {

		super();
		setNrosApostados(15);
		setQttInicial(50000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15_14());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
