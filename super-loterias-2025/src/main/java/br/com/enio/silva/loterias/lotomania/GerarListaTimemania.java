package br.com.enio.silva.loterias.lotomania;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.util.ConstantesUtil;

import br.com.silva.enio.loterias.controller.TimemaniaRN;

public class GerarListaTimemania extends GerarLista {

	private static GerarListaTimemania instance;

	public static GerarListaTimemania getInstance() {
		if (instance == null) {
			instance = new GerarListaTimemania();
		}
		return instance;
	}

	protected GerarListaTimemania() {
	}

	@Override
	public List<List<Integer>> gerarArquivoResultado() throws IOException {

		String zipName = ConstantesUtil.TIMEMANI_ZIP;
		String base = ConstantesUtil.TIMEMANIA_CAMINHO_DOWNLOAD;

		zipFileName = base + zipName;
		unzip(zipFileName);

		caminhoHtml = base + ConstantesUtil.TIMEMANIA_HTML;

		return gerarArquivoResultado(TimemaniaRN.getInstance(), caminhoHtml);
	}
}
