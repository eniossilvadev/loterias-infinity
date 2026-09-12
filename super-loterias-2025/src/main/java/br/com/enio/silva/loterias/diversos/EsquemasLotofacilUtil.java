package br.com.enio.silva.loterias.diversos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.exception.EstrategiaRNExepcion;

import util.ListUtil;

public class EsquemasLotofacilUtil {

	private static final Random random = new Random();

	public static List<List<Integer>> gerarEsquemaMaisCinco(List<Integer> original) {
		List<List<Integer>> retorno = new ArrayList<>();
		System.out.println(printList("Original:", original));
		for (int i = 0; i < 5; i++) {
			final int mult = i;
			List<Integer> lista = original.stream().map(el -> (el + mult * 5) % 25)
			        .map(el -> el == 0 ? 25 : el).collect(Collectors.toList());
			retorno.add(lista);

			System.out.println(printList("Lista:" + i, lista));

			Collections.sort(lista);

			System.out.println(printList("Lista Ordenada:" + i, lista));
		}
		return retorno;
	}

	public static List<List<Integer>> gerarEsquemaMaisCincoLista(List<List<Integer>> original) {
		final List<List<Integer>> retorno = new ArrayList<>();

		original.forEach(l -> retorno.addAll(gerarEsquemaMaisCinco(l)));

		return retorno;

	}

	public static List<List<Integer>> gerarEsquemaMaisCincoLista(List<List<Integer>> original,
	        boolean removerRepeticoes) {
		List<List<Integer>> retorno = gerarEsquemaMaisCincoLista(original);
		if (removerRepeticoes) {
			return new ArrayList<>(new HashSet<>(retorno));
		}
		return retorno;
	}

	public static List<Integer> gerarEsquemaMaisN(List<Integer> original, int n) {
		List<Integer> lista = original.stream().map(el -> (el + n) % 25)
		        .map(el -> el == 0 ? 25 : el).collect(Collectors.toList());
		return lista;
	}

	public static List<Integer> gerarEsquemaMaisX(List<Integer> original) {
		int r = Math.abs(random.nextInt()) % 24;
		return gerarEsquemaMaisN(original, r + 1);
	}

	public static List<List<Integer>> gerarEsquemaXLista(List<List<Integer>> original) {
		final List<List<Integer>> retorno = new ArrayList<>();

		original.forEach(l -> {
			List<Integer> list = gerarEsquemaMaisX(l);
			Collections.sort(list);
			retorno.add(list);
		});

		return retorno;

	}

	public static List<List<Integer>> gerarEsquemaXLista(List<List<Integer>> original, int times) {
		final List<List<Integer>> retorno = new ArrayList<>();

		for (int i = 0; i < times; i++) {
			Collections.shuffle(original);
			final List<Integer> current = original.get(i % original.size());
			List<Integer> list = gerarEsquemaMaisX(current);
			Collections.sort(list);
			retorno.add(list);
		}

		return retorno;
	}

	public static List<Integer> gerarJogoEstrategiaXdoTikTok(final List<Integer> listaInicial) {
		if (listaInicial == null || listaInicial.isEmpty()) {
			throw new EstrategiaRNExepcion("A Lista não pode ser nula nem vazia.");
		}
		final List<Integer> listaX = new ArrayList<Integer>();
		listaInicial.forEach(el -> {
			final int element = (el + 15) % 25;
			listaX.add(element == 0 ? 25 : element);
		});
		System.out.println(listaInicial);
		System.out.println(listaX);
		System.out.println("\n");

		return listaX;
	}

	public static List<List<Integer>> gerarJogosEstrategiaXdoTikTok(
	        final List<List<Integer>> listasIniciais, boolean sort) {
		if (listasIniciais == null) {
			throw new EstrategiaRNExepcion("A Lista não pode ser nula.");
		}
		final List<List<Integer>> listasX = new ArrayList<>();
		listasIniciais.forEach(lista -> {
			List<Integer> listaLocal = gerarJogoEstrategiaXdoTikTok(lista);
			if (listaLocal != null) {
				if (sort) {
					Collections.sort(listaLocal);
				}
				listasX.add(listaLocal);
			}
		});

		return listasX;
	}

