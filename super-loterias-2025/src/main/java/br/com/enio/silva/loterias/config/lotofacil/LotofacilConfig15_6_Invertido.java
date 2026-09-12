package br.com.enio.silva.loterias.config.lotofacil;

import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15SimplesInvertido;

public class LotofacilConfig15_6_Invertido extends LotofacilConfig {

	public LotofacilConfig15_6_Invertido() {

		super();
		setNrosApostados(15);
		setQttInicial(26000);
		setMaxAnterior(1);
		setPontuador(new PontuadorLotofacil15SimplesInvertido());
		setPrefixo("LF");
		setPontuacaoExtra(false);
	}
}
