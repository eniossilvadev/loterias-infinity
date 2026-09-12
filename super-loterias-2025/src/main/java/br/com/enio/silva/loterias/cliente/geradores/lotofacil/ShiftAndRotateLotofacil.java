package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.cliente.geradores.PositionalReplacement;
import br.com.enio.silva.loterias.cliente.geradores.ShiftBy;
import br.com.enio.silva.loterias.diversos.LotoUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class ShiftAndRotateLotofacil {

	public static void main(String[] args) throws IOException {
		final String pathMatriz = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\a.txt";
		final String finalCleanListPath = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\a.txt";
		final int max = 25;
		final String sep = ",";

		final List<List<Integer>> matriz = ArquivoUtil.obterLinhasComoListasUnique(pathMatriz);
		final List<List<Integer>> positionalList = PositionalReplacement.getPositionalReplacement(matriz, max);
		final List<List<Integer>> shiftedList = ShiftBy.getShifted(matriz, max);

		final List<List<Integer>> finalList = new ArrayList<>(matriz);
		finalList.addAll(positionalList);
		finalList.addAll(shiftedList);
		final List<List<Integer>> cleanList = LotoUtils.gc(finalList);
		ArquivoUtil.saveLists(cleanList, finalCleanListPath, sep, 2);

	}

}
