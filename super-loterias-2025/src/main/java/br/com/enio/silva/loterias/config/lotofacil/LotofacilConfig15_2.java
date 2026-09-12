package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15;

public class LotofacilConfig15_2 extends LotofacilConfig {

	public LotofacilConfig15_2() {

		super();
		setNrosApostados(15);
		setQttInicial(100000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
