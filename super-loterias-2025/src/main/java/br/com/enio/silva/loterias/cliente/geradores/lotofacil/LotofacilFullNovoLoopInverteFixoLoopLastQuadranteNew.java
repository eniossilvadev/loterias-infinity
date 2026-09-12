package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.Config;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.lotofacil.*;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesLotofacil;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.*;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LotofacilFullNovoLoopInverteFixoLoopLastQuadranteNew extends GerarJogosLotofacil2020Ab {

	public static final LotofacilConfig POWER_CONFIG = new LotofacilConfig15_2();

	public static final int QUANTIDADE_DE_JOGOS = 12;

	public static int lastInicio = 100;

	public static int lastFim = 3000;

	public static final Boolean shift = true;

	public static final Boolean positional = true;

	public static int ultimos = -1;

	public static final int modulador = 1;

	private static final int DEFAULT_SIZE = 15;

	private static final int quantidade = 200000;

	public static String sep = ",";



	public static List<List<Integer>> execute(final int times, final int defaultSize)
			throws IOException {
		return execute(times, defaultSize, null);
	}

	public static List<List<Integer>> execute(final int times, final int defaultSize, Config conf)
			throws IOException {

		List<List<Integer>> retorno = null;

		List<Integer> la = new ArrayList<>(Arrays.asList(13,20));

		LotofacilConfig configuration = POWER_CONFIG;

		List<List<Integer>> resultados = configuration.getTodosResultados();

		String f = configuration.getCaminhoJogoAtual();
		List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

		System.out.println("Tamanho da lista de origem (sem repetiçoes): " + atual.size());

		String lm = configuration.getCaminhoJogoCorrente();

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int count = 0;


		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "\\LSN.txt";


		List<List<Integer>> full = LotoUtils.getAll(saveLotofacil.getCurr(), defaultSize);

		listaJogosAtuais = new ArrayList<>(full);

		listaJogosCorrentes = new ArrayList<>(full);

		String agora = LocalDateTime.now().toString().replaceAll("[^0-9]", "");
		String pathBkpCorrentes = base + "/bkp/bkp_" + agora + "_1.txt";
		ArquivoUtil.saveLists(listaJogosCorrentes, pathBkpCorrentes, sep, 2);
		String pathBkpAtuais = base + "/bkp/bkp_" + agora + "_2.txt";
		ArquivoUtil.saveLists(listaJogosAtuais, pathBkpAtuais, sep, 2);


		if(atual.isEmpty()){
			if(!listaJogosCorrentes.isEmpty()){
				atual = new ArrayList<>(listaJogosCorrentes);
			} else {
				List<Integer> list = IntStream.rangeClosed(1, 25)
						.boxed()
						.collect(Collectors.toList());
				Collections.shuffle(list);
				list = new ArrayList<>(list.subList(0,15));
				atual.add(list);
			}

		}


		List<List<Integer>> inicio = new ArrayList<>(atual);
		Collections.shuffle(inicio);



		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String pathListaDeJogos = configuration.getCaminhoJogoAtual();
		String pathListaJogosCorrentes = configuration.getCaminhoJogoCorrente();

		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";

		beforeLotofacil(base, somenteNovos, defaultSize, currDate, currDateTime);

		List<Integer> ultimoSorteio = resultados.get(resultados.size() - 1);

		for (int i = 0; i < times; i++) {

			String dt = DateUtils.getCurrentDefaultDateTime();

			boolean b1 = random.nextBoolean();
			boolean b2 = random.nextBoolean();
			int num = Math.abs(random.nextInt());

			num = num % 20;

			sleep(1);

			count++;


			List<Integer> listaBaseNovosJogos = CombinationUtils.gerarListaSimples(1, 25);
			if (la != null && !la.isEmpty()) {
				listaBaseNovosJogos.removeAll(la);
			} else {
				Collections.shuffle(listaBaseNovosJogos);
				listaBaseNovosJogos = listaBaseNovosJogos.subList(0, 10);
			}

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
			List<Integer> elements = MapUtil.getElements(mapAtuais, (i % modulador) + 1);


			if (elements.isEmpty()) {
				List<Integer> listaRange = ListaUtils.getListaRange(1, 25);
				Collections.shuffle(listaRange);
				elements.addAll(listaRange.subList(0, (i % modulador) + 1));
			}

			if (!elements.isEmpty()) {
				la.removeAll(elements);
			}

			int completar = configuration.getNrosApostados() - listaBaseNovosJogos.size()
					- elements.size();

			List<List<Integer>> preJogos = new ArrayList<>();

			System.out.println("completar (size): " + completar);
			System.out.println("Lista restante: " + la);
			System.out.println("Lista Base: " + listaBaseNovosJogos);
			System.out.println("Lista elements: " + elements);

			sleep(1);

			System.out.println("Gerando...");
			int listSize = configuration.getNrosApostados() - elements.size();
			List<List<Integer>> pjs = QuadrantesLotofacil.generateList(quantidade, listSize,
					elements, listaBaseNovosJogos);
			try {
				for (List<Integer> pj : pjs) {
					if (configuration.getNrosApostados() < pj.size()) {
						throw new Exception("Unespected size");
					}
					pj.addAll(elements);
					preJogos.add(pj);
				}

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);
				preJogos.removeAll(somenteNovos);
				preJogos.removeAll(full);

				String destino = configuration.getFullPath();
				String params = configuration.getFullPathParams();

				lastFim = lastFim < lastInicio? resultados.size() - 1: lastFim;
				ultimos = ThreadLocalRandom.current().nextInt(lastInicio, lastFim);
				String asterics = StringUtils.repeat('*', 20);
				String spaces = StringUtils.repeat(' ', 20);
				System.out.println(asterics + spaces + "Últimos: " + ultimos + spaces + asterics);

				List<List<Integer>> ultimosResultados = configuration.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

				Map<Integer, Integer> mapResultado = new ConcurrentHashMap<>(configuration.getMapResultado(resultados,
						ultimos));
				ArquivoUtil.save(mapResultado.toString(),
						configuration.getBasePath() + "RESULT.txt");

				Map<Integer, Integer> mapAtraso =  new ConcurrentHashMap<>(configuration.getMapAtraso(resultados));
				ArquivoUtil.save(mapAtraso.toString(),
						configuration.getBasePath() + "ULTIMA_VEZ.txt");

				String remove = configuration.getCaminhoJogoAtual();
				List<List<Integer>> rem = ArquivoUtil.obterLinhasComoListasUnique(remove);
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				remover.addAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosCorrentes, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				if (random.nextBoolean()) {
					preJogos = aplicarFiltroUltimoSorteio(ultimoSorteio, preJogos, 9, 12);
				}

				preJogos.removeAll(remover);

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (

						List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = configuration.getPontuador();

				int x = 0;

				final List<List<Integer>> finalUltimosResultados = new ArrayList<>(ultimosResultados);
				jogosLM.parallelStream().forEach(meuJogo -> {
					pontuador.pontuar(finalUltimosResultados, meuJogo);
				});
				sleep(2);



				if (configuration.isPontuacaoExtra()) {
					// Passo 2: Use AtomicInteger para o contador.
					final AtomicInteger xA = new AtomicInteger(0);

					// Garanta que mapResultado e mapAtraso são ConcurrentHashMap! (Passo 1)

					jogosLM.parallelStream().forEach(meuJogo -> {
						// A lógica interna do loop permanece quase a mesma
						Integer before = meuJogo.getPontuacao();
						if (!b1 && b2) {
							pontuador.pontuarMap(meuJogo, mapResultado, 25, 4);
						}
						if (!(b1 && b2)) {
							pontuador.pontuarMap(meuJogo, mapAtraso, 25, 3);
						}
						Integer after = meuJogo.getPontuacao();
						List<Integer> lista = meuJogo.getNumerosAsList();

						// Passo 2 (continuação): Use getAndIncrement() para uma operação atômica
						if (xA.getAndIncrement() % 100 == 0) {
							// Atenção: A ordem deste log será aleatória.
							String msg = String.format("[%s, %s] %s", before, after, lista);
							System.out.println(msg);
						}
					});
				}

				sleep(2);

				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, configuration.getNrosJogos());

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

				ArquivoUtil.saveLists(jogosOut, destino, sep);

				ArquivoUtil.save(configuration.toString(), params);

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

				configuration.setSufixo("_shuffle");
				if (b2 && random.nextBoolean()) {
					Collections.shuffle(jogosLM);
				} else {
					Collections.sort(jogosLM);
				}

				String output = base + "ind/" + currDate + "/LFI" + dt + ".txt";
				saveDefault2(jogos, output);
				saveDefault2(somenteNovos, sn);
				saveDefault2(somenteNovos, snBkp);

				saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
				saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

				List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
				String mc = configuration.getFrequencia(statC);
				ArquivoUtil.save(mc, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

				List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
				String ma = configuration.getFrequencia(statA);
				ArquivoUtil.save(ma, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");

				la = new ArrayList<>(jogos.get(0));

				retorno = new ArrayList<>(somenteNovos);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Não foi possível processar lista: " + la);
				erro.add(la);
				count--;
			}

		}

		if (!erro.isEmpty()) {
			System.out.println("Favor reprocessar :" + erro);
		}

		return retorno;
	}

	private static LotofacilConfig getConfiguration() {
		return getConfiguration(0);
	}

	private static LotofacilConfig getConfiguration(final int numero) {
		final int num = numero % 9;
		if (num == 4) {
			return new LotofacilConfig15_6_Invertido();
		} else if (num == 5 || num == 8) {
			return new LotofacilConfig15_6();
		}
		return new LotofacilConfig15_5();
	}

	public static void main(String[] args) throws IOException {

		final int quantidade = QUANTIDADE_DE_JOGOS; // vezes 3
		Config configuration = null;

		final int max = 25;
		final int size = DEFAULT_SIZE;
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String basePath = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\";
		String basePathName = basePath + "01_" + currDate + "_";
		String finalCleanListPath = basePathName + currDateTime + ".txt";

        List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

		List<List<Integer>> mainList = execute(quantidade, size, configuration);
		print(mainList, "Main");
        List<List<Integer>> finalList = new ArrayList<>(mainList);

		if (positional) {
			List<List<Integer>> positionalList = PositionalReplacement
					.getPositionalReplacement(mainList, max);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		if (shift) {
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

		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);

	}
}
