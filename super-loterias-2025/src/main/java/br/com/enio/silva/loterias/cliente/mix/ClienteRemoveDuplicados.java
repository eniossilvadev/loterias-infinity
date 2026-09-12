package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;

import br.com.enio.silva.loterias.diversos.LotoUtils;

public class ClienteRemoveDuplicados {

	public static void main(String[] args) throws IOException {
		String path = "C:\\loterias\\gerador-apostas\\lotofacil\\curr\\01_20251222_20251222185010828.txt";

		LotoUtils.limpaLinhas(path);

		LotoUtils.removerDuplicados(path, ",", true);

		//		DesdobraCombinacao.desdobraCombinacao(path, path, 15, false);

	}

}
