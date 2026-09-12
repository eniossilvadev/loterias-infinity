package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GenerateRandom {

	public static final int minimo = 1;

	public static final int maximo = 25;

	public static final int quantidade = 4;

	public static final int tamanho = 15;

	public static final String caminhoDestimo = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LSN.txt";

	public static void main(String[] args) throws IOException {

		List<List<Integer>> listas = new ArrayList<>();

		for (int i = 0; i < quantidade; i++) {
			List<Integer> lista = ListaUtils.getListaRange(minimo, maximo);
			for (int j = 0; j < 10; j++) {
				Collections.shuffle(lista);
			}
			lista = lista.subList(0, tamanho);
			Collections.sort(lista);
			listas.add(lista);
		}

		ArquivoUtil.saveLists(listas, caminhoDestimo, "\t", 2);
	}

}
