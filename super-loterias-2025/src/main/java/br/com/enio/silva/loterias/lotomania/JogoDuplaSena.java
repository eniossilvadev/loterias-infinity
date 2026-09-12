package br.com.enio.silva.loterias.lotomania;

import java.util.List;

public class JogoDuplaSena extends JogoAb implements Comparable<JogoDuplaSena> {

	public JogoDuplaSena(Integer[] numeros) {
		super(6, numeros);

	}

	public JogoDuplaSena(List<Integer> lista) {
		super(lista.size(), lista);
	}

	@Override
	public int compareTo(JogoDuplaSena o) {

		if (pontuacao != null && o != null && o.getPontuacao() != null) {
			return o.getPontuacao().compareTo(pontuacao);
		}
		return 0;
	}
}
