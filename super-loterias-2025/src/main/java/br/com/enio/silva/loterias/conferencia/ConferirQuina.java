package br.com.enio.silva.loterias.conferencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.lotomania.GerarListaQuina;

import br.com.silva.enio.loterias.controller.ConferirRN;
import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ConferirQuina {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		List<List<Integer>> results = GerarListaQuina.getInstance().gerarArquivoResultado();

		int inicio = 3763;
		int fim = 3786;

		String base = "C:\\loterias\\quina\\3763-3786\\";
		String id = System.currentTimeMillis() + "";
		String input = base + "3763_3786.txt";
		String outputTodosResultados = base + id + "_todos_resultados.txt";
		String outputResultados = base + id + "_resultados.txt";
		String outputResultadosStr = base + id + "_resultadosStr.txt";
		String output = base + id + "_resultados_conferidos.txt";
		String premiados = base + id + "_premiados.txt";

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

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);

		StringBuilder str = new StringBuilder();
		StringBuilder premio = new StringBuilder();

		int count = inicio;
		for (List<Integer> result : resultados) {

			str.append("Concurso: " + count + "\n");
			int jog = 1;
			for (List<Integer> jogo : jogos) {

				int qtt = ConferirRN.conferir(result, jogo);
				if (qtt >= 3) {
					str.append("premio\t");
					premio.append(count).append("\t");
					premio.append(jog).append("\t");
					premio.append(qtt).append("\t");
					premio.append(jogo).append("\n");
				} else {
					str.append("\t\t");
				}

				jog++;

				str.append(qtt).append(jogo).append("\n");
			}
			count++;
		}

		ArquivoUtil.save(str.toString(), output);
		ArquivoUtil.save(premio.toString(), premiados);
		ArquivoUtil.saveLists(jogos, input + "2", "\t");

		System.out.println("done");
	}
}
