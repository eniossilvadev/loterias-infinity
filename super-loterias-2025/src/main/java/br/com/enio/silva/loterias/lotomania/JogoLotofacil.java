package br.com.enio.silva.loterias.lotomania;

import java.util.List;

public class JogoLotofacil extends JogoAb implements Comparable<JogoLotofacil> {

	public JogoLotofacil(Integer[] numeros) {
		super(numeros.length, numeros);

	}

	public JogoLotofacil(List<Integer> lista) {
		super(lista.size(), lista);
	}

	@Override
	public int compareTo(JogoLotofacil o) {

		if (pontuacao != null && o != null && o.getPontuacao() != null) {
			return o.getPontuacao().compareTo(pontuacao);
		}
		return 0;
	}
}