	public static List<List<Integer>> gerarJogosIniciaisEstrategiaXdoTikTok(int xTimes) {
		final List<List<Integer>> preBase = CombinationUtils.gerarListaCombinacao(1, 10, 5);
		final List<List<Integer>> base = new ArrayList<>();
		for (int i = 0; i < xTimes; i++) {
			base.addAll(preBase);
		}

		List<List<Integer>> listaMaior10 = ListUtil.gerarListasMinMaxRemove(11, 25, 10,
		        base.size());

		List<List<Integer>> listaRetorno = new ArrayList<>();
		for (int i = 0; i < base.size(); i++) {
			List<Integer> current = new ArrayList<>();
			current.addAll(base.get(i));
			current.addAll(listaMaior10.get(i));
			listaRetorno.add(current);
		}

		return new ArrayList<>(new HashSet<>(listaRetorno));
	}

	public static List<List<Integer>> gerarJogosIniciaisEstrategiaXdoTikTok16(int xTimes) {
		final int xt1 = xTimes / 2;
		final int xt2 = xTimes - xt1;
		final List<List<Integer>> lista1 = gerarJogosIniciaisEstrategiaXdoTikTokFlex(xt1, 5, 11);
		final List<List<Integer>> lista2 = gerarJogosIniciaisEstrategiaXdoTikTokFlex(xt2, 6, 10);
		final Set<List<Integer>> set = new HashSet<>(lista1);
		set.addAll(lista2);

		List<List<Integer>> retorno = new ArrayList<>(set);
		return retorno;
	}

	public static List<List<Integer>> gerarJogosIniciaisEstrategiaXdoTikTok17(int xTimes) {
		final List<List<Integer>> preBase = CombinationUtils.gerarListaCombinacao(1, 10, 6);
		final List<List<Integer>> base = new ArrayList<>();
		for (int i = 0; i < xTimes; i++) {
			base.addAll(preBase);
		}

		List<List<Integer>> listaMaior10 = ListUtil.gerarListasMinMaxRemove(11, 25, 11,
		        base.size());

		List<List<Integer>> listaRetorno = new ArrayList<>();
		for (int i = 0; i < base.size(); i++) {
			List<Integer> current = new ArrayList<>();
			current.addAll(base.get(i));
			current.addAll(listaMaior10.get(i));
			listaRetorno.add(current);
		}

		return new ArrayList<>(new HashSet<>(listaRetorno));
	}

	public static List<List<Integer>> gerarJogosIniciaisEstrategiaXdoTikTokFlex(int xTimes, int p1,
	        int p2) {
		final List<List<Integer>> preBase = CombinationUtils.gerarListaCombinacao(1, 10, p1);
		final List<List<Integer>> base = new ArrayList<>();
		for (int i = 0; i < xTimes; i++) {
			base.addAll(preBase);
		}

		List<List<Integer>> listaMaior10 = ListUtil.gerarListasMinMaxRemove(11, 25, p2,
		        base.size());

		List<List<Integer>> listaRetorno = new ArrayList<>();
		for (int i = 0; i < base.size(); i++) {
			List<Integer> current = new ArrayList<>();
			current.addAll(base.get(i));
			current.addAll(listaMaior10.get(i));
			listaRetorno.add(current);
		}

		return new ArrayList<>(new HashSet<>(listaRetorno));
	}

	private static String printList(String txt, List<Integer> lista) {
		if (txt == null || lista == null || lista.isEmpty()) {
			return StringUtils.EMPTY;
		}
		List<String> list = lista.stream().map(el -> String.valueOf(el))
		        .collect(Collectors.toList());
		return txt + "\t" + String.join("\t", list);
	}
}
