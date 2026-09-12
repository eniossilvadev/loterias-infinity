package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_5;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_6;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_6_Invertido;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesLotofacil;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LotofacilUIltimoConcursoQuadrante extends GerarJogosLotofacil2020Ab {

	public static int ultimos = -1;

	public static final int TIMES = 10;

	public static final int MODULADOR = 1;

	public static final int QUANTIDADE_ULTIMO_CONCURSO = 10;

	private static final int DEFAULT_SIZE = 15;

	private static final int QUANTIDADE = 100000;

	public static String sep = ",";

	public static List<List<Integer>> getAll() throws IOException {

		List<List<Integer>> retorno = new ArrayList<>();

		int[] ultimosJogos = { 0, 0, 26, 0, 0, 50, 0, 0, 100, 0, 0, 500, 0, 0, 0 };

		config = new LotofacilConfig15_6_Invertido();

		List<List<Integer>> resultados = config.getTodosResultados();

		String f = config.getCaminhoJogoAtual();
		List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

		System.out.println("Tamanho da lista de origem (sem repetiçoes): " + atual.size());

		String lm = config.getCaminhoJogoCorrente();

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int count = 0;

		int magicNumber = random.nextInt(26) + 1;

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "\\LSN.txt";

		var full = LotoUtils.getAll(saveLotofacil.getCurr(), DEFAULT_SIZE);

		listaJogosAtuais = new ArrayList<>(full);
		listaJogosCorrentes = new ArrayList<>(full);

		String agora = LocalDateTime.now().toString().replaceAll("[^0-9]", "");
		String pathBkpCorrentes = base + "/bkp/bkp_" + agora + "_1.txt";
		ArquivoUtil.saveLists(listaJogosCorrentes, pathBkpCorrentes, sep, 2);
		String pathBkpAtuais = base + "/bkp/bkp_" + agora + "_2.txt";
		ArquivoUtil.saveLists(listaJogosAtuais, pathBkpAtuais, sep, 2);

		List<List<Integer>> inicio = new ArrayList<>(atual.isEmpty() ? listaJogosCorrentes : atual);
		Collections.shuffle(inicio);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String pathListaDeJogos = config.getCaminhoJogoAtual();
		String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();

		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";

		beforeLotofacil(base, somenteNovos, DEFAULT_SIZE, currDate, currDateTime);

		List<Integer> ultimoSorteio = resultados.get(resultados.size() - 1);

		for (int i = 0; i < TIMES; i++) {

			String dt = DateUtils.getCurrentDefaultDateTime();

			boolean b1 = random.nextBoolean();
			boolean b2 = random.nextBoolean();
			int num = Math.abs(random.nextInt());

			num = num % 20;

			if (num == 5) {
				config = new LotofacilConfig15_6_Invertido();
			} else if (num < 6) {
				config = new LotofacilConfig15_6();
			} else {
				config = new LotofacilConfig15_5();
			}

			sleep(1);

			count++;

			List<Integer> la = new ArrayList<>(ultimoSorteio);
			Collections.shuffle(la);
			int quantidadeCorrente = QUANTIDADE_ULTIMO_CONCURSO + i % (MODULADOR * 2 + 1)
					- MODULADOR;
			List<Integer> listaBaseNovosJogos = la.subList(0, quantidadeCorrente);

			List<Integer> all = new ArrayList<>();
			if (b1) {
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
			List<Integer> elements = MapUtil.getElements(mapAtuais, (i % MODULADOR) + 1);

			if (elements == null) {
				elements = new ArrayList<>();
			}

			if (elements.isEmpty()) {
				List<Integer> listaRange = ListaUtils.getListaRange(1, 25);
				Collections.shuffle(listaRange);
				elements.addAll(listaRange.subList(0, (i % MODULADOR) + 1));
			}

			int completar = config.getNrosApostados() - listaBaseNovosJogos.size()
					- elements.size();

			List<List<Integer>> preJogos = new ArrayList<>();

			System.out.println("completar (size): " + completar);
			System.out.println("Lista Base: " + listaBaseNovosJogos);
			System.out.println("Lista elements: " + elements);

			sleep(1);

			System.out.println("Gerando...");
			int listSize = config.getNrosApostados() - elements.size();
			List<List<Integer>> pjs = QuadrantesLotofacil.generateList(QUANTIDADE, listSize,
					elements, listaBaseNovosJogos);
			for (List<Integer> pj : pjs) {
				pj.addAll(elements);
				preJogos.add(pj);
			}

			List<List<Integer>> fullList = new ArrayList<>();
			fullList.addAll(listaJogosCorrentes);
			fullList.addAll(listaJogosAtuais);
			fullList.addAll(resultados);
			fullList.addAll(resultados);
			Set<List<Integer>> fullSet = new HashSet<>(fullList);
			fullList = new ArrayList<>(fullSet);

			preJogos.removeAll(fullList);

			try {

				String destino = config.getFullPath();
				String params = config.getFullPathParams();

				ultimos = ultimosJogos[(count + magicNumber) % ultimosJogos.length];

				int ultimos = ultimosJogos[count % ultimosJogos.length];
				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
						: ultimos;

				String asterics = StringUtils.repeat('*', 20);
				String spaces = StringUtils.repeat(' ', 20);
				System.out.println(asterics + spaces + "Últimos: " + ultimos + spaces + asterics);

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

				preJogos = aplicarFiltroRemoverIntersecao(fullList, preJogos);

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
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
						if (!b1 && b2) {
							pontuador.pontuarMap(meuJogo, mapResultado, 25, 4);
						}
						if (!(b1 && b2)) {
							pontuador.pontuarMap(meuJogo, mapAtraso, 25, 3);
						}
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
				}

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				ArquivoUtil.saveLists(jogosOut, destino, sep);

				ArquivoUtil.save(config.toString(), params);

				rem.addAll(jogosOut);
				ArquivoUtil.saveLists(rem, remove, sep);

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
				if (b2 && random.nextBoolean()) {
					Collections.shuffle(jogosLM);
				} else {
					Collections.sort(jogosLM);
				}

				String output = base + "ind/" + currDate + "/LFI" + dt + ".txt";
				saveDefault2(jogos, output);
				saveDefault2(somenteNovos, sn);
				saveDefault2(somenteNovos, snBkp);

				String todo = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\" + currDateTime + ".txt";
				saveDefault2(somenteNovos, todo);

				saveDefault(listaJogosAtuais, pathListaDeJogos, DEFAULT_SIZE);
				saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, DEFAULT_SIZE);

				List<List<Integer>> statC = gc(listaJogosCorrentes, DEFAULT_SIZE);
				String mc = config.getFrequencia(statC);
				ArquivoUtil.save(mc, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

				List<List<Integer>> statA = gc(listaJogosAtuais, DEFAULT_SIZE);
				String ma = config.getFrequencia(statA);
				ArquivoUtil.save(ma, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");

				retorno = new ArrayList<>(somenteNovos);

			} catch (Exception e) {
				e.printStackTrace();
				count--;
			}

		}

		if (!erro.isEmpty()) {
			System.out.println("Favor reprocessar :" + erro);
		}

		return retorno;

	}

	public static void main(String[] args) throws IOException {
		List<List<Integer>> mainList = getAll();
		mainList.forEach(System.out::println);

	}
}
