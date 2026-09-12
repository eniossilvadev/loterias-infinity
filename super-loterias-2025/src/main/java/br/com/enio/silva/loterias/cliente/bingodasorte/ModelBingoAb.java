package br.com.enio.silva.loterias.cliente.bingodasorte;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelBingoAb implements Comparable<ModelBingoAb> {

	protected static int safeId = 1;

	protected long id;

	protected String nome;

	protected List<Integer> jogos;

	protected List<Integer> restantes;

	protected int pontuacao = 0;

	@Override
	public int compareTo(ModelBingoAb o) {
		int c = this.getPontuacao() - o.getPontuacao();
		if (c == 0) {

			c = getComp(this.getRestantes(), o.getRestantes());

			if (c == 0) {
				c = (int) (this.getId() - o.getId());
			}
		}
		return c;
	}

	private int getComp(List<Integer> o1, List<Integer> o2) {
		int c = 0;
		for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
			c = o1.get(i).compareTo(o2.get(i));
			if (c != 0) {
				return c;
			}
		}

		return Integer.compare(o1.size(), o2.size());
	}

	public long getId() {
		return id;
	}

	public List<Integer> getJogos() {
		return jogos;
	}

	public String getJogosAsString() {
		StringBuilder jogo = new StringBuilder();
		jogos.forEach(j -> jogo.append(String.format("%02d ", j)));
		return jogo.toString();
	}

	public List<String> getJogosAsStringList() {
		List<String> retorno = jogos.stream().map(m -> m.toString()).collect(Collectors.toList());
		return retorno;
	}

	public String getNome() {
		return nome;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public List<Integer> getRestantes() {
		return restantes;
	}

	public void pontuar(List<Integer> sorteio) {
		try {
			if (this.getJogos() != null) {
				this.setPontuacao(this.getJogos().stream().filter(sorteio::contains)
						.collect(Collectors.toList()).size());
				this.setRestantes(this.getJogos().stream().filter(s -> !sorteio.contains(s))
						.collect(Collectors.toList()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(this.getJogos());
			System.out.println(sorteio);
		}
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setJogos(List<Integer> jogos) {
		this.jogos = jogos;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public void setRestantes(List<Integer> restantes) {
		this.restantes = restantes;
	}

	@Override
	public String toString() {
		try {
			String ident = String.format("%05d", id);
			StringBuilder jogo = new StringBuilder();
			if (jogos != null) {
				jogos.forEach(j -> jogo.append(String.format("%02d ", j)));
			}

			StringBuilder rest = new StringBuilder();
			if (restantes != null && !restantes.isEmpty()) {
				restantes.forEach(j -> rest.append(String.format("%02d ", j)));
			}
			StringBuilder sort = new StringBuilder();
			List<Integer> sorteados = jogos != null ? new ArrayList<>(jogos) : new ArrayList<>();
			if (restantes != null && !restantes.isEmpty()) {
				sorteados.removeAll(restantes);
			}
			sorteados.forEach(s -> sort.append(String.format("%02d ", s)));

			return "id=" + ident + ", jogos=" + jogo.toString() + ", sorteados=" + sort
					+ ", restantes=" + rest + ", pontuacao=" + pontuacao + ", nome=" + nome + "";
		} catch (Exception e) {
			return id + "xxx";
		}
	}

}
