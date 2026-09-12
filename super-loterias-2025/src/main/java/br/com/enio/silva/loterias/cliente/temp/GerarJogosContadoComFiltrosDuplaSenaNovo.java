package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.duplasena.GerarListaDuplaSena;
import br.com.enio.silva.loterias.duplasena.PontuadorDuplaSenaP2;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoDuplaSena;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.silva.enio.loterias.controller.DuplaSenaRN;
import util.ImprimirUtil;
import util.ListUtil;

public class GerarJogosContadoComFiltrosDuplaSenaNovo {

	public static List<JogoDuplaSena> filtroSkip(List<JogoDuplaSena> jogos, int sk) {

		System.out.println("Skipping... " + sk + "!");
		List<JogoDuplaSena> retorno = new ArrayList<JogoDuplaSena>();
		int skip = sk;
		for (JogoDuplaSena lista : jogos) {
			if (skip++ % sk == 0) {
				retorno.add(lista);
			}
		}
		return retorno;
	}

	public static void main(String[] args) throws IOException {

		Integer qttInicial = 50000;
		Integer qttJogos = 1;
		int tamanhoJogo = 6;
		int skip = 0;
		boolean pontuacaoExtra = true;
		String baseNome = "ESSBSEF_";
		int top = 0;
		int maxAnterior = tamanhoJogo - 5;
		boolean inverte = true;
		int maxNum = 50;

		String nome = baseNome + System.currentTimeMillis();

		String folder = "E:\\loterias\\zip\\";
		String output = folder + nome + " HT.txt";
		String all1 = folder + "all1.txt";
		String all2 = folder + "pontuacao1.txt";
		String all3 = folder + "pontuacao2.txt";

		String path = ConstantesUtil.DUPLA_SENA_CAMINHO_DOWNLOAD + "RESULTADO.txt";
		List<List<Integer>> resultados = GerarListaDuplaSena.getInstance()
		        .gerarArquivoResultado(path);

		Set<Integer> set = new HashSet<Integer>(Arrays.asList());
		List<Integer> temUm = new ArrayList<Integer>();

		if (inverte) {
			for (int i = 1; i < maxNum; i++) {
				temUm.add(i);
			}

			temUm.removeAll(set);
		} else {
			temUm = new ArrayList<Integer>(set);
		}

		float teste = new Random().nextFloat();
		// int ultimos = resultados.size();
		int ultimos = resultados.size() / 2;
		// int ultimos = 100;

		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
		        .getMapContaResultados(resultados, ultimos);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);

		Map<Integer, Integer> mapAtraso = ContaAtrasosResultados
		        .getInstance(DuplaSenaRN.MAIOR_NUMERO).getMapContaResultados(resultados);
		System.out.println(mapAtraso);

		mapAtraso = MapUtil.sortByValueDesc(mapAtraso);
		System.out.println(mapAtraso);

		try {
			System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Integer> last = resultados.get(resultados.size() - 1);
		// TOP 10

		List<Integer> top10 = new ArrayList<Integer>();
		if (top > 0) {
			for (Map.Entry<Integer, Integer> entry : mapResultado.entrySet()) {
				Integer key = entry.getKey();
				if (last.contains(key)) {
					top10.add(key);
				}
				if (top10.size() == top) {
					break;
				}
			}

			System.out.println("Top: " + top10);
		}

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		for (int i = 0; i < qttInicial; i++) {
			preJogo = new ArrayList<Integer>(top10);
			preJogos.add(preJogo);
		}

		preJogos = ListUtil.completar(preJogos, tamanhoJogo, DuplaSenaRN.MAIOR_NUMERO);

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		// filtro = new FiltroUltimoSorteio(8, tamanhoJogo - 5, last);
		// preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroDivide(3, DuplaSenaRN.MAIOR_NUMERO);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroParImpar(3);
		preJogos = filtro.filtrarListas(preJogos);

		// filtro = new FiltroMinimoLinhas(5, 10, 2);
		// preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMaximoLinhas(5, 10, 2);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroExcluirTemUm(temUm);
		// preJogos = filtro.filtrarListas(preJogos);

		ArquivoUtil.save(ImprimirUtil.printListas(preJogos), all1);

		List<JogoDuplaSena> jogosLM = new ArrayList<JogoDuplaSena>();
		JogoDuplaSena jlm = null;
		for (List<Integer> pj : preJogos) {
			jlm = new JogoDuplaSena(pj);
			jogosLM.add(jlm);
		}

		System.out.println("\n");

		StringBuilder str = new StringBuilder();
		Pontuador pontuador = new PontuadorDuplaSenaP2();

		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			Integer before = meuJogo.getPontuacao();
			((PontuadorDuplaSenaP2) pontuador).pontuarPorPosicao(resultados, meuJogo, 6);

			int now = meuJogo.getPontuacao();
			str.append("[" + before + ", " + now + "] " + meuJogo.getNumerosAsList()).append("\n");

		}

		ArquivoUtil.save(str.toString(), all2);

		try {
			System.out.println("Wainting...");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		str = new StringBuilder();
		if (pontuacaoExtra) {
			for (JogoAb meuJogo : jogosLM) {

				int before = meuJogo.getPontuacao();
				pontuador.pontuarMap(meuJogo, mapResultado, 25, 3);
				// pontuador.pontuarMap(meuJogo, mapResultado, 20, 1);
				pontuador.pontuarMap(meuJogo, mapResultado, 15, 1);
				pontuador.pontuarMap(meuJogo, mapAtraso, 25, 4);

				int now = meuJogo.getPontuacao();
				str.append("[" + before + ", " + now + "] " + meuJogo.getNumerosAsList())
				        .append("\n");
			}
		}

		ArquivoUtil.save(str.toString(), all3);

		Collections.sort(jogosLM);

		if (skip > 0) {
			jogosLM = filtroSkip(jogosLM, skip);
		}

		filtro = new FiltroMaxmoIgualAnterior(maxAnterior);
		preJogos = filtro.filtrarListas(preJogos);
		jogosLM = jogosLM.subList(0, qttJogos);
		int c = 0;
		for (JogoAb meuJogo : jogosLM) {
			System.out.println(
			        meuJogo.getPontuacao() + "[" + c++ + "]: " + meuJogo.getNumerosAsList());
		}

		List<List<Integer>> jogos = new ArrayList<List<Integer>>();
		for (JogoAb jj : jogosLM) {
			jogos.add(jj.getNumerosAsList());
		}

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo);
		}

		ArquivoUtil.saveLists(jogos, output, "\t");

	}
}
