package br.com.enio.silva.loterias.cliente.geradores.quina;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.quina.QuinaConfig10;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesQuina;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Gerador de jogos da Quina com quadrantes.
 * Refatorado seguindo padrões do SuperMega2025.
 */
public class QuinaQuadranteBaseN extends Base {

	// ==================== CONSTANTES ====================

	public static final int QUANTIDADE_DE_JOGOS = 2;
	private static final int QUANTIDADE_INICIAL = 150_000;
	private static final int TAM_PADRAO_EXCLUIR = 15;
	private static final int DEFAULT_SIZE = 5;
	private static final int MAX_LISTA_BASE = 2;
	private static final int MAX_NUM = 80;

	// Configurações de transformação
	public static final int SHIFT_COUNT = 0;
	public static final int POSITIONAL_COUNT = 0;

	// Caminhos
	private static final String BASE_PATH = "C:\\loterias\\gerador-apostas\\quina\\meus-jogos\\";
	private static final String CONFIG_PATH = "C:\\loterias\\gerador-apostas\\quina\\config\\";

	// Utilitários
	private static final Random RAND = new Random();
	private static final String SEP = ",";

	// Strings de formatação
	private static final String REPEATED_50 = "-".repeat(50);
	private static final String REPEATED_20 = "-".repeat(20);

	// Configuração do jogo
	//public static final QuinaConfigAb QUINA_CONFIG = new QuinaConfig10();
	public static final QuinaConfigAb QUINA_CONFIG = new QuinaConfig5();

	// ==================== MÉTODO PRINCIPAL DE EXECUÇÃO ====================

	public static List<List<Integer>> execute(final int totalDeJogos) throws IOException {
		return execute(totalDeJogos, MAX_LISTA_BASE, DEFAULT_SIZE, QUANTIDADE_INICIAL, TAM_PADRAO_EXCLUIR);
	}

	public static List<List<Integer>> execute(final int totalDeJogos,
											  final int maxListaBase,
											  final int defaultSize,
											  final int qttInicialFixo,
											  final int tamPadraoExcluir) throws IOException {

		List<List<Integer>> retorno = new ArrayList<>();

		// Configuração de ciclo de últimos jogos
		int[] ultimosJogos = {0, 50, 0, 100, 0, 260, 0, 500, 0, 1000};

		List<Integer> theList =  Collections.emptyList();
		int tamanhoIncluirLista = 0; // theList.size();
		Set<Integer> listaInclFixa = new HashSet<>();

//		List<Integer> theList =  Collections.emptyList();
//		int tamanhoIncluirLista = 0; // theList.size();
//		Set<Integer> listaInclFixa = new HashSet<>(List.of(22,28,46,55,64,77,79));

//		List<Integer> theList =  List.of(5,7,9,13,17,25,33,62,71,76);
//		int tamanhoIncluirLista = 7;
//		Set<Integer> listaInclFixa = new HashSet<>();

		// Carga inicial de dados
		String pathListaDeJogos = CONFIG_PATH + "meu_jogos.txt";
		String pathListaJogosCorrentes = CONFIG_PATH + "novos_jogos.txt";

		List<List<Integer>> listaJogosAtuais = ArquivoUtil.obterLinhasComoListasUnique(pathListaDeJogos);
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		List<List<Integer>> full = LotoUtils.getAll(BASE_PATH, defaultSize);
		listaJogosAtuais.addAll(full);
		listaJogosCorrentes.addAll(full);

		normalizarListas(listaJogosAtuais);
		normalizarListas(listaJogosCorrentes);

		System.out.println("Jogos atuais carregados: " + listaJogosAtuais.size());

		// Carregar resultados oficiais
		String pathResultados = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(pathResultados);
		normalizarListas(resultados);

		listaJogosCorrentes.removeAll(resultados);

		// Backup
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();
		String pathBkpCorrentes = CONFIG_PATH + "bkp/" + currDate + "/bkp_" + currDateTime + "_c.txt";
		String pathBkpAtuais = CONFIG_PATH + "bkp/" + currDate + "/bkp_" + currDateTime + "_a.txt";

		saveDefault(listaJogosCorrentes, pathBkpCorrentes, defaultSize);
		saveDefault(listaJogosAtuais, pathBkpAtuais, defaultSize);

		// Controle de novos jogos
		String snPath = CONFIG_PATH + "SNQ.txt";
		Set<List<Integer>> somenteNovosSet = new HashSet<>();

		int magicNumber = RAND.nextInt(1000);

		saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
		saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);

