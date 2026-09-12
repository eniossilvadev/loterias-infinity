package br.com.enio.silva.loterias.lotomania;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Teste {

	public static void main(String[] args) {
		Integer[] numeros = { 1, 2, 3, 4, 5 };
		JogoLotomania meuJogo = new JogoLotomania(numeros);

		List<Integer> s1 = Arrays.asList(new Integer[] { 1, 2, 3 });
		List<Integer> s2 = Arrays.asList(new Integer[] { 1, 2, 6 });
		List<Integer> s3 = Arrays.asList(new Integer[] { 6, 7, 8 });
		List<List<Integer>> todosSorteios = new ArrayList<List<Integer>>();
		todosSorteios.add(s1);
		todosSorteios.add(s2);
		todosSorteios.add(s3);

		Pontuador pontuador = new PontuadorBasico();
		pontuador.pontuar(todosSorteios, meuJogo);

		System.out.println(meuJogo.getPontuacao());

	}
}
