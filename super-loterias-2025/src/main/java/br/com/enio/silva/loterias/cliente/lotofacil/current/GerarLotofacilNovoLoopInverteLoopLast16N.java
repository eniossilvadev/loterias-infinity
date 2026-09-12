package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig16;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarLotofacilNovoLoopInverteLoopLast16N extends GerarJogosLotofacil2020Ab {

	public static int ultimos = -1;

	public static final int times = 10;

	public static final int modulador = 2;

	public static void main(String[] args) throws IOException {

		//		int[] ultimosJogos = { 0, 25, 0, 100, 0, 260, 0, 500, 50, 25, 2000, 0, 1000, 0, 0 };

		int[] ultimosJogos = { 26, 50, 100, 500, 0 };

		config = new LotofacilConfig16();

		List<List<Integer>> resultados = config.getTodosResultados();

		String f = config.getCaminhoJogoAtual();
		List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

		System.out.println("Tamanho da lista de origem (sem repeti��es): " + atual.size());

		String lm = config.getCaminhoJogoCorrente();

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int count = 0;

		int magicNumber = random.nextInt(100);

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		String cja = config.getCaminhoJogoAtual();
		listaJogosAtuais = ArquivoUtil.obterLinhasComoListasUnique(cja);

		String cjc = config.getCaminhoJogoCorrente();
		listaJogosCorrentes = ArquivoUtil.obterLinhasComoListasUnique(cjc);

		List<List<Integer>> inicio = new ArrayList<>(atual);
		Collections.shuffle(inicio);

		List<Integer> la = inicio.get(inicio.size() / 2);

		for (int i = 0; i < times; i++) {

			boolean especial = random.nextBoolean();

			sleep(2);

			count++;

			List<Integer> listaBaseNovosJogos = CombinationUtils.gerarListaSimples(1, 25);
			listaBaseNovosJogos.removeAll(la);

			List<Integer> all = new ArrayList<>();
			if (especial) {
				listaJogosAtuais.forEach(c -> {
					final List<Integer> theList = new ArrayList<>(c);
					all.addAll(theList);
				});
			} else {
				listaJogosCorrentes.forEach(c -> {
					final List<Integer> theList = new ArrayList<>(c);
					all.addAll(theList);
				});
			}

			Map<Integer, Integer> mapAtuais = MapUtil.getMapFrequencia(all, 1, 25);
			mapAtuais = MapUtil.sortByValue2(mapAtuais);
			System.out.println("Map Atuais: " + mapAtuais);
			mapAtuais = MapUtil.removeAll(mapAtuais, listaBaseNovosJogos);
			List<Integer> elements = MapUtil.getElements(mapAtuais, (i % modulador) + 1);

			if(elements == null) {
				elements = new ArrayList<>();
			}

			if(elements.isEmpty()) {
				List<Integer> listaRange = ListaUtils.getListaRange(1, 25);
				Collections.shuffle(listaRange);
				elements.addAll(listaRange.subList(0, (i % modulador) + 1));
			}

			if(elements != null && !elements.isEmpty()) {
				la.removeAll(elements);
			}

			int completar = config.getNrosApostados() - listaBaseNovosJogos.size()
					- elements.size();

			List<List<Integer>> preJogos = new ArrayList<>();

			System.out.println("completar (size): " + completar);
			System.out.println("Lista restante: " + la);
			System.out.println("Lista Base: " + listaBaseNovosJogos);
			System.out.println("Lista elements: " + elements);

			sleep(5);

			List<List<Integer>> pjs = CombinationUtils.gerarCombinacao(la, completar);
			for (List<Integer> pj : pjs) {
				pj.addAll(listaBaseNovosJogos);
				pj.addAll(elements);
				preJogos.add(pj);
			}

			preJogos.removeAll(listaJogosCorrentes);
			preJogos.removeAll(listaJogosAtuais);
			preJogos.removeAll(resultados);
			preJogos.removeAll(somenteNovos);

			try {

				String destino = config.getFullPath();
				String params = config.getFullPathParams();

				ultimos = ultimosJogos[(count + magicNumber) % ultimosJogos.length];

				int ultimos = ultimosJogos[count % ultimosJogos.length];
				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
						: ultimos;

				String asterics = StringUtils.repeat('*', 20);
				String spaces = StringUtils.repeat(' ', 20);
				System.out.println(asterics + spaces + "�ltimos: " + ultimos + spaces + asterics);

				List<List<Integer>> ultimosResultados = config.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

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

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosCorrentes, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				preJogos.removeAll(remover);

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

					pontuador.pontuar(ultimosResultados, meuJogo);
				}
				sleep(2);

				if (config.isPontuacaoExtra()) {
					for (JogoAb meuJogo : jogosLM) {

						Integer before = meuJogo.getPontuacao();
						if (especial && random.nextBoolean()) {
							pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);

							pontuador.pontuarMap(meuJogo, mapAtraso, 25, 10);

							Integer after = meuJogo.getPontuacao();
							List<Integer> lista = meuJogo.getNumerosAsList();

							if (x++ % 100 == 0) {
								String msg = String.format("[%s, %s] %s", before, after, lista);
								System.out.println(msg);
							}
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
							la.stream().map(Object::toString).collect(Collectors.joining("\t"))
							+ " | " + jogo.stream().map(Object::toString)
							.collect(Collectors.joining("\t")));
				}

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				ArquivoUtil.saveLists(jogosOut, destino, "\t");

				ArquivoUtil.save(config.toString(), params);

				rem.addAll(jogosOut);
				ArquivoUtil.saveLists(rem, remove, "\t");

				listaJogosCorrentes.addAll(jogos);
				listaJogosAtuais.addAll(jogos);
				somenteNovos.addAll(jogos);

				listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

				int size = somenteNovos.size();
				somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
				if (somenteNovos.size() < size) {
					count = count - (size - somenteNovos.size());
				}

				config.setSufixo("_shuffle");
				if (especial && random.nextBoolean()) {
					Collections.shuffle(jogosLM);
				} else {
					Collections.sort(jogosLM);
				}

				listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais,
						config.getNrosApostados());

				saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, count,
						jogosOut);

				la = new ArrayList<>(jogos.get(0));
				sleep(30);

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

		String sep = ", ";

		ArquivoUtil.saveLists(jogosOut, destino, sep, 2);
		ArquivoUtil.saveLists(listaJogosCorrentes, jogosCorrentes, sep, 2);
		ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, sep, 2);

		String jc = String.valueOf(count + 1);
		String sf = "Concurso: %s, ";
		System.out.println(String.format(sf + "Jogos Correntes: ", jc, jogosCorrentes));

		System.out.println("\n\n\n");

		String mc = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mc, config.getBasePath() + "correntes.txt");
		String ma = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(ma, config.getBasePath() + "atuais.txt");

		ArquivoUtil.saveLists(somenteNovos, sn, ", ", 2);
	}
}
