package br.com.enio.silva.loterias.cliente.bingodasorte.newp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.diversos.LotoUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Novo03NumerosMaisJogados  extends NovoBingo {

	public static void main(String[] args) throws IOException {
		String output = BASE + "inner\\novo_03_numeros_mais_jogados.txt";
		String sep = "\t";

		List<List<Integer>> listaDeJogos = ArquivoUtil.obterLinhasComoListasUnique(input);

		List<List<Integer>> resultados = ArquivoUtil.obterLinhasComoListasUnique(path);

		List<Integer> listaMais = LotoUtils.obterNumerosQueMaisSairam(resultados, 10);

		Collections.sort(listaMais);

		List<List<Integer>> listaMaisJogados = new ArrayList<>();
		listaMaisJogados.add(listaMais);

		List<List<Integer>> listaFull = new ArrayList<>(listaDeJogos);
		listaFull.add(listaMais);

		ArquivoUtil.saveLists(listaMaisJogados, output, sep, 2);
		ArquivoUtil.saveLists(listaFull, fullOutput, sep, 2);
	}



}
