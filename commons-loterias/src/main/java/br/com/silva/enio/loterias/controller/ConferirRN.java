package br.com.silva.enio.loterias.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;

public class ConferirRN {

	public static Integer conferir(List<Integer> resultado, List<Integer> jogo) {
		return ListUtils.intersection(resultado, jogo).size();
	}

	public static List<Integer> conferir(List<List<Integer>> sorteios, List<List<Integer>> jogos,
			int concurso) {

		est = new Estatisticas();
		totalSorteios = est.obterTotalDeSorteios(sorteios);
		if (concurso > totalSorteios) {
			System.out.println("Concurso " + concurso + " ainda não foi sorteado. ");
			return null;
		}

		List<Integer> sorteio = sorteios.get(concurso - 1);
		List<Integer> acertos = new ArrayList<Integer>();

		for (List<Integer> jogo : jogos) {
			acertos.add(ListUtils.intersection(sorteio, jogo).size());
		}

		return acertos;
	}

	public static List<List<Integer>> conferir(List<List<Integer>> sorteios,
			List<List<Integer>> jogos, int concursoInicio, int concursoFinal) {
		List<List<Integer>> acertos = new ArrayList<List<Integer>>();
		for (int i = concursoInicio; i <= concursoFinal; i++) {
			acertos.add(conferir(sorteios, jogos, i));
		}
		return acertos;
	}

	@SuppressWarnings("unchecked")
	public static List<Integer> intersecao(List<Integer> resultado, List<Integer> jogo) {
		return ListUtils.intersection(resultado, jogo);
	}

	private static Estatisticas est = new Estatisticas();

	private static int totalSorteios = 0;

}
