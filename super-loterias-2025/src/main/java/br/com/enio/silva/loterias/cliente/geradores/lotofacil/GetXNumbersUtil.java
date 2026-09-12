package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GetXNumbersUtil {

	public static void main(String[] args) throws IOException {

		int max = 25;
		int size = 15;
		int times = 8;
		String path = "E:\\loterias\\gerador-apostas\\lotofacil\\J_08x15.txt";

		GetXNumbersUtil generate = new GetXNumbersUtil(max, size, times);
		List<List<Integer>> listas = generate.get();
		System.out.println(listas);
		ArquivoUtil.saveLists(listas, path, ",", 2);
	}

	private final int min;

	private final int max;

	private final int size;

	private final int times;

	private List<Integer> restantes;

	public GetXNumbersUtil(int max, int size, int times) {
		this(1, max, size, times);
	}

	public GetXNumbersUtil(int min, int max, int size, int times) {
		super();
		this.min = min;
		this.max = max;
		this.size = size;
		this.times = times;
		this.restantes = getFullList();
	}

	public List<List<Integer>> get() {
		List<List<Integer>> all = new ArrayList<>();

		for (int i = 0; i < times; i++) {

			List<Integer> next = getNext();

			List<String> list = next.stream()
					.map(el -> StringUtils.leftPad(String.valueOf(el), 2, "0"))
					.collect(Collectors.toList());
			System.out.println(list);

			all.add(next);
		}

		return all;
	}

	public List<Integer> getComplex() {
		List<Integer> current = new ArrayList<>(restantes);
		restantes = getFullList();
		restantes.removeAll(current);
		int tamanhoRestante = size - current.size();
		Collections.shuffle(restantes);
		List<Integer> currentRest = new ArrayList<>(restantes.subList(0, tamanhoRestante));
		current.addAll(currentRest);
		restantes.removeAll(current);
		Collections.sort(current);
		return current;
	}

	public List<Integer> getFullList() {
		return ListaUtils.getListaRange(min, max);
	}

	public List<Integer> getNext() {

		if (restantes == null || restantes.isEmpty()) {
			restantes = getFullList();
		}

		if (restantes.size() >= size) {
			return getSimples();
		}

		return getComplex();
	}

	public List<Integer> getSimples() {
		Collections.shuffle(restantes);
		List<Integer> current = new ArrayList<>(restantes.subList(0, size));
		restantes.removeAll(current);
		Collections.sort(current);
		return current;
	}
}
