package br.com.enio.silva.loterias.conferencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.lotomania.GerarListaLotofacil;

import br.com.silva.enio.loterias.controller.ConferirRN;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConferirMegaSena {

	public static void main(String[] args) throws IOException {

		List<List<Integer>> results = GerarListaLotofacil.getInstance().gerarArquivoResultado();

		int inicio = 1184;
		int fim = 1195;

		String base = "C:\\loterias\\lotofacil\\1184-1195\\";
		String id = System.currentTimeMillis() + "";
		String outputTodosResultados = base + id + "_todos_resultados.txt";
		String outputResultados = base + id + "_resultados.txt";
		String outputResultadosStr = base + id + "_resultadosStr.txt";
		String output = base + id + "_resultados_conferidos.txt";

		ArquivoUtil.saveLists(results, outputTodosResultados, "\t");

		int i = 0;
		List<List<Integer>> resultados = new ArrayList<List<Integer>>();
		StringBuilder strB = new StringBuilder();
		for (List<Integer> result : results) {
			// System.out.println(result);
			i++;
			if (i >= inicio && i <= fim) {
				resultados.add(result);
				// System.out.println(">>>>");
				strB.append(i);
				strB.append(result);
				strB.append("\n");
			}
		}
		ArquivoUtil.saveLists(resultados, outputResultados, "\t");
		ArquivoUtil.save(strB.toString(), outputResultadosStr);

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(base + "1184-1195_HT");

		StringBuilder str = new StringBuilder();
		List<List<Integer>> out = new ArrayList<List<Integer>>();
		int count = inicio;
		for (List<Integer> result : resultados) {
			str.append("Concurso: " + count++ + "\n");
			for (List<Integer> jogo : jogos) {
				int qtt = ConferirRN.conferir(result, jogo);
				str.append(qtt);
				str.append(jogo);
				str.append("\n");
			}
		}

		ArquivoUtil.save(str.toString(), output);
		System.out.println("done");
	}
}
