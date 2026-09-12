package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig18;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;

import util.ListUtil;

public class GerarLotofacilFrequenciaMaisMenosFree18 extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 250000;

	public static int total = 12;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 8;

	public static int maximoInicio = 7;

	public static int minimoFim = 18;

	public static int tamPadraoExcluir;

	public static int tamPadraoIncluir;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		int[] ultimosJogos = { 0 };

		int diff = Math.abs(new Random().nextInt());

		Set<List<Integer>> top = new HashSet<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		init();

		save(listaJogosAtuais, config.getCaminhoJogoAtual());
		save(listaJogosCorrentes, config.getCaminhoJogoCorrente());

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		int curr = 0;

		saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, 0, null, null,
				null);

		for (int count = 0; count < total; count++) {

			tamPadraoExcluir = 2 + curr % 4;
			tamPadraoIncluir = 6 - tamPadraoExcluir;
			curr++;

			try {

				config = new LotofacilConfig18();

				config.setQttInicial(qttInicial);

				List<Integer> jogosAtuaisFlat = getFlat(config.getCaminhoJogoAtual(),
						config.getCaminhoJogoCorrente(), count);

				Set<Integer> listaIncl = new HashSet<Integer>() {

					/**
					 *
					 */
					private static final long serialVersionUID = 2957125828918837445L;

					{
					}
				};

				int tmpTamPadraoExcluir = tamPadraoExcluir + tamPadraoIncluir - listaIncl.size();

				Set<Integer> listaExcl = gerarExcluirMax(config, tmpTamPadraoExcluir,
						jogosAtuaisFlat);

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
				List<Integer> qtt = Arrays.asList();

				List<List<Integer>> resultados = config.getTodosResultados();

				listaJogosCorrentes.removeAll(resultados);

				ultimos = ultimosJogos[(count + diff) % ultimosJogos.length];

				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
						: ultimos;
				List<List<Integer>> ultimosResultados = config.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

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
				if (totalIncluirExcluir >= 3) {
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

				preJogos = aplicarFiltroQuantidadeDaLista(config, qtt, preJogos);

				// preJogos = aplicarFiltroUltimoSorteio(last, preJogos,
				// listaExcl.size());

				// if (count >= total / 2) {
				// preJogos = aplicarFiltros(maximoRepetidosLista, listaMax,
				// count,
				// filtrarMaximoRepetidosLista, preJogos, sequenciaMaxima,
				// maximoInicio,
				// minimoFim);
				// }

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(ultimosResultados, meuJogo);
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
