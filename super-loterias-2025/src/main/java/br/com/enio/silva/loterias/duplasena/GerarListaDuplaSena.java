package br.com.enio.silva.loterias.duplasena;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.lotomania.GerarLista;
import br.com.enio.silva.loterias.util.ConstantesUtil;

import br.com.silva.enio.loterias.controller.DuplaSenaRN;
import br.com.silva.enio.loterias.controller.DuplaSenaRNS1;
import br.com.silva.enio.loterias.controller.DuplaSenaRNS2;

public class GerarListaDuplaSena extends GerarLista {

	private static GerarListaDuplaSena instance;

	public static GerarListaDuplaSena getInstance() {
		if (instance == null) {
			instance = new GerarListaDuplaSena();
		}
		return instance;
	}

	protected GerarListaDuplaSena() {
	}

	@Override
	public List<List<Integer>> gerarArquivoResultado() throws IOException {

		String zipName = ConstantesUtil.DUPLA_SENA_ZIP;
		String base = ConstantesUtil.DUPLA_SENA_CAMINHO_DOWNLOAD;

		zipFileName = base + zipName;
		unzip(zipFileName);

		caminhoHtml = base + ConstantesUtil.DUPLA_SENA_HTML;

		return gerarArquivoResultado(DuplaSenaRN.getInstance(), caminhoHtml);

	}

	public List<List<Integer>> gerarArquivoResultado1() throws IOException {

		String zipName = ConstantesUtil.DUPLA_SENA_ZIP;
		String base = ConstantesUtil.DUPLA_SENA_CAMINHO_DOWNLOAD;

		zipFileName = base + zipName;
		unzip(zipFileName);

		caminhoHtml = base + ConstantesUtil.DUPLA_SENA_HTML;

		return gerarArquivoResultado(DuplaSenaRNS1.getInstance(), caminhoHtml);

	}

	public List<List<Integer>> gerarArquivoResultado2() throws IOException {

		String zipName = ConstantesUtil.DUPLA_SENA_ZIP;
		String base = ConstantesUtil.DUPLA_SENA_CAMINHO_DOWNLOAD;

		zipFileName = base + zipName;
		unzip(zipFileName);

		caminhoHtml = base + ConstantesUtil.DUPLA_SENA_HTML;

		return gerarArquivoResultado(DuplaSenaRNS2.getInstance(), caminhoHtml);

	}
}
