package br.com.enio.silva.loterias.cliente.mix;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class SplitAndJoin {

	public static String getSplitedAndJoined(final String input, final int chunkSize) {
		String result = IntStream.range(0, input.length())
				.filter(i -> i % chunkSize == 0)
				.mapToObj(i -> input.substring(i, Math.min(i + chunkSize, input.length())))
				//				.mapToObj(i -> input.substring(i, chunkSize))
				.collect(Collectors.joining("\t"));
		return result;
	}

	public static void main(String[] args) {

		final StringBuilder content = new StringBuilder();
		final int chunkSize = 2; // Set your desired chunk size (n) here

		String basePath = "C:\\loterias\\python-utils\\full-results\\data\\";
		String inputPath = basePath + "split_and_join.txt";
		String outputPath = basePath + "split_and_join_out.txt";

		List<String> contentsAsList = ArquivoUtil.getContentsAsList(inputPath);
		contentsAsList.forEach(el -> {
			String curr = getSplitedAndJoined(el, chunkSize);
			content.append(curr).append("\n");
		});

		ArquivoUtil.safeSave(content.toString(), outputPath);
	}
}
