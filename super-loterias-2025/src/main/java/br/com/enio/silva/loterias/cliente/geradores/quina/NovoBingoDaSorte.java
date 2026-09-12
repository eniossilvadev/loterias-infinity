package br.com.enio.silva.loterias.cliente.geradores.quina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.commons.SaveQuina;
import br.com.enio.silva.loterias.config.quina.QuinaConfig10;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5_1;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class NovoBingoDaSorte extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static final int maxFromList = 8;

	public static void main(String[] args) throws IOException {

		int[] ultimosJogos = { 0, 30, 50, 100, 0, 260, 0, 500, 0, 1000 };

		String listPath = "C:\\loterias\\auxiliar\\bs_base.txt";
		List<List<Integer>> listaExterna = ArquivoUtil.obterLinhasComoListas(listPath);
		System.out.println(listaExterna.size());

		SaveQuina sq = new SaveQuina();

		List<List<Integer>> listaDeJogos = ArquivoUtil
				.obterLinhasComoListasUnique(sq.getCaminhoAtuais());

		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(sq.getCaminhoCorrentes());

//		System.out.println(listaDeJogos);

		String pathResultados = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = ArquivoUtil.obterLinhasComoListas(pathResultados);

		List<List<Integer>> somenteNovos = new ArrayList<>();

		listaExterna.forEach(theList -> {

			QuinaConfigAb config = new QuinaConfig10();

			Collections.shuffle(theList);
			Set<Integer> listaIncl = new HashSet<>(theList.subList(0, maxFromList - 2));


			int rand = Math.abs(new Random().nextInt(100));

			int ultimos = ultimosJogos[(rand) % ultimosJogos.length];
			ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size() - 1
					: ultimos - 1;
			List<List<Integer>> ultimosResultados = ArquivoUtil.obterLinhasComoListas(pathResultados);
			Collections.reverse(ultimosResultados);
			ultimosResultados = ultimosResultados.subList(0, ultimos);

			try {

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);

				List<List<Integer>> jogosLista = ArquivoUtil
						.obterLinhasComoListasUnique(sq.getCaminhoAtuais());
				List<List<Integer>> novosLista = ArquivoUtil
						.obterLinhasComoListasUnique(sq.getCaminhoCorrentes());

				remover.addAll(jogosLista);
				remover.addAll(novosLista);
				remover = new ArrayList<>(new HashSet<>(remover));


				System.out.println(repeated50);

				System.out.println("Números incluídos: " + listaIncl);

				System.out.println(repeated50);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				List<Integer> excluir = config.getExcluir();

				List<Integer> listaRange = ListaUtils.getListaRange(1, 80);
				listaRange.removeAll(listaIncl);
				Collections.shuffle(listaRange);
				listaRange.removeAll(listaRange.subList(0,35));

				List<Integer> listaFixos = new ArrayList<>(listaIncl);

				System.out.println("Números incluídos: " + listaFixos);
				listaRange.removeAll(listaFixos);

				int size = config.getNrosApostados() - listaFixos.size();

				preJogos = CombinationUtils.gerarCombinacaoComFixos(listaFixos, listaRange,
						size);

				preJogos.removeAll(remover);

				FiltroIf filtro = null;


				filtro = new FiltroRemoverIntersecao(remover);
				preJogos = filtro.filtrarListas(preJogos);

				List<List<Integer>> lista = new ArrayList<>();
				lista.add(theList);
				filtro = new FiltroMaxLista(lista, maxFromList);
				filtro.filtrarListas(preJogos);

				if (!preJogos.isEmpty()) {
					filtro = new FiltroMaxConsecutivos(3);
				}

				if (!preJogos.isEmpty()) {
					filtro = new FiltroMaximoLinhas(6, 10, 4);
				}




					List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
					JogoQuina jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoQuina(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(ultimosResultados, meuJogo);
					}

					Collections.sort(jogosLM);
					if (random.nextBoolean() && random.nextBoolean()) {
						// Collections.reverse(jogosLM);
					}

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						System.out.println(jj.toString());
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
						listaDeJogos.add(novo);
						listaJogosCorrentes.add(novo);
						somenteNovos.add(novo);
					}

					for (List<Integer> jogo : jogos) {
						Collections.replaceAll(jogo, 0, 0);
						Collections.sort(jogo);
					}

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo);
					}

//					sq.saveIndividual(jogos, "tst");
//					sq.saveAtuais(listaDeJogos);
//					sq.saveCorrentes(listaJogosCorrentes);
					sq.saveNovos(somenteNovos);


			} catch (Exception e) {
				e.printStackTrace();
				sq.saveAtuais(listaDeJogos);
				sq.saveCorrentes(listaJogosCorrentes);
				sq.saveNovos(somenteNovos);
			}
		});
	}
}
