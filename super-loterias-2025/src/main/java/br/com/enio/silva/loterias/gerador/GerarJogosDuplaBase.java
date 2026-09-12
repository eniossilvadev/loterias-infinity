package br.com.enio.silva.loterias.gerador;

import java.util.Random;

import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig10;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig6A2;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig6A3;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig6A4;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig6AF;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig7;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig8;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig9;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfigAb;

public class GerarJogosDuplaBase extends Base {

	protected static String repeated50 = new String(new char[50]).replace("\0", "-");

	protected static String repeated20 = new String(new char[20]).replace("\0", "-");

	protected static DuplaSenaConfigAb getConfig(int count) {

		int mod = count % 6;

		if (mod == 0 || mod == 3) {
			return new DuplaSenaConfig6A2();
		}

		if (mod == 1 || mod == 4) {
			return new DuplaSenaConfig6AF();
		}

		if (mod == 2) {
			return new DuplaSenaConfig6A3();
		}

		if (mod == 5) {
			return new DuplaSenaConfig6A4();
		}

		return null;
	}

	protected static DuplaSenaConfigAb getConfigBySize(int size) {
		return getConfigBySize(size, new Random().nextInt());
	}

	protected static DuplaSenaConfigAb getConfigBySize(int size, int mod) {

		int count = Math.abs(mod);

		if(size == 6) {
			return getConfig(count);
		}

		if(size == 7) {
			return new DuplaSenaConfig7();
		}

		if(size == 8) {
			return new DuplaSenaConfig8();
		}

		if(size == 9) {
			return new DuplaSenaConfig9();
		}

		if(size == 10) {
			return new DuplaSenaConfig10();
		}

		return getConfig(count);
	}

}
