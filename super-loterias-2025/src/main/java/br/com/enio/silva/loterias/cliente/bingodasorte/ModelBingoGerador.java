package br.com.enio.silva.loterias.cliente.bingodasorte;

import java.util.ArrayList;
import java.util.List;

public class ModelBingoGerador {

	private List<List<Integer>> jogosTradicionais = new ArrayList<>();

	private List<Integer> mix = new ArrayList<>();

	public void addJogoTradicional(List<Integer> jogo) {
		this.jogosTradicionais.add(jogo);
	}

	public List<List<Integer>> getAll() {
		List<List<Integer>> all = new ArrayList<>();
		if (jogosTradicionais != null && !jogosTradicionais.isEmpty()) {
			all.addAll(jogosTradicionais);
		}
		if (mix != null && !mix.isEmpty()) {
			all.add(mix);
		}
		return all;
	}

	public List<List<Integer>> getJogosTradicionais() {
		return jogosTradicionais;
	}

	public List<Integer> getMix() {
		return mix;
	}

	public void setJogosTradicionais(List<List<Integer>> jogosTradicionais) {
		this.jogosTradicionais = jogosTradicionais;
	}

	public void setMix(List<Integer> mix) {
		this.mix = mix;
	}
}
