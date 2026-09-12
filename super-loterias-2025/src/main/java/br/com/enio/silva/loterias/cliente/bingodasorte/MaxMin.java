package br.com.enio.silva.loterias.cliente.bingodasorte;

public class MaxMin {

	public int max = Integer.MIN_VALUE;

	public int min = Integer.MAX_VALUE;

	@Override
	public String toString() {
		return "max = " + max + "\tmin = " + min;
	}
}