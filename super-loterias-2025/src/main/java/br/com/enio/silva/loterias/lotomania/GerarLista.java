package br.com.enio.silva.loterias.lotomania;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.config.Config;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import br.com.silva.enio.loterias.commons.util.ZipUtil;
import br.com.silva.enio.loterias.controller.LoteriaRNAb;

public abstract class GerarLista {

	protected String caminhoDownload;

	protected String zipFileName;

	protected String caminhoHtml;

	public abstract List<List<Integer>> gerarArquivoResultado() throws IOException;

	public List<List<Integer>> gerarArquivoResultado(Config conf) throws IOException {
		return gerarArquivoResultado(conf.getCaminhoTodosResultados());
	}

	public List<List<Integer>> gerarArquivoResultado(LoteriaRNAb rn, String caminhoHtml)
			throws IOException {

		List<List<Integer>> sorteios = rn.getNumerosSorteados(caminhoHtml);
		System.out.println("Quantidade de Resultados: " + (sorteios.size()));
		return sorteios;
	}

	public List<List<Integer>> gerarArquivoResultado(String path) throws IOException {
		List<List<Integer>> retorno = ArquivoUtil.obterLinhasComoListas(path);
		System.err.println("Path: " + path);
		System.err.println("Quantidade Final de Resultados (********** NOVO ************): " + (retorno.size()));
		ArquivoUtil.saveLists(retorno, path, "\t", 2);
		return retorno;
	}

	protected void unzip(String zipFileName) {
		try {
			ZipUtil.unzip(zipFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
