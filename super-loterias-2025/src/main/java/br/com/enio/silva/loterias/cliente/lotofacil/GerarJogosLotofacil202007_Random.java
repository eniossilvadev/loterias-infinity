package br.com.enio.silva.loterias.cliente.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotofacil202007_Random extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 50000;

	public static int total = 4;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 8;

	public static int maximoInicio = 5;

	public static int minimoFim = 20;

	public static int topN = 2;

	public static int minPercent = 50;

	public static int maxPercent = 60;

	public static int tamPadraoExcluir = 4;

	public static int tamPadraoIncluir = 2;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		int maximoRepetidosLista = 12;

		List<List<Integer>> somenteNovos = new ArrayList<>();

		List<List<Integer>> listaMax = getListaMax();

		List<List<Integer>> listaJogosCorrentes = new LinkedList<>(
				ArquivoUtil.obterLinhasComoListasUnique(config.getCaminhoJogoCorrente(),
						config.getNrosApostados()));

		List<List<Integer>> listaJogosAtuais = new ArrayList<>(listaJogosCorrentes);
		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);
		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);
		List<Integer> last = getLast(listaJogosCorrentes);

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		int curr = 0;

		for (int count = 0; count < total; count++) {

			tamPadraoExcluir = 2 + curr % 4;
			tamPadraoIncluir = 6 - tamPadraoExcluir;
			curr++;

			try {

				topN = getTopN(config, topN);

				config = getConfig(count);

				maximoRepetidosLista = getMaximoRepetidosLista(count, config);

				config.setQttInicial(qttInicial);

				List<Integer> lista = ListaUtils.iterateStream(1, 1, 25);
				Set<Integer> listaIncl = ListaUtils.getFromList(lista, tamPadraoIncluir);
				Set<Integer> listaExcl = ListaUtils.getFromList(lista, tamPadraoExcluir);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				listaIncl.removeAll(listaExcl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				boolean filtrarMaximoRepetidosLista = true && maximoRepetidosLista > 0;

				List<List<Integer>> resultados = config.getTodosResultados();

				listaJogosCorrentes.removeAll(resultados);

				saveMapResultado(config, resultados, ultimos);

				saveMapAtraso(config, resultados);

				System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = Arrays.asList(config.getPre());

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = preJogo;

				int totalIncluirExcluir = excluir.size() + incluir.size();
				if (totalIncluirExcluir >= 4) {
					preJogos = CombinationUtils.gerarCombinacao(tam, 1, maxNum, excluir, incluir);
				} else {
					for (int i = 0; i < config.getQttInicial(); i++) {
						preJogos.add(preJogo);
					}
					preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
							incluir);
				}

				List<Integer> listaPre = Arrays.asList(config.getPre());
				boolean validarPre = listaPre != null && !listaPre.isEmpty();
				for (List<Integer> t : top) {
					List<Integer> inter = ListUtils.intersection(t, excluir);

					if (inter == null || inter.isEmpty()) {
						if (validarPre) {
							inter = ListUtils.intersection(t, listaPre);
							if (inter != null && inter.size() == listaPre.size()) {
								preJogos.add(new ArrayList<>(t));
							}
						} else {
							preJogos.add(new ArrayList<>(t));
						}
					}
				}

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				// Filtro �ltimo sorteado
				preJogos = aplicarFiltroUltimoSorteio(last, preJogos, tamPadraoExcluir);

				if (count >= total / 2) {
					preJogos = aplicarFiltros(maximoRepetidosLista, listaMax, count,
							filtrarMaximoRepetidosLista, preJogos, sequenciaMaxima, maximoInicio,
							minimoFim);
				}

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(resultados, meuJogo);
				}
				Collections.sort(jogosLM);

				topN = Math.min(topN, jogosLM.size() - 1);
				List<JogoLotofacil> jogosTop = jogosLM.subList(0, topN);
				for (JogoLotofacil jj : jogosTop) {
					top.add(jj.getNumerosAsList());
				}

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(c++ + "\t" + meuJogo);
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {

					List<Integer> novo = jj.getNumerosAsList();

					System.out.println(novo + "\t" + jj.getMapConta());

					jogos.add(novo);
					if (listaJogosAtuais.contains(novo) || listaJogosCorrentes.contains(novo)) {
						throw new Exception("J� tem esse jogo: " + novo);
					}
					listaJogosAtuais.add(novo);
					listaJogosCorrentes.add(novo);
					last = new ArrayList<>(novo);

					somenteNovos.add(novo);
				}

				StringBuilder str = printOrdenado(jogos);

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, count,
						listaExcl, str, jogosOut);

			} catch (Exception e) {
				e.printStackTrace();
				count--;
				save(listaJogosAtuais, config.getCaminhoJogoAtual());
				save(listaJogosCorrentes, config.getCaminhoJogoCorrente());
				save(somenteNovos, sn);
			}
		}
	}

}
