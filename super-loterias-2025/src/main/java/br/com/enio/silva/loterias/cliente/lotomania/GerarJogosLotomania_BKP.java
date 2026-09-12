package br.com.enio.silva.loterias.cliente.lotomania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroSubstituirUltimoSorteio;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomania;
import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.silva.enio.loterias.controller.LotomaniaRN;
import util.ListUtil;

public class GerarJogosLotomania_BKP extends GerarJogosLotomaniaBase {

	public static void main(String[] args) throws IOException, InvalidLenghtException {

		String nconcurso = "1981";
		String time = "" + System.currentTimeMillis();
		String baseNome = "LM_" + nconcurso + "_02_";
		String nome = baseNome + time;
		String folder = "E:\\loterias\\Lotomania\\novos\\" + nconcurso + "\\";
		String output = folder + nome + " HT.txt";
		String paramsPath = folder + nome + "_params.txt";
		// String all = folder + "all" + time + ".txt";

		String path = ConstantesUtil.LM_CAMINHO_DOWNLOAD + "RESULTADO.txt";
		List<List<Integer>> resultados = GerarListaLotomania.getInstance()
		        .gerarArquivoResultado(path);
		// Arquivos - Fim

		// Par�metros - Início
		StringBuilder params = new StringBuilder();

		Integer qttInicial = 100000;
		Integer qttJogos = 1;
		int tamanhoJogo = 80;
		int skip = 0;
		boolean pontuacaoExtra = true;
		int top = 0;
		int maxAnterior = tamanhoJogo - 5;
		int ultimos = resultados.size();
		// int ultimos = resultados.size();
		// int ultimos = resultados.size() / 3;
		// Par�metros - Início

		params.append("qttInicial: ").append(qttInicial).append("\n");
		params.append("qttJogos: ").append(qttJogos).append("\n");
		params.append("tamanhoJogo: ").append(tamanhoJogo).append("\n");
		params.append("skip: ").append(skip).append("\n");
		params.append("pontuacaoExtra: ").append(pontuacaoExtra).append("\n");
		params.append("top: ").append(top).append("\n");
		params.append("maxAnterior: ").append(maxAnterior).append("\n");
		params.append("ultimos: ").append(ultimos).append("\n");

		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
		        .getMapContaResultados(resultados, ultimos);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);

		Map<Integer, Integer> mapAtraso = ContaAtrasosResultados
		        .getInstance(LotomaniaRN.MAIOR_NUMERO).getMapContaResultados(resultados);
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

		preJogos = ListUtil.completar(preJogos, tamanhoJogo, LotomaniaRN.MAIOR_NUMERO);

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		filtro = new FiltroSubstituirUltimoSorteio(last, LotomaniaRN.MAIOR_NUMERO, 15);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroUltimoSorteio(0, 5, last);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroDivide(10, LotomaniaRN.MAIOR_NUMERO);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroParImpar(10);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		filtro = new FiltroMinimoLinhas(10, 10, 2);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		int max = Math.round(tamanhoJogo + 2 / 7);
		params.append("max: ").append(max).append("\n");
		// filtro = new FiltroMaximoLinhas(10, 10, max);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		Set<Integer> set = new HashSet<Integer>(Arrays.asList(-1));
		List<Integer> temUm = new ArrayList<Integer>(set);
		filtro = new FiltroExcluirTemUm(temUm);
		// preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");

		// ArquivoUtil.save(ImprimirUtil.printListas(preJogos), all);

		List<JogoLotomania> jogosLM = new ArrayList<JogoLotomania>();
		JogoLotomania jlm = null;
		for (List<Integer> pj : preJogos) {
			jlm = new JogoLotomania(pj);
			jogosLM.add(jlm);
		}

		System.out.println("\n");
		Pontuador pontuador = new PontuadorLotomania();

		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			Integer before = 0; // meuJogo.getPontuacao();
			// ((PontuadorLotomania) pontuador).pontuarPorPosicao(resultados,
			// meuJogo, 1);

			Integer after = meuJogo.getPontuacao();
			List<Integer> list = meuJogo.getNumerosAsList();
			System.out.println("[" + before + ", " + after + "] " + list);
		}

		try {
			System.out.println("Wainting...");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		if (pontuacaoExtra) {
			// for (JogoAb meuJogo : jogosLM) {

			// Integer before = meuJogo.getPontuacao();
			// pontuador.pontuarMap(meuJogo, mapResultado, 30, 1);
			// pontuador.pontuarMap(meuJogo, mapResultado, 20, 1);
			// pontuador.pontuarMap(meuJogo, mapResultado, 65, 4);
			// pontuador.pontuarMap(meuJogo, mapAtraso, 50, 4);

			// Integer after = meuJogo.getPontuacao();
			// List<Integer> list = meuJogo.getNumerosAsList();
			// System.out.println("[" + before + ", " + after + "] " +
			// list);
			// }
		}

		Collections.sort(jogosLM);

		if (skip > 0) {
			jogosLM = filtroSkip(jogosLM, skip);
		}

		filtro = new FiltroMaxmoIgualAnterior(maxAnterior);
		// preJogos = filtro.filtrarListas(preJogos);

		if (jogosLM != null && jogosLM.size() > 0) {
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
			Map<String, String> replace = new HashMap<String, String>();
			replace.put("100", "0");

			jogos = getEsquemas(jogos, mapResultado, mapAtraso);

			ArquivoUtil.saveLists(jogos, output, "\t", replace);
		}

		ArquivoUtil.save(params.toString(), paramsPath);

	}
}
