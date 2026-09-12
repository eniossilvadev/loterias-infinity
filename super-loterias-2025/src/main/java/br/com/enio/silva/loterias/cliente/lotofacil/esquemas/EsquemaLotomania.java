package br.com.enio.silva.loterias.cliente.lotofacil.esquemas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.LoteriaUtil;

public class EsquemaLotomania {

	public static List<Integer> clean(List<Integer> lista) {
		return new ArrayList<>(new HashSet<>(lista));
	}

	public static List<List<Integer>> converterEsquema80n0f10a(List<Integer> numeros)
	        throws br.com.enio.silva.loterias.exception.InvalidLenghtException {

		int size = 80;

		String err = null;
		if (numeros == null) {
			err = "O par�metro \"numeros\" não pode ser nulo.";
			throw new InvalidLenghtException(err);
		}

		if (numeros.size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos.";
			throw new InvalidLenghtException(err);
		}

		if (new HashSet<Integer>(numeros).size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos distintos.";
			throw new InvalidLenghtException(err);
		}

		List<Integer> fixas = new ArrayList<Integer>();
		Collections.sort(numeros);
		for (int i = 0; i < 5; i++) {
			fixas.add(numeros.get(i));
		}

		List<Integer> restante = new ArrayList<Integer>(numeros);
		restante.removeAll(fixas);

		Collections.shuffle(restante);
		int n = 15;
		List<Integer> a = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(a);
		List<Integer> b = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(b);
		List<Integer> c = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(c);
		List<Integer> d = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(d);
		List<Integer> e = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(e);

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		List<Integer> ret = null;

		// ABC
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(c);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ABD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ABE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ACD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(c);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ACE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(c);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ADE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BCD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(c);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BCE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(c);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BDE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// CDE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(c);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		return retorno;

	}

	public static Collection<? extends List<Integer>> esquema65n0f14a(List<Integer> numeros)
	        throws InvalidLenghtException {

		int size = 65;

		String err = null;
		if (numeros == null) {
			err = "O par�metro \"numeros\" não pode ser nulo.";
			throw new InvalidLenghtException(err);
		}

		if (numeros.size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos.";
			throw new InvalidLenghtException(err);
		}

		if (new HashSet<Integer>(numeros).size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos distintos.";
			throw new InvalidLenghtException(err);
		}

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		int[] pos1 = { 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 20, 21, 22, 24,
		        25, 26, 27, 29, 30, 31, 33, 36, 37, 39, 40, 41, 42, 43, 45, 46, 49, 50, 51, 53, 54,
		        55, 56, 59, 60, 61, 62, 64, 65 };
		int[] pos2 = { 1, 2, 3, 4, 6, 7, 8, 10, 15, 17, 18, 19, 21, 22, 23, 25, 26, 27, 28, 29, 30,
		        31, 32, 33, 34, 35, 36, 38, 39, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54,
		        55, 56, 57, 58, 59, 60, 63, 64 };
		int[] pos3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 23, 24,
		        28, 30, 32, 33, 34, 35, 37, 38, 39, 40, 41, 43, 44, 46, 47, 48, 49, 50, 51, 52, 54,
		        56, 57, 58, 60, 61, 62, 63, 65 };
		int[] pos4 = { 1, 2, 3, 5, 6, 7, 8, 10, 12, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
		        28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 42, 44, 45, 47, 48, 49, 50, 52, 53,
		        54, 55, 57, 58, 59, 62, 63, 64 };
		int[] pos5 = { 1, 2, 3, 6, 7, 8, 9, 10, 11, 14, 15, 17, 18, 19, 21, 22, 23, 25, 26, 27, 28,
		        29, 30, 31, 32, 33, 34, 35, 36, 38, 39, 40, 41, 42, 44, 45, 47, 48, 50, 52, 53, 54,
		        55, 57, 58, 59, 61, 63, 64, 65 };
		int[] pos6 = { 1, 4, 5, 6, 8, 9, 11, 12, 13, 14, 16, 17, 19, 20, 21, 23, 24, 25, 26, 28, 29,
		        30, 32, 34, 35, 37, 38, 39, 40, 41, 43, 44, 45, 46, 47, 48, 49, 51, 52, 54, 55, 56,
		        57, 58, 60, 61, 62, 63, 64, 65 };
		int[] pos7 = { 1, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 20, 22, 23, 24, 27, 28, 30,
		        31, 32, 34, 35, 37, 38, 39, 40, 41, 42, 43, 44, 46, 47, 48, 49, 51, 52, 53, 54, 56,
		        57, 58, 59, 60, 61, 62, 63, 65 };
		int[] pos8 = { 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 20, 21, 22, 24,
		        25, 26, 27, 29, 30, 35, 37, 38, 39, 40, 41, 43, 44, 46, 47, 48, 49, 50, 51, 52, 54,
		        56, 57, 58, 60, 61, 62, 63, 65 };
		int[] pos9 = { 1, 2, 3, 5, 6, 7, 8, 10, 12, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
		        28, 29, 30, 31, 32, 33, 36, 37, 40, 41, 42, 43, 44, 45, 46, 49, 50, 51, 52, 53, 55,
		        56, 58, 59, 60, 61, 62, 64, 65 };
		int[] pos10 = { 1, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 16, 17, 19, 20, 22, 23, 24, 27, 28,
		        30, 31, 32, 34, 35, 37, 38, 39, 40, 41, 43, 44, 45, 46, 47, 48, 49, 51, 52, 54, 55,
		        56, 57, 58, 60, 61, 62, 63, 64, 65 };
		int[] pos11 = { 2, 3, 4, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 20, 21, 22, 24, 25,
		        26, 27, 28, 29, 31, 32, 33, 36, 37, 40, 41, 42, 43, 44, 45, 46, 49, 50, 51, 52, 53,
		        55, 56, 58, 59, 60, 61, 62, 64, 65 };
		int[] pos12 = { 2, 3, 4, 5, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23, 24,
		        25, 26, 27, 29, 31, 33, 36, 37, 40, 41, 42, 43, 45, 46, 47, 48, 49, 50, 51, 52, 53,
		        55, 56, 59, 60, 61, 62, 63, 64, 65 };
		int[] pos13 = { 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 21, 22, 23, 24, 25,
		        26, 27, 29, 31, 33, 34, 35, 36, 37, 40, 41, 42, 43, 45, 46, 49, 50, 51, 52, 53, 55,
		        56, 57, 59, 60, 61, 62, 64, 65 };
		int[] pos14 = { 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18, 20, 21, 22, 23, 24, 25,
		        26, 27, 29, 31, 33, 36, 37, 40, 41, 42, 43, 45, 46, 47, 48, 49, 50, 51, 52, 53, 55,
		        56, 59, 60, 61, 62, 63, 64, 65 };

