package br.com.enio.silva.loterias.conferator;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaLotofacil;

public class ConferatorLotofacil extends Conferator {

	@Override
	public JogoConfereAb getConfereImpl(int concurso, List<Integer> j, List<Integer> s) {
		return new JogoConfereLotofacil(concurso, j, s);
	}

	@Override
	public String getPathIn() {
		return "C:\\loterias\\gerador-apostas\\lotofacil\\curr";
	}

	@Override
	public String getPathOut() {
		return "E:\\loterias\\confere\\new\\lf_" + getDefaultName() + ".txt";
	}

	@Override
	public List<List<Integer>> getResultados() {
		try {
			String path = CaminhoResultados.LOTOFACIL.getPath();
			List<List<Integer>> resultados = GerarListaLotofacil.getInstance()
					.gerarArquivoResultado(path);
			return resultados;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}
