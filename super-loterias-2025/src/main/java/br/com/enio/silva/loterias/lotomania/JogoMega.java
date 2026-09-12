package br.com.enio.silva.loterias.lotomania;

import java.util.List;

public class JogoMega extends JogoAb implements Comparable<JogoMega> {

	public JogoMega(Integer[] numeros) {
		super(numeros.length, numeros);

	}

	public JogoMega(List<Integer> lista) {
		super(lista.size(), lista);
	}

	@Override
	public int compareTo(JogoMega o) {

		if (pontuacao != null && o != null && o.getPontuacao() != null) {
			return o.getPontuacao().compareTo(pontuacao);
		}
		return 0;
	}
}
