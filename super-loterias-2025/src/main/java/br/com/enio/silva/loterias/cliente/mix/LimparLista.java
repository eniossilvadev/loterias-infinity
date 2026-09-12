package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.diversos.LotoUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LimparLista {

	public static void main(final String[] args) throws IOException {

		int size = 15; // Importante
		boolean sort = true;
		boolean shuffe = false;

		final String f = "D:\\Meus Documentos\\Downloads\\apostas-20240312130330.txt";
		final String out = f;
		final List<List<Integer>> lista = ArquivoUtil.obterLinhasComoListasUnique(f);

		lista.forEach(l -> {
			final int s = l.size();
			System.out.println(l.size() + "\t" + (s >= size) + "\t" + l);
		});

		final List<List<Integer>> listGc = sort ? sort(lista, size): (shuffe? shuffle(lista): new ArrayList<>(lista));
		ArquivoUtil.saveLists(listGc, out, ",", 2);
	}

	public static List<List<Integer>> shuffle(List<List<Integer>> lista){
		Collections.shuffle(lista);
		return lista;
	}

	public static List<List<Integer>> sort(List<List<Integer>> lista, int size) {
		if(size > 0) {
			return LotoUtils.gc(lista, size); // Atenção ********
		} else {
			return LotoUtils.gc(lista);
		}
	}

}
