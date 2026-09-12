package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.lotomania.PontuadorBasico;
import br.com.enio.silva.loterias.util.MapUtil;

import util.ImprimirUtil;
import util.ListUtil;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosContadoComFilltros {

	public static void main(String[] args) throws IOException {
		List<List<Integer>> resultados = GerarListaLotomania.getInstance().gerarArquivoResultado();
		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
				.getMapContaResultados(resultados);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);

		// TOP 10
		List<Integer> top10 = new ArrayList<Integer>();
		for (Map.Entry<Integer, Integer> entry : mapResultado.entrySet()) {
			top10.add(entry.getKey());
			if (top10.size() == 10) {
				break;
			}
		}
		System.out.println(top10);

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		for (int i = 0; i < 250000; i++) {
			preJogo = new ArrayList<Integer>(top10);
			preJogos.add(preJogo);
		}

		preJogos = ListUtil.completar(preJogos, 50, 100);

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());
		filtro = new FiltroParImpar(3);
		filtro.filtrarListas(preJogos);
		filtro = new FiltroMinimoLinhas(10, 10, 3);
		System.out.println("Depois: " + preJogos.size());

		System.out.println(ImprimirUtil.printListas(preJogos));

		List<JogoLotomania> jogosLM = new ArrayList<JogoLotomania>();
		JogoLotomania jlm = null;
		for (List<Integer> pj : preJogos) {
			jlm = new JogoLotomania(pj);
			jogosLM.add(jlm);
		}

		System.out.println("\n");
		Pontuador pontuador = new PontuadorBasico();
		for (JogoLotomania meuJogo : jogosLM) {
			pontuador.pontuar(resultados, meuJogo);
			System.out.println(meuJogo.getPontuacao() + ": " + meuJogo.getNumerosAsList());

		}

		for (JogoLotomania meuJogo : jogosLM) {
			pontuador.pontuarMap(meuJogo, mapResultado, 30);
			// System.out.println(meuJogo.getPontuacao() + ": " +
			// meuJogo.getNumerosAsList());
		}

		Collections.sort(jogosLM);

		System.out.println("\nAfter sort");
		for (JogoLotomania meuJogo : jogosLM) {
			// System.out.println(meuJogo.getPontuacao() + ": " +
			// meuJogo.getNumerosAsList());
		}

		jogosLM = jogosLM.subList(0, 6);
		for (JogoLotomania meuJogo : jogosLM) {
			System.out.println(meuJogo.getPontuacao() + ": " + meuJogo.getNumerosAsList());
		}

		String nome = "Enio Silva 1";

		String input = "C:\\Users\\Enio Silva\\Desktop\\Loterias\\lotomania\\" + nome + ".txt";
		String output = "C:\\Users\\Enio Silva\\Desktop\\Loterias\\lotomania\\" + nome + " HT.txt";

		List<List<Integer>> jogos = new ArrayList<List<Integer>>();
		for (JogoLotomania jj : jogosLM) {
			jogos.add(jj.getNumerosAsList());
		}

		System.out.println("Original");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
			Collections.replaceAll(jogo, 100, 0);
			Collections.sort(jogo);
		}

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
		}

		ArquivoUtil.saveLists(jogos, output, " ");

	}
}
