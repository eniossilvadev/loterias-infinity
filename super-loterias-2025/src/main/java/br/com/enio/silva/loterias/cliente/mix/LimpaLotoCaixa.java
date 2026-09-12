package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import br.com.enio.silva.loterias.diversos.LotoUtils;

public class LimpaLotoCaixa {

	public static void main(String[] args) throws IOException {
		String path = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\site.txt";

		boolean preecherAtuais = true;

		LotoUtils.limparLoto(path, "\t"); // Limpar copiado do site da caixa

		LotoUtils.limpaLinhas(path);

		LotoUtils.removerDuplicados(path, "\t");

		LotoUtils.removerPorTamanho(path, "\t", 15);

		//		preencherAtuais(preecherAtuais, path);
		//		preencherAtuaisPremiados(preecherAtuais, path);

	}

	public static void preencherAtuais(boolean preencher, String path) throws IOException {
		if (preencher) {
			String lja = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\novos_jogos.txt";
			String ljc = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\todos_jogos.txt";

			//			String lja = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJA.txt";
			//			String ljc = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJC.txt";

			Path originalPath = Paths.get(path);

			Path copiedLJA = Paths.get(lja);
			Files.copy(originalPath, copiedLJA, StandardCopyOption.REPLACE_EXISTING);

			Path copiedLJC = Paths.get(ljc);
			Files.copy(originalPath, copiedLJC, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public static void preencherAtuaisPremiados(boolean preencher, String path) throws IOException {
		if (preencher) {
			String lja = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\premiados.txt";

			Path originalPath = Paths.get(path);

			Path copiedLJA = Paths.get(lja);
			Files.copy(originalPath, copiedLJA, StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
