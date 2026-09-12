package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.diversos.EsquemasLotofacilUtil;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;

public class LotofacilGerarJogosEsquemaXisDoTikTok extends GerarLotofacilModular {

	private static int qttInicial = 100; // vezes 252

	public static int total = 10;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 11;

	public static int maximoInicio = 7;

	public static int minimoFim = 18;

	public static void main(String[] args) throws IOException {

		List<List<Integer>> resultados = new LotofacilConfig15().obterTodosResultados();

		//		int[] ultimosJogos = { 50, 0, 100, 0, 260, 500, 0, 1000, 2000, 0, 75, 26, 125 };

		int[] ultimosJogos = { 0, 10, 13, 26 };

		List<List<Integer>> somenteNovos = new ArrayList<>();

		init();

		save(listaJogosAtuais, config.getCaminhoJogoAtual());
		save(listaJogosCorrentes, config.getCaminhoJogoCorrente());

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		int curr = 0;

		saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, 0, null, null,
				null);

		for (int count = 0; count < total; count++) {

			curr++;

			try {

				config = getConfig(curr);
				config.setQttInicial(qttInicial);
				config.setExcluir(new ArrayList<>());
				config.setIncluir(Arrays.asList());

				List<Integer> qtt = Arrays.asList();

				listaJogosCorrentes.removeAll(resultados);

				ultimos = getUltimos(resultados, ultimosJogos, count);

				List<List<Integer>> ultimosResultados = getUltimosResultadosCorrentes(ultimos);

				saveMapResultado(config, resultados, ultimos);

				saveMapAtraso(config, resultados);

				System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

				List<List<Integer>> preJogos = EsquemasLotofacilUtil
						.gerarJogosIniciaisEstrategiaXdoTikTok(qttInicial);

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				preJogos = aplicarFiltroQuantidadeDaLista(config, qtt, preJogos);

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(ultimosResultados, meuJogo);
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

				StringBuilder str = printOrdenado(jogos);

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, count,
						new HashSet<>(), str, jogosOut);

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
