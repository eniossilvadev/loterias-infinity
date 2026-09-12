package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import util.ListUtil;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogoLotomaniaComplemento {

	public static void main(String[] args) throws IOException {

		String nome = "Luciano Barros";

		String input = "C:\\Users\\Enio Silva\\Desktop\\Loterias\\lotomania\\" + nome + ".txt";
		String output = "C:\\Users\\Enio Silva\\Desktop\\Loterias\\lotomania\\" + nome + " HT.txt";

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		jogos = ListUtil.completar(jogos, 50, 100);

		System.out.println("Original");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
			Collections.replaceAll(jogo, 100, 0);
			Collections.sort(jogo);
		}

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo.size() + "\t" + jogo);
		}

		ArquivoUtil.saveLists(jogos, output, " ");
	}
}
