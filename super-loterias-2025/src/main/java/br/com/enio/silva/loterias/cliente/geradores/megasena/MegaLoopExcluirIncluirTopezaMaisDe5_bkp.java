package br.com.enio.silva.loterias.cliente.geradores.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.megasena.GerarJogosMegaLoopAb;
import br.com.enio.silva.loterias.commons.SaveMegaSena;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class MegaLoopExcluirIncluirTopezaMaisDe5_bkp extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		SaveMegaSena saveMegaSena = new SaveMegaSena();

		final int totalDeJogos = 3;

		final int tamanhoDoJogo = 6;

		int qttInicialFixo = 150000;
		final int qttOriginal = qttInicialFixo;

		int tamPadraoExcluir = 3;
		int tamPadraoIncluir = 2;
		int minPercent = 10;
		int maxPercent = 15;

		final int defaultSize = 6;

		// https://www.mazusoft.com.br/mega/tabela-sequencia-atraso.php
		// 11, 42, 7, 50, 40, 21, 60, 8, 47, 29
		List<Integer> theList = Arrays.asList();
		Collections.shuffle(theList);

		String sep = ",";

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		List<List<Integer>> full = LotoUtils.getAll(saveMegaSena.getCurr(), defaultSize);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		String snBkp = base + "novos/" + currDateTime + "_snms.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		MegaSenaConfigAb config = getConfigBySize(tamanhoDoJogo);

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		int magicNumber = rand.nextInt(1000);

		int seq = rand.nextInt(100);

		theSep = sep;

		for (int count = 0; count < totalDeJogos; count++) {

			config = getConfigBySize(tamanhoDoJogo);

			config.setQttInicial(qttOriginal);

			try {

				System.out.println("Magic Number: " + magicNumber);
				//				tamPadraoIncluir = 4 - magicNumber % 3;
				//				tamPadraoExcluir = 13 + magicNumber % 13;
				magicNumber++;

				config.setQttInicial(qttInicialFixo);

				String output = base + "ind/" + currDate + "/" + config.getDefaultName()
				+ DateUtils.getCurrentDefaultDateTime() + ".txt";

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				List<Integer> jogosAtuaisFlat = LotoUtils.getAllInSingleList(saveMegaSena.getCurr(), defaultSize);

				Set<Integer> listaExcl = new HashSet<>();
				Set<Integer> listaIncl = new HashSet<>();
				if (rand.nextBoolean()) {
					listaExcl = gerarExcluirAcimaMax(config, maxPercent, tamPadraoExcluir,
							jogosAtuaisFlat);
					listaIncl = gerarIncluirAbaixoMinimo(config, minPercent, tamPadraoIncluir,
							jogosAtuaisFlat);
				} else {
					listaExcl = gerarExcluirMax(config, tamPadraoExcluir, jogosAtuaisFlat);
					listaIncl = gerarIncluirMinimo(config, tamPadraoIncluir, jogosAtuaisFlat);
				}

				if (theList != null && !theList.isEmpty()) {
					theList = new ArrayList<>(new HashSet<>(theList));

					if (rand.nextBoolean()) {
						listaIncl.add(theList.get(seq++ % theList.size()));
					} else {
						listaIncl.add(theList.get(count % theList.size()));
					}
				}

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (last != null) {
					Collections.shuffle(lasts);
					lasts = lasts != null && lasts.size() > 11 ? lasts.subList(0, 8) : lasts;
					lasts.addAll(last);
					lasts = new ArrayList<>(new HashSet<>(lasts));
					listaExcl.addAll(lasts);
					listaIncl.removeAll(lasts);
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
				int r = config.getNrosApostados() - tamPadraoIncluir;
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
				fullList.addAll(full);
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

				filtro = new FiltroDivide(4, 60);
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

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
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
				ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
			}
		}
	}

}
