package br.com.enio.silva.loterias.cliente.temp;

import java.io.File;

public class _con {

	public static void main(String[] args) {

		String input = "E:\\loterias\\testes\\input.txt";

		File f = new File(input);
		String output = f.getParent();

		System.out.println(output);
	}

}
