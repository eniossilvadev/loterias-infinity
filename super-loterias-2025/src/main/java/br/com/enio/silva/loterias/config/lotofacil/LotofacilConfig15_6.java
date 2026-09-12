package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15_11;

public class LotofacilConfig15_6 extends LotofacilConfig {

	public LotofacilConfig15_6() {

		super();
		setNrosApostados(15);
		setQttInicial(50000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15_11());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
