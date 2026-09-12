package br.com.enio.silva.loterias.conferencia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.util.ConstantesUtil;

public class Conferencia {

	public static final String pJogos = "E:\\loterias\\lotomania\\";

	public static final String pResultados = ConstantesUtil.LM_CAMINHO_DOWNLOAD + "RESULTADOS.txt";

	private static File[] getJogos() {
		return null;
	}

	private static List<Integer> getResultados() {
		return null;
	}

	public static void main(String[] args) {

		List<Integer> resultados = getResultados();
		File[] arquivosJogos = getJogos();
		for (File f : arquivosJogos) {

		}
		List<Integer> jogos = new ArrayList<>();

		System.out.println(resultados);
		System.out.println(jogos);
	}

}
