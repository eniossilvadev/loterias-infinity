package br.com.enio.silva.loterias.util;

import java.util.ArrayList;
import java.util.List;

public class LoteriaUtil {

	public static List<Integer> getArrayCompleto(int max) {
		List<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < max; i++) {
			tmp.add(i + 1);
		}
		return tmp;
	}

}
