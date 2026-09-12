package br.com.enio.silva.loterias.conferator;

import java.util.Arrays;
import java.util.List;

public class JogoConfereDuplaSena extends JogoConfereAb {

	public JogoConfereDuplaSena(int concurso, List<Integer> jogo, List<Integer> sorteados) {
		super(concurso, jogo, sorteados);

	}

	@Override
	public List<Integer> getPremiacao() {
		List<Integer> listaPremiados = Arrays.asList(3, 4, 5, 6);
		return listaPremiados;
	}

}
