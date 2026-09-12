package br.com.enio.silva.loterias.conferencia;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.silva.enio.loterias.controller.ConferirRN;

public class Conferir {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String nConcurso = "2113";
		String base = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\";
		String subpasta = "";
		String input = base + subpasta + "LI.txt";
		String resultado = "C:\\loterias\\gerador-apostas\\lotofacil\\zip\\RESULTADO_LF.txt";
		String id = System.currentTimeMillis() + "";
		String output = base + subpasta + "conferido_" + id + ".txt";
		String outputPre = base + subpasta + "conferido_" + id + "_p.txt";
		Integer[] faixas = { Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13),
		        Integer.valueOf(14), Integer.valueOf(15) };
		List<Integer> pre = Arrays.asList(faixas);

		int inicio = Integer.valueOf(nConcurso);
		int fim = Integer.valueOf(nConcurso);

		List<List<Integer>> jogos = ArquivoUtil.obterLinhasComoListas(input);
		List<List<Integer>> resultados = ArquivoUtil.obterLinhasComoListas(resultado);

		StringBuilder str = new StringBuilder();
		StringBuilder premio = new StringBuilder();

		int count = 1;
		boolean encontrou = false;
		for (List<Integer> result : resultados) {

			if (count >= inicio && count <= fim) {
				encontrou = true;
				str.append("Concurso: " + count).append("\n");
				str.append("Resultado: ").append(result).append("\n");
				int jog = 1;
				for (List<Integer> jogo : jogos) {

					int qtt = ConferirRN.conferir(result, jogo);
					if (pre.contains(qtt)) {
						str.append("pr\t");
						premio.append(count).append("\t");
						premio.append(jog).append("\t");
						premio.append(qtt).append("\t");
						premio.append(jogo).append("\n");
					} else {
						str.append("\t");
					}

					jog++;

					str.append(qtt).append("\t").append(jogo).append("\n");
				}
			}
			count++;
		}

		if (!encontrou) {
			str.append("Não foi encontrado o(s) concurso(s) informado(s)! ");
			str.append("\nConcursos informados: de ").append(inicio);
			str.append(" at� ").append(fim);
		}

		ArquivoUtil.saveLists(jogos, input, "\t");
		ArquivoUtil.save(str.toString(), output);
		ArquivoUtil.save(premio.toString(), outputPre);
		ArquivoUtil.saveLists(resultados, resultado, "\t");
	}
}
