package br.com.enio.silva.loterias.conferator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.enio.silva.loterias.util.ListaUtils;
import com.google.common.collect.Sets;

public abstract class JogoConfereAb {

	protected List<Integer> jogo;

	protected List<Integer> sorteados;

	protected Boolean isPremiado;

	protected List<Integer> premiacao;

	protected List<Integer> marcados;

	protected int pontos;

	protected int concurso;

	public JogoConfereAb(int concurso, List<Integer> jogo, List<Integer> sorteados) {
		this.concurso = concurso;
		this.jogo = jogo;
		this.sorteados = sorteados;
		this.premiacao = getPremiacao();
		this.calcular();
	}

	private void calcular() {
		Set<Integer> setJogo = Sets.newHashSet(jogo);
		Set<Integer> setSorteados = Sets.newHashSet(sorteados);
		this.marcados = new ArrayList<>(Sets.intersection(setJogo, setSorteados));
		this.pontos = marcados.size();
		this.isPremiado = this.premiacao.contains(pontos);
	}

	public int getConcurso() {
		return concurso;
	}

	public List<Integer> getJogo() {
		return jogo;
	}

	public String getJogoStr() {
		return ListaUtils.getAsString(getJogo(), 2);
	}

	public List<Integer> getMarcados() {
		return marcados;
	}

	public String getMarcadosStr() {
		int quatidadeAcertados = getMarcados().size();
		return quatidadeAcertados > 0? ListaUtils.getAsString(getMarcados(), 2) : "[x]";
	}

	public int getPontos() {
		return pontos;
	}

	public abstract List<Integer> getPremiacao();

	public List<Integer> getSorteados() {
		return sorteados;
	}

	public Boolean isPremiado() {
		return isPremiado;
	}

	public String isPremiadoStr() {
		return isPremiado ? "Sim": "Não";
	}

}
