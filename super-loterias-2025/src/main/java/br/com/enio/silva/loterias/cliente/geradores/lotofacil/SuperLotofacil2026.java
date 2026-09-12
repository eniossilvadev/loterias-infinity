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
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.*;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SuperLotofacil2026 extends GerarJogosLotofacil2020Ab {

    // Configurações Estáticas
//    public static final LotofacilConfig POWER_CONFIG = new LotofacilConfig15();
     public static final LotofacilConfig POWER_CONFIG = new LotofacilConfig16();
//    public static final LotofacilConfig POWER_CONFIG = new LotofacilConfig17();
    public static final int QUANTIDADE_DE_JOGOS = 1;
    public static final Boolean SHIFT = true;
    public static final Boolean POSITIONAL = true;
    public static final int MODULADOR = 1;
    private static final int DEFAULT_SIZE = 15;
    private static final int QUANTIDADE_GERACAO = 360_000;

    // Controle de Intervalo
    public static int lastInicio = 150;
    public static int lastFim = 900;
    public static String sep = ",";

    // Otimização: Random Thread-Safe
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public static List<List<Integer>> execute(final int times, final int defaultSize) throws IOException {
        return execute(times, defaultSize, null);
    }

    public static List<List<Integer>> execute(final int times, final int defaultSize, Config confIgnored) throws IOException {
        long startTime = System.currentTimeMillis();

        LotofacilConfig config = POWER_CONFIG;
        SaveLotofacil saveLotofacil = new SaveLotofacil();

        // ... (Carregamentos iniciais mantidos iguais) ...
        System.out.println("Carregando bases de dados...");
        List<List<Integer>> resultados = config.getTodosResultados();
        normalizarListas(resultados);

        String pathAtual = config.getCaminhoJogoAtual();
        String pathCorrente = config.getCaminhoJogoCorrente();

        List<List<Integer>> fullBase = LotoUtils.getAll(saveLotofacil.getCurr(), defaultSize);
        List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(pathAtual);

        // Inicialização segura
        if (atual.isEmpty()) {
            if (!fullBase.isEmpty()) {
                atual = new ArrayList<>(fullBase);
            } else {
                List<Integer> list = IntStream.rangeClosed(1, 25).boxed().collect(Collectors.toList());
                Collections.shuffle(list);
                atual.add(new ArrayList<>(list.subList(0, 15)));
            }
        }

        List<List<Integer>> listaJogosAtuais = new ArrayList<>(fullBase);
        List<List<Integer>> listaJogosCorrentes = new ArrayList<>(fullBase);
        Set<List<Integer>> somenteNovosSet = new HashSet<>();

        realizarBackupInicial(saveLotofacil.getConfig(), listaJogosCorrentes, listaJogosAtuais);

        Set<List<Integer>> bloqueados = new HashSet<>(resultados.size() + fullBase.size() + 5000);
        bloqueados.addAll(resultados);
        bloqueados.addAll(fullBase);
        bloqueados.addAll(atual);
        bloqueados.addAll(listaJogosCorrentes);

        List<List<Integer>> inicioShuffle = new ArrayList<>(atual);
        Collections.shuffle(inicioShuffle);
        List<Integer> la = inicioShuffle.isEmpty() ? new ArrayList<>() : inicioShuffle.get(inicioShuffle.size() / 2);

        String currDate = DateUtils.getCurrentDefaultDate();
        String outputBase = saveLotofacil.getConfig() + "ind/" + currDate + "/LFI";
        String snPath = saveLotofacil.getConfig() + "\\LSN.txt";

        // --- CORREÇÃO DO LOOP DE CONTROLE ---
        int maxRetries = times * 100; // Dá mais fôlego para tentar achar os jogos
        int currentTry = 0;

        // AQUI: Enquanto não tiver a quantidade desejada ('times'), continua tentando
        while (somenteNovosSet.size() < times && currentTry < maxRetries) {
            currentTry++;

            try {
                // Log apenas para acompanhar visualmente
                if (currentTry % 10 == 0 || somenteNovosSet.size() % 10 == 0) {
                    System.out.println("Gerados: " + somenteNovosSet.size() + "/" + times + " (Tentativa " + currentTry + ")");
                }

                boolean b1 = RANDOM.nextBoolean();

                List<Integer> listaBaseNovosJogos = gerarListaBase(la);
                List<Integer> elements = calcularElementosPorFrequencia(
                        b1 ? listaJogosAtuais : listaJogosCorrentes,
                        listaBaseNovosJogos,
                        somenteNovosSet.size() // Usa o tamanho real como semente
                );

                if (la != null && elements != null) {
                    la.removeAll(elements);
                }

                List<List<Integer>> preJogos = QuadrantesLotofacil.generateList(
                        QUANTIDADE_GERACAO,
                        config.getNrosApostados() - (elements != null ? elements.size() : 0),
                        elements,
                        listaBaseNovosJogos
                );

                normalizarListas(preJogos);
                bloqueados.addAll(somenteNovosSet);

                final List<Integer> ultimoSorteio = resultados.get(resultados.size() - 1);

                List<List<Integer>> candidatosFiltrados = preJogos.parallelStream()
                        .filter(jogo -> jogo.size() == config.getNrosApostados()) // Também adicionei a proteção de tamanho aqui
                        .filter(jogo -> !bloqueados.contains(jogo))
                        .filter(jogo -> !RANDOM.nextBoolean() || filtrarUltimoSorteio(jogo, ultimoSorteio))
                        .collect(Collectors.toList());

                if (candidatosFiltrados.isEmpty()) {
                    continue;
                }

                // Lógica de pontuação mantida
                int rangeFim = Math.min(lastFim, resultados.size());
                int ultimosQtd = RANDOM.nextInt(Math.min(lastInicio, rangeFim), rangeFim);

                List<List<Integer>> ultimosResultadosTest = resultados.subList(
                        Math.max(0, resultados.size() - ultimosQtd),
                        resultados.size()
                );
                List<List<Integer>> ultimosResultadosReverso = new ArrayList<>(ultimosResultadosTest);
                Collections.reverse(ultimosResultadosReverso);

                Pontuador pontuador = config.getPontuador();

                List<JogoLotofacil> jogosRankeados = candidatosFiltrados.parallelStream()
                        .map(JogoLotofacil::new)
                        .peek(jogo -> pontuador.pontuar(ultimosResultadosReverso, jogo))
                        .sorted()
                        .limit(config.getNrosJogos())
                        .collect(Collectors.toList());

                if (!jogosRankeados.isEmpty()) {
                    // Adiciona ao acumulador
                    List<List<Integer>> novosJogos = jogosRankeados.stream()
                            .map(JogoAb::getNumerosAsList)
                            .collect(Collectors.toList());

                    int antes = somenteNovosSet.size();
                    somenteNovosSet.addAll(novosJogos);
                    int depois = somenteNovosSet.size();

                    // Se realmente adicionou algo novo, salva e prossegue
                    if (depois > antes) {
                        exibirMelhoresJogos(jogosRankeados);
                        String arquivoSaida = outputBase + DateUtils.getCurrentDefaultDateTime() + ".txt";
                        saveDefault2(novosJogos, arquivoSaida);

                        listaJogosCorrentes.addAll(novosJogos);
                        listaJogosAtuais.addAll(novosJogos);
                        la = new ArrayList<>(novosJogos.get(0));

                        saveDefault2(new ArrayList<>(somenteNovosSet), snPath);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        saveDefault(listaJogosAtuais, pathAtual, defaultSize);
        saveDefault(listaJogosCorrentes, pathCorrente, defaultSize);

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("\nProcessamento Concluído em: " + (duration / 1000) + "s");
        System.out.println("Total de jogos gerados: " + somenteNovosSet.size());

        // Retorna lista limitada ao solicitado, caso tenha passado um pouco
        List<List<Integer>> retornoFinal = new ArrayList<>(somenteNovosSet);
        if(retornoFinal.size() > times) {
            return retornoFinal.subList(0, times);
        }
        return retornoFinal;
    }

    // --- Métodos Auxiliares de Lógica de Negócio ---

    private static List<Integer> gerarListaBase(List<Integer> la) {
        List<Integer> lista = CombinationUtils.gerarListaSimples(1, 25);
        if (la != null && !la.isEmpty()) {
            lista.removeAll(la);
        } else {
            Collections.shuffle(lista);
            lista = lista.subList(0, 10);
        }
        return lista;
    }

    private static List<Integer> calcularElementosPorFrequencia(List<List<Integer>> fonte, List<Integer> baseExclusao, int iteracao) {
        List<Integer> allFlat = fonte.parallelStream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Map<Integer, Integer> mapFreq = MapUtil.getMapFrequencia(allFlat, 1, 25);
        mapFreq = MapUtil.sortByValue2(mapFreq);
        mapFreq = MapUtil.removeAll(mapFreq, baseExclusao);

        List<Integer> elements = MapUtil.getElements(mapFreq, (iteracao % MODULADOR) + 1);

        if (elements == null || elements.isEmpty()) {
            List<Integer> range = ListaUtils.getListaRange(1, 25);
            Collections.shuffle(range);
            elements = range.subList(0, (iteracao % MODULADOR) + 1);
        }
        return elements;
    }

    private static boolean filtrarUltimoSorteio(List<Integer> jogo, List<Integer> ultimoSorteio) {
        // Lógica replicada do original: manter jogos que tenham entre 9 e 12 repetições do último sorteio
        // Isso é padrão "ouro" da Lotofácil
        long hits = jogo.stream().filter(ultimoSorteio::contains).count();
        return hits >= 9 && hits <= 12;
    }

    private static void realizarBackupInicial(String basePath, List<List<Integer>> correntes, List<List<Integer>> atuais) throws IOException {
        String agora = LocalDateTime.now().toString().replaceAll("[^0-9]", "");
        String pathBkpCorrentes = basePath + "/bkp/bkp_" + agora + "_1.txt";
        String pathBkpAtuais = basePath + "/bkp/bkp_" + agora + "_2.txt";
        ArquivoUtil.saveLists(correntes, pathBkpCorrentes, sep, 2);
        ArquivoUtil.saveLists(atuais, pathBkpAtuais, sep, 2);
    }

    private static void exibirMelhoresJogos(List<JogoLotofacil> jogos) {
        System.out.println("--- Melhores Jogos do Lote ---");
        int c = 1;
        for (JogoLotofacil jogo : jogos) {
            System.out.printf("#%d Pontos: %d | %s%n", c++, jogo.getPontuacao(), jogo.getNumerosAsList());
        }
    }

    private static void normalizarListas(List<List<Integer>> listas) {
        if (listas == null) return;
        // Ordena cada sublista para garantir que [1,2] seja igual a [2,1] no HashSet
        listas.parallelStream().forEach(Collections::sort);
    }

    // --- Configuração Dinâmica (Mantida do original) ---
    private static LotofacilConfig getConfiguration(final int numero) {
        final int num = numero % 9;
        if (num == 4) {
            return new LotofacilConfig15_6_Invertido();
        } else if (num == 5 || num == 8) {
            return new LotofacilConfig15_6();
        }
        return new LotofacilConfig15_5();
    }

    // --- MAIN ---
    public static void main(String[] args) throws IOException {

        long inicio = System.currentTimeMillis();

        System.out.println("Iniciando SuperLotofacil2025 Optimized...");

        final int quantidade = QUANTIDADE_DE_JOGOS;
        final int max = 25;
        final int size = DEFAULT_SIZE;

        String currDate = DateUtils.getCurrentDefaultDate();
        String currDateTime = DateUtils.getCurrentDefaultDateTime();
        String basePath = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\";
        String basePathName = basePath + "01_" + currDate + "_";
        String finalCleanListPath = basePathName + currDateTime + ".txt";

        // Carrega Lista de Remoção (Jogos antigos/premiados)
        // Set para performance O(1)
        Set<List<Integer>> listaRemover = new HashSet<>(ListaUtils.getAll(basePath, size));
        normalizarListas(new ArrayList<>(listaRemover));

        // Executa Lógica Principal
        List<List<Integer>> mainList = execute(quantidade, size, null);
        print(mainList, "Main Generated List");

        final int totalMainList = mainList.size();
        int totalPositional = mainList.size();
        int totalShift = mainList.size();

        List<List<Integer>> finalList = new ArrayList<>(mainList);

        // Pós-processamento (Positional / Shift)
        if (POSITIONAL) {
            List<List<Integer>> positionalList = PositionalReplacement.getPositionalReplacement(mainList, max);
            print(positionalList, "Positional Variations");
            finalList.addAll(positionalList);

            totalPositional = finalList.size();
        }

        if (SHIFT) {
            List<List<Integer>> shiftedList = ShiftBy.getShifted(mainList, max);
            print(shiftedList, "Shift Variations");
            finalList.addAll(shiftedList);

            totalShift = finalList.size();
        }

        // Limpeza Final
        normalizarListas(finalList);

        // Remove duplicatas internas e remove jogos da lista de bloqueio
        Set<List<Integer>> cleanSet = new HashSet<>(finalList);
        cleanSet.removeAll(listaRemover);

        List<List<Integer>> cleanList = new ArrayList<>(cleanSet);
        Collections.sort(cleanList, new ListOfListComparator());

        print(cleanList, "Final Clean List");

        // Salva Resultado Final
        ArquivoUtil.saveLists(cleanList, finalCleanListPath, sep, 2);
        System.out.println("Arquivo final salvo em: " + finalCleanListPath);

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

        System.out.printf("Quantidades: %d -> %d -> %d", totalMainList, totalPositional, totalShift);
    }
}