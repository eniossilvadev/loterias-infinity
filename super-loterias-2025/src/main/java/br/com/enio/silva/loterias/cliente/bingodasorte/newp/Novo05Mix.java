package br.com.enio.silva.loterias.cliente.bingodasorte.newp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.diversos.LotoUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Novo05Mix  extends NovoBingo {

	public static void main(String[] args) throws IOException {

		String output = BASE + "inner\\novo_05_mix.txt";

		int size = 10;

		List<List<Integer>> listaDeJogos = ArquivoUtil.obterLinhasComoListasUnique(input);

		List<Integer> mix = LotoUtils.mix(size, input);

		Collections.sort(mix);

		List<List<Integer>> listaMaisJogados = new ArrayList<>();
		listaMaisJogados.add(mix);

		List<List<Integer>> listaFull = new ArrayList<>(listaDeJogos);
		listaFull.add(mix);

		ArquivoUtil.saveLists(listaMaisJogados, output, sep, 2);
		ArquivoUtil.saveLists(listaFull, fullOutput, sep, 2);

		listaDeJogos.add(mix);
		ArquivoUtil.saveLists(listaDeJogos, input, sep, 2);
	}

}
