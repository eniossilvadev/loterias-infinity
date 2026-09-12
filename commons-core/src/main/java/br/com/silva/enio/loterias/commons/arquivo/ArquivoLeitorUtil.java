package br.com.silva.enio.loterias.commons.arquivo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArquivoLeitorUtil {

    private static final Logger LOGGER = Logger.getLogger(ArquivoLeitorUtil.class.getName());

    /**
     * Lê todas as linhas de um arquivo como uma lista de strings.
     *
     * @param caminho Caminho absoluto ou relativo do arquivo
     * @return Lista de linhas ou lista vazia se houver erro
     */
    public static List<String> lerLinhas(String caminho) {
        Path path = Paths.get(caminho);
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler arquivo: " + caminho, e);
            return new ArrayList<>();
        }
    }

    /**
     * Lê as linhas de um arquivo, ignorando linhas em branco ou com espaços
     *
     * @param caminho Caminho do arquivo
     * @return Lista de linhas não vazias
     */
    public static List<String> lerLinhasNaoVazias(String caminho) {
        List<String> linhas = lerLinhas(caminho);
        List<String> resultado = new ArrayList<>();
        for (String linha : linhas) {
            if (linha != null && !linha.trim().isEmpty()) {
                resultado.add(linha);
            }
        }
        return resultado;
    }

    /**
     * Verifica se um arquivo existe
     *
     * @param caminho Caminho do arquivo
     * @return true se o arquivo existe e é legível
     */
    public static boolean existe(String caminho) {
        Path path = Paths.get(caminho);
        return Files.exists(path) && Files.isReadable(path);
    }
}
