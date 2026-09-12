package br.com.enio.silva.loterias.lotomania;

import java.util.List;

public class JogoTimemania extends JogoAb implements Comparable<JogoTimemania> {

	public JogoTimemania(Integer[] numeros) {
		super(numeros.length, numeros);

	}

	public JogoTimemania(List<Integer> lista) {
		super(lista.size(), lista);
	}

	@Override
	public int compareTo(JogoTimemania o) {

		if (pontuacao != null && o != null && o.getPontuacao() != null) {
			return o.getPontuacao().compareTo(pontuacao);
		}
		return 0;
	}
}
