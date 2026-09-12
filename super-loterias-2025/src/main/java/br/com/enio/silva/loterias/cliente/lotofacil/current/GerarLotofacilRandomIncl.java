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
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_6_Invertido;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarLotofacilRandomIncl extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 50000;

	public static int total = 2;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 9;

	public static int maximoInicio = 6;

	public static int minimoFim = 18;

	public static int tamPadraoExcluir = 1;

	public static int tamPadraoIncluir = 9;

	public static List<Integer> listaSelInclusao = Arrays.asList(22, 10, 19, 15, 13, 11, 17, 6, 16);

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		init();

		List<Integer> last = getLast(listaJogosCorrentes);

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		int defaultSize = 15;
		String output = base + "ind/" + currDate + "/" + config.getDefaultName()
		+ DateUtils.getCurrentDefaultDateTime() + ".txt";
		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";
		String pathListaDeJogos = config.getCaminhoJogoAtual();
		String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();

		saveDefault2(somenteNovos, sn);
		saveDefault2(somenteNovos, snBkp);

		saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
		saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

		List<List<Integer>> statC1 = gc(listaJogosCorrentes, defaultSize);
		String mc1 = config.getFrequencia(statC1);
		ArquivoUtil.save(mc1, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

		List<List<Integer>> statA1 = gc(listaJogosAtuais, defaultSize);
		String ma1 = config.getFrequencia(statA1);
		ArquivoUtil.save(ma1, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");

		tamPadraoIncluir = tamPadraoIncluir <= listaSelInclusao.size() ? tamPadraoIncluir
				: listaSelInclusao.size();

		for (int count = 0; count < total; count++) {

			boolean bool = count % 2 == 0;

			if (bool) {
				config = new LotofacilConfig15();
			} else {
				config = new LotofacilConfig15_6_Invertido();
			}

			try {

				config = getConfig(count);

				config.setQttInicial(qttInicial);

				List<Integer> listaSelecaoInclusao = new ArrayList<>(listaSelInclusao);
				Set<Integer> listaIncl = ListaUtils.getFromList(listaSelecaoInclusao,
						tamPadraoIncluir);

				List<Integer> lista = ListaUtils.iterateStream(1, 1, 25);
				lista.removeAll(count % 2 == 0 ? listaSelecaoInclusao : listaIncl);

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
				if (bool) {
					preJogos = aplicarFiltroUltimoSorteio(last, preJogos, tamPadraoExcluir);
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
