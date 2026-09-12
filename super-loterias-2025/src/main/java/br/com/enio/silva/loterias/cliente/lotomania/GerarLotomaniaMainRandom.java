package br.com.enio.silva.loterias.cliente.lotomania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotomania;
import br.com.enio.silva.loterias.config.LotomaniaConfig80;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroSubstituirUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomaniaWinnerSimple01;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarLotomaniaMainRandom extends GerarJogosLotomaniaBase {

	public static List<List<Integer>> execute(List<Integer> preJogo)
	        throws IOException, InvalidLenghtException {

		LotomaniaConfigAb conf = getConfig() != null ? getConfig() : new LotomaniaConfig80();

		List<List<Integer>> resultados = GerarListaLotomania.getInstance()
		        .gerarArquivoResultado(conf);

		String pathListaHoje = "E:\\loterias\\lotomania\\info\\HOJE.txt";
		List<List<Integer>> listaHoje = ArquivoUtil.obterLinhasComoListasUnique(pathListaHoje);

		String pathMaxLista = conf.getBasePath() + "MAX_LISTA.txt";
		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(pathMaxLista);

		int ultimos = resultados.size();

		Map<Integer, Integer> mapResultado = getMapResultado(resultados, ultimos);
		Map<Integer, Integer> mapAtraso = getMapAtraso(resultados, conf.getMaxNum());

		List<Integer> last = resultados.get(resultados.size() - 1);

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		for (int i = 0; i < conf.getQttInicial(); i++) {
			List<Integer> lista = new ArrayList<>(preJogo);
			preJogos.add(lista);
		}

		preJogos = ListUtil.completar(preJogos, conf.getNrosApostados(), conf.getMaxNum(), true);

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		filtro = new FiltroSubstituirUltimoSorteio(last, conf.getMaxNum(), 15);
		preJogos = filtro.filtrarListas(preJogos);

		// filtro = new FiltroUltimoSorteio(0, 5, last);

		if (listaMax != null && !listaMax.isEmpty()) {
			filtro = new FiltroMaxLista(listaMax, conf.getNrosApostados() - 10);
			preJogos = filtro.filtrarListas(preJogos);
		}

		filtro = new FiltroDivide(12, conf.getMaxNum());
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroParImpar(12);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMinimoLinhas(10, 10, 2);
		preJogos = filtro.filtrarListas(preJogos);

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

			List<List<Integer>> cj = EsquemaLotomania
			        .esquema80n0f10aRandom(meuJogo.getNumerosAsList(), mapResultado, mapAtraso);
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
				String s = String.format("%s\t%s %s [%s,%s] %s", count, max, up, before, after,
				        list);
				System.out.println(s);
			}
			count++;
		}

		Collections.sort(jogosLM);

		jogosLM = jogosLM.subList(0, conf.getNrosJogos());
		int c = 0;
		for (JogoAb meuJogo : jogosLM) {
			System.out.println(
			        meuJogo.getPontuacao() + "[" + c++ + "]: " + meuJogo.getNumerosAsList());
		}

		List<List<Integer>> jogos = new ArrayList<>();
		List<List<Integer>> jogos80 = new ArrayList<>();
		for (JogoAb jj : jogosLM) {
			jogos.addAll(jj.getListaDeJogos());
			jogos80.add(jj.getNumerosAsList());
		}

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo);
		}

		List<List<Integer>> original = new ArrayList<>(jogos80);
		listaMax.addAll(original);
		ArquivoUtil.saveLists(listaMax, pathMaxLista, "\t", new HashMap<String, String>(), 2);

		HashMap<String, String> replace = new HashMap<String, String>();
		replace.put("100", "0");
		List<List<Integer>> hoje = new ArrayList<>(jogos);
		listaHoje.addAll(hoje);
		ArquivoUtil.saveLists(listaHoje, pathListaHoje, "\t", replace, 2);

		ArquivoUtil.saveLists(jogos, conf.getCaminhoDefaultOutput(), "\t",
		        new HashMap<String, String>(), 2);

		return jogos;
	}

	public static void main(String[] args) throws IOException, InvalidLenghtException {
		execute(new ArrayList<>());
	}

}
