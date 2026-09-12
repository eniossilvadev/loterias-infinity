package br.com.enio.silva.loterias.conferator;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.commons.SaveMegaSena;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;

public class ConferatorMegaSena extends Conferator {

	@Override
	public JogoConfereAb getConfereImpl(int concurso, List<Integer> j, List<Integer> s) {
		return new JogoConfereMegaSena(concurso, j, s);
	}

	@Override
	public String getPathIn() {
		SaveMegaSena saveMegaSena = new SaveMegaSena();
		return saveMegaSena.getCurr();
	}

	@Override
	public String getPathOut() {
		return "E:\\loterias\\confere\\new\\ms_" + getDefaultName() + ".txt";
	}

	@Override
	public List<List<Integer>> getResultados() {
		try {
			String path = CaminhoResultados.MEGA_SENA.getPath();
			List<List<Integer>> resultados = GerarListaMega.getInstance()
					.gerarArquivoResultado(path);
			return resultados;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}
