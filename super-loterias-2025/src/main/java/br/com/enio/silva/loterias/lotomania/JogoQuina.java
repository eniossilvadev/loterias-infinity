package br.com.enio.silva.loterias.lotomania;

import java.util.List;

public class JogoQuina extends JogoAb implements Comparable<JogoQuina> {

	public JogoQuina(Integer[] numeros) {
		super(numeros.length, numeros);

	}

	public JogoQuina(List<Integer> lista) {
		super(lista.size(), lista);
	}

	@Override
	public int compareTo(JogoQuina o) {

		if (pontuacao != null && o != null && o.getPontuacao() != null) {
			return o.getPontuacao().compareTo(pontuacao);
		}
		return 0;
	}
}
