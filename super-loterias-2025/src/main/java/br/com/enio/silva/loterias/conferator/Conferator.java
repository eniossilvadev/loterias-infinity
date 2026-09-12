package br.com.enio.silva.loterias.conferator;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.diversos.LotoUtils;
import br.com.enio.silva.loterias.util.DateUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public abstract class Conferator {

	public static void main(String[] args) throws IOException {
	}

	protected int concurso;

	protected List<List<Integer>> resultados;

	public void confere(final int concurso) {
		confere(concurso, false);
	}

	public void confere(final int concurso, boolean somentePremiados) {

		final int concursoLista = concurso - 1;

		final String pathIn = getPathIn();
		final String pathOut = getPathOut();

		if (resultados == null || resultados.isEmpty()) {
			resultados = getResultados();
		}

		if (concursoLista > resultados.size() - 1) {
			System.out.println("Concurso não apurado.");
		} else {
			final List<Integer> sorteados = resultados.get(concursoLista);

			List<List<Integer>> full = LotoUtils.getAll(pathIn);

			final StringBuilder str = new StringBuilder();
			full.forEach(j -> {

				JogoConfereAb confere = getConfereImpl(concurso, j, sorteados);

				boolean premiado = confere.isPremiado();

				boolean imprimir = somentePremiados ? premiado : true;

				if (imprimir) {
					str.append("[").append(concurso).append("]\t");
					str.append("[").append(confere.isPremiadoStr()).append("]\t");
					if (confere.isPremiado()) {
						str.append("[").append(confere.getMarcados().size()).append("]\t");
					} else {
						str.append("[  ]\t");
					}
					str.append("[").append(confere.getJogoStr()).append("]\t");

					if (confere.getMarcados().size() > 0) {
						str.append("[").append(confere.getMarcadosStr()).append("]\t");

					} else {
						str.append("[*]\t");
					}
					str.append("\n");
				}

			});

			// System.out.println(str.toString());
			ArquivoUtil.safeSave(str.toString(), pathOut);

		}
	}

	public int getConcurso() {
		return this.concurso;
	}

	public abstract JogoConfereAb getConfereImpl(int concurso, List<Integer> j, List<Integer> s);

	public String getDate() {
		return DateUtils.getCurrentDefaultDate();
	}

	public String getDateTime() {
		return DateUtils.getCurrentDefaultDateTime();
	}

	public String getDefaultName() {
		return getDate() + "_" + getConcurso() + "_" + getDateTime();
	}

	public abstract String getPathIn();

	public abstract String getPathOut();

	public abstract List<List<Integer>> getResultados();

}
