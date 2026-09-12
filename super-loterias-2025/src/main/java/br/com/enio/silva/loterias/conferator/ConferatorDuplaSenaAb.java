package br.com.enio.silva.loterias.conferator;

import java.util.List;

public abstract class ConferatorDuplaSenaAb extends Conferator {

	@Override
	public JogoConfereAb getConfereImpl(int concurso, List<Integer> j, List<Integer> s) {
		return new JogoConfereDuplaSena(concurso, j, s);
	}

	public abstract String getDs();

	@Override
	public String getPathIn() {
		return "E:\\loterias\\dupla_sena\\curr";
	}

	@Override
	public String getPathOut() {
		return "E:\\loterias\\confere\\new\\" + getDs() + getDefaultName() + ".txt";
	}

}
