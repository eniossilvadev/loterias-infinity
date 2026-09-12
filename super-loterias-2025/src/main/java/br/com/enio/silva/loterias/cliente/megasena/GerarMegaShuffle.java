package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarMegaShuffle extends GerarJogosMegaLoopAb {

	protected static List<Integer> getListaFlat(List<List<Integer>> lista) throws IOException {
		List<Integer> retorno = new ArrayList<>();
		if (lista != null && !lista.isEmpty()) {
			for (List<Integer> curr : lista) {
				retorno.addAll(curr);
			}
		}
		return retorno;
	}

	protected static List<Integer> getListaFlatUnique(List<List<Integer>> lista)
	        throws IOException {
		return new ArrayList<>(new HashSet<>(getListaFlat(lista)));
	}

	protected static List<List<Integer>> getListaJogosAtuais(boolean removerDuplicados)
	        throws IOException {
		MegaSenaConfigAb config = new MegaSenaConfig6();
		String path = config.getCaminhoJogoAtual();
		List<List<Integer>> retorno = ArquivoUtil.obterLinhasComoListasUnique(path);

		if (removerDuplicados) {
			retorno = new ArrayList<>(new HashSet<>(retorno));
		}

		ArquivoUtil.saveLists(retorno, path, "\t", 2);

		return retorno;
	}

	protected static List<List<Integer>> getListaJogosCorrentes() throws IOException {
		MegaSenaConfigAb config = new MegaSenaConfig6();
		String path = config.getCaminhoJogoCorrente();
		List<List<Integer>> retorno = ArquivoUtil.obterLinhasComoListasUnique(path);

		retorno = new ArrayList<>(new HashSet<>(retorno));

		ArquivoUtil.saveLists(retorno, path, "\t", 2);

		return retorno;
	}

	public static void main(String[] args) throws IOException {

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";
		String sn = base + "SNMS.txt";

		MegaSenaConfigAb config = new MegaSenaConfig6();
		List<List<Integer>> atuais = getListaJogosAtuais(true);
		List<List<Integer>> correntes = getListaJogosCorrentes();
		List<List<Integer>> resultados = config.getTodosResultados();

		List<Integer> numeros = ListaUtils.iterateStream(1, 1, 60);

		int jogos = 10;
		int size = config.getNrosApostados();

		Set<List<Integer>> listaJogos = new HashSet<>();

		for (int i = 0; i < jogos; i++) {
			List<Integer> curr = new ArrayList<>();
			Collections.shuffle(numeros);
			if (numeros.size() >= size) {
				curr.addAll(numeros.subList(0, size));
			} else {
				curr.addAll(numeros.subList(0, numeros.size()));
				numeros = ListaUtils.iterateStream(1, 1, 60);
				numeros.removeAll(curr);
				curr.addAll(numeros.subList(0, size - curr.size()));
			}
			numeros.removeAll(curr);
			if (atuais.contains(curr) || correntes.contains(curr) || resultados.contains(curr)
			        || listaJogos.contains(curr)) {
				i--;
				System.out.println("Repetido: " + curr);
			} else {
				Collections.sort(curr);
				listaJogos.add(curr);
			}
		}

		for (List<Integer> lista : listaJogos) {
			System.out.println(lista);
		}

		List<List<Integer>> novos = new ArrayList<>(listaJogos);
		atuais.addAll(novos);
		correntes.addAll(novos);

		ArquivoUtil.saveLists(atuais, config.getCaminhoJogoAtual(), "\t", 2);
		ArquivoUtil.saveLists(correntes, config.getCaminhoJogoCorrente(), "\t", 2);
		ArquivoUtil.saveLists(novos, sn, "\t", 2);

		String mcb = config.getFrequencia(correntes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(atuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

	}

}
