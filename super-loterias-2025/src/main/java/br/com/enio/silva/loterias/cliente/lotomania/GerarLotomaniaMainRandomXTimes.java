package br.com.enio.silva.loterias.cliente.lotomania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.config.LotomaniaConfig80;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomaniaWinnerSimple01;
import br.com.enio.silva.loterias.util.ListaUtils;

public class GerarLotomaniaMainRandomXTimes extends GerarLotomaniaMainRandom {

	public static void execute() throws IOException, InvalidLenghtException {

		LotomaniaConfigAb conf = new LotomaniaConfig80();
		conf.setQttInicial(150000);
		conf.setPontuador(new PontuadorLotomaniaWinnerSimple01());
		setConfig(conf);

		int times = 1;
		List<List<Integer>> last = new ArrayList<>();
		for (int i = 0; i < times; i++) {
			List<Integer> param = new ArrayList<>();
			if (!last.isEmpty()) {
				List<Integer> full = ListaUtils.iterateStream(1, 1, 100);
				last.forEach(l -> full.removeAll(l));
				param = full;
				System.out.println(param);
			}
			last = execute(param);
		}
	}

	public static void main(String[] args) throws IOException, InvalidLenghtException {
		execute();
	}

}
