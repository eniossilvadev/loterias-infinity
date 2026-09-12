package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GravaResultados {

	public void gravarResultados(List<List<Integer>> resultados, String caminhoSalvacao)
			throws IOException {
		gravarResultados(resultados, caminhoSalvacao, "\t");
	}

	public void gravarResultados(List<List<Integer>> resultados, String caminhoSalvacao,
			String separador) throws IOException {

		ArquivoUtil.saveLists(resultados, caminhoSalvacao, separador);
	}

	public void gravarResultadosHTLoto(List<List<Integer>> resultados, String caminhoSalvacao)
			throws IOException {
		ArquivoUtil.saveLists(resultados, caminhoSalvacao, " ");
	}
}
