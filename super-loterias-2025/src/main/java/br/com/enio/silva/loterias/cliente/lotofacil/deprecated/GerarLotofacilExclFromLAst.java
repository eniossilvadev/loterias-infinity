package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.ListUtils;

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

public class GerarLotofacilExclFromLAst extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 50000;

	public static int total = 10;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 10;

	public static int maximoInicio = 5;

	public static int minimoFim = 20;

	public static int minPercent = 45;

	public static int maxPercent = 65;

	public static int tamPadraoExcluir = 6;

	public static List<Integer> listaSelExclusao = Arrays.asList(1, 2, 3, 23, 24, 25);

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

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

		for (int count = 0; count < total; count++) {

			System.out.println("Last: " + last);
			System.out.println("Lista de Exclus�o: " + listaSelExclusao);

			try {

				TimeUnit.SECONDS.sleep(3);

				config = getConfig(count);

				config.setQttInicial(qttInicial);

				List<Integer> listaSelecaoExclusao = new ArrayList<>(listaSelExclusao);
				Set<Integer> listaExcl = ListaUtils.getFromList(listaSelecaoExclusao,
						tamPadraoExcluir);

				List<Integer> lista = ListaUtils.iterateStream(1, 1, 25);
				lista.removeAll(count % 5 == 0 ? listaExcl : listaSelecaoExclusao);
				Set<Integer> listaIncl = new HashSet<>();

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

				System.out.println("Lista Incluir: " + listaSelExclusao);
				saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, count,
						listaExcl, str, jogosOut);

				listaSelExclusao = new ArrayList<>(last);

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
