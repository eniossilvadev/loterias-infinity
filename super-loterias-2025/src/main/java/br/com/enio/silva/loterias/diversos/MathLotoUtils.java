package br.com.enio.silva.loterias.diversos;

import java.util.Random;

public class MathLotoUtils {

	public static int getBalance(int mod, int balanceador) {
		return Math.abs(new Random().nextInt()) % mod - balanceador;
	}
}
