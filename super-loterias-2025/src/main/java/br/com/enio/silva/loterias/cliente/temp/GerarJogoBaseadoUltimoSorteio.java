package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;

import br.com.silva.enio.loterias.controller.LotomaniaRN;
import util.ImprimirUtil;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogoBaseadoUltimoSorteio {

	public static void main(String[] args) throws IOException {

		List<List<Integer>> resultados = GerarListaLotomania.getInstance().gerarArquivoResultado();

		System.out.println(ImprimirUtil.printListas(resultados));
		Collections.reverse(resultados);
		List<Integer> ultimo = resultados.get(0);

		List<List<Integer>> jogos = LotomaniaRN.getInstance().gerarVolantesApostas(ultimo);
		jogos.addAll(LotomaniaRN.getInstance().gerarVolantesApostas(ultimo));

		String nome = "Alex Neves";

		String output = "C:\\Users\\Enio Silva\\Desktop\\Loterias\\lotomania\\" + nome + " HT.txt";

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
