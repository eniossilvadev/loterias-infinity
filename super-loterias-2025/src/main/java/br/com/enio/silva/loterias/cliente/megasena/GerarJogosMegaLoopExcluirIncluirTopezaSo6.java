package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosMegaLoopExcluirIncluirTopezaSo6 extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 15;

		int qttInicialFixo = 50000;
		int qttOriginal = qttInicialFixo;

		int tamPadraoExcluir = 10;
		int tamPadraoIncluir = 3;
		int minPercent = 10;
		int maxPercent = 15;

		int seq = new Random().nextInt(100);

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "todos_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		MegaSenaConfigAb config = new MegaSenaConfig6();

		String mcb = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		listaJogosAtuais = getListCombination(listaJogosAtuais, config.getNrosApostados());
		listaJogosCorrentes = getListCombination(listaJogosCorrentes, config.getNrosApostados());

		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
		ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		int magicNumber = rand.nextInt(26) + 1;

		for (int count = 0; count < totalDeJogos; count++) {
			config = new MegaSenaConfig6();
			config.setQttInicial(qttOriginal);

			try {

				System.out.println("Magic Number: " + magicNumber);
				// tamPadraoIncluir = 4 - magicNumber % 3;
				tamPadraoExcluir = 13 + magicNumber % 3;
				magicNumber++;

				config.setQttInicial(qttInicialFixo);

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove);
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				String jogosAtuais = config.getCaminhoJogoAtual();

				List<Integer> jogosAtuaisFlat = getFlatMega(pathListaJogosCorrentes, count,
						jogosAtuais);

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

				//				https://www.mazusoft.com.br/mega/tabela-sequencia-atraso.php
				List<Integer> theList = Arrays.asList();

				if(theList != null && !theList.isEmpty()) {
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
				boolean comb = false;
				if (config.getQttInicial() > totalCombinacoes) {
					System.out.println("Novo máximo número de jogos: " + totalCombinacoes);
					config.setQttInicial(totalCombinacoes);
					comb = true;
				}
				// TimeUnit.SECONDS.sleep(30);
				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				// if (comb) {
				// preJogos = CombinationUtils.gerarCombinacao(tam, 1, maxNum,
				// excluir, incluir);
				//
				// } else {
				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);
				// }

				// TimeUnit.SECONDS.sleep(30);
				boolean filtrar = rand.nextBoolean() || rand.nextBoolean();
				FiltroIf filtro = null;

				if (filtrar) {

					filtro = new FiltroRemoverIntersecao(remover);
					preJogos = filtro.filtrarListas(preJogos);

					int maxRep = 3;
					filtro = new FiltroMaxConsecutivos(maxRep);
					preJogos = filtro.filtrarListas(preJogos);

					int maxLinhas = 3 + count % 3;
					filtro = new FiltroMaximoLinhas(6, 10, maxLinhas);
					preJogos = filtro.filtrarListas(preJogos);

				}

				if (preJogos != null && !preJogos.isEmpty()) {

					List<JogoMega> jogosLM = new ArrayList<JogoMega>();
					JogoMega jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoMega(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(resultados, meuJogo);
					}

					if (random.nextBoolean() && random.nextBoolean()) {
						Collections.shuffle(jogosLM);
					} else {
						Collections.sort(jogosLM);
					}

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

					listaJogosAtuais.sort(new ListOfListComparator());
					listaJogosCorrentes.sort(new ListOfListComparator());
					ArquivoUtil.saveLists(jogos, output, "\t", 2);
					ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);
					ArquivoUtil.saveLists(listaJogosAtuais, pathListaJogosCorrentes, "\t", 2);
					ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);

					String mc = config.getFrequencia(listaJogosCorrentes);
					ArquivoUtil.save(mc,
							CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
					String ma = config.getFrequencia(listaJogosAtuais);
					ArquivoUtil.save(ma, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

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
				ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
				ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);
			}
		}

		listaJogosAtuais.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);
		listaJogosCorrentes.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
	}

}
