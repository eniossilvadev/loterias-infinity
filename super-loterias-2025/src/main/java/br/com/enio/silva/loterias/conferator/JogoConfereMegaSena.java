package br.com.enio.silva.loterias.conferator;

import java.util.Arrays;
import java.util.List;

public class JogoConfereMegaSena extends JogoConfereAb {

	public JogoConfereMegaSena(int concurso, List<Integer> jogo, List<Integer> sorteados) {
		super(concurso, jogo, sorteados);

	}

	@Override
	public List<Integer> getPremiacao() {
		List<Integer> listaPremiados = Arrays.asList(4, 5, 6);
		return listaPremiados;
	}

}
