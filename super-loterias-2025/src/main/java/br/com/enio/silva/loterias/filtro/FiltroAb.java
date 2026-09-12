package br.com.enio.silva.loterias.filtro;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.enio.silva.loterias.lotomania.JogoAb;

public abstract class FiltroAb implements FiltroIf {

	private static DecimalFormat df2 = new DecimalFormat("#.####");

	@Override
	public List<? extends JogoAb> filtrarJogos(List<? extends JogoAb> jogos) {
		System.out.println(
		        this.getClass().getSimpleName() + " -> antes do filtro -> " + jogos.size());

		List<JogoAb> resultado = new ArrayList<JogoAb>();

		List<Integer> tmp = new ArrayList<>();
		for (JogoAb jogo : jogos) {
			tmp = filtrarLista(jogo.getNumerosAsList());
			if (tmp != null && tmp.size() > 0) {
				resultado.add(jogo);
			}
		}

		System.out.println(
		        this.getClass().getSimpleName() + " -> depois do filtro -> " + resultado.size());

		return resultado;
	}

	@Override
	public List<List<Integer>> filtrarListas(List<List<Integer>> listas) {

		String clazz = this.getClass().getSimpleName();
		int before = listas.size();

		LocalDateTime inicio = LocalDateTime.now();

		List<List<Integer>> resultado = new ArrayList<List<Integer>>();

		List<Integer> tmp = new ArrayList<>();
		for (List<Integer> lista : listas) {
			tmp = filtrarLista(lista);
			if (tmp != null && tmp.size() > 0) {
				resultado.add(lista);
			}
		}

		int after = resultado.size();

		double percent = Double.valueOf(after) / Double.valueOf(before);

		LocalDateTime fim = LocalDateTime.now();

		long duration = ChronoUnit.MILLIS.between(inicio, fim);

		try {
			String msg = "Duration: %s, Before: %s, After: %s, Percent: %s, Class: %s";

			msg = String.format(msg, duration, before, after, df2.format(percent), clazz);

			System.out.println(msg);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return resultado;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE));
		return str.toString();
	}

}