		retorno.add(EsquemaUtil.getByPosition(numeros, pos1));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos2));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos3));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos4));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos5));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos6));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos7));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos8));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos9));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos10));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos11));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos12));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos13));
		retorno.add(EsquemaUtil.getByPosition(numeros, pos14));

		return retorno;
	}

	public static List<JogoLotomania> esquema80n0f10a(JogoLotomania jogo,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso)
	        throws InvalidLenghtException {
		List<List<Integer>> numeros = esquema80n0f10a(jogo.getNumerosAsList(), mapResultado,
		        mapAtraso);
		List<JogoLotomania> jogos = new ArrayList<>();
		numeros.forEach(j -> {
			jogos.add(new JogoLotomania(j.toArray(new Integer[j.size()])));
		});
		return jogos;
	}

	/**
	 * http://www.soloterias.net.br/2011/05/apostando-com-80-dezenas-na-
	 * lotomania.html
	 *
	 * @param numeros
	 * @param mapResultado
	 * @param mapAtraso
	 * @return
	 * @throws InvalidLenghtException
	 */
	public static List<List<Integer>> esquema80n0f10a(List<Integer> numeros,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso)
	        throws InvalidLenghtException {

		int size = 80;

		String err = null;
		if (numeros == null) {
			err = "O par�metro \"numeros\" não pode ser nulo.";
			throw new InvalidLenghtException(err);
		}

		if (numeros.size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos.";
			throw new InvalidLenghtException(err);
		}

		if (new HashSet<Integer>(numeros).size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos distintos.";
			throw new InvalidLenghtException(err);
		}

		List<Integer> fixas = new ArrayList<Integer>();
		fixas.addAll(EsquemaUtil.getFromMap(numeros, mapResultado, 2));
		Map<Integer, Integer> atr = new HashMap<>(mapAtraso);
		fixas.forEach(f -> atr.remove(f));
		fixas.addAll(EsquemaUtil.getFromMap(numeros, atr, 3));

		List<Integer> restante = new ArrayList<Integer>(numeros);
		restante.removeAll(fixas);

		Collections.shuffle(restante);
		int n = 15;
		List<Integer> a = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(a);
		List<Integer> b = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(b);
		List<Integer> c = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(c);
		List<Integer> d = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(d);
		List<Integer> e = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(e);

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		List<Integer> ret = null;

		// ABC
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(c);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ABD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ABE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ACD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(c);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ACE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(c);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ADE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BCD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(c);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BCE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(c);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BDE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// CDE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(c);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		return retorno;

	}

	/**
	 * http://www.soloterias.net.br/2011/05/apostando-com-80-dezenas-na-
	 * lotomania.html
	 *
	 * @param numeros
	 * @param mapResultado
	 * @param mapAtraso
	 * @return
	 * @throws InvalidLenghtException
	 */
	public static List<List<Integer>> esquema80n0f10aRandom(List<Integer> numeros,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso)
	        throws InvalidLenghtException {

		int size = 80;

		String err = null;
		if (numeros == null) {
			err = "O par�metro \"numeros\" não pode ser nulo.";
			throw new InvalidLenghtException(err);
		}

		if (numeros.size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos.";
			throw new InvalidLenghtException(err);
		}

		if (new HashSet<Integer>(numeros).size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos distintos.";
			throw new InvalidLenghtException(err);
		}

		Collections.shuffle(numeros);
		List<Integer> fixas = numeros.subList(0, 5);
		List<Integer> restante = new ArrayList<Integer>(numeros);
		restante.removeAll(fixas);

		Collections.shuffle(restante);
		int n = 15;
		List<Integer> a = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(a);
		List<Integer> b = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(b);
		List<Integer> c = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(c);
		List<Integer> d = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(d);
		List<Integer> e = new ArrayList<Integer>(restante.subList(0, n));
		restante.removeAll(e);

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();
		List<Integer> ret = null;

		// ABC
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(c);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ABD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ABE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(b);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ACD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(c);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ACE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(c);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// ADE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(a);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BCD
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(c);
		ret.addAll(d);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BCE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(c);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// BDE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(b);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		// CDE
		ret = new ArrayList<Integer>();
		ret.addAll(fixas);
		ret.addAll(c);
		ret.addAll(d);
		ret.addAll(e);
		Collections.sort(ret);
		retorno.add(clean(ret));

		return retorno;

	}

	public static List<List<Integer>> esquemaNetsorte100n30f21a(List<Integer> fixas,
	        Map<Integer, Integer> mapAtraso) throws InvalidLenghtException {

		int size = 30;

		String err = null;
		if (fixas == null) {
			err = "O par�metro \"numeros\" não pode ser nulo.";
			throw new InvalidLenghtException(err);
		}

		if (fixas.size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos.";
			throw new InvalidLenghtException(err);
		}

		if (new HashSet<Integer>(fixas).size() != size) {
			err = "O par�metro \"numeros\" deve conter " + size + " elementos distintos.";
			throw new InvalidLenghtException(err);
		}

		List<Integer> restante = LoteriaUtil
		        .getArrayCompleto(ConstantesUtil.LOTOMANIA_MAIOR_NUMERO);
		restante.removeAll(fixas);

		System.out.println("Fixas: " + fixas);
		System.out.println("Restantes: " + restante);

		Collections.shuffle(restante);
		List<Integer> grupo1 = getSublista(restante, 10);
		restante.removeAll(grupo1);

		List<Integer> grupo2 = EsquemaUtil.getFromMap(restante, mapAtraso, 10);
		restante.removeAll(grupo2);

		Collections.shuffle(restante);
		List<Integer> grupo3 = getSublista(restante, 10);
		restante.removeAll(grupo3);

		List<Integer> grupo4 = EsquemaUtil.getFromMap(restante, mapAtraso, 10);
		restante.removeAll(grupo4);

		Collections.shuffle(restante);
		List<Integer> grupo5 = getSublista(restante, 10);
		restante.removeAll(grupo5);

		List<Integer> grupo6 = EsquemaUtil.getFromMap(restante, mapAtraso, 10);
		restante.removeAll(grupo6);

		Collections.shuffle(restante);
		List<Integer> grupo7 = getSublista(restante, 10);
		restante.removeAll(grupo7);

		System.out.println(restante);
		System.out.println(grupo1);
		System.out.println(grupo2);
		System.out.println(grupo3);
		System.out.println(grupo4);
		System.out.println(grupo5);
		System.out.println(grupo6);
		System.out.println(grupo7);

		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		// F12
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo1, grupo2));

		// F13
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo1, grupo3));

		// F14
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo1, grupo4));

		// F15
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo1, grupo5));

		// F16
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo1, grupo6));

		// F17
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo1, grupo7));

		// F23
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo2, grupo3));

		// F24
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo2, grupo4));

		// F25
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo2, grupo5));

		// F26
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo2, grupo6));

		// F27
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo2, grupo7));

		// F34
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo3, grupo4));

		// F35
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo3, grupo5));

		// F36
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo3, grupo6));

		// F37
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo3, grupo7));

		// F45
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo4, grupo5));

		// F46
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo4, grupo6));

		// F47
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo4, grupo7));

		// F56
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo5, grupo6));

		// F57
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo5, grupo7));

		// F67
		retorno.add(EsquemaUtil.getMergedList(fixas, grupo6, grupo7));

		return retorno;
	}

	/**
	 * @param restante
	 * @return
	 */
	private static List<Integer> getSublista(List<Integer> restante, int tamanho) {
		List<Integer> retorno = new ArrayList<Integer>();
		for (int i = 0; i < tamanho; i++) {
			retorno.add(restante.get(i));
		}
		return retorno;
	}
}
