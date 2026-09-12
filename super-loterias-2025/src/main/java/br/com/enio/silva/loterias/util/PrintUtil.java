package br.com.enio.silva.loterias.util;

import java.util.List;

import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;

public class PrintUtil {

	public static void imprimirJogosPontuados(List<JogoMega> jogosLM) {
		for (JogoAb meuJogo : jogosLM) {
			System.out.println(meuJogo.getPontuacao() + ": " + meuJogo.getNumerosAsList());
		}
	}

}
