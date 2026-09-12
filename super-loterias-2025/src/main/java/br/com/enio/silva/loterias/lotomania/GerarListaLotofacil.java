package br.com.enio.silva.loterias.lotomania;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.util.ConstantesUtil;
import br.com.enio.silva.loterias.util.ContantesIf;

import br.com.silva.enio.loterias.controller.LotofacilRN;

public class GerarListaLotofacil extends GerarLista {

	private static GerarListaLotofacil instance;

	public static GerarListaLotofacil getInstance() {
		if (instance == null) {
			instance = new GerarListaLotofacil();
		}
		return instance;
	}

	protected GerarListaLotofacil() {
	}

	@Override
	public List<List<Integer>> gerarArquivoResultado() throws IOException {

		String zipName = ConstantesUtil.LOTOFACIL_ZIP;
		String base = ConstantesUtil.LF_CAMINHO_DOWNLOAD;

		// zipFileName = base + zipName;
		// unzip(zipFileName);

		caminhoHtml = base + ContantesIf.FilePath.LOTOFACIL_HTML;

		return gerarArquivoResultado(LotofacilRN.getInstance(), caminhoHtml);

	}
}
