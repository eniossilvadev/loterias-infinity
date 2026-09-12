package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil19;

public class LotofacilConfig19 extends LotofacilConfig {

	public LotofacilConfig19() {

		super();
		setNrosApostados(19);
		setQttInicial(177100 / 2);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil19());
		setPrefixo("LF");

	}
}
