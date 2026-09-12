package br.com.enio.silva.loterias.config;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.controller.LotofacilRN;

public abstract class ConfigJogosComum extends Config {

	public static final String EXT = ".txt";

	private static DecimalFormat df2 = new DecimalFormat("#0.0000");

	private int nrosApostados = 6;

	private int qttInicial = 125000;

	private int nrosJogos = 1;

	private Integer[] pre = new Integer[] {};

	private List<Integer> excluir = new ArrayList<Integer>();

	private List<Integer> incluir = new ArrayList<Integer>();

	public ArrayList<Integer> getArrayListPre() {
		return new ArrayList<>(Arrays.asList(getPre()));
	}

	public abstract String getCaminhoDefaultOutput();

	public abstract String getCaminhoDownload();

	public abstract String getCaminhoJogoAtual();

	public abstract String getCaminhoJogoCorrente();

	public abstract String getCaminhoResultados();

	public abstract int getDefaultSize();

	public List<Integer> getExcluir() {
		List<Integer> exc = new ArrayList<>(excluir);
		if (excluir != null) {
			exc.removeAll(getArrayListPre());
			exc.sort((i1, i2) -> Integer.compare(i1, i2));
		}
		return exc;
	}

	public String getFrequencia(List<List<Integer>> resultados) {

		if(resultados == null || resultados.isEmpty()) {
			return StringUtils.EMPTY;
		}

		StringBuilder str = new StringBuilder();

		int total = resultados.size();

		Map<Integer, Integer> map = getMapResultado(resultados, total);
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int key = entry.getKey();
			int val = entry.getValue();
			if (val > max) {
				max = val;
			}
			if (val < min) {
				min = val;
			}
			double percent = (double) val / total;

			String sKey = String.format("%02d", key);
			str.append(sKey).append("\t").append(val).append("\t").append(df2.format(percent))
			.append("\n");
		}
		str.append("\n");
		str.append("Max: ").append(max).append("\n");
		str.append("Min: ").append(min).append("\n");
		str.append("Dif: ").append(max - min).append("\n");
		return str.toString();
	}

	public String getFrequenciaSimples(List<List<Integer>> resultados) {
		StringBuilder str = new StringBuilder();

		int total = resultados.size();

		Map<Integer, Integer> map = getMapResultado(resultados, total);
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			int key = entry.getKey();
			int val = entry.getValue();
			if (val > max) {
				max = val;
			}
			if (val < min) {
				min = val;
			}
			String sKey = String.format("%02d", key);
			str.append(sKey).append(": ").append(val).append(", ");
		}
		return str.toString();
	}

	public List<Integer> getIncluir() {
		return incluir;
	}

	/**
	 * @param resultados
	 * @return
	 */
	public Map<Integer, Integer> getMapAtraso(List<List<Integer>> resultados) {
		ContaAtrasosResultados instance = ContaAtrasosResultados
				.getInstance(LotofacilRN.MAIOR_NUMERO);
		Map<Integer, Integer> map = instance.getMapContaResultados(resultados);
		return MapUtil.sortByValueDesc(map);
	}

	/**
	 * @param resultados
	 * @param ultimos
	 * @return
	 */
	public Map<Integer, Integer> getMapResultado(List<List<Integer>> resultados, int ultimos) {
		ContaNumerosResultados instance = ContaNumerosResultados.getInstance();
		Map<Integer, Integer> map = instance.getMapContaResultados(resultados, ultimos);
		return MapUtil.sortByValueDesc(map);
	}

	public int getNrosApostados() {
		return nrosApostados;
	}

	public int getNrosJogos() {
		return nrosJogos;
	}

	public Integer[] getPre() {
		return pre;
	}

	public int getQttInicial() {
		return qttInicial;
	}

	public void setExcluir(List<Integer> excluir) {
		List<Integer> unique = new ArrayList<>(new HashSet<>(excluir));
		Collections.sort(unique);
		this.excluir = unique;
	}

	public void setIncluir(List<Integer> incluir) {
		List<Integer> unique = new ArrayList<>(new HashSet<>(incluir));
		Collections.sort(unique);
		this.incluir = unique;
	}

	public void setNrosApostados(int nrosApostados) {
		this.nrosApostados = nrosApostados;
	}

	public void setNrosJogos(int nrosJogos) {
		this.nrosJogos = nrosJogos;
	}

	public void setPre(Integer[] pre) {
		this.pre = pre;
	}

	public void setQttInicial(int qttInicial) {
		this.qttInicial = qttInicial;
	}

}
