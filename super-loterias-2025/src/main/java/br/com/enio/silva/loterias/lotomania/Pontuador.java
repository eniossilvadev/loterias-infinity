package br.com.enio.silva.loterias.lotomania;

import java.util.List;
import java.util.Map;

public interface Pontuador {

	void pontuar(List<List<Integer>> resultados, JogoAb jogo);

	void pontuar(List<List<Integer>> resultados, List<JogoAb> agrupamentosJogos, JogoAb main);

	void pontuarMap(JogoAb meuJogo, Map<Integer, Integer> map, Integer limite);

	void pontuarMap(JogoAb meuJogo, Map<Integer, Integer> map, Integer limite,
	        Integer multiplicador);

}
