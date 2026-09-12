package br.com.silva.enio.loterias.commons.arquivo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArquivoEscritorUtil {

    private static final Logger LOGGER = Logger.getLogger(ArquivoEscritorUtil.class.getName());

    /**
     * Salva uma lista de strings em um arquivo, sobrescrevendo caso exista.
     *
     * @param caminho Caminho do arquivo
     * @param linhas Lista de linhas para salvar
     * @return true se salvo com sucesso
     */
    public static boolean salvarLinhas(String caminho, List<String> linhas) {
        Path path = Paths.get(caminho);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao escrever no arquivo: " + caminho, e);
            return false;
        }
    }

    /**
     * Cria o diretório pai do caminho informado, se não existir.
     *
     * @param caminho Caminho do arquivo
     */
    public static void criarDiretorioSeNaoExistir(String caminho) {
        Path path = Paths.get(caminho).getParent();
        if (path != null && !Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Erro ao criar diretórios para: " + caminho, e);
            }
        }
    }
}
