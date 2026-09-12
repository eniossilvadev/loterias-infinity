package br.com.enio.silva.loterias.cliente.bingodasorte.newp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.cliente.bingodasorte.ModelBingoGerador;
import br.com.enio.silva.loterias.cliente.bingodasorte.gerador.GeradorGrupoBingoDaSorteQuadrante;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Novo08GeradorQuadrante  extends NovoBingo {

	public static void main(String[] args) throws IOException {

		int qtt = 4;

		String output = BASE + "inner\\out.txt";

		List<List<Integer>> listaDeJogos = ArquivoUtil.obterLinhasComoListasUnique(input);

		GeradorGrupoBingoDaSorteQuadrante g2 = new GeradorGrupoBingoDaSorteQuadrante(qtt, input);
		g2.gerar();

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
