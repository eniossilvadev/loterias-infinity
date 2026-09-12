package br.com.enio.silva.loterias.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.config.ConfigJogosComum;
import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public abstract class Base {

	protected static String theSep = ",";

	public static Random random = new Random();

	public static Map<Integer, Integer> countDupl(List<List<Integer>> list) {

		List<Integer> lista = new ArrayList<>();
		for (List<Integer> lst : list) {
			lista.addAll(lst);
		}

		Set<Integer> set = new TreeSet<>(lista);

		List<Integer> listaOriginal = new ArrayList<>(lista);
		Map<Integer, Integer> mapa = new HashMap<>();
		List<Integer> listaAtual = new ArrayList<>();
		for (Integer s : set) {
			int qtt = listaOriginal.size();
			listaAtual.add(s);
			listaOriginal.removeAll(listaAtual);
			qtt = qtt - listaOriginal.size();
			mapa.put(s, qtt);
		}
		return mapa;
	}

	public static Map<Integer, Integer> countDuplicado(List<Integer> lista) {

		Set<Integer> set = new TreeSet<>(lista);

		List<Integer> listaOriginal = new ArrayList<>(lista);
		Map<Integer, Integer> mapa = new HashMap<>();
		List<Integer> listaAtual = new ArrayList<>();
		for (Integer s : set) {
			int qtt = listaOriginal.size();
			listaAtual.add(s);
			listaOriginal.removeAll(listaAtual);
			qtt = qtt - listaOriginal.size();
			mapa.put(s, qtt);
		}
		return mapa;
	}

	protected static List<List<Integer>> gc(List<List<Integer>> theList, int size) {
		return LotoUtils.gc(theList, size);
	}

	protected static List<List<Integer>> gc2(List<List<Integer>> theList) {
		theList = CollectionsUtils.removeDuplicated(theList);
		theList.sort(new ListOfListComparator());
		return theList;
	}

	protected static List<Integer> getExclusions(List<Integer> last, List<List<Integer>> fullList) {
		return getExclusions(last, fullList, 15);
	}

	protected static List<Integer> getExclusions(List<Integer> last, List<List<Integer>> fullList,
			int maxListSize) {

		// if (last == null) {
		// return Collections.emptyList();
		// }

		List<Integer> lLast = new ArrayList<>(new HashSet<>(last));

		// if (lLast.isEmpty()) {
		// return Collections.emptyList();
		// }

		if (lLast.size() >= maxListSize) {
			Collections.shuffle(lLast);
			return lLast.subList(0, maxListSize);
		}

		List<Integer> plainFullList = getPlainFullList(fullList);
		if (plainFullList != null && !plainFullList.isEmpty()) {
			plainFullList.removeAll(lLast);
		}

		if (plainFullList == null || plainFullList.isEmpty()) {
			return lLast;
		}

		Collections.shuffle(plainFullList);

		Set<Integer> exclusions = new HashSet<>();
		Random rand = new Random();
		Set<Integer> retorno = new HashSet<>(lLast);
		while (retorno.size() < maxListSize && !plainFullList.isEmpty()) {
			int nextIndex = rand.nextInt(plainFullList.size());
			Integer nextNumber = plainFullList.get(nextIndex);

			retorno.add(nextNumber);

			exclusions.add(nextNumber);
			plainFullList.removeAll(exclusions);
			exclusions.remove(nextNumber);
		}

		List<Integer> ret = new ArrayList<>(retorno);
		Collections.sort(ret);
		return ret;
	}

	protected static List<Integer> getExclusions(List<Integer> last, List<List<Integer>> fullList,
			int maxListSize, List<Integer> include) {

		// if (last == null) {
		// return Collections.emptyList();
		// }

		List<Integer> lLast = new ArrayList<>(new HashSet<>(last));
		if (last != null && !last.isEmpty()) {
			lLast.removeAll(include);
		}

		// if (lLast.isEmpty()) {
		// return Collections.emptyList();
		// }

		if (lLast.size() >= maxListSize) {
			return lLast.subList(0, maxListSize);
		}

		List<Integer> plainFullList = getPlainFullList(fullList);
		if (plainFullList != null && !plainFullList.isEmpty()) {
			plainFullList.removeAll(lLast);
		}

		if (plainFullList == null || plainFullList.isEmpty()) {
			return lLast;
		}

		Set<Integer> exclusions = new HashSet<>();
		Random rand = new Random();
		Set<Integer> retorno = new HashSet<>(lLast);
		while (retorno.size() < maxListSize && !plainFullList.isEmpty()) {

			Collections.shuffle(plainFullList);

			int nextIndex = rand.nextInt(plainFullList.size());
			Integer nextNumber = plainFullList.get(nextIndex);

			retorno.add(nextNumber);

			exclusions.add(nextNumber);
			plainFullList.removeAll(exclusions);
			exclusions.remove(nextNumber);
			Collections.shuffle(plainFullList);
		}
		List<Integer> ret = new ArrayList<>(retorno);
		Collections.sort(ret);
		return ret;
	}

	public static List<List<Integer>> getListCombination(List<List<Integer>> listaIn, int K) {
		return CombinationUtils.gerarCombinacoes(listaIn, K);
	}

	public static Integer getMaisSaiu(List<List<Integer>> list, int size, Integer max) {
		List<Integer> l = new ArrayList<>();
		Map<Integer, Integer> mapa = countDupl(list);
		if (max != null && max > 0) {
			for (int i = 1; i <= max; i++) {
				if (mapa.get(i) == null) {
					mapa.put(i, 0);
				}
			}
		}
		mapa = MapUtil.sortByValueDesc(mapa);
		System.out.println(mapa);
		for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
			l.add(entry.getKey());
			if (l.size() == size) {
				Collections.shuffle(l);
				return l.get(0);
			}
		}

		return -1;
	}

	public static Integer getMenosSaiu(List<List<Integer>> list, int size) {
		return getMenosSaiu(list, size, null);
	}

	public static Integer getMenosSaiu(List<List<Integer>> list, int size, Integer max) {
		List<Integer> l = new ArrayList<>();
		Map<Integer, Integer> mapa = countDupl(list);
		if (max != null && max > 0) {
			for (int i = 1; i <= max; i++) {
				if (mapa.get(i) == null) {
					mapa.put(i, 0);
				}
			}
		}
		mapa = MapUtil.sortByValue(mapa);
		System.out.println(mapa);
		for (Map.Entry<Integer, Integer> entry : mapa.entrySet()) {
			l.add(entry.getKey());
			if (l.size() == size) {
				Collections.shuffle(l);
				return l.get(0);
			}
		}

		return -1;
	}

	protected static int getMrmj(QuinaConfigAb config) {
		int max = config.getNrosApostados() - 2;

		List<Integer> lista = new ArrayList<>();
		for (int i = 0; i < max; i++) {
			int incluir = i + 1;
			lista.add(incluir);
			for (int j = i; j < max; j++) {
				lista.add(i);
			}
		}
		Collections.shuffle(lista);
		Collections.shuffle(lista);
		Collections.shuffle(lista);
		return lista.get(0);
	}

	protected static int getMrmj(QuinaConfigAb config, int mrmj) {
		int max = config.getNrosApostados() - 2;
		return Math.min(mrmj + 1, max);
	}

	protected static <T extends ConfigJogosComum> int getMrmj(T config) {
		int max = config.getNrosApostados() - 2;

		List<Integer> lista = new ArrayList<>();
		for (int i = 0; i < max; i++) {
			int incluir = i + 1;
			lista.add(incluir);
			for (int j = i; j < max; j++) {
				lista.add(i);
			}
		}
		Collections.shuffle(lista);
		Collections.shuffle(lista);
		Collections.shuffle(lista);
		return lista.get(0);
	}

	protected static <T extends ConfigJogosComum> int getMrmj(T config, int mrmj) {
		int max = config.getNrosApostados() - 2;
		return Math.min(mrmj + 1, max);
	}

	protected static List<Integer> getPlainFullList(List<List<Integer>> fullList) {

		if (fullList != null && !fullList.isEmpty()) {

			return fullList.stream().flatMap(List::stream).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	protected static boolean isEmpty(Collection<?> value) {
		return value == null || value.isEmpty();
	}

	public static boolean isFiltrarAtual(List<List<Integer>> preJogos) {
		boolean filtrar = new Random().nextBoolean() || new Random().nextBoolean();
		return preJogos != null && !preJogos.isEmpty() && filtrar;
	}

	protected static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	/**
	 * @param mapResultado
	 * @param last
	 */
	protected static void obterTopLast(Map<Integer, Integer> mapResultado, List<Integer> last) {
		List<Integer> top10 = new ArrayList<Integer>();
		for (Map.Entry<Integer, Integer> entry : mapResultado.entrySet()) {
			Integer key = entry.getKey();
			if (last.contains(key)) {
				top10.add(key);
				if (top10.size() == 1) {
					break;
				}
			}
		}
		System.out.println(top10);
	}

	public static void print(List<List<Integer>> lista, String text) {
		System.out.println(text + "\t" + lista.size());
		lista.forEach(System.out::println);
		System.out.println("\n");
	}

	protected static void saveDefault(List<List<Integer>> lista, String path, int size)
			throws IOException {
		lista = gc(lista, size);
		ArquivoUtil.saveLists(lista, path, theSep, 2);
	}

	protected static void saveDefault2(List<List<Integer>> lista, String path) throws IOException {
		lista = gc2(lista);
		ArquivoUtil.saveLists(lista, path, theSep, 2);
	}

}
