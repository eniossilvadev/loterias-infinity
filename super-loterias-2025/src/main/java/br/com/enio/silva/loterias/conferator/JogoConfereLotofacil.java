package br.com.enio.silva.loterias.conferator;

import java.util.Arrays;
import java.util.List;

public class JogoConfereLotofacil extends JogoConfereAb {

	public JogoConfereLotofacil(int concurso, List<Integer> jogo, List<Integer> sorteados) {
		super(concurso, jogo, sorteados);

	}

	@Override
	public List<Integer> getPremiacao() {
		List<Integer> listaPremiados = Arrays.asList(11, 12, 13, 14, 15);
		return listaPremiados;
	}

}
