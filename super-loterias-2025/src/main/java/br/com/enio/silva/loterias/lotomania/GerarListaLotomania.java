package br.com.enio.silva.loterias.lotomania;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.util.ConstantesUtil;

import br.com.silva.enio.loterias.controller.LotomaniaRN;

public class GerarListaLotomania extends GerarLista {

	public static GerarListaLotomania getInstance() {
		if (instance == null) {
			instance = new GerarListaLotomania();
		}
		return instance;
	}

	private static GerarListaLotomania instance;

	protected GerarListaLotomania() {
	}

	@Override
	public List<List<Integer>> gerarArquivoResultado() throws IOException {

		String zipFileName = ConstantesUtil.LM_CAMINHO_DOWNLOAD
		        + ConstantesUtil.LOTOMANIA_ZIP;

		unzip(zipFileName);

		String caminhoHtml = ConstantesUtil.LM_CAMINHO_DOWNLOAD
		        + ConstantesUtil.LOTOMANIA_HTML;

		return gerarArquivoResultado(LotomaniaRN.getInstance(), caminhoHtml);
	}

}
