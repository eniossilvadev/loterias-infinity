package br.com.silva.enio.loterias.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.silva.enio.loterias.model.mapper.LotomaniaMapper;

import org.apache.commons.collections.ListUtils;

import util.ListUtil;
import util.MathUtil;

public class LotomaniaRN implements LoteriaRNAb {

	public static LotomaniaRN getInstance() {
		if (instance == null) {
			instance = new LotomaniaRN();
		}
		return instance;
	}

	public static void main(String[] args) {
		Set<Integer> teste = new HashSet<Integer>();
		while (teste.size() < 20) {
			Integer i = (int) (1 + Math.random() * 100);
			teste.add(i);
		}
		List<Integer> sorteados = new ArrayList<Integer>(teste);
		LotomaniaRN lrn = new LotomaniaRN();
		lrn.gerarVolantesApostas(sorteados);
	}

	private static LotomaniaRN instance = null;

	public static final int MAIOR_NUMERO = 100;

	private LotomaniaRN() {

	}

	@SuppressWarnings("unchecked")
	public List<List<Integer>> aplicarFiltro(List<List<Integer>> input, List<Integer> ultimoSorteio) {
		List<List<Integer>> output = new ArrayList<List<Integer>>();

		// Trocar
		// int[] repeticoes = {9, 10, 11};
		System.out.println(getClass().getName() + " ultimo sorteio ");
		List<Integer> repeticoes = new ArrayList<Integer>();
		repeticoes.add(7);
		repeticoes.add(8);
		repeticoes.add(9);
		repeticoes.add(10);
		// repeticoes.add(11);

		Object obj = ((ArrayList<List<Integer>>) input).clone();
		List<List<Integer>> todosInput = (ArrayList<List<Integer>>) obj;
		for (List<Integer> in : todosInput) {
			Integer rep = ConferirRN.conferir(in, ultimoSorteio);
			if (repeticoes.contains(rep)) {
				output.add(in);
			}
		}

		return output;
	}

	public List<List<Integer>> aplicarFiltro2(List<List<Integer>> jogos) {
		List<List<Integer>> output = new ArrayList<List<Integer>>();

		System.out.println(getClass().getName() + " par x impar ");
		int par, impar, diff;
		for (List<Integer> jogo : jogos) {
			par = MathUtil.countEven(jogo);
			impar = MathUtil.countOdd(jogo);
			diff = Math.abs(par - impar);
			if (diff <= 2) {
				output.add(jogo);
			}
		}

		return output;
	}

	public List<List<Integer>> gerarVolantesApostas(List<Integer> sorteados) {
		System.out.println(sorteados);
		Collections.shuffle(sorteados);

		List<Integer> naoSorteados = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			naoSorteados.add(i);
		}
		System.out.println(naoSorteados);
		Collections.shuffle(naoSorteados);
		naoSorteados = ListUtils.subtract(naoSorteados, sorteados.subList(0, 10));
		System.out.println(naoSorteados);

		List<Integer> jogo1 = new ArrayList<Integer>();
		List<Integer> jogo2 = new ArrayList<Integer>();
		List<Integer> jogo3 = new ArrayList<Integer>();
		for (int i = 0; i < naoSorteados.size(); i++) {
			Integer elem = naoSorteados.get(i);
			if (i % 3 == 0) {
				jogo1.add(elem);
			}
			if (i % 3 == 1) {
				jogo2.add(elem);
			}
			if (i % 3 == 2) {
				jogo3.add(elem);
			}
		}

		Collections.shuffle(sorteados);
		sorteados = sorteados.subList(10, 20);
		System.out.println(sorteados.size());

		List<List<Integer>> listaDeJogos = new ArrayList<List<Integer>>();
		listaDeJogos.add(jogo1);
		listaDeJogos.add(jogo2);
		listaDeJogos.add(jogo3);
		// jogo1.addAll(sorteados);
		// jogo2.addAll(sorteados);
		// jogo3.addAll(sorteados);

		listaDeJogos = ListUtil.completar(listaDeJogos, 50, 100);

		Collections.sort(jogo1);
		Collections.sort(jogo2);
		Collections.sort(jogo3);
		System.out.println(jogo1.size() + " -> " + jogo1);
		System.out.println(jogo2.size() + " -> " + jogo2);
		System.out.println(jogo3.size() + " -> " + jogo3);

		return listaDeJogos;
	}

	public List<List<Integer>> gerarVolantesApostas(List<List<Integer>> sorteios, int qttJogos)
	        throws IOException {
		LotomaniaRN rn = new LotomaniaRN();
		List<Integer> todosNumeros = rn.getTodosNumeros();
		List<Integer> ultimosorteio = sorteios.get(sorteios.size() - 1);

		List<List<Integer>> sorteados10 = new ArrayList<List<Integer>>();
		for (int i = 0; i < qttJogos; i++) {
			Collections.shuffle(ultimosorteio);
			List<Integer> fromLast = new ArrayList(ultimosorteio.subList(0, 10));

			sorteados10.add(fromLast);
		}

		List<List<Integer>> jogosGerados = new ArrayList<List<Integer>>();
		for (List<Integer> chosenFromLast : sorteados10) {
			List<Integer> notFromLast = ListUtils.subtract(todosNumeros, chosenFromLast);

			List<Integer> jogo1 = new ArrayList<Integer>();
			List<Integer> jogo2 = new ArrayList<Integer>();
			List<Integer> jogo3 = new ArrayList<Integer>();
			jogo1.addAll(chosenFromLast);
			jogo2.addAll(chosenFromLast);
			jogo3.addAll(chosenFromLast);

			for (int i = 0; i < notFromLast.size(); i++) {
				Integer elem = notFromLast.get(i);
				if (i % 3 == 0) {
					jogo1.add(elem);
				}
				if (i % 3 == 1) {
					jogo2.add(elem);
				}
				if (i % 3 == 2) {
					jogo3.add(elem);
				}
			}

			Collections.sort(jogo1);
			Collections.sort(jogo2);
			Collections.sort(jogo3);
			if (!jogosGerados.contains(jogo1)) {
				jogosGerados.add(jogo1);
			}
			if (!jogosGerados.contains(jogo2)) {
				jogosGerados.add(jogo2);
			}
			if (!jogosGerados.contains(jogo3)) {
				jogosGerados.add(jogo3);
			}
		}
		System.out.println("");
		return jogosGerados;
	}

	@Override
	public List<List<Integer>> getNumerosSorteados(String fileName) throws IOException {
		return new LotomaniaMapper().getNumerosSorteados(fileName);
	}

	public List<Integer> getTodosNumeros() {
		List<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < MAIOR_NUMERO; i++) {
			numeros.add(i + 1);
		}
		return numeros;
	}

	@Override
	public List<List<String>> parseToList(String fileName) throws IOException {
		return new LotomaniaMapper().getLists(fileName);
	}
}
