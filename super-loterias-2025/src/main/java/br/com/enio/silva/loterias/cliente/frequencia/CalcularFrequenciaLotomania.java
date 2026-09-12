package br.com.enio.silva.loterias.cliente.frequencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.enio.silva.loterias.config.LotomaniaConfig;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class CalcularFrequenciaLotomania {

	public static List<Integer> getBottom(String path, int quantidade, int maxNumber) {

		final List<Integer> listaOrdenada = getList(path, maxNumber);
		final List<Integer> lista = getSubList(listaOrdenada, quantidade, false);

		return lista;
	}

	private static List<Integer> getList(String path, int maxNumber) {
		final List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(path);
		final List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1, maxNumber);
		return listaOrdenada;
	}

	private static List<Integer> getSubList(List<Integer> listaOrdenada, int quantidade,
			boolean reverse) {
		if (reverse) {
			Collections.reverse(listaOrdenada);
		}
		final List<Integer> lista = listaOrdenada.subList(0, quantidade);
		Collections.sort(lista);
		return lista;
	}

	public static List<Integer> getTop(String path, int quantidade, int maxNumber) {

		final List<Integer> listaOrdenada = getList(path, maxNumber);
		final List<Integer> lista = getSubList(listaOrdenada, quantidade, true);

		return lista;
	}

	public static void main(String[] args) throws IOException {

		String pathInOut = "D:\\Meus Documentos\\�rea de Trabalho\\z.txt";
		String pathOut = "D:\\Meus Documentos\\�rea de Trabalho\\w.txt";
		String sep = ",";
		int pad = 2;

		LotomaniaConfigAb conf = new LotomaniaConfig();
		List<List<Integer>> resultados = GerarListaLotomania.getInstance()
				.gerarArquivoResultado(conf);
		ArquivoUtil.safeSaveLists(resultados, pathInOut, sep, pad);

		int quantidade = 40;
		int maxNumber = conf.getMaxNum();

		List<Integer> listaTop = getTop(pathInOut, quantidade, maxNumber);
		System.out.println(listaTop);

		final List<Integer> all = IntStream.rangeClosed(1, maxNumber).boxed()
				.collect(Collectors.toList());
		List<Integer> restante = new ArrayList<>(all);
		restante.removeAll(listaTop);

		List<List<Integer>> finalList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			Collections.shuffle(restante);
			List<Integer> curr = new ArrayList<>(listaTop);
			List<Integer> currRestante = restante.subList(0, 10);
			curr.addAll(new ArrayList<>(currRestante));

			restante.removeAll(currRestante);

			if (curr.contains(100)) {
				curr.replaceAll(x -> (x < 100 ? x : 0));
			}
			Collections.sort(curr);
			finalList.add(curr);
		}

		List<List<Integer>> retorno = new ArrayList<>(finalList);
		ArquivoUtil.safeSaveLists(retorno, pathOut, sep, pad);
	}

}
