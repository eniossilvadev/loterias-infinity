package br.com.enio.silva.loterias.cliente.lotomania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.LotomaniaConfig;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.diversos.QuadrantesLotomania;
import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroSubstituirUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomaniaWinnerSimple01;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LotomaniaFullPremiado extends GerarJogosLotomaniaBase {

	public static final int NUMERO_JOGOS = 1;
	public static final int PREMIADO = 35;
	public static final int shif = 1;
	public static final int positional = 0;

	public static final String CAMINHO_BASE = "C:\\loterias\\gerador-apostas\\lotomania\\";
	public static final String PREMIADAS = CAMINHO_BASE + "premiada.txt";
	public static final String CURR = CAMINHO_BASE + "curr\\";
	public static final String HOJE = CURR + "HOJE.txt";

	public static List<List<Integer>> execute(List<Integer> preJogo, int total, int premiado)
			throws IOException, InvalidLenghtException {

		LotomaniaConfigAb conf = new LotomaniaConfig();

		String premiadas = PREMIADAS;
		List<List<Integer>> listaPremiada = fixInput(ArquivoUtil.obterLinhasComoListasUnique(premiadas));
		Collections.shuffle(listaPremiada);

		ArquivoUtil.saveLists(listaPremiada, premiadas, ", ", 2);

		List<List<Integer>> resultados = fixInput(GerarListaLotomania.getInstance()
				.gerarArquivoResultado(conf));

		List<List<Integer>> listaHoje = new ArrayList<>();
		List<List<Integer>> listaOut = new ArrayList<>();

		for (int counter = 0; counter < total; counter++) {
			try {
				if (counter % 2 == 0) {
					conf.setQttInicial(50000);
				} else {
					conf.setQttInicial(40000);
				}
				List<Integer> bilhetePremiado = listaPremiada.get(counter % listaPremiada.size());

				String pathListaHoje = HOJE;
				listaHoje = ArquivoUtil.obterLinhasComoListasUnique(pathListaHoje);

				String pathMaxLista = conf.getBasePath() + "MAX_LISTA.txt";
				List<List<Integer>> listaMax = fixInput(ArquivoUtil
						.obterLinhasComoListasUnique(pathMaxLista));

				List<Integer> last = resultados.get(resultados.size() - 1);

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				for (int i = 0; i < conf.getQttInicial(); i++) {
					List<Integer> lista = new ArrayList<>(preJogo);
					preJogos.add(lista);
				}

				preJogos = QuadrantesLotomania.generateList(200000, 50);

				FiltroIf filtro = null;

				System.out.println("Antes: " + preJogos.size());

				filtro = new FiltroQuantidadeDaLista(premiado, bilhetePremiado);
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos.size() > 0) {
					filtro = new FiltroSubstituirUltimoSorteio(last, conf.getMaxNum(), 15);
					preJogos = filtro.filtrarListas(preJogos);
				}

				Set<Integer> set = new HashSet<Integer>(Arrays.asList(-1));
				List<Integer> temUm = new ArrayList<Integer>(set);
				filtro = new FiltroExcluirTemUm(temUm);

				List<JogoLotomania> jogosLM = new ArrayList<JogoLotomania>();
				JogoLotomania jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotomania(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");
				PontuadorLotomaniaWinnerSimple01 pontuador = new PontuadorLotomaniaWinnerSimple01();

				int count = 0;
				int max = 0;
				Random r = new Random();
				boolean isMaior = false;
				String up = ">";
				for (JogoAb meuJogo : jogosLM) {

					pontuador.pontuar(resultados, meuJogo);
					Integer before = meuJogo.getPontuacao();

					List<List<Integer>> cj = new ArrayList<>();
					cj.add(meuJogo.getNumerosAsList());
					cj = getEspelhos(cj);
					meuJogo.setListaDeJogos(cj);

					List<JogoAb> listaCorrenteLotomania = getJogosLotomania(cj);
					pontuador.pontuar(resultados, listaCorrenteLotomania, meuJogo);

					Integer after = meuJogo.getPontuacao();
					List<Integer> list = meuJogo.getNumerosAsList();
					isMaior = after > max;
					if (isMaior) {
						max = after;
						up = ">";
					} else {
						up = "-";
					}
					if (isMaior || (count % (r.nextInt(5000) + 1)) == 0) {
						String s = String.format("%s\t%s %s [%s,%s] %s", count, max, up, before,
								after, list);
						System.out.println(s);
					}
					count++;
				}

				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, conf.getNrosJogos());
				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(meuJogo.getPontuacao() + "[" + c++ + "]: "
							+ meuJogo.getNumerosAsList());
				}

				List<List<Integer>> jogos = new ArrayList<>();
				for (JogoAb jj : jogosLM) {
					jogos.addAll(jj.getListaDeJogos());
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					System.out.println(jogo);
				}

				ArquivoUtil.saveLists(listaMax, pathMaxLista, "\t", new HashMap<String, String>(),
						2);

				HashMap<String, String> replace = new HashMap<String, String>();
				replace.put("100", "00");
				List<List<Integer>> hoje = new ArrayList<>(jogos);
				listaHoje.addAll(hoje);
				listaOut.addAll(hoje);
				ArquivoUtil.saveLists(listaHoje, pathListaHoje, "\t", replace, 2);

				ArquivoUtil.saveLists(jogos, conf.getCaminhoDefaultOutput(), "\t",
						new HashMap<String, String>(), 2);

			} catch (Exception e) {
				total++;
				e.printStackTrace();
			}
		}
		return listaOut;

	}

	public static List<List<Integer>> fixInput(final List<List<Integer>> input){
		List<List<Integer>> output = new ArrayList<>();

		input.forEach(l -> {
			List<Integer> lista = new ArrayList<>(l);
			if(l.contains(0)) {
				lista.remove(0);
				lista.add(100);
			}
			output.add(lista);
		});

		return output;
	}

	public static void main(String[] args) throws IOException, InvalidLenghtException {

		int total = NUMERO_JOGOS;
		int premiado = PREMIADO;
		final int max = 100;
		final int size = 50;


		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = CURR;
		String basePathName = basePath + "01_" + currDate + "_sorte_grande_";
		String finalListPath = basePathName + currDateTime + "_final.txt";
		String finalCleanListPath = basePathName + currDateTime + ".txt";
		List<List<Integer>> finalList = new ArrayList<>();

		List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		List<List<Integer>> mainList = fixInput(execute(new ArrayList<>(), total, premiado));
		print(mainList, "Main");
		finalList.addAll(mainList);

		for (int i = 0; i < positional; i++) {
			List<List<Integer>> positionalList = PositionalReplacement
					.getPositionalReplacement(mainList, max);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		for (int i = 0; i < shif; i++) {
			List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, max);
			print(shiftedList, "Shifed");
			finalList.addAll(shiftedList);
		}

		print(finalList, "Final -> Before");
		Collections.sort(finalList, new ListOfListComparator());
		print(finalList, "Final -> After");

		List<List<Integer>> cleanList = new ArrayList<>(finalList);
		cleanList = ListaUtils.removerDaLista(cleanList, listaRemover);
		cleanList = CollectionsUtils.removeDuplicated(cleanList);
		print(cleanList, "Clean List");

		ArquivoUtil.saveLists(finalList, finalListPath, theSep, 2);
		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);

		HashMap<String, String> replace = new HashMap<String, String>();
		replace.put("100", "00");
		ArquivoUtil.saveLists(finalList, finalListPath, theSep, replace, 2);
		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, replace, 2);
	}

}
