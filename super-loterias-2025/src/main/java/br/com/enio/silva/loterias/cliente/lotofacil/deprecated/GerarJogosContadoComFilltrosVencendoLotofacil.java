package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaVencendoLotofacil;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ImprimirUtil;

public class GerarJogosContadoComFilltrosVencendoLotofacil extends GerarJogosLotofacilAb {

	private static Integer qttInicial = 250000;

	private static int tamanhoJogo = 15;

	private static boolean pontuacaoExtra = true;

	private static String fName = String.valueOf(System.currentTimeMillis());

	private static String baseNome = "LF_" + fName + "_";

	// private static int maxAnterior = 10;

	private static String nome = baseNome + System.currentTimeMillis();

	private static String folder = "C:\\loterias\\gerador-apostas\\lotofacil\\" + fName + "\\";

	private static String output = folder + nome + " HT.txt";

	private static String paramsPath = folder + nome + "_params.txt";

	private static String all = folder + "all.txt";

	private static int ultimos = 0;

	/**
	 * @return
	 */
	protected static List<Integer> getPseudoFixo(List<List<Integer>> pf) {

		Random random = new Random();
		int aleatorio = random.nextInt(pf.size());
		List<Integer> psedoFixo = pf.get(aleatorio);
		info01("Pseudo: " + psedoFixo);
		return psedoFixo;
	}

	public static void main(String[] args) throws IOException {

		StringBuilder params = new StringBuilder();

		List<List<Integer>> resultados = obterTodosResultados();
		ultimos = resultados.size();

		Map<Integer, Integer> mapResultado = getMapResultados(resultados, ultimos);

		printTopMap(mapResultado, 9);

		Map<Integer, Integer> mapAtraso = getMapAtrasos(resultados);

		info01("Esperer um pouco! Número de concursos: " + resultados.size());

		List<Integer> last = resultados.get(resultados.size() - 1);
		System.out.println("Last" + last);

		// List<Integer> psedoFixo = Arrays.asList(5, 6, 7, 12, 13, 14, 19, 20,
		// 21);
		List<Integer> psedoFixo = Arrays.asList(13, 11, 24, 10, 2, 3, 14, 20, 1);

		List<List<Integer>> pfTodos = new ArrayList<List<Integer>>();
		pfTodos.add(psedoFixo);
		List<List<Integer>> pf = EsquemaVencendoLotofacil.obterPadraoOito16Numeros(psedoFixo);
		psedoFixo = getPseudoFixo(pf);

		List<List<Integer>> preJogos = criarJogosBase(psedoFixo, qttInicial, tamanhoJogo);

		System.out.println("Antes: " + preJogos.size());

		preJogos = filtroIntersecao(params, preJogos, pfTodos);

		// Remover jogos j� "existentes"
		preJogos = filtroIntersecao(params, preJogos);

		preJogos = filtroUltimoSorteio(params, last, preJogos, 7, 12);

		preJogos = filtroMaximaSequencia(params, preJogos, 4);

		preJogos = filtroDivide(params, preJogos, 3);

		preJogos = filtroParImpar(params, preJogos, 3);

		preJogos = filtroMinimoLinhas(params, preJogos, 2);

		preJogos = filtroMaximoLinhas(params, preJogos, 4);

		ArquivoUtil.save(ImprimirUtil.printListas(preJogos), all);

		List<JogoLotofacil> jogosLM = criarJogosLotofacil(preJogos);

		pontuar(resultados, jogosLM, new PontuadorLotofacil());

		info01("Wainting...");

		if (pontuacaoExtra) {
			pontuacaoExtra(mapResultado, mapAtraso, jogosLM, new PontuadorLotofacil());
		}

		Collections.sort(jogosLM);

		// filtroMaxmoIgualAnterior(params, preJogos, maxAnterior);

		jogosLM = jogosLM.subList(0, 1);
		JogoAb meuJogo = jogosLM.get(0);
		info01(meuJogo.getPontuacao() + "\t" + meuJogo.getNumerosAsList());

		List<List<Integer>> jogos = new ArrayList<List<Integer>>();
		for (JogoAb jj : jogosLM) {
			jogos.add(jj.getNumerosAsList());
		}

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo);
		}

		List<Integer> diff = new ArrayList<Integer>(jogos.get(0));
		System.out.println("inicial: " + diff);
		System.out.println("psedoFixo: " + psedoFixo);
		diff.removeAll(psedoFixo);
		System.out.println("diff: " + diff);
		List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
		System.out.println("");
		System.out.println("Jogos - Início");
		for (List<Integer> j : pf) {
			j.addAll(diff);
			Collections.sort(j);
			jogosOut.add(j);
		}
		System.out.println("Jogos - Fim");
		System.out.println("");

		for (List<Integer> j : jogos) {
			params.append(j);
		}

		ArquivoUtil.saveLists(jogosOut, output, "\t", 2);

		ArquivoUtil.save(params.toString(), paramsPath);

	}

}
