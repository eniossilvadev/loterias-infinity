package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina5_1;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ImprimirUtil;
import util.ListUtil;

public class GerarJogosContadoComFilltrosQuina {

	/**
	 * @param jogosLM
	 */
	@SuppressWarnings("unused")
	private static void imprimirJogosPontuados(List<JogoQuina> jogosLM) {
		System.out.println("\nAfter sort");
		for (JogoAb meuJogo : jogosLM) {
			System.out.println(meuJogo.getPontuacao() + ": " + meuJogo.getNumerosAsList());
		}
	}

	public static void main(String[] args) throws IOException {

		Integer qttInicial = 100000;
		Integer qttJogos = 1;
		Integer numPorJogo = 5;

		String path = ConstantesUtil.QUINA_CAMINHO_DOWNLOAD + "RESULTADO_Q.txt";
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);

		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
		        .getMapContaResultados(resultados);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);

		Map<Integer, Integer> mapAtraso = ContaAtrasosResultados.getInstance(80)
		        .getMapContaResultados(resultados);
		System.out.println(mapAtraso);

		mapAtraso = MapUtil.sortByValueDesc(mapAtraso);
		System.out.println(mapAtraso);

		Set<Integer> set = new HashSet<Integer>(
		        Arrays.asList(22, 28, 45, 49, 56, 9, 22, 39, 64, 72, 15, 22, 28, 52, 79));
		List<Integer> temUm = new ArrayList<Integer>();

		boolean inverte = false;
		int maxNum = 80;
		if (inverte) {
			temUm = new ArrayList<Integer>(set);
		} else {

			for (int i = 1; i < maxNum; i++) {
				temUm.add(i);
			}

			temUm.removeAll(set);

		}

		System.out.println("Filtro tem um: " + temUm.size() + "\t" + temUm);

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		for (int i = 0; i < qttInicial; i++) {
			preJogo = new ArrayList<Integer>();
			preJogos.add(preJogo);
		}

		preJogos = ListUtil.completar(preJogos, numPorJogo, 80);

		if (preJogos != null && preJogos.size() > 0) {
			System.out.println(ImprimirUtil.printListas(preJogos));

			FiltroIf filtro = null;

			System.out.println("Antes: " + preJogos.size());

			filtro = new FiltroDivide(1, 80);
			preJogos = filtro.filtrarListas(preJogos);
			System.out.println(FiltroDivide.class.getName() + "Depois Filtro " + preJogos.size());

			filtro = new FiltroParImpar(1);
			preJogos = filtro.filtrarListas(preJogos);
			System.out.println(FiltroParImpar.class.getName() + "Depois Filtro " + preJogos.size());

			filtro = new FiltroMinimoLinhas(8, 5, 2);
			// preJogos = filtro.filtrarListas(preJogos);
			System.out.println(
			        FiltroMinimoLinhas.class.getName() + "Depois Filtro " + preJogos.size());

			filtro = new FiltroMaximoLinhas(8, 10, 2);
			preJogos = filtro.filtrarListas(preJogos);
			System.out.println(
			        FiltroMaximoLinhas.class.getName() + "Depois Filtro " + preJogos.size());

			filtro = new FiltroExcluirTemUm(temUm);
			preJogos = filtro.filtrarListas(preJogos);

			System.out.println("Depois: " + preJogos.size());

			if (preJogos != null && preJogos.size() > 0) {
				// System.out.println(ImprimirUtil.printListas(preJogos));

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// System.out.println(ImprimirUtil.printListas(preJogos));

				List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
				JogoQuina jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoQuina(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");
				Pontuador pontuador = new PontuadorQuina5_1();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(resultados, meuJogo);
					System.out.println(meuJogo.getPontuacao() + ": " + meuJogo.getNumerosAsList());

				}

				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuarMap(meuJogo, mapResultado, 80, 1);
					pontuador.pontuarMap(meuJogo, mapResultado, 60, 1);
					pontuador.pontuarMap(meuJogo, mapAtraso, 80, 5);
					// System.out.println(meuJogo.getPontuacao() + ": " +
					// meuJogo.getNumerosAsList());
				}

				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, qttJogos);
				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(meuJogo.getPontuacao() + "[" + ++c + "]: "
					        + meuJogo.getNumerosAsList());
				}

				String nome = "" + System.currentTimeMillis();
				String folder = "C:\\loterias\\gerador-apostas\\quina\\";

				String output = folder + nome + " HT.txt";

				// String nome = baseNome + System.currentTimeMillis();
				//
				// String output = folder + nome + " HT.txt";
				// String paramsPath = folder + nome + "_params.txt";
				// String all = folder + "all.txt";

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {
					jogos.add(jj.getNumerosAsList());
				}

				System.out.println("Original");
				for (List<Integer> jogo : jogos) {
					System.out.println(jogo);
					Collections.replaceAll(jogo, 0, 0);
					Collections.sort(jogo);
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					System.out.println(jogo);
				}

				ArquivoUtil.saveLists(jogos, output, " ");
			}
		}
	}
}
