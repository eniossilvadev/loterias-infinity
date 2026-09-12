package br.com.enio.silva.loterias.cliente.geradores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.bingodasorte.newp.GerarJogosQuinaBingoAb;
import br.com.enio.silva.loterias.config.quina.QuinaConfig10;
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

public class BingoDaSorte2023 extends GerarJogosQuinaBingoAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 3;

		int qttInicialFixo = 100000;
		int qttOriginal = qttInicialFixo;

		int tamPadraoExcluir = 20;
		int tamanhoIncluirLista = 3;

		// https://www.mazusoft.com.br/quina/tabela-sequencia-atraso.php
		List<Integer> theList = Arrays.asList();

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

		String sep = ", ";

		String base = CaminhoResultados.QUINA.getBasePath() + "\\config\\";

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String path = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNQ.txt";
		String snBkp = base + "novos/" + currDateTime + "_snms.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		QuinaConfigAb config = new QuinaConfig10();

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		int magicNumber = rand.nextInt(1000);

		List<Integer> jogosAtuaisFlat = new ArrayList<>();

		theSep = sep;

		for (int count = 0; count < totalDeJogos; count++) {

			config = new QuinaConfig10();

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
				fullList.addAll(somenteNovos);

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
						somenteNovos.add(novo);
						jogosAtuaisFlat.addAll(novo);
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
