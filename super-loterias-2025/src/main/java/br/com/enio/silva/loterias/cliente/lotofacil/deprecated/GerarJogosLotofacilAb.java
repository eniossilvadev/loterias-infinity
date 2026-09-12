package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaLotofacil;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.lotomania.PontuadorBasico;
import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.silva.enio.loterias.controller.LotofacilRN;
import util.ListUtil;

public abstract class GerarJogosLotofacilAb extends Base {

	public static Random random = new Random();

	/**
	 * @param psedoFixo
	 * @return
	 */
	protected static List<List<Integer>> criarJogosBase(List<Integer> psedoFixo, int quantidade,
			int tamanhoJogo) {
		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		for (int i = 0; i < quantidade; i++) {
			preJogo = new ArrayList<Integer>(psedoFixo);
			preJogos.add(preJogo);
		}

		preJogos = ListUtil.completar(preJogos, tamanhoJogo, LotofacilRN.MAIOR_NUMERO);
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<JogoLotofacil> criarJogosLotofacil(List<List<Integer>> preJogos) {
		List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();
		JogoLotofacil jlm = null;
		for (List<Integer> pj : preJogos) {
			jlm = new JogoLotofacil(pj);
			jogosLM.add(jlm);
		}
		return jogosLM;
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroDivide(StringBuilder params,
			List<List<Integer>> preJogos, int diff) {
		FiltroIf filtro;
		filtro = new FiltroDivide(diff, LotofacilRN.MAIOR_NUMERO);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroIntersecao(StringBuilder params,
			List<List<Integer>> preJogos) {
		String remove = ConstantesUtil.LOTOFACIL_CAMINHO_JOGO_ATUAL + ConstantesUtil.LF_JOGO_ATUAL;
		List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListas(remove);
		return filtroIntersecao(params, preJogos, remover);
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroIntersecao(StringBuilder params,
			List<List<Integer>> preJogos, List<List<Integer>> remover) {

		FiltroIf filtro = new FiltroRemoverIntersecao(remover);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroMaximaSequencia(StringBuilder params,
			List<List<Integer>> preJogos, int maxx) {
		FiltroIf filtro;
		filtro = new FiltroMaximaSequencia(maxx);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroMaximoLinhas(StringBuilder params,
			List<List<Integer>> preJogos, int max) {
		FiltroIf filtro;
		filtro = new FiltroMaximoLinhas(5, 5, max);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	/**
	 * @param preJogos
	 */
	protected static void filtroMaxmoIgualAnterior(StringBuilder params,
			List<List<Integer>> preJogos, int maxAnterior) {
		FiltroIf filtro = new FiltroMaxmoIgualAnterior(maxAnterior);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroMinimoLinhas(StringBuilder params,
			List<List<Integer>> preJogos, int min) {
		FiltroIf filtro;
		filtro = new FiltroMinimoLinhas(5, 5, min);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	/**
	 * @param params
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroParImpar(StringBuilder params,
			List<List<Integer>> preJogos, int diff) {
		FiltroIf filtro;
		filtro = new FiltroParImpar(diff);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	/**
	 * @param params
	 * @param last
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> filtroUltimoSorteio(StringBuilder params,
			List<Integer> last, List<List<Integer>> preJogos, int min, int max) {
		FiltroIf filtro;
		filtro = new FiltroUltimoSorteio(min, max, last);
		preJogos = filtro.filtrarListas(preJogos);
		params.append(filtro).append("\n");
		return preJogos;
	}

	protected static Set<Integer> gerarExcluirMax(LotofacilConfig config, int tamPadraoIncluir,
			List<Integer> jogosAtuaisFlat) {

		if (jogosAtuaisFlat == null || jogosAtuaisFlat.isEmpty()) {
			return getSet(config.getMaxNum(), tamPadraoIncluir, null);
		}

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValueDesc(map);

		return getSublistaMap(map, tamPadraoIncluir);
	}

	protected static Set<Integer> gerarIncluirMinimo(LotofacilConfig config, int tamPadraoIncluir,
			List<Integer> jogosAtuaisFlat) {

		if (jogosAtuaisFlat == null || jogosAtuaisFlat.isEmpty()) {
			return getSet(config.getMaxNum(), tamPadraoIncluir, null);
		}

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValue(map);

		return getSublistaMap(map, tamPadraoIncluir);
	}

	/**
	 * @param resultados
	 * @return
	 */
	protected static Map<Integer, Integer> getMapAtrasos(List<List<Integer>> resultados) {
		Map<Integer, Integer> mapAtraso = ContaAtrasosResultados
				.getInstance(LotofacilRN.MAIOR_NUMERO).getMapContaResultados(resultados);
		System.out.println(mapAtraso);

		mapAtraso = MapUtil.sortByValueDesc(mapAtraso);
		System.out.println(mapAtraso);
		return mapAtraso;
	}

	/**
	 * @param resultados
	 * @return
	 */
	protected static Map<Integer, Integer> getMapResultados(List<List<Integer>> resultados,
			int ultimos) {

		Map<Integer, Integer> mapResultado = ContaNumerosResultados.getInstance()
				.getMapContaResultados(resultados, ultimos);
		System.out.println(mapResultado);

		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		System.out.println(mapResultado);
		return mapResultado;
	}

	protected static Set<Integer> getSet(int max, int qtt, List<Integer> lista) {

		List<Integer> lt = new ArrayList<>();
		for (int i = 1; i <= max; i++) {
			lt.add(i);
		}
		if (lista != null) {
			lt.removeAll(lista);
		}
		Collections.shuffle(lt);
		return new HashSet<>(lt.subList(0, qtt));
	}

	protected static Set<Integer> getSublistaMap(Map<Integer, Integer> map, int size) {
		Set<Integer> resultado = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

			resultado.add(entry.getKey());

			if (resultado.size() >= size) {
				return resultado;
			}
		}
		return resultado;
	}

	/**
	 * @param config
	 * @param topN
	 * @return
	 */
	protected static int getTopN(LotofacilConfig config, int topN) {
		try {
			topN = Math.min(new Random().nextInt(topN * 3) + 1000, config.getQttInicial() / 5);
		} catch (Exception e) {
			System.out.println("Top N recalculado: " + e.getMessage());
			topN = 100;
		}
		return topN;
	}

	/**
	 * @param resultados
	 */
	protected static void info01(String msg) {
		try {
			System.out.println(msg);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> obterTodosResultados() throws IOException {
		String path = ConstantesUtil.LF_CAMINHO_DOWNLOAD + "RESULTADO_LF.txt";
		List<List<Integer>> resultados = GerarListaLotofacil.getInstance()
				.gerarArquivoResultado(path);
		return resultados;
	}

	/**
	 * @param mapResultado
	 * @param mapAtraso
	 * @param jogosLM
	 * @param pontuador
	 */
	protected static void pontuacaoExtra(Map<Integer, Integer> mapResultado,
			Map<Integer, Integer> mapAtraso, List<JogoLotofacil> jogosLM, Pontuador pontuador) {
		for (JogoAb meuJogo : jogosLM) {

			Integer before = meuJogo.getPontuacao();

			pontuador.pontuarMap(meuJogo, mapResultado, 20, 1);
			pontuador.pontuarMap(meuJogo, mapAtraso, 25, 1);

			Integer after = meuJogo.getPontuacao();
			List<Integer> l = meuJogo.getNumerosAsList();

			System.out.println("[" + before + ", " + after + "] " + l);
		}
	}

	/**
	 * @param resultados
	 * @param jogosLM
	 * @param pontuador
	 */
	protected static void pontuar(List<List<Integer>> resultados, List<JogoLotofacil> jogosLM,
			Pontuador pontuador) {
		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			Integer before = meuJogo.getPontuacao();
			((PontuadorBasico) pontuador).pontuarPorPosicao(resultados, meuJogo, 1);
			Integer after = meuJogo.getPontuacao();
			List<Integer> l = meuJogo.getNumerosAsList();
			System.out.println("[" + before + ", " + after + "] " + l);

		}
	}

	protected static void print(int count, String text) {
		System.out.println((count + 1) + "\t\t" + text);
	}

	protected static void printTopMap(Map<Integer, Integer> mapResultado, int max) {

		Set<Integer> chaves = mapResultado.keySet();
		String resultado = StringUtils.EMPTY;
		int i = 0;
		for (Integer chave : chaves) {
			if (chave != null && i++ < max) {
				resultado += chave;
			}

			if (i == max) {
				System.out.println(resultado);
				return;
			}
			resultado += ", ";
		}

	}

	protected static void sleep(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}