package br.com.enio.silva.loterias.lotomania;

import java.util.Arrays;
import java.util.List;

public class JogoLotomania extends JogoAb implements Comparable<JogoLotomania> {

	public JogoLotomania(Integer[] numeros) {
		super(numeros.length, numeros);

	}

	public JogoLotomania(List<Integer> lista) {
		super(lista.size(), lista);
	}

	@Override
	public void addPontuacao(Integer pontuacao) {
		this.pontuacao += pontuacao;
	}

	@Override
	public int compareTo(JogoLotomania o) {

		if (pontuacao != null && o != null && o.getPontuacao() != null) {
			return o.getPontuacao().compareTo(pontuacao);
		}
		return 0;
	}

	@Override
	public Integer[] getNumeros() {
		return numeros;
	}

	@Override
	public List<Integer> getNumerosAsList() {
		return Arrays.asList(numeros);
	}

	@Override
	public Integer getPontuacao() {
		return pontuacao;
	}

	@Override
	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

}
