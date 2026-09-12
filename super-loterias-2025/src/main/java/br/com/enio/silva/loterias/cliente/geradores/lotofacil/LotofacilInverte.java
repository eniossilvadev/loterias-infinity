package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.Config;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_5;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_6;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_6_Invertido;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.diversos.QuadrantesLotofacil;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.DateUtils;
import br.com.enio.silva.loterias.util.ListaUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LotofacilInverte extends GerarJogosLotofacil2020Ab {

    // ==================== CONFIGURAÇÃO CENTRALIZADA ====================
    // Parâmetros de quantidade e limites
    public static final int QUANTIDADE_DE_JOGOS = 6;
    public static final int DEFAULT_SIZE = 15;
    public static final int QUANTIDADE_GERACAO = 300000;

    // Parâmetros de comportamento
    public static final Boolean USAR_SHIFT = false;
    public static final Boolean USAR_POSITIONAL = false;

    // Parâmetros de modulação e ajuste
    public static final int MODULADOR = 2;
    public static final int RANDOM_RANGE_MAX = 26;
    public static final int RANDOM_RANGE_MIN = 1;

    // Parâmetros de números da lotofácil
    public static final int PRIMEIRO_NUMERO = 1;
    public static final int ULTIMO_NUMERO = 25;
    public static final int TOTAL_NUMEROS = 25;

    // Parâmetros de processamento
    public static final int MOD_VERIFICACAO = 20;
    public static final int TAMANHO_SUBLIST_BASE = 10;
    public static final int TAMANHO_DECORACAO = 20;

    // Parâmetros de pontuação
    public static final int MINIMO_PONTOS_FILTRO = 9;
    public static final int MAXIMO_PONTOS_FILTRO = 12;
    public static final int MAPA_RESULTADO_LIMITE = 25;
    public static final int MAPA_RESULTADO_BONUS = 4;
    public static final int MAPA_ATRASO_LIMITE = 25;
    public static final int MAPA_ATRASO_BONUS = 3;

    // Parâmetros de arquivo e caminho
    public static final String CAMINHO_BASE = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\";
    public static final String CAMINHO_LSN = CAMINHO_BASE + "\\LSN.txt";

    // Parâmetros de separador e sufixos
    public static String SEPARADOR = ",";
    public static final String SUFIXO_PADRAO = "_shuffle";
    public static final String SUFIXO_ARQUIVO_1 = "_1.txt";
    public static final String SUFIXO_ARQUIVO_2 = "_2.txt";
    public static final String SUFIXO_FINAL_TEMP = "_final_temp.txt";
    public static final String SUFIXO_FINAL = ".txt";

    // Parâmetros de nomes de arquivos
    public static final String NOME_ARQUIVO_RESULT = "RESULT.txt";
    public static final String NOME_ARQUIVO_ULTIMA_VEZ = "ULTIMA_VEZ.txt";
    public static final String PREFIXO_ARQUIVO_LFI = "LFI";
    public static final String PREFIXO_ARQUIVO_INDEPENDENCIA = "01_";

    // Parâmetros de intervalo e sleeps
    public static final int INTERVALO_SLEEP_PEQUENO = 1;
    public static final int INTERVALO_SLEEP_MEDIO = 2;
    public static final int INTERVALO_LOG_MOD = 100;

    // Parâmetros de array dos últimos jogos
    public static final int[] ULTIMOS_JOGOS_PADRAO = { 0, 0, 26, 0, 0, 50, 0, 0, 100, 0, 0, 500, 0, 0, 0 };
    public static final int MINIMO_VALIDO_ULTIMOS = 10;

    // ===================================================================

    public static int ultimos = -1;

    public static List<List<Integer>> execute(final int times, final int defaultSize)
            throws IOException {
        return execute(times, defaultSize, null);
    }

    public static List<List<Integer>> execute(final int times, final int defaultSize, Config conf)
            throws IOException {

        int[] ultimosJogos = ULTIMOS_JOGOS_PADRAO.clone();

        List<List<Integer>> retorno = null;

        boolean change = conf == null;
        LotofacilConfig configuration = change ? getConfiguration() : (LotofacilConfig) conf;

        List<List<Integer>> resultados = configuration.getTodosResultados();

        String f = configuration.getCaminhoJogoAtual();
        List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

        System.out.println("Tamanho da lista de origem (sem repetiçoes): " + atual.size());

        String lm = configuration.getCaminhoJogoCorrente();

        List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);

        List<List<Integer>> erro = new ArrayList<>();

        List<List<Integer>> somenteNovos = new ArrayList<>();

        int count = 0;

        int magicNumber = random.nextInt(RANDOM_RANGE_MAX) + 1;

        String base = CAMINHO_BASE;
        String sn = CAMINHO_LSN;

        SaveLotofacil saveLotofacil = new SaveLotofacil();
        var full = LotoUtils.getAll(saveLotofacil.getCurr(), defaultSize);

        listaJogosAtuais = new ArrayList<>(full);
        listaJogosCorrentes = new ArrayList<>(full);


        List<List<Integer>> inicio = new ArrayList<>(atual.isEmpty() ? listaJogosCorrentes : atual);
        Collections.shuffle(inicio);

        List<Integer> la = inicio.get(inicio.size() / 2);

        String currDate = DateUtils.getCurrentDefaultDate();
        String currDateTime = DateUtils.getCurrentDefaultDateTime();

        String pathListaDeJogos = configuration.getCaminhoJogoAtual();
        String pathListaJogosCorrentes = configuration.getCaminhoJogoCorrente();

        beforeLotofacil(base, somenteNovos, defaultSize, currDate, currDateTime);

        List<Integer> ultimoSorteio = resultados.get(resultados.size() - 1);

        for (int i = 0; i < times; i++) {

            String dt = DateUtils.getCurrentDefaultDateTime();

            boolean b1 = random.nextBoolean();
            boolean b2 = random.nextBoolean();
            int num = Math.abs(random.nextInt());

            num = num % MOD_VERIFICACAO;

            sleep(INTERVALO_SLEEP_PEQUENO);

            count++;

            if (change) {
                configuration = getConfiguration(count);
            }

            List<Integer> listaBaseNovosJogos = CombinationUtils.gerarListaSimples(PRIMEIRO_NUMERO, ULTIMO_NUMERO);
            if (la != null && !la.isEmpty()) {
                listaBaseNovosJogos.removeAll(la);
            } else {
                Collections.shuffle(listaBaseNovosJogos);
                listaBaseNovosJogos = listaBaseNovosJogos.subList(0, TAMANHO_SUBLIST_BASE);
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

            Map<Integer, Integer> mapAtuais = MapUtil.getMapFrequencia(all, PRIMEIRO_NUMERO, ULTIMO_NUMERO);
            mapAtuais = MapUtil.sortByValue2(mapAtuais);
            System.out.println("Map Atuais: " + mapAtuais);
            mapAtuais = MapUtil.removeAll(mapAtuais, listaBaseNovosJogos);
            List<Integer> elements = MapUtil.getElements(mapAtuais, (i % MODULADOR) + 1);

            if (elements == null) {
                elements = new ArrayList<>();
            }

            if (elements.isEmpty()) {
                List<Integer> listaRange = ListaUtils.getListaRange(PRIMEIRO_NUMERO, ULTIMO_NUMERO);
                Collections.shuffle(listaRange);
                elements.addAll(listaRange.subList(0, (i % MODULADOR) + 1));
            }

            if (elements != null && !elements.isEmpty()) {
                la.removeAll(elements);
            }

            int completar = configuration.getNrosApostados() - listaBaseNovosJogos.size()
                    - elements.size();

            List<List<Integer>> preJogos = new ArrayList<>();

            System.out.println("completar (size): " + completar);
            System.out.println("Lista restante: " + la);
            System.out.println("Lista Base: " + listaBaseNovosJogos);
            System.out.println("Lista elements: " + elements);

            sleep(INTERVALO_SLEEP_PEQUENO);

            System.out.println("Gerando...");
            int listSize = configuration.getNrosApostados() - elements.size();
            List<List<Integer>> pjs = QuadrantesLotofacil.generateList(QUANTIDADE_GERACAO, listSize,
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

                ultimos = ultimosJogos[(count + magicNumber) % ultimosJogos.length];

                int ultimos = ultimosJogos[count % ultimosJogos.length];
                ultimos = ultimos <= MINIMO_VALIDO_ULTIMOS || ultimos > resultados.size() - 1 ? resultados.size()
                        : ultimos;

                String asterics = StringUtils.repeat('*', TAMANHO_DECORACAO);
                String spaces = StringUtils.repeat(' ', TAMANHO_DECORACAO);
                System.out.println(asterics + spaces + "Últimos: " + ultimos + spaces + asterics);

                List<List<Integer>> ultimosResultados = configuration.getTodosResultados();
                Collections.reverse(ultimosResultados);
                ultimosResultados = ultimosResultados.subList(0, ultimos);

                Map<Integer, Integer> mapResultado = configuration.getMapResultado(resultados,
                        ultimos);
                ArquivoUtil.save(mapResultado.toString(),
                        configuration.getBasePath() + NOME_ARQUIVO_RESULT);

                Map<Integer, Integer> mapAtraso = configuration.getMapAtraso(resultados);
                ArquivoUtil.save(mapAtraso.toString(),
                        configuration.getBasePath() + NOME_ARQUIVO_ULTIMA_VEZ);

                String remove = configuration.getCaminhoJogoAtual();
                List<List<Integer>> rem = ArquivoUtil.obterLinhasComoListasUnique(remove);
                List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
                remover.addAll(resultados);

                preJogos = new ArrayList<>(new HashSet<>(preJogos));

                preJogos = aplicarFiltroRemoverIntersecao(listaJogosAtuais, preJogos);

                preJogos = aplicarFiltroRemoverIntersecao(listaJogosCorrentes, preJogos);

                preJogos = aplicarFiltroRemoverIntersecao(resultados, preJogos);

                if (random.nextBoolean()) {
                    preJogos = aplicarFiltroUltimoSorteio(ultimoSorteio, preJogos, MINIMO_PONTOS_FILTRO, MAXIMO_PONTOS_FILTRO);
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
                for (JogoAb meuJogo : jogosLM) {

                    pontuador.pontuar(ultimosResultados, meuJogo);
                }
                sleep(INTERVALO_SLEEP_MEDIO);

                if (configuration.isPontuacaoExtra()) {
                    for (JogoAb meuJogo : jogosLM) {

                        Integer before = meuJogo.getPontuacao();
                        if (!b1 && b2) {
                            pontuador.pontuarMap(meuJogo, mapResultado, MAPA_RESULTADO_LIMITE, MAPA_RESULTADO_BONUS);
                        }
                        if (!(b1 && b2)) {
                            pontuador.pontuarMap(meuJogo, mapAtraso, MAPA_ATRASO_LIMITE, MAPA_ATRASO_BONUS);
                        }
                        Integer after = meuJogo.getPontuacao();
                        List<Integer> lista = meuJogo.getNumerosAsList();

                        if (x++ % INTERVALO_LOG_MOD == 0) {
                            String msg = String.format("[%s, %s] %s", before, after, lista);
                            System.out.println(msg);
                        }
                    }
                }

                sleep(INTERVALO_SLEEP_MEDIO);

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

                ArquivoUtil.saveLists(jogosOut, destino, SEPARADOR);

                ArquivoUtil.save(configuration.toString(), params);

                rem.addAll(jogosOut);
                ArquivoUtil.saveLists(rem, remove, SEPARADOR);

                listaJogosCorrentes.addAll(jogos);
                listaJogosAtuais.addAll(jogos);
                somenteNovos.addAll(jogos);

                listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

                int size = somenteNovos.size();
                somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
                if (somenteNovos.size() < size) {
                    count = count - (size - somenteNovos.size());
                }

                configuration.setSufixo(SUFIXO_PADRAO);
                if (b2 && random.nextBoolean()) {
                    Collections.shuffle(jogosLM);
                } else {
                    Collections.sort(jogosLM);
                }

                saveDefault2(somenteNovos, sn);

                // String todo = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\" +
                // currDateTime + ".txt";
                // saveDefault2(somenteNovos, todo);

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
        if (num == 2) {
            return new LotofacilConfig15_6_Invertido();
        } else if (num == 5 || num == 8) {
            return new LotofacilConfig15_6();
        }
        return new LotofacilConfig15_5();
    }

    public static void main(String[] args) throws IOException {

        final int quantidade = QUANTIDADE_DE_JOGOS; // vezes 3
        Config configuration = null;

        final int max = ULTIMO_NUMERO;
        final int size = DEFAULT_SIZE;
        String currDate = DateUtils.getCurrentDefaultDate();
        String currDateTime = DateUtils.getCurrentDefaultDateTime();
        String basePath = CAMINHO_BASE;
        String basePathName = basePath + PREFIXO_ARQUIVO_INDEPENDENCIA + currDate + "_independencia_";
        // String basePathName = basePath + "03_" + currDate + "_ELL_";
        String finalListPath = basePathName + currDateTime + SUFIXO_FINAL_TEMP;
        String finalCleanListPath = basePathName + currDateTime + SUFIXO_FINAL;
        List<List<Integer>> finalList = new ArrayList<>();

        List<List<Integer>> listaRemover = ListaUtils.getAll(basePath, size);

        List<List<Integer>> mainList = execute(quantidade, size, configuration);
        print(mainList, "Main");
        finalList.addAll(mainList);

        if (USAR_POSITIONAL) {
            List<List<Integer>> positionalList = PositionalReplacement
                    .getPositionalReplacement(mainList, max);
            print(positionalList, "Positional");
            finalList.addAll(positionalList);
        }

        if (USAR_SHIFT) {
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

        ArquivoUtil.saveLists(finalList, finalListPath, SEPARADOR, 2);
        ArquivoUtil.saveLists(cleanList, finalCleanListPath, SEPARADOR, 2);

    }
}