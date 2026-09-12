package br.com.enio.silva.loterias.cliente.temp;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PahtTest {

	public static void main(String[] args) {
		String file = "E:\\loterias\\dupla_sena\\config\\novos_jogos.txt";
		Path path = Paths.get(file);
		System.out.println(path.getFileName());
		System.out.println(path.getParent());
		System.out.println(path.getRoot());
		System.out.println(path.getNameCount());
		System.out.println(path);
		System.out.println(path);

		Path p2 = Paths.get(path.getParent().toString(), path.getFileName().toString());
		System.out.println("p2: " + p2);
	}

}
