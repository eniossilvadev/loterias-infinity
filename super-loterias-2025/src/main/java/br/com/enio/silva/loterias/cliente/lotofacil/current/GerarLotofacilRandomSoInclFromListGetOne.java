package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.diversos.MathLotoUtils;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarLotofacilRandomSoInclFromListGetOne extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 100000;

	private static int times = 2;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 10;

	public static int maximoInicio = 6;

	public static int minimoFim = 19;

	public static int tamIncluir = 12;

	public static void beforeLotofacil(String base, List<List<Integer>> listaSN, int defaultSize, String currDate, String currDateTime) {
		try {
			String sn = base + "\\LSN.txt";
			String snBkp = base + "novos/" + currDateTime + "_nlf.txt";
			String pathListaDeJogos = config.getCaminhoJogoAtual();
			String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();

			saveDefault2(listaSN, sn);
			saveDefault2(listaSN, snBkp);

			saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
			saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

			List<List<Integer>> statC1 = gc(listaJogosCorrentes, defaultSize);
			String mc1 = config.getFrequencia(statC1);
			ArquivoUtil.save(mc1, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

			List<List<Integer>> statA1 = gc(listaJogosAtuais, defaultSize);
			String ma1 = config.getFrequencia(statA1);
			ArquivoUtil.save(ma1, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		init();

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		List<Integer> theList = Arrays.asList(2, 4, 5, 8, 10, 11, 12, 13, 14, 17, 18, 20, 21, 22,
				25);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		int defaultSize = 15;
		String pathListaDeJogos = config.getCaminhoJogoAtual();
		String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();
		String dt = DateUtils.getCurrentDefaultDateTime();
		String output = base + "ind/" + currDate + "/" + config.getDefaultName() + dt + ".txt";
		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";
		beforeLotofacil(base, somenteNovos, defaultSize, currDate, currDateTime);

		for (int count = 0; count < times; count++) {

			try {

				int tamPadraoIncluir = tamIncluir + MathLotoUtils.getBalance(3, 1);

				List<Integer> listaSelInclusao = new ArrayList<>(theList);

				config = getConfig(count);

				config.setQttInicial(qttInicial);

				List<Integer> listaSelecaoInclusao = new ArrayList<>(listaSelInclusao);
				Set<Integer> listaIncl = ListaUtils.getFromList(listaSelecaoInclusao,
						tamPadraoIncluir);

				List<Integer> lista = ListaUtils.iterateStream(1, 1, 25);
				lista.removeAll(listaSelecaoInclusao);

				Set<Integer> listaExcl = new HashSet<>(listaSelecaoInclusao);
				listaExcl.removeAll(listaIncl);

				System.out.println("Números incluídos: " + listaIncl);

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

				Set<List<Integer>> filtroIntersecao = new HashSet<>(listaJogosAtuais);
				filtroIntersecao.addAll(resultados);
				filtroIntersecao.addAll(listaJogosCorrentes);

				preJogos = aplicarFiltroRemoverIntersecao(new ArrayList<>(filtroIntersecao),
						preJogos);

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

					somenteNovos.add(novo);
				}

				saveDefault2(jogos, output);
				saveDefault2(somenteNovos, sn);
				saveDefault2(somenteNovos, snBkp);

				saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
				saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

				List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
				String mc = config.getFrequencia(statC);
				ArquivoUtil.save(mc, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

				List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
				String ma = config.getFrequencia(statA);
				ArquivoUtil.save(ma, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");


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
