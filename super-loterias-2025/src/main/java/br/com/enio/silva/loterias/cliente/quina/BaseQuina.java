package br.com.enio.silva.loterias.cliente.quina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoInicio;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoFim;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.gerador.Base;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class BaseQuina extends Base {

	/**
	 * @param preJogos
	 * @param sequenciaMaxima
	 * @return
	 */
	private static List<List<Integer>> aplicarFiltroMaximaSequencia(List<List<Integer>> preJogos,
	        int sequenciaMaxima) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroMaximaSequencia(sequenciaMaxima);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @param maximoInicio
	 * @return
	 */
	private static List<List<Integer>> aplicarFiltroMaximoInicio(List<List<Integer>> preJogos,
	        int maximoInicio) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroMaximoInicio(maximoInicio);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroMaximoLinhas(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroMaximoLinhas(5, 5, 4);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param maximoRepetidosLista
	 * @param pathListaMax
	 * @param listaMax
	 * @param filtrarMaximoRepetidosLista
	 * @param preJogos
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> aplicarFiltroMaxLista(int maximoRepetidosLista,
	        String pathListaMax, List<List<Integer>> listaMax, boolean filtrarMaximoRepetidosLista,
	        List<List<Integer>> preJogos) throws IOException {
		FiltroIf filtro;
		if (filtrarMaximoRepetidosLista && !listaMax.isEmpty()) {
			filtro = new FiltroMaxLista(listaMax, maximoRepetidosLista);
			preJogos = filtro.filtrarListas(preJogos);
			ArquivoUtil.saveLists(listaMax, pathListaMax, "\t");
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroMinimoColunas(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroMinimoColunas(5, 1);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @param minimoFim
	 * @return
	 */
	private static List<List<Integer>> aplicarFiltroMinimoFim(List<List<Integer>> preJogos,
	        int minimoFim) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroMinimoFim(minimoFim);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroMinimoLinhas(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroMinimoLinhas(5, 5, 1);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroParImpar(List<List<Integer>> preJogos) {
		FiltroIf filtro;
		if (isNotEmpty(preJogos)) {
			filtro = new FiltroParImpar(5);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param config
	 * @param qtt
	 * @param filtrarQtt
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroQuantidadeDaLista(QuinaConfigAb config,
	        List<Integer> qtt, List<List<Integer>> preJogos) {

		if (isNotEmpty(preJogos)) {

			boolean filtrarQtt = qtt != null && qtt.size() > 0;

			if (filtrarQtt) {
				int mxQtt = config.getNrosApostados() - (config.getMaxNum() - qtt.size());
				FiltroIf filtro = new FiltroQuantidadeDaLista(mxQtt, qtt);
				preJogos = filtro.filtrarListas(preJogos);
			}
		}
		return preJogos;
	}

	/**
	 * @param listaJogosAtuais
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroRemoverIntersecao(
	        List<List<Integer>> listaJogosAtuais, List<List<Integer>> preJogos) {
		if (isNotEmpty(preJogos)) {
			FiltroIf filtro = new FiltroRemoverIntersecao(listaJogosAtuais);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param maximoRepetidosLista
	 * @param pathListaMax
	 * @param listaMax
	 * @param count
	 * @param filtrarMaximoRepetidosLista
	 * @param preJogos
	 * @param minimoFim
	 * @param maximoInicio
	 * @param sequenciaMaxima
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> aplicarFiltros(int maximoRepetidosLista,
	        List<List<Integer>> listaMax, int count, boolean filtrarMaximoRepetidosLista,
	        List<List<Integer>> preJogos, int sequenciaMaxima, int maximoInicio, int minimoFim)
	        throws IOException {
		boolean filtrar = (count % 5) != 0;

		if (filtrar && isNotEmpty(preJogos)) {

			System.out.println("Antes: " + preJogos.size());

			filtrarMaximoRepetidosLista = filtrarMaximoRepetidosLista && isNotEmpty(preJogos);

			preJogos = aplicarFiltroMaximaSequencia(preJogos, sequenciaMaxima);

			preJogos = aplicarFiltroMinimoFim(preJogos, minimoFim);

			preJogos = aplicarFiltroMinimoColunas(preJogos);

			preJogos = aplicarFiltroParImpar(preJogos);

			preJogos = aplicarFiltroMaximoInicio(preJogos, maximoInicio);

			preJogos = aplicarFiltroMinimoLinhas(preJogos);

			preJogos = aplicarFiltroMaximoLinhas(preJogos);
		}
		return preJogos;
	}

	/**
	 * @param last
	 * @param preJogos
	 * @return
	 */
	protected static List<List<Integer>> aplicarFiltroUltimoSorteio(List<Integer> last,
	        List<List<Integer>> preJogos) {
		if (isNotEmpty(preJogos) && isNotEmpty(last)) {
			FiltroIf filtro = new FiltroUltimoSorteio(6, 10, last);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	protected static List<List<Integer>> aplicarFiltroUltimoSorteio(List<Integer> last,
	        List<List<Integer>> preJogos, int excl) {
		if (isNotEmpty(preJogos) && isNotEmpty(last)) {
			int min = 5 + excl;
			int max = min + 2;
			FiltroIf filtro = new FiltroUltimoSorteio(min, max, last);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	protected static List<List<Integer>> aplicarFiltroUltimoSorteio(List<Integer> last,
	        List<List<Integer>> preJogos, int min, int max) {

		if (isNotEmpty(preJogos) && isNotEmpty(last)) {
			FiltroIf filtro = new FiltroUltimoSorteio(min, max, last);
			preJogos = filtro.filtrarListas(preJogos);
		}
		return preJogos;
	}

	protected static List<List<Integer>> clear(List<List<Integer>> lista) {
		List<List<Integer>> retorno = new ArrayList<>();

		for (List<Integer> l : lista) {
			if (!retorno.contains(l)) {
				retorno.add(l);
			}
		}

		return retorno;
	}

	/**
	 * @param jogosAtuais
	 * @param jogosCorrentes
	 * @param count
	 * @return
	 */
	protected static List<Integer> getFlat(String jogosAtuais, String jogosCorrentes, int count) {

		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);
		if (count % 4 == 0) {
			jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosCorrentes);
		} else if (count % 4 == 1 || count % 4 == 3) {
			jogosAtuaisFlat.addAll(ArquivoUtil.obterLinhasComoLista(jogosCorrentes));
		}
		return jogosAtuaisFlat;
	}

	/**
	 * @param config
	 * @param listaJogosAtuais
	 * @return
	 * @throws IOException
	 */
	protected static List<List<Integer>> getJogosAtuais(QuinaConfigAb config,
	        List<List<Integer>> listaJogosAtuais) throws IOException {
		String m = "1. Arquivo %s, tamanho %s";
		m = String.format(m, config.getCaminhoJogoAtual(), listaJogosAtuais.size());
		System.out.println(m);

		listaJogosAtuais
		        .addAll(ArquivoUtil.obterLinhasComoListasUnique(config.getCaminhoJogoAtual()));
		m = "2. Arquivo %s, tamanho %s";
		m = String.format(m, config.getCaminhoJogoAtual(), listaJogosAtuais.size());
		System.out.println(m);

		listaJogosAtuais = new ArrayList<>(new HashSet<>(listaJogosAtuais));

		m = "3. Arquivo %s, tamanho %s";
		m = String.format(m, config.getCaminhoJogoAtual(), listaJogosAtuais.size());
		System.out.println(m);

		ArquivoUtil.saveLists(listaJogosAtuais, config.getCaminhoJogoAtual(), "\t", 2);
		return listaJogosAtuais;
	}

	protected static List<Integer> getLast(List<List<Integer>> curr) {
		if (isNotEmpty(curr)) {
			return curr.get(curr.size() - 1);
		}
		return Collections.emptyList();
	}

	protected static List<List<Integer>> getListaMax() {
		String pathListaMax = "C:\\loterias\\gerador-apostas\\quina\\config\\LISTA_MAX.txt";
		return ArquivoUtil.obterLinhasComoListasUnique(pathListaMax);
	}

	protected static int getMaximoRepetidosLista(int count, QuinaConfigAb config) {
		int qttNumerosApostados = config.getNrosApostados();
		int n1 = qttNumerosApostados - 4;
		int n2 = n1 + 1;
		int n3 = n1 + 2;
		int n4 = n1 + 3;

		count = count % 6;

		if (count == 1) {
			return n1;
		}

		if (count == 3) {
			return n3;
		}

		if (count == 5) {
			return n4;
		}

		return n2;
	}

	/**
	 * @param config
	 * @param topN
	 * @return
	 */
	protected static int getTopN(QuinaConfigAb config, int topN) {
		try {
			topN = Math.min(new Random().nextInt(topN * 3) + 1000, config.getQttInicial() / 5);
		} catch (Exception e) {
			System.out.println("Top N recalculado: " + e.getMessage());
			topN = 100;
		}
		return topN;
	}

	protected static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	/**
	 * @param jogos
	 * @return
	 */
	protected static StringBuilder printOrdenado(List<List<Integer>> jogos) {
		System.out.println("Ordenado");
		Collector<CharSequence, ?, String> clt = Collectors.joining("\t");
		StringBuilder str = new StringBuilder();
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo);
			String atual = jogo.stream().map(Object::toString).collect(clt);
			System.out.println(atual);
			str.append(atual);
		}
		return str;
	}

	protected static void save(List<List<Integer>> lista, String path) throws IOException {
		if (lista != null && path != null) {
			ArquivoUtil.saveLists(lista, path, "\t", 2);
		}
	}
}
