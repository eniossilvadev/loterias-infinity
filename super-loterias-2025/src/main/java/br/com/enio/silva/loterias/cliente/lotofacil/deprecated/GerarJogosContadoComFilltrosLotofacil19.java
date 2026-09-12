package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotofacil;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.lotomania.PontuadorBasico;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil19;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaLotofacil;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.silva.enio.loterias.controller.LotofacilRN;
import util.ImprimirUtil;
import util.ListUtil;

public class GerarJogosContadoComFilltrosLotofacil19 {

	public static final String ESQUEMA_PADRAO = "ESQUEMA_PADRAO";

	public static final String ESQUEMA_19 = "ESQUEMA_19";

	public static List<JogoLotofacil> filtroSkip(List<JogoLotofacil> jogos, int sk) {

		System.out.println("Skipping... " + sk + "!");
		List<JogoLotofacil> retorno = new ArrayList<JogoLotofacil>();
		int skip = sk;
		for (JogoLotofacil lista : jogos) {
			if (skip++ % sk == 0) {
				retorno.add(lista);
			}
		}
		return retorno;
	}

	public static void main(String[] args) throws IOException {

		StringBuilder params = new StringBuilder();

		String esquema = ESQUEMA_19;

		Integer qttInicial = 150000;
		params.append("qttInicial: ").append(qttInicial).append("\n");

		Integer qttJogos = 1;
		params.append("qttJogos: ").append(qttJogos).append("\n");

		int tamanhoJogo = 19;
		params.append("tamanhoJogo: ").append(tamanhoJogo).append("\n");

		int skip = 0;
		params.append("skip: ").append(skip).append("\n");

		boolean pontuacaoExtra = true;
		params.append("pontuacaoExtra: ").append(pontuacaoExtra).append("\n");

		String concursos = "WINNER";
		String baseNome = "LF_" + concursos + "_01_";
		int top = 3;
		params.append("top: ").append(top).append("\n");

		int maxAnterior = 12;
		params.append("maxAnterior: ").append(maxAnterior).append("\n");

		String nome = baseNome + System.currentTimeMillis();

		String folder = "C:\\loterias\\gerador-apostas\\lotofacil\\" + concursos + "\\";
		String output = folder + nome + " HT.txt";
		String paramsPath = folder + nome + "_params.txt";
		String all = folder + "all.txt";

		String path = ConstantesUtil.LF_CAMINHO_DOWNLOAD + "RESULTADO_LF.txt";
		List<List<Integer>> resultados = GerarListaLotofacil.getInstance()
		        .gerarArquivoResultado(path);

		int ultimos = resultados.size() / 2;
		// int ultimos = resultados.size() / 5;
		// int ultimos = 100;
		params.append("ultimos: ").append(ultimos).append("\n");

		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
		        .getMapContaResultados(resultados, ultimos);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);

		Map<Integer, Integer> mapAtraso = ContaAtrasosResultados
		        .getInstance(LotofacilRN.MAIOR_NUMERO).getMapContaResultados(resultados);
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
		// 5, 6, 7, 12, 13, 14, 19, 20, 21

		top10.add(1);
		top10.add(17);

		top10.add(25);
		if (top > 0) {
			// for (Map.Entry<Integer, Integer> entry : mapResultado.entrySet())
			// {
			// Integer key = entry.getKey();
			// if (last.contains(key)) {
			// top10.add(key);
			// }
			// if (top10.size() == top) {
			// break;
			// }
			// }
			//
		}

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		for (int i = 0; i < qttInicial; i++) {
			preJogo = new ArrayList<Integer>(top10);
			preJogos.add(preJogo);
		}

		preJogos = ListUtil.completar(preJogos, tamanhoJogo, LotofacilRN.MAIOR_NUMERO);

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		String remove = "C:\\loterias\\gerador-apostas\\lotofacil\\corrente.txt";
		List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListas(remove);

		filtro = new FiltroRemoverIntersecao(remover);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroUltimoSorteio(7, 14, last);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroDivide(3, LotofacilRN.MAIOR_NUMERO);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroParImpar(3);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroMinimoLinhas(5, 5, 2);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroMaximoLinhas(5, 5, 4);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		Random r = new Random();
		List<Integer> excluir = new ArrayList<>();
		excluir.add(0);
		while (excluir.size() < 6) {
			excluir.add(r.nextInt(ConstantesUtil.LOTOFACIL_MAIOR_NUMERO) + 1);
		}
		Set<Integer> set = new HashSet<Integer>(excluir);
		List<Integer> temUm = new ArrayList<Integer>(set);
		filtro = new FiltroExcluirTemUm(temUm);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\t").append(excluir).append("\n");

		ArquivoUtil.save(ImprimirUtil.printListas(preJogos), all);

		List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();
		JogoLotofacil jlm = null;
		for (List<Integer> pj : preJogos) {
			jlm = new JogoLotofacil(pj);
			jogosLM.add(jlm);
		}

		System.out.println("\n");
		Pontuador pontuador = new PontuadorLotofacil();
		if (ESQUEMA_19.equals(esquema)) {
			pontuador = new PontuadorLotofacil19();
		}

		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			Integer before = meuJogo.getPontuacao();
			((PontuadorBasico) pontuador).pontuarPorPosicao(resultados, meuJogo, 1);

			System.out.println("[" + before + ", " + meuJogo.getPontuacao() + "] "
			        + meuJogo.getNumerosAsList());
		}

		try {
			System.out.println("Wainting...");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (pontuacaoExtra) {
			for (JogoAb meuJogo : jogosLM) {

				Integer before = meuJogo.getPontuacao();
				pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);
				pontuador.pontuarMap(meuJogo, mapResultado, 19, 1);
				// pontuador.pontuarMap(meuJogo, mapResultado, 15, 1);
				pontuador.pontuarMap(meuJogo, mapAtraso, 25, 1);

				System.out.println("[" + before + ", " + meuJogo.getPontuacao() + "] "
				        + meuJogo.getNumerosAsList());
			}
		}

		Collections.sort(jogosLM);

		if (skip > 0) {
			jogosLM = filtroSkip(jogosLM, skip);
		}

		filtro = new FiltroMaxmoIgualAnterior(maxAnterior);
		// preJogos = filtro.filtrarListas(preJogos);
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

		List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();

		for (List<Integer> j : jogos) {
			params.append(j);
		}

		if (ESQUEMA_19.equals(esquema)) {
			for (List<Integer> j : jogos) {
				jogosOut.addAll(EsquemaLotofacil.getEsquema19n11a(j));
			}
		} else {
			jogosOut.addAll(jogos);
		}

		ArquivoUtil.saveLists(jogosOut, output, "\t");

		ArquivoUtil.save(params.toString(), paramsPath);

	}
}
