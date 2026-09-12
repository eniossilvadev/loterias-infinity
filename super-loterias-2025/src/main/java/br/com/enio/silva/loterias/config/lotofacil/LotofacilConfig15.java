package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15Simples;

public class LotofacilConfig15 extends LotofacilConfig {

	public LotofacilConfig15() {

		super();
		setNrosApostados(15);
		setQttInicial(26000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15Simples());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
