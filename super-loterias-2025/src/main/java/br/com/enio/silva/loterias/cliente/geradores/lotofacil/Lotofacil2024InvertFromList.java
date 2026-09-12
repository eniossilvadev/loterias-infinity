package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig16;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesLotofacil;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Lotofacil2024InvertFromList extends Lotofacil2024InverteAb {

	private static final int times = 5;

	private static final int defaultSize = 15;

	private static final LotofacilConfig superConfig = new LotofacilConfig16();

	private static final int quantidade = 150000;

	public static String sep = ",";

	private static final int modulador = 1;

	public static void main(String[] args) throws IOException {

		config = superConfig;
		final List<Integer> listaFixa = Arrays.asList();
		final String input = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\z.txt";

		final String outputPath = getOutPut(input);
		final List<List<Integer>> inputList = ArquivoUtil.obterLinhasComoListasUnique(input);

		int[] ultimosJogos = { 0, 0, 26, 0, 0, 50, 0, 0, 100, 0, 0, 500, 0, 0, 0 };

		List<List<Integer>> resultados = config.getTodosResultados();

		String lm = config.getCaminhoJogoCorrente();

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int count = 0;

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "\\LSN.txt";

		List<List<Integer>> full = LotoUtils.getAll(saveLotofacil.getCurr(), defaultSize);

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String pathListaDeJogos = config.getCaminhoJogoAtual();
		String pathListaJogosCorrentes = config.getCaminhoJogoCorrente();

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

		String snBkp = base + "novos/" + currDateTime + "_nlf.txt";

		beforeLotofacil(base, somenteNovos, defaultSize, currDate, currDateTime);

		Collections.shuffle(inputList);

		int currentList = 0;

		for (int i = 0; i < times; i++) {

			List<Integer> la = new ArrayList<>(inputList.get(currentList++ % inputList.size()));
			String dt = DateUtils.getCurrentDefaultDateTime();

			if (listaFixa != null && !listaFixa.isEmpty()) {
				la.removeAll(listaFixa);
			}

			sleep(2);

			count++;

			List<Integer> listaBaseNovosJogos = CombinationUtils.gerarListaSimples(1, 25);
			if (la != null && !la.isEmpty()) {
				listaBaseNovosJogos.removeAll(la);
			} else {
				Collections.shuffle(listaBaseNovosJogos);
				listaBaseNovosJogos = listaBaseNovosJogos.subList(0, 10);
			}

			List<Integer> all = new ArrayList<>();
			if (getRandomBoolean()) {
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

			if (elements == null) {
				elements = new ArrayList<>();
			}

			if (elements.isEmpty()) {
				List<Integer> listaRange = ListaUtils.getListaRange(1, 25);
				Collections.shuffle(listaRange);
				elements.addAll(listaRange.subList(0, (i % modulador) + 1));
			}

			if (elements != null && !elements.isEmpty()) {
				la.removeAll(elements);
			}

			int completar = config.getNrosApostados() - listaBaseNovosJogos.size()
					- elements.size();

			List<List<Integer>> preJogos = new ArrayList<>();

			System.out.println("completar (size): " + completar);
			System.out.println("Lista restante: " + la);
			System.out.println("Lista Base: " + listaBaseNovosJogos);
			System.out.println("Lista elements: " + elements);

			sleep(1);

			System.out.println("Gerando...");
			int listSize = config.getNrosApostados() - elements.size();
			List<List<Integer>> pjs = QuadrantesLotofacil.generateList(quantidade, listSize,
					elements, listaBaseNovosJogos);
			for (List<Integer> pj : pjs) {
				pj.addAll(elements);
				preJogos.add(pj);
			}

			preJogos.removeAll(listaJogosCorrentes);
			preJogos.removeAll(listaJogosAtuais);
			preJogos.removeAll(resultados);
			preJogos.removeAll(somenteNovos);
			preJogos.removeAll(full);

			try {

				String destino = config.getFullPath();
				String params = config.getFullPathParams();

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

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(listaJogosCorrentes, preJogos);

				preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

				// if(random.nextBoolean()) {
				// preJogos = aplicarFiltroUltimoSorteio(ultimoSorteio,
				// preJogos, 9, 12);
				// }

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

				config.setPontuacaoExtra(!config.isPontuacaoExtra());

				if (config.isPontuacaoExtra()) {
					for (JogoAb meuJogo : jogosLM) {

						Integer before = meuJogo.getPontuacao();
						if (!getRandomBoolean() && getRandomBoolean()) {
							pontuador.pontuarMap(meuJogo, mapResultado, 25, 2);
						}
						if (!(getRandomBoolean() && getRandomBoolean())) {
							pontuador.pontuarMap(meuJogo, mapAtraso, 25, 2);
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
					System.out.println(
							la.stream().map(Object::toString).collect(Collectors.joining("\t"))
							+ " | " + jogo.stream().map(Object::toString)
							.collect(Collectors.joining("\t")));
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
				if (getRandomBoolean() && random.nextBoolean()) {
					Collections.shuffle(jogosLM);
				} else {
					Collections.sort(jogosLM);
				}

				String output = base + "ind/" + currDate + "/LFI" + dt + ".txt";
				saveDefault2(jogos, output);
				saveDefault2(somenteNovos, sn);
				saveDefault2(somenteNovos, snBkp);
				saveDefault2(somenteNovos, outputPath);

				saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
				saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

				List<List<Integer>> statC = gc(listaJogosCorrentes, defaultSize);
				String mc = config.getFrequencia(statC);
				ArquivoUtil.save(mc, CaminhoResultados.LOTOFACIL.getBasePath() + "correntes.txt");

				List<List<Integer>> statA = gc(listaJogosAtuais, defaultSize);
				String ma = config.getFrequencia(statA);
				ArquivoUtil.save(ma, CaminhoResultados.LOTOFACIL.getBasePath() + "atuais.txt");

				System.out.println("See: " + outputPath);

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
	}
}
