package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class CreateListaRemoverL2DeL1 {

	public static String lista01 = "C:\\loterias\\gerador-apostas\\mega_sena\\config\\todos_jogos.txt";

	// Valores a serem removidos
	public static String lista02 = "D:\\Meus Documentos\\�rea de Trabalho\\n.txt";

	public static List<List<Integer>> getList(List<List<Integer>> l) {
		return new ArrayList<List<Integer>>(new HashSet<>(l));
	}

	public static void main(String[] args) throws IOException {

		Path path = Paths.get(lista01);

		String base = path.getParent().toString();

		Path l3 = Paths.get(base, "Lista01MenosLista02.txt");

		Path l4 = Paths.get(base, "_lista02-lista01.txt");

		Path l5 = Paths.get(base, "_original_xor_remover.txt");

		List<List<Integer>> list1 = ArquivoUtil.obterLinhasComoListasUnique(lista01);
		List<List<Integer>> list2 = ArquivoUtil.obterLinhasComoListasUnique(lista02);

		List<List<Integer>> lista1 = getList(list1);
		List<List<Integer>> lista2 = getList(list2);

		lista1.removeAll(lista2);
		ArquivoUtil.saveLists(lista1, l3.toString(), "\t", 2);

		lista1 = getList(list2);
		lista2 = getList(list1);

		lista1.removeAll(lista2);
		ArquivoUtil.saveLists(lista1, l4.toString(), "\t", 2);

		lista1 = getList(list2);
		lista2 = getList(list1);

		@SuppressWarnings("unchecked")
		List<List<Integer>> union = ListUtils.union(lista1, lista2);
		@SuppressWarnings("unchecked")
		List<List<Integer>> intersection = ListUtils.intersection(lista1, lista2);
		union.removeAll(intersection);

		ArquivoUtil.saveLists(union, l5.toString(), "\t", 2);

	}
}
