package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarLotofacilRandomGroup extends GerarJogosLotofacil2020Ab {

	public static void main(String[] args) throws IOException {

		List<Integer> numeros = ListaUtils.iterateStream(1, 1, 25);
		// System.out.println(numeros);
		Collections.shuffle(numeros);
		List<List<Integer>> sublistas = new ArrayList<>();
		int inicio = 0;
		int fim = 5;
		for (int i = 0; i < 5; i++) {
			List<Integer> sl = numeros.subList(inicio, fim);
			sublistas.add(sl);
			// System.out.println(sl);
			inicio += 5;
			fim += 5;
		}

		List<Integer> comb = ListaUtils.iterateStream(0, 1, sublistas.size());
		List<List<Integer>> gerarComb = CombinationUtils.gerarCombinacao(comb, 3);
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

		LotofacilConfig config = new LotofacilConfig15();

		SaveLotofacil saveLotofacil = new SaveLotofacil();
		String base = saveLotofacil.getConfig();
		String sn = base + "LSN.txt";

		List<List<Integer>> listaJogosCorrentes = new LinkedList<>(
				ArquivoUtil.obterLinhasComoListasUnique(config.getCaminhoJogoCorrente(),
						config.getNrosApostados()));

		List<List<Integer>> listaJogosAtuais = new ArrayList<>(listaJogosCorrentes);
		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		List<List<Integer>> resultados = config.getTodosResultados();

		listaJogos.removeAll(listaJogosCorrentes);
		listaJogos.removeAll(listaJogosAtuais);
		listaJogos.removeAll(resultados);

		ArquivoUtil.saveLists(listaJogos, sn, "\t", 2);

		listaJogosCorrentes.addAll(listaJogos);
		ArquivoUtil.saveLists(listaJogosCorrentes, config.getCaminhoJogoCorrente(), "\t", 2);

		listaJogosAtuais.addAll(listaJogos);
		ArquivoUtil.saveLists(listaJogosAtuais, config.getCaminhoJogoAtual(), "\t", 2);

		String mc = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mc, config.getBasePath() + "correntes.txt");
		String ma = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(ma, config.getBasePath() + "atuais.txt");

	}
}
