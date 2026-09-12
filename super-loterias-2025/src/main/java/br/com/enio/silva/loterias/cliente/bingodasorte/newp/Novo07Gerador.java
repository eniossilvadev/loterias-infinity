package br.com.enio.silva.loterias.cliente.bingodasorte.newp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.cliente.bingodasorte.ModelBingoGerador;
import br.com.enio.silva.loterias.cliente.bingodasorte.gerador.GeradorGrupoBingoDaSorteNovoComLista;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Novo07Gerador  extends NovoBingo {

	public static void main(String[] args) throws IOException {

		//TODO GeradorGrupoBingoDaSorteNovoComLista
		int qtt = 4; // 3 + 1

		String output = BASE + "\\inner\\novo_08.txt";
		String pathListaBase = "E:\\loterias\\bingo-da-sorte\\20221201\\lista_base_01.txt";

		List<List<Integer>> listaDeJogos = ArquivoUtil.obterLinhasComoListasUnique(input);
		List<List<Integer>> listaBase = ArquivoUtil.obterLinhasComoListasUnique(pathListaBase);

		GeradorGrupoBingoDaSorteNovoComLista g2 = new GeradorGrupoBingoDaSorteNovoComLista(qtt, input);
		g2.gerar(listaBase, 8, qtt);

		ModelBingoGerador mbg = g2.getGerados();
		List<List<Integer>> all = mbg.getAll();

		all = all.stream().map(e -> {
			Collections.sort(e);
			return e;
		}).collect(Collectors.toList());

		List<List<Integer>> listaMaisJogados = new ArrayList<>();
		listaMaisJogados.addAll(all);

		List<List<Integer>> listaFull = new ArrayList<>(listaDeJogos);
		listaFull.addAll(all);

		ArquivoUtil.saveLists(listaMaisJogados, output, sep, 2);
		ArquivoUtil.saveLists(listaFull, fullOutput, sep, 2);

		listaDeJogos.addAll(all);
		ArquivoUtil.saveLists(listaDeJogos, input, sep, 2);
	}

}
