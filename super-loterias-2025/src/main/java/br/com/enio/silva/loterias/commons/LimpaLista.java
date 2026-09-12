package br.com.enio.silva.loterias.commons;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LimpaLista {

	public static List<List<Integer>> getList(String path) {
		return ArquivoUtil.obterLinhasComoListasUnique(path);
	}

	public static void main(String[] args) throws IOException {

		Path pathNova = Paths.get("E:\\loterias\\lotomania\\clean\\nova.txt");
		Path pathExistente = Paths.get("E:\\loterias\\lotomania\\clean\\nova.txt");
		Path pathCampea = Paths.get(pathNova.getParent().toString(), "campea.txt");

		/**
		 * Remove os de <b>existente</> na lista <b>principal</>
		 */
		List<List<Integer>> nova = getList(pathNova.toString());
		List<List<Integer>> existente = getList(pathExistente.toString());
		List<List<Integer>> campea = getList(pathNova.toString());

		campea.removeAll(existente);
		existente.addAll(campea);

		ArquivoUtil.saveLists(nova, pathNova.toString(), "\t", 2);
		ArquivoUtil.saveLists(existente, pathExistente.toString(), "\t", 2);
		ArquivoUtil.saveLists(campea, pathCampea.toString(), ",", 2);
	}

}
