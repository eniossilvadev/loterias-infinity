package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;


public class Lotofacil2024InverteAb extends GerarJogosLotofacil2020Ab {

	protected static String getOutPut(String input) {

		Path inputPath = Paths.get(input);

		String parent = inputPath.getParent().toString();
		String fileName[] = inputPath.getFileName().toString().split("\\.");
		var currTime = String.valueOf(System.currentTimeMillis());

		System.out.println(inputPath.getFileName().toString());

		var newName = fileName[0] + "_" + currTime + "." + fileName[1];

		Path newFile = Paths.get(parent, newName);

		return newFile.toString();
	}

	protected static boolean getRandomBoolean() {
		return random.nextBoolean();
	}

	//	public static void main(String[] args) {
	//		final String input = "E:\\loterias\\gerador-apostas\\lotofacil\\a.txt";
	//		System.out.println(getOutPut(input));
	//	}

}
