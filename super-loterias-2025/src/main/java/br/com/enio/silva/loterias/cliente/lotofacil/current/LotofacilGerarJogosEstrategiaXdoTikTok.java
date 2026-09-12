package br.com.enio.silva.loterias.cliente.lotofacil.current;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.diversos.EsquemasLotofacilUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class LotofacilGerarJogosEstrategiaXdoTikTok {

	private static String somenteNovo = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LSN.txt";

	private static String novosJogos = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJC.txt";

	private static String todosJogos = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJC.txt";

	private static String hoje = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\hoje.txt";

	public static void main(String[] args) throws IOException {

		List<List<Integer>> listaIncial = ArquivoUtil.obterLinhasComoListas(somenteNovo);
		final List<List<Integer>> listaX = EsquemasLotofacilUtil.gerarJogosEstrategiaXdoTikTok(listaIncial,
				true);

		List<List<Integer>> listaFinal = new ArrayList<>();
		listaFinal.addAll(listaIncial);
		listaFinal.addAll(listaX);

		ArquivoUtil.saveLists(listaX, somenteNovo, "\t", 2);
		ArquivoUtil.saveLists(listaFinal, novosJogos, "\t", 2);
		ArquivoUtil.saveLists(listaFinal, todosJogos, "\t", 2);
		ArquivoUtil.saveLists(listaX, hoje, ", ", 2);
	}

}
