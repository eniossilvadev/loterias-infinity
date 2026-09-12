package br.com.enio.silva.loterias.cliente.mix;

import java.io.IOException;

import br.com.enio.silva.loterias.diversos.LotoUtils;

public class LimpaLoto {

	public static void main(String[] args) throws IOException {

		final String path = "D:\\Meus Documentos\\Downloads\\sorted.txt";
		final String sep = ",";
		final int size = 15;

		//		LotoUtils.limparLoto(path, sep); // Limpar copiado do site da caixa

		LotoUtils.limpaLinhas(path);

		LotoUtils.removerDuplicados(path, sep, true);

		//		LotoUtils.removerPorTamanho(path, sep, size);

	}

}
