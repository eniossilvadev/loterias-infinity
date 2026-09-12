package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15Simples2;

public class LotofacilConfig15_4 extends LotofacilConfig {

	public LotofacilConfig15_4() {

		super();
		setNrosApostados(15);
		setQttInicial(300000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15Simples2());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
