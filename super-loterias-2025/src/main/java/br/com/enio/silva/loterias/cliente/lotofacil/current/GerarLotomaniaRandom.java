package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.config.LotomaniaConfig;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarLotomaniaRandom {

	public static void main(String[] args) throws IOException {

		List<Integer> numeros = ListaUtils.iterateStream(1, 1, 25);
		// System.out.println(numeros);
		Collections.shuffle(numeros);
		List<List<Integer>> sublistas = new ArrayList<>();
		int inicio = 0;
		int fim = 25;
		for (int i = 0; i < 4; i++) {
			List<Integer> sl = numeros.subList(inicio, fim);
			sublistas.add(sl);
			// System.out.println(sl);
			inicio += 25;
			fim += 25;
		}

		List<Integer> comb = ListaUtils.iterateStream(0, 1, 4);
		List<List<Integer>> gerarComb = CombinationUtils.gerarCombinacao(comb, 2);
		// System.out.println(gerarComb);

		List<List<Integer>> listaJogos = new ArrayList<>();

		for (List<Integer> curr : gerarComb) {
			List<Integer> jogoAtual = new ArrayList<>();
			for (Integer pcurr : curr) {
				jogoAtual.addAll(sublistas.get(pcurr));
			}
			Collections.sort(jogoAtual);
			listaJogos.add(jogoAtual);
		}

		listaJogos = new ArrayList<>(new HashSet<>(listaJogos));

		LotomaniaConfigAb conf = new LotomaniaConfig();
		ArquivoUtil.saveLists(listaJogos, conf.getCaminhoDefaultOutput(), "\t", 2);

	}

}
