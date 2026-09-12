package br.com.enio.silva.loterias.cliente.mix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LotofacilEsquema01 {

	public static void main(String[] args) {
		int totalDeJogos = 40;
		int times = 24;
		int size = 15;
		int min = 1;
		int max = 25;

		List<Integer> listaPre = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
		List<Integer> lista = new ArrayList<>();
		for(int i = 0; i < times; i++) {
			Collections.shuffle(listaPre);
			lista.addAll(listaPre);
		}
		Collections.shuffle(lista);


		List<List<Integer>> retorno = new ArrayList<>();
		List<Integer> restante = new ArrayList<>();
		for(int i = 0; i < totalDeJogos; i++) {

			//			Collections.shuffle(listaPre);
			listaPre.removeAll(restante);
			System.out.println("listaPre" + listaPre);
			Collections.shuffle(listaPre);

			List<Integer> listaJ = new ArrayList<>(restante);
			listaJ.addAll(listaPre);
			System.out.println("listaJ" + listaJ);
			List<Integer> listaCurr = new ArrayList<>();
			listaCurr.addAll(listaJ.subList(0, size));
			Collections.sort(listaCurr);
			retorno.add(listaCurr);

			restante = new ArrayList<>(listaJ);
			restante.removeAll(listaCurr);
			Collections.sort(restante);
			System.out.println("listaCurr" + listaCurr);
			System.out.println("restante" + restante);
		}
		retorno.forEach(System.out::println);
	}

	public static void t1() {

		int totalDeJogos = 40;
		int times = 24;
		int size = 15;
		int min = 1;
		int max = 25;
		List<List<Integer>> retorno = new ArrayList<>();
		List<Integer> restante = new ArrayList<>();
		for(int i = 0; i < totalDeJogos; i++) {
			List<Integer> listaPre = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
			//			Collections.shuffle(listaPre);
			listaPre.removeAll(restante);
			System.out.println("listaPre" + listaPre);
			Collections.shuffle(listaPre);

			List<Integer> listaJ = new ArrayList<>(restante);
			listaJ.addAll(listaPre);
			System.out.println("listaJ" + listaJ);
			List<Integer> listaCurr = new ArrayList<>();
			listaCurr.addAll(listaJ.subList(0, size));
			Collections.sort(listaCurr);
			retorno.add(listaCurr);

			restante = new ArrayList<>(listaJ);
			restante.removeAll(listaCurr);
			Collections.sort(restante);
			System.out.println("listaCurr" + listaCurr);
			System.out.println("restante" + restante);
		}
		retorno.forEach(System.out::println);

	}

}
