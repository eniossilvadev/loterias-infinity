package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarMegaRandom extends GerarJogosMegaLoopAb {

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

		MegaSenaConfigAb config = new MegaSenaConfig6();
		List<List<Integer>> atuais = getListaJogosAtuais(true);
		List<List<Integer>> correntes = getListaJogosCorrentes();
		List<List<Integer>> resultados = config.getTodosResultados();

		List<Integer> atuaisFlat = getListaFlat(atuais);
		List<Integer> atuaisCorrentesUnique = getListaFlatUnique(correntes);
		List<Integer> numeros = ListaUtils.iterateStream(1, 1, 60);
		List<Integer> diff = new ArrayList<>();

		if (numeros.size() > atuaisCorrentesUnique.size()) {
			diff = ListaUtils.iterateStream(1, 1, 60);
			diff.removeAll(atuaisCorrentesUnique);
			System.out.println(diff);
		}

		System.out.println(config);
		System.out.println(atuais);
		System.out.println(correntes);
		System.out.println(resultados);
		System.out.println(atuaisFlat.size() + "\t" + atuaisFlat);
		System.out.println(atuaisCorrentesUnique.size() + "\t" + atuaisCorrentesUnique);
		System.out.println(numeros);
		System.out.println();
		System.out.println(diff);
	}

}
