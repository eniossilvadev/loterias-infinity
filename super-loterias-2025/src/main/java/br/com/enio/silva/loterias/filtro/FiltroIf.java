package br.com.enio.silva.loterias.filtro;

import java.util.List;

import br.com.enio.silva.loterias.lotomania.JogoAb;

public interface FiltroIf {

	List<? extends JogoAb> filtrarJogos(List<? extends JogoAb> jogos);

	List<Integer> filtrarLista(List<Integer> lista);

	List<List<Integer>> filtrarListas(List<List<Integer>> listas);

}
