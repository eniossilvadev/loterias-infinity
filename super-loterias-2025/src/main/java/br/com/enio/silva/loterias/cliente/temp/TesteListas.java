package br.com.enio.silva.loterias.cliente.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;

public class TesteListas {

	public static List<Integer> getLista50() {
		Set<Integer> set = new HashSet<Integer>();
		Random rand = new Random();
		do {
			set.add(rand.nextInt(25) + 1);
		} while (set.size() <= 50);

		List<Integer> retorno = new ArrayList<Integer>(set);
		// System.out.println(retorno);
		Collections.sort(retorno);
		return retorno;
	}

	public static void main(String[] args) {

		List<Integer> line = new ArrayList<Integer>();
		FiltroMinimoLinhas filtro = new FiltroMinimoLinhas(10, 10, 3);
		for (int i = 0; i < 10; i++) {
			line = getLista50();
			System.out.println("Before" + line);
			line = filtro.filtrarLista(line);
			System.out.println("After" + line);
		}
	}

}
