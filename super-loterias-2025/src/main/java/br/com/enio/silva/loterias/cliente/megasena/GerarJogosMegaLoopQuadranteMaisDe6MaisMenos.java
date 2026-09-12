package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.commons.SaveMegaSena;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesMegaSena;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosMegaLoopQuadranteMaisDe6MaisMenos extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		SaveMegaSena saveMegaSena = new SaveMegaSena();

		int totalDeJogos = 6;

		int tamanhoDoJogo = 6;

		int qttInicialFixo = 130000;
		int qttOriginal = qttInicialFixo;

		int tamPadraoExcluir = 20;
		int tamanhoIncluirLista = 3;
		int defaultSize = 6;

		String pathCorrente = CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt";
		String pathAtuais = CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt";

		// https://www.mazusoft.com.br/mega/tabela-sequencia-atraso.php
		List<Integer> theList = Arrays.asList();

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

		String sep = ",";

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "todos_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		List<List<Integer>> full = LotoUtils.getAll(saveMegaSena.getCurr(), defaultSize);
		listaJogosAtuais.addAll(full);
		listaJogosCorrentes.addAll(full);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String pathBkpCorrentes = base + "/bkp/" + currDate + "/" + "bkp_" + currDateTime
				+ "_c.txt";
		String pathBkpAtuais = base + "/bkp/" + currDate + "/" + "bkp_" + currDateTime + "_a.txt";

		saveDefault(listaJogosCorrentes, pathBkpCorrentes, defaultSize);
		saveDefault(listaJogosAtuais, pathBkpAtuais, defaultSize);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		String snBkp = base + "novos/" + currDateTime + "_snms.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		MegaSenaConfigAb config = getConfigBySize(tamanhoDoJogo);

		if (listaJogosCorrentes != null && !listaJogosCorrentes.isEmpty()) {
			String mcb = config.getFrequencia(listaJogosCorrentes);
			ArquivoUtil.save(mcb, pathCorrente);
		}
		if (listaJogosAtuais != null && !listaJogosAtuais.isEmpty()) {
			String mab = config.getFrequencia(listaJogosAtuais);
			ArquivoUtil.save(mab, pathAtuais);
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

			config = getConfigBySize(tamanhoDoJogo);

			config.setQttInicial(qttOriginal);

			try {

				System.out.println("Magic Number: " + magicNumber);
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

				List<Integer> jogosAtuaisFlat = getFlatMega(pathListaJogosCorrentes, count,
						jogosAtuais);

				List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1,
						config.getMaxNum());

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
				config.setIncluir(new ArrayList<>(listaIncl));

				int n = config.getMaxNum() - tamPadraoExcluir;
				int r = config.getNrosApostados() - listaIncl.size();
				int totalCombinacoes = CombinationUtils.calcNumberOfCombinations(n, r).intValue();
				if (config.getQttInicial() > totalCombinacoes && totalCombinacoes > 0) {
					System.out.println("Novo máximo número de jogos: " + totalCombinacoes);
					config.setQttInicial(totalCombinacoes);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				Thread.sleep(5000);

				preJogos = QuadrantesMegaSena.generateList(qttInicialFixo, tamanhoDoJogo, excluir,
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
				filtro = new FiltroMaximoLinhas(6, 10, maxLinhas);
				preJogos = filtro.filtrarListas(preJogos);

				filtro = new FiltroDivide(4, config.getMaxNum());
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && !preJogos.isEmpty()) {

					List<JogoMega> jogosLM = new ArrayList<JogoMega>();
					JogoMega jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoMega(pj);
						jogosLM.add(jlm);
					}

					System.out.println("Pontuando...");
					final Pontuador pontuador = config.getPontuador();
					final List<List<Integer>> results = new ArrayList<>(resultados);
					for (final JogoAb meuJogo : jogosLM) {
						pontuador.pontuar(results, meuJogo);
					}
					System.out.println("Fim da pontuação!!!");

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

					List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
					List<Integer> listaC = MapUtil.getListaOrdenadaListas(statC, 1,
							config.getMaxNum());
					String mc = MapUtil.getFrequenciaString(statC, 1, config.getMaxNum()) + "\n\n"
							+ listaC;
					ArquivoUtil.save(mc, pathCorrente);

					List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
					List<Integer> listaA = MapUtil.getListaOrdenadaListas(statA, 1,
							config.getMaxNum());
					String ma = MapUtil.getFrequenciaString(statA, 1, config.getMaxNum()) + "\n\n"
							+ listaA;
					ArquivoUtil.save(ma, pathAtuais);

					List<Integer> listaOrdenadaStat = MapUtil.getListaOrdenadaListas(statA, 1,
							config.getMaxNum());
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
