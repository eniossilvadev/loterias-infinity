package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.lotomania.PontuadorBasico;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosLotofacilNovoLoopInverte20200409 extends GerarJogosLotofacil2020Ab {

	public static int total = 30;

	public static int ultimos = 1000;

	public static int sequenciaMaxima = 7;

	public static void main(String[] args) throws IOException {

		int nJogos = total;

		LotofacilConfig config = getConfig(0);
		List<List<Integer>> resultados = config.getTodosResultados();

		String f = config.getCaminhoJogoCorrente();
		List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

		System.out.println("Tamanho da lista de origem (sem repeti��es): " + atual.size());

		String lm = config.getCaminhoJogoCorrente();
		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(lm);

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int count = 0;

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUnique(config.getCaminhoJogoAtual());
		listaJogosAtuais = clear(listaJogosAtuais);
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(config.getCaminhoJogoCorrente());
		listaJogosCorrentes = clear(listaJogosCorrentes);

		while (count < nJogos) {

			sleep(3);

			config = getConfig(count);

			count++;
			Collections.shuffle(listaMax);
			Collections.shuffle(listaMax);
			List<Integer> la = listaMax.get(new Random().nextInt(listaMax.size()));

			List<Integer> listaBaseNovosJogos = CombinationUtils.gerarListaSimples(1, 25);
			listaBaseNovosJogos.removeAll(la);

			List<List<Integer>> preJogos = new ArrayList<>();
			List<List<Integer>> pjs = CombinationUtils.gerarCombinacao(la, 5);
			for (List<Integer> pj : pjs) {
				pj.addAll(listaBaseNovosJogos);
				preJogos.add(pj);
			}

			preJogos.removeAll(listaJogosCorrentes);
			preJogos.removeAll(listaJogosAtuais);
			preJogos.removeAll(resultados);

			try {

				List<Integer> qtt = la;

				String destino = config.getFullPath();
				String params = config.getFullPathParams();

				System.out.println(config.toJson());

				if (ultimos <= 0) {
					ultimos = resultados.size();
				}

				Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
				ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");

				Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
				ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> rem = ArquivoUtil.obterLinhasComoListasUnique(remove);
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				remover.addAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				preJogos = aplicarFiltroQuantidadeDaLista(config, qtt, preJogos);

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (

						List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();

				int x = 0;
				for (JogoAb meuJogo : jogosLM) {

					pontuador.pontuar(resultados, meuJogo);
					Integer before = meuJogo.getPontuacao();
					((PontuadorBasico) pontuador).pontuarPorPosicao(resultados, meuJogo, 1);
					Integer after = meuJogo.getPontuacao();
					List<Integer> lista = meuJogo.getNumerosAsList();

					if (x++ % 100 == 0) {
						String msg = String.format("[%s, %s] %s", before, after, lista);
						System.out.println(msg);
					}
				}
				sleep(2);

				int y = 0;
				if (config.isPontuacaoExtra()) {
					for (JogoAb meuJogo : jogosLM) {

						Integer before = meuJogo.getPontuacao();
						pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);

						Integer after = meuJogo.getPontuacao();
						List<Integer> lista = meuJogo.getNumerosAsList();

						if (x++ % 100 == 0) {
							String msg = String.format("[%s, %s] %s", before, after, lista);
							System.out.println(msg);
						}
					}
				}

				sleep(2);

				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(meuJogo.getPontuacao() + "[" + c++ + "]: "
							+ meuJogo.getNumerosAsList());
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (

						JogoAb jj : jogosLM) {
					jogos.add(jj.getNumerosAsList());
					System.out.println(jj.getNumerosAsList() + "\t" + jj.getMapConta());
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					todos.addAll(jogo);
					System.out.println(jogo);
					System.out.println(
							jogo.stream().map(Object::toString).collect(Collectors.joining("\t")));
				}

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				ArquivoUtil.saveLists(jogosOut, destino, "\t");

				ArquivoUtil.save(config.toString(), params);

				rem.addAll(jogosOut);
				ArquivoUtil.saveLists(rem, remove, "\t");

				listaJogosCorrentes.addAll(jogos);
				somenteNovos.addAll(jogos);
				int size = listaJogosCorrentes.size();
				listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
				if (listaJogosCorrentes.size() < size) {
					count = count - (size - listaJogosCorrentes.size());
				}

				config.setSufixo("_shuffle");
				Collections.shuffle(jogosOut);

				saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, count,
						jogosOut);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Não foi processar lista: " + la);
				erro.add(la);
				count--;
			}
		}

		if (!erro.isEmpty()) {
			System.out.println("Favor reprocessar :" + erro);
		}
	}

	/**
	 * @param config
	 * @param jogosAtuais
	 * @param jogosCorrentes
	 * @param somenteNovos
	 * @param destino
	 * @param listaJogosAtuais
	 * @param listaJogosCorrentes
	 * @param sn
	 * @param count
	 * @param listaExcl
	 * @param str
	 * @param jogosOut
	 * @throws IOException
	 */
	protected static void saveAll(LotofacilConfig config, List<List<Integer>> somenteNovos,
			List<List<Integer>> listaJogosAtuais, List<List<Integer>> listaJogosCorrentes,
			String sn, int count, List<List<Integer>> jogosOut) throws IOException {

		String jogosAtuais = config.getCaminhoJogoAtual();
		String jogosCorrentes = config.getCaminhoJogoCorrente();
		String destino = config.getFullPath();

		ArquivoUtil.saveLists(jogosOut, destino, "\t");
		ArquivoUtil.saveLists(listaJogosCorrentes, jogosCorrentes, "\t");
		ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, "\t");

		String jc = String.valueOf(count + 1);
		String sf = "Concurso: %s, ";
		System.out.println(String.format(sf + "Jogos Correntes: ", jc, jogosCorrentes));

		System.out.println("\n\n\n");

		String mc = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mc, config.getBasePath() + "correntes.txt");
		String ma = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(ma, config.getBasePath() + "atuais.txt");

		ArquivoUtil.saveLists(somenteNovos, sn, "\t");
	}
}