		// ==================== LOOP PRINCIPAL ====================

		for (int count = 0; count < totalDeJogos; count++) {

			List<Integer> listaBase = ListaUtils.getElements(1, MAX_NUM, maxListaBase);
			QuinaConfigAb config = QUINA_CONFIG;

			List<List<Integer>> ultimosResultados = prepararUltimosResultados(config, ultimosJogos, count);

			try {
				System.out.println(REPEATED_50);
				System.out.println(REPEATED_20 + " Jogo " + (count + 1) + " " + REPEATED_20);
				System.out.println("Magic Number: " + magicNumber);
				magicNumber++;

				// Preparar listas de inclusão e exclusão
				Set<Integer> listaIncl = getListaFixa(listaInclFixa, theList, tamanhoIncluirLista);

				Set<Integer> listaExcl = gerarListaExclusao(config, listaIncl, listaBase, tamPadraoExcluir);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				// Configurar pré-requisitos
				if (!listaIncl.isEmpty()) {
					config.setPre(listaIncl.toArray(new Integer[0]));
				}

				config.setQttInicial(qttInicialFixo);

				String output = CONFIG_PATH + "individual\\" + currDate + "\\" + config.getDefaultName()
						+ "_" + currDateTime + ".txt";

				// Preparar histórico para lookup O(1)
				Set<List<Integer>> historicoCompleto = new HashSet<>();
				historicoCompleto.addAll(listaJogosAtuais);
				historicoCompleto.addAll(listaJogosCorrentes);
				historicoCompleto.addAll(somenteNovosSet);
				historicoCompleto.addAll(resultados);

				List<Integer> inclusao = listaIncl != null && !listaIncl.isEmpty() ? new ArrayList<>(listaIncl): listaBase;

				// Gerar e filtrar jogos
				List<List<Integer>> preJogos = gerarEFiltrarJogos(
						config, qttInicialFixo, inclusao, maxListaBase,
						new ArrayList<>(listaExcl), historicoCompleto);

				if (preJogos != null && !preJogos.isEmpty()) {

					List<List<Integer>> jogosProcessados = processarResultadosFinais(
							config, preJogos, ultimosResultados);

					System.out.println("Ordenado:");
					for (List<Integer> jogo : jogosProcessados) {
						System.out.println(jogo + "\t" + listaIncl + "\t" + listaExcl);
					}

					// Atualizar listas
					somenteNovosSet.addAll(jogosProcessados);
					listaJogosAtuais.addAll(jogosProcessados);
					listaJogosCorrentes.addAll(jogosProcessados);

					List<List<Integer>> novosParaSalvar = new ArrayList<>(somenteNovosSet);

					// Salvar resultados
					saveDefault(listaJogosAtuais, pathListaDeJogos, defaultSize);
					saveDefault(listaJogosCorrentes, pathListaJogosCorrentes, defaultSize);
					ArquivoUtil.saveLists(jogosProcessados, output, SEP, 2);
					ArquivoUtil.saveLists(novosParaSalvar, snPath, SEP, 2);

					retorno = new ArrayList<>(novosParaSalvar);

				} else {
					lidarComFalhaDeFiltro();
					count--;
				}

			} catch (Exception e) {
				e.printStackTrace();
				count--;

				// Salvar estado atual em caso de erro
				ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, SEP, 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, SEP, 2);
				ArquivoUtil.saveLists(new ArrayList<>(somenteNovosSet), snPath, SEP, 2);
			}
		}

		return retorno;
	}

	private static Set<Integer> getListaFixa(Set<Integer> listaInclFixa, List<Integer> theList, int tamanhoIncluirLista) {
		if(listaInclFixa != null && !listaInclFixa.isEmpty()){
			return new HashSet<>(listaInclFixa);
		} else if(theList != null && !theList.isEmpty()){
			List<Integer> aux = new ArrayList<>(theList);
			Collections.shuffle(aux);
			return new HashSet<>(aux.subList(0, tamanhoIncluirLista));
		}
		return new HashSet<>();
	}

	// ==================== LÓGICA CENTRAL OTIMIZADA ====================

	private static List<List<Integer>> gerarEFiltrarJogos(QuinaConfigAb config,
														  int qttInicial,
														  List<Integer> listaBase,
														  int maxListaBase,
														  List<Integer> excluir,
														  Set<List<Integer>> historicoCompleto) {

		// 1. Geração via Quadrantes
		int tamanhoDoJogo = config.getNrosApostados();
		List<List<Integer>> preJogos = QuadrantesQuina.generateList(
				qttInicial, tamanhoDoJogo, excluir, listaBase, maxListaBase);

		normalizarListas(preJogos);

		// 2. Predicates para filtragem
		Predicate<List<Integer>> filtroNaoRepetido = jogo -> !historicoCompleto.contains(jogo);

		Predicate<List<Integer>> filtroListaBase = jogo -> {
			int count = 0;
			for (Integer num : jogo) {
				if (listaBase.contains(num)) {
					count++;
				}
			}
			return count >= maxListaBase;
		};

		// 3. Pipeline de filtragem paralela
		return preJogos.parallelStream()
				.filter(filtroNaoRepetido)
				.filter(FiltroMaxConsecutivos.filtroMaxConsecutivos(3))
				.filter(FiltroMaximoLinhas.filtroMaximoLinhas(5, 10, 4))
				.filter(filtroListaBase)
				.collect(Collectors.toList());
	}

	// ==================== MÉTODOS DE SUPORTE ====================

	private static void normalizarListas(List<List<Integer>> listas) {
		listas.parallelStream().forEach(Collections::sort);
	}

	private static List<List<Integer>> prepararUltimosResultados(QuinaConfigAb config,
																 int[] ultimosJogos,
																 int count) {
		List<List<Integer>> ultimosResultados = new ArrayList<>(config.getTodosResultados());
		Collections.reverse(ultimosResultados);

		int ultimos = ultimosJogos[count % ultimosJogos.length];
		ultimos = (ultimos <= 10 || ultimos > ultimosResultados.size() - 1)
				? ultimosResultados.size()
				: ultimos;

		return ultimosResultados.subList(0, ultimos);
	}

	private static Set<Integer> gerarListaExclusao(QuinaConfigAb config,
												   Set<Integer> listaIncl,
												   List<Integer> listaBase,
												   int tamPadraoExcluir) throws IOException {
		String jogosAtuais = config.getCaminhoJogoAtual();
		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);
		List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1, config.getMaxNum());

		Collections.reverse(listaOrdenada);
		listaOrdenada.removeAll(listaIncl);
		listaOrdenada.removeAll(listaBase);

		Set<Integer> listaExcl = new HashSet<>();
		if (!listaOrdenada.isEmpty()) {
			listaExcl.addAll(listaOrdenada.subList(0, Math.min(listaOrdenada.size(), tamPadraoExcluir)));
		}
		listaExcl.removeAll(listaIncl);

		return listaExcl;
	}

	private static List<List<Integer>> processarResultadosFinais(QuinaConfigAb config,
																 List<List<Integer>> preJogos,
																 List<List<Integer>> resultados) {
		// Criar objetos JogoQuina em paralelo
		List<JogoQuina> jogosLM = preJogos.parallelStream()
				.map(JogoQuina::new)
				.collect(Collectors.toList());

		// Pontuação (sequencial - pontuador pode não ser thread-safe)
		Pontuador pontuador = config.getPontuador();
		for (JogoAb meuJogo : jogosLM) {
			pontuador.pontuar(resultados, meuJogo);
		}

		// Ordenar e opcionalmente inverter
		Collections.sort(jogosLM);
		if (RAND.nextBoolean() && RAND.nextBoolean()) {
			Collections.reverse(jogosLM);
		}

		// Selecionar melhores jogos
		int qtdJogos = Math.min(jogosLM.size(), config.getNrosJogos());
		List<JogoQuina> melhoresJogos = jogosLM.subList(0, qtdJogos);

		// Converter para listas
		return melhoresJogos.stream()
				.map(JogoAb::getNumerosAsList)
				.peek(jogo -> {
					jogo.removeAll(Collections.singleton(0));
					Collections.sort(jogo);
				})
				.collect(Collectors.toList());
	}

	private static void lidarComFalhaDeFiltro() throws InterruptedException {
		for (int i = 0; i < 6; i++) {
			System.out.println("Não há resultados com os filtros aplicados");
			Thread.sleep(500);
		}
	}

	// ==================== MAIN ====================

	public static void main(String[] args) throws IOException {
		long inicio = System.currentTimeMillis();

		final int quantidade = QUANTIDADE_DE_JOGOS;

		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		String basePathName = BASE_PATH + "01_" + currDate + "_quina_da_sorte_";
		String finalListPath = basePathName + currDateTime + "_final_temp.txt";
		String finalCleanListPath = basePathName + currDateTime + ".txt";

		// Carregar lista para remoção de duplicatas
		Set<List<Integer>> listaRemoverSet = new HashSet<>(ListaUtils.getAll(BASE_PATH, QUINA_CONFIG.getDefaultSize()));

		// Executar geração principal
		List<List<Integer>> mainList = execute(quantidade);
		print(mainList, "Main");

		List<List<Integer>> finalList = new ArrayList<>(mainList);

		// Aplicar transformações opcionais
		for (int i = 0; i < POSITIONAL_COUNT; i++) {
			List<List<Integer>> positionalList = PositionalReplacement.getPositionalReplacement(mainList, MAX_NUM);
			print(positionalList, "Positional " + (i + 1));
			finalList.addAll(positionalList);
		}

		for (int i = 0; i < SHIFT_COUNT; i++) {
			List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, MAX_NUM);
			print(shiftedList, "Shifted " + (i + 1));
			finalList.addAll(shiftedList);
		}

		// Normalizar e ordenar
		normalizarListas(finalList);
		finalList.sort(new ListOfListComparator());
		print(finalList, "Final -> Sorted");

		// Limpar duplicatas usando LinkedHashSet (mantém ordem)
		Set<List<Integer>> cleanSet = new LinkedHashSet<>(finalList);
		cleanSet.removeAll(listaRemoverSet);

		List<List<Integer>> cleanList = new ArrayList<>(cleanSet);
		print(cleanList, "Clean List");

		// Salvar resultados
		ArquivoUtil.saveLists(finalList, finalListPath, theSep, 2);
		ArquivoUtil.saveLists(cleanList, finalCleanListPath, theSep, 2);

		// Métricas de tempo
		long fim = System.currentTimeMillis();
		long duracao = fim - inicio;
		long minutos = (duracao / 1000) / 60;
		long segundos = (duracao / 1000) % 60;

		System.out.println("\n" + REPEATED_50);
		System.out.println("Processamento Concluído!");
		System.out.printf("Tempo Total: %d min %d seg (%d ms)%n", minutos, segundos, duracao);
		System.out.printf("Jogos gerados: %d | Jogos únicos: %d%n", finalList.size(), cleanList.size());
		System.out.println(REPEATED_50);
	}
}