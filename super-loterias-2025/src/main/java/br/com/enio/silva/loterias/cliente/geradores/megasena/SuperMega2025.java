package br.com.enio.silva.loterias.cliente.geradores.megasena;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.cliente.megasena.GerarJogosMegaLoopAb;
import br.com.enio.silva.loterias.commons.SaveMegaSena;
import br.com.enio.silva.loterias.commons.SaveMegaSenaEspecial;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.*;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SuperMega2025 extends GerarJogosMegaLoopAb {


	// Constantes
	public static final int QUANTIDADE_DE_JOGOS = 20;
	private static final int TAMANHO_DO_JOGO = 6;
	private static final int QUANTIDADE_INICIAL =100_000;
	private static final int DEFAULT_SIZE = 6;

	public static final int TAM_PADRAO_EXCLUIR = 13;

	// Configurações
	public static final Boolean SHIFT = true;
	public static final Boolean POSITIONAL = true;

	public static String sep = "\t";
	private static final Random RAND = new Random();

	public static List<List<Integer>> execute(final int totalDeJogos) throws IOException {
		List<List<Integer>> retorno = new ArrayList<>();
		SaveMegaSena saveMegaSena = new SaveMegaSena();

		String basePath = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";
		String currDate = DateUtils.getCurrentDefaultDate();
		String currDateTime = DateUtils.getCurrentDefaultDateTime();

		// Carga Inicial
		List<List<Integer>> fullBase = LotoUtils.getAll(saveMegaSena.getCurr(), DEFAULT_SIZE);
		MegaSenaConfigAb config = getConfigBySize(TAMANHO_DO_JOGO);
		List<List<Integer>> resultados = config.getTodosResultados();

		normalizarListas(fullBase);
		normalizarListas(resultados);

		List<Integer> theList = List.of();
		Set<Integer> listaInclFixa = new HashSet<>();
		Set<List<Integer>> somenteNovosSet = new HashSet<>();

		List<Integer> last = new ArrayList<>();
		List<Integer> lasts = new ArrayList<>();

		String sn = basePath + "SNMS.txt";
		String snBkp = basePath + "novos/" + currDateTime + "_snms.txt";
		theSep = ","; // Atualiza estático da superclasse

		int magicNumber = RAND.nextInt(1000);

		for (int count = 0; count < totalDeJogos; count++) {
			config = getConfigBySize(TAMANHO_DO_JOGO);
			config.setQttInicial(QUANTIDADE_INICIAL);
			int tamanhoIncluirLista = 1 + count % 3;

			try {
				System.out.println("Magic Number: " + magicNumber);
				magicNumber++;

				Set<Integer> listaIncl = prepararListaInclusao(listaInclFixa, theList, magicNumber);

				String output = basePath + "ind/" + currDate + "/" + config.getDefaultName()
						+ DateUtils.getCurrentDefaultDateTime() + ".txt";

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				processarJogosAtuais(config, listaIncl, tamanhoIncluirLista);
				Set<Integer> listaExcl = gerarListaExclusao(config, listaIncl, last, lasts);

				if (!listaIncl.isEmpty()) {
					config.setPre(listaIncl.toArray(new Integer[0]));
				}
				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Collections.emptyList());

				ajustarQuantidadeInicial(config, listaIncl);

				// GERAÇÃO E FILTRAGEM PARALELA
				List<List<Integer>> preJogos = gerarEFiltrarJogos(config, fullBase, somenteNovosSet, resultados);

				if (preJogos != null && !preJogos.isEmpty()) {
					List<List<Integer>> jogosProcessados = processarResultadosFinais(
							config, preJogos, resultados, last, lasts);

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogosProcessados) {
						System.out.println(jogo + "\t" + listaIncl + "\t" + listaExcl);
					}

					somenteNovosSet.addAll(jogosProcessados);
					List<List<Integer>> novosParaSalvar = new ArrayList<>(somenteNovosSet);

					saveDefault2(jogosProcessados, output);
					saveDefault2(novosParaSalvar, sn);
					saveDefault2(novosParaSalvar, snBkp);

					retorno = new ArrayList<>(novosParaSalvar);
				} else {
					lidarComFalhaDeFiltro();
					count--;
				}

			} catch (Exception e) {
				e.printStackTrace();
				count--;
			}
		}
		return retorno;
	}

	// --- Lógica Central Otimizada (Passo 3) ---

	private static List<List<Integer>> gerarEFiltrarJogos(MegaSenaConfigAb config,
														  List<List<Integer>> fullBase, Set<List<Integer>> somenteNovosSet, List<List<Integer>> resultados) {

		// 1. Geração Combinatória (Base)
		List<List<Integer>> preJogos = config.getPreJogos();
		preJogos = ListUtil.completarExcluirIncluir(preJogos, config.getNrosApostados(),
				config.getMaxNum(), config.getExcluir(), config.getIncluir());

		normalizarListas(preJogos);

		// 2. Preparar Histórico para Busca Rápida (O(1))
		Set<List<Integer>> historicoCompleto = new HashSet<>(fullBase.size() + somenteNovosSet.size() + resultados.size());
		historicoCompleto.addAll(fullBase);
		historicoCompleto.addAll(somenteNovosSet);
		historicoCompleto.addAll(resultados);

		Predicate<List<Integer>> filtroNaoRepetido = jogo -> !historicoCompleto.contains(jogo);

		// 3. Pipeline de Filtragem em Paralelo
		// Substituímos as classes Filtro* por Predicates nativos
		return preJogos.parallelStream()
				.filter(FiltroDivide.filtroDivisaoOtimizado(3, 60))
				.filter(filtroNaoRepetido) // FiltroRemoverIntersecao
				.filter(FiltroMaxConsecutivos.filtroMaxConsecutivos(3))                  // FiltroMaxConsecutivos
				.filter(FiltroMaximoLinhas.filtroMaximoLinhas(6, 10,3))                     // FiltroMaximoLinhas (max 3 por linha)
				.collect(Collectors.toList());
	}

	// --- Métodos de Suporte (Inalterados ou Ajustados para Fluxo) ---

	private static void normalizarListas(List<List<Integer>> listas) {
		listas.parallelStream().forEach(Collections::sort); // Parallel sort se a lista for gigante
	}

	private static Set<Integer> prepararListaInclusao(Set<Integer> fixa, List<Integer> theList, int magicNumber) {
		Set<Integer> listaIncl = new HashSet<>(fixa);
		if (theList != null && !theList.isEmpty()) {
			listaIncl.add(theList.get(magicNumber % theList.size()));
		}
		return listaIncl;
	}

	private static void processarJogosAtuais(MegaSenaConfigAb config, Set<Integer> listaIncl, int tamanhoIncluir) throws IOException {
		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(config.getCaminhoJogoAtual());
		List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1, config.getMaxNum());
		if (!listaOrdenada.isEmpty()) {
			listaIncl.addAll(listaOrdenada.subList(0, Math.min(listaOrdenada.size(), tamanhoIncluir)));
		}
	}

	private static Set<Integer> gerarListaExclusao(MegaSenaConfigAb config, Set<Integer> listaIncl,
												   List<Integer> last, List<Integer> lasts) throws IOException {
		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(config.getCaminhoJogoAtual());
		List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1, config.getMaxNum());
		Collections.reverse(listaOrdenada);

		Set<Integer> listaExcl = new HashSet<>();
		if (!listaOrdenada.isEmpty()) {
			listaExcl.addAll(listaOrdenada.subList(0, Math.min(listaOrdenada.size(), TAM_PADRAO_EXCLUIR)));
		}
		listaExcl.removeAll(listaIncl);

		if (last != null && !last.isEmpty()) {
			Collections.shuffle(lasts);
			if (lasts.size() > 11) {
				List<Integer> sub = new ArrayList<>(lasts.subList(0, 8));
				lasts.clear();
				lasts.addAll(sub);
			}
			lasts.addAll(last);
			Set<Integer> uniqueLasts = new HashSet<>(lasts);
			lasts.clear();
			lasts.addAll(uniqueLasts);
			uniqueLasts.removeAll(listaIncl);
			listaExcl.addAll(uniqueLasts);
		}
		return listaExcl;
	}

	private static void ajustarQuantidadeInicial(MegaSenaConfigAb config, Set<Integer> listaIncl) {
		int n = config.getMaxNum() - TAM_PADRAO_EXCLUIR;
		int r = config.getNrosApostados() - listaIncl.size();
		if (r >= 0) {
			long totalCombinacoes = CombinationUtils.calcNumberOfCombinations(n, r).longValue();
			if (config.getQttInicial() > totalCombinacoes && totalCombinacoes > 0) {
				config.setQttInicial((int) totalCombinacoes);
			}
		}
	}

	private static List<List<Integer>> processarResultadosFinais(MegaSenaConfigAb config,
																 List<List<Integer>> preJogos, List<List<Integer>> resultados,
																 List<Integer> last, List<Integer> lasts) {

		// Uso de Stream Paralelo para criar objetos JogoMega (pode ser custoso)
		List<JogoMega> jogosLM = preJogos.parallelStream()
				.map(JogoMega::new)
				.collect(Collectors.toList());

		// Pontuação (Iterativo pois o pontuador pode não ser thread-safe)
		for (JogoAb meuJogo : jogosLM) {
			config.getPontuador().pontuar(resultados, meuJogo);
		}

		Collections.sort(jogosLM);

		// Sublista antes de processar resto
		int qtdJogos = Math.min(jogosLM.size(), config.getNrosJogos());
		List<JogoMega> melhoresJogos = jogosLM.subList(0, qtdJogos);

		List<List<Integer>> jogosFinais = new ArrayList<>();

		// Atualiza estado (last/lasts) de forma serial para manter consistência
		for (JogoAb jj : melhoresJogos) {
			List<Integer> novo = jj.getNumerosAsList();
			jogosFinais.add(novo);
			last.clear();
			last.addAll(novo);
			lasts.addAll(last);
		}

		// Limpeza final
		for (List<Integer> jogo : jogosFinais) {
			jogo.removeAll(Collections.singleton(0));
			Collections.sort(jogo);
		}

		return jogosFinais;
	}

	private static void lidarComFalhaDeFiltro() throws InterruptedException {
		for (int i = 0; i < 6; i++) {
			System.out.println("Não há resultados com os filtros aplicados");
			Thread.sleep(500);
		}
	}

	public static void main(String[] args) throws IOException {
		long inicio = System.currentTimeMillis();

		SaveMegaSenaEspecial saveMegaSenaEspecial = new SaveMegaSenaEspecial();
		String basePath = saveMegaSenaEspecial.getCurr();
		String finalCleanListPath = basePath + "01_" + DateUtils.getCurrentDefaultDate() + "_mega_sorte_" + DateUtils.getCurrentDefaultDateTime() + ".txt";

		Set<List<Integer>> listaRemoverSet = new HashSet<>(ListaUtils.getAll(basePath, DEFAULT_SIZE));
		normalizarListas(new ArrayList<>(listaRemoverSet));

		List<List<Integer>> mainList = execute(QUANTIDADE_DE_JOGOS);
		print(mainList, "Main");

		List<List<Integer>> finalList = new ArrayList<>(mainList);

		if (POSITIONAL) {
			List<List<Integer>> positionalList = PositionalReplacement.getPositionalReplacement(mainList, 60);
			print(positionalList, "Positional");
			finalList.addAll(positionalList);
		}

		if (SHIFT) {
			List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, 60);
			print(shiftedList, "Shifed");
			finalList.addAll(shiftedList);
		}

		normalizarListas(finalList);
		finalList.sort(new ListOfListComparator());

		Set<List<Integer>> cleanSet = new LinkedHashSet<>(finalList);
		cleanSet.removeAll(listaRemoverSet);

		print(new ArrayList<>(cleanSet), "Clean List");

		ArquivoUtil.saveLists(new ArrayList<>(cleanSet), finalCleanListPath, sep, 2);

		// --- Cálculo e Exibição do Tempo ---
		long fim = System.currentTimeMillis(); // <--- Fim da contagem
		long duracao = fim - inicio;

		// Formatação simples para legibilidade
		long minutos = (duracao / 1000) / 60;
		long segundos = (duracao / 1000) % 60;

		System.out.println("\n------------------------------------------------");
		System.out.println("Processamento Concluído!");
		System.out.printf("Tempo Total: %d min %d seg (%d ms)%n", minutos, segundos, duracao);
		System.out.println("------------------------------------------------");
	}
}