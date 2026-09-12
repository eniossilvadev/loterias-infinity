package br.com.enio.silva.loterias.cliente.quina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.bingodasorte.newp.GerarJogosQuinaBingoAb;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosQuina2023 extends GerarJogosQuinaBingoAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 30;

		int qttInicialFixo = 150000;
		int qttOriginal = qttInicialFixo;

		int tamPadraoExcluir = 15;
		int tamanhoIncluirLista = 3;
		int defaultSize = 5;

		// https://www.mazusoft.com.br/quina/tabela-sequencia-atraso.php
		List<Integer> theList = Arrays.asList(67, 71, 72, 51, 31, 53, 54, 8, 60, 48, 58, 23, 80,
				13);

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

		String sep = ", ";

		String base = CaminhoResultados.QUINA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "todos_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String pathBkpCorrentes = base + "/bkp/" + currDate + "/" + "bkp_" + currDateTime
				+ "_c.txt";
		String pathBkpAtuais = base + "/bkp/" + currDate + "/" + "bkp_" + currDateTime + "_a.txt";

		saveDefault(listaJogosCorrentes, pathBkpCorrentes, defaultSize);
		saveDefault(listaJogosAtuais, pathBkpAtuais, defaultSize);

		listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais, defaultSize);
		listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosCorrentes, defaultSize);

		String path = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNQ.txt";
		String snBkp = base + "novos/" + currDateTime + "_snms.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		QuinaConfigAb config = new QuinaConfig5();

		if (listaJogosCorrentes != null && !listaJogosCorrentes.isEmpty()) {
			String mcb = config.getFrequencia(listaJogosCorrentes);
			ArquivoUtil.save(mcb, CaminhoResultados.QUINA.getBasePath() + "correntes.txt");
		}
		if (listaJogosAtuais != null && !listaJogosAtuais.isEmpty()) {
			String mab = config.getFrequencia(listaJogosAtuais);
			ArquivoUtil.save(mab, CaminhoResultados.QUINA.getBasePath() + "atuais.txt");
		}

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		int magicNumber = rand.nextInt(1000);

		theSep = sep;

		saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
		saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

		for (int count = 0; count < totalDeJogos; count++) {

			config = new QuinaConfig5();

			config.setQttInicial(qttOriginal);

			try {

				System.out.println("Magic Number: " + magicNumber);
				tamPadraoExcluir = 13 + magicNumber % 13;
				magicNumber++;

				Set<Integer> listaIncl = new HashSet<>(listaInclFixa);
				if (theList != null && !theList.isEmpty()) {
					int el = magicNumber % theList.size();
					int sorte = theList.get(el);
					listaIncl.add(sorte);

					System.out.println("el: " + el + "\t" + sorte);
				}

				config.setQttInicial(qttInicialFixo);

				String output = base + "ind/" + currDate + "/" + config.getDefaultName()
				+ DateUtils.getCurrentDefaultDateTime() + ".txt";

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				String jogosAtuais = config.getCaminhoJogoAtual();

				List<Integer> jogosAtuaisFlat = getFlatQuina(pathListaJogosCorrentes, count,
						jogosAtuais);


				List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1, config.getMaxNum());

				listaIncl.addAll(listaOrdenada.subList(0, tamanhoIncluirLista));

				Collections.reverse(listaOrdenada);
				Set<Integer> listaExcl = new HashSet<>(listaOrdenada.subList(0, tamPadraoExcluir));

				listaExcl.removeAll(listaIncl);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (last != null) {
					Collections.shuffle(lasts);
					lasts = lasts != null && lasts.size() > 11 ? lasts.subList(0, 8) : lasts;
					lasts.addAll(last);
					lasts = new ArrayList<>(new HashSet<>(lasts));
					lasts.removeAll(listaIncl);
					listaExcl.addAll(lasts);
				}

				System.out.println(repeated50);

				System.out.println("Last: " + last);
				System.out.println("Lasts: " + lasts);

				lasts = new ArrayList<>(new HashSet<>(lasts));

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				int n = config.getMaxNum() - tamPadraoExcluir;
				int r = config.getNrosApostados() - listaIncl.size();
				int totalCombinacoes = CombinationUtils.calcNumberOfCombinations(n, r).intValue();
				if (config.getQttInicial() > totalCombinacoes && totalCombinacoes > 0) {
					System.out.println("Novo máximo número de jogos: " + totalCombinacoes);
					config.setQttInicial(totalCombinacoes);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				List<List<Integer>> fullList = new ArrayList<>();
				fullList.addAll(listaJogosAtuais);
				fullList.addAll(listaJogosCorrentes);
				fullList.addAll(somenteNovos);
				fullList.addAll(resultados);

				preJogos.removeAll(fullList);

				FiltroIf filtro = null;

				filtro = new FiltroRemoverIntersecao(fullList);
				preJogos = filtro.filtrarListas(preJogos);

				int maxRep = 3;
				filtro = new FiltroMaxConsecutivos(maxRep);
				preJogos = filtro.filtrarListas(preJogos);

				int maxLinhas = 3 + count % 3;
				filtro = new FiltroMaximoLinhas(8, 10, maxLinhas);
				preJogos = filtro.filtrarListas(preJogos);

				filtro = new FiltroDivide(4, config.getMaxNum());
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && !preJogos.isEmpty()) {

					List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
					JogoQuina jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoQuina(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(resultados, meuJogo);
					}

					Collections.sort(jogosLM);

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
						listaJogosAtuais.add(novo);
						listaJogosCorrentes.add(novo);
						somenteNovos.add(novo);
						last = new ArrayList<>(novo);
						lasts.addAll(last);
					}

					for (List<Integer> jogo : jogos) {
						Collections.replaceAll(jogo, 0, 0);
						Collections.sort(jogo);
					}

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo + "\t" + listaIncl + "\t" + listaExcl);
					}

					saveDefault2(jogos, output);
					saveDefault2(somenteNovos, sn);
					saveDefault2(somenteNovos, snBkp);

					saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
					saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);
					//					saveDefault(listaJogosCorrentes, pathListaMeusJogos, defaultSize);

					List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
					List<Integer> listaC = MapUtil.getListaOrdenadaListas(statC, 1, config.getMaxNum());
					String mc = config.getFrequencia(statC) + "\n\n" + listaC;
					ArquivoUtil.save(mc, CaminhoResultados.QUINA.getBasePath() + "correntes.txt");

					List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
					List<Integer> listaA = MapUtil.getListaOrdenadaListas(statA, 1, config.getMaxNum());
					String ma = config.getFrequencia(statA) + "\n\n" + listaA;
					ArquivoUtil.save(ma, CaminhoResultados.QUINA.getBasePath() + "atuais.txt");

					List<Integer> listaOrdenadaStat = MapUtil.getListaOrdenadaListas(statA, 1, config.getMaxNum());
					System.out.println("Lista Ordenada: " + listaOrdenadaStat);
					System.out.println("Frequencia MA: " + config.getFrequenciaSimples(statA));
					System.out.println("Frequencia MC: " + config.getFrequenciaSimples(statC));

				} else {

					for (int i = 0; i < 6; i++) {
						System.out.println("Não há resultados com os filtros aplicados");
						Thread.sleep(500);
					}
					count--;
				}
			} catch (Exception e) {
				e.printStackTrace();
				count--;
				ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, sep, 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
				ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
			}
		}
	}


}
