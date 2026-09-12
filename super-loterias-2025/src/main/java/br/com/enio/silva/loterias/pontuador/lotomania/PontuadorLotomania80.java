package br.com.enio.silva.loterias.pontuador.lotomania;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotomania;
import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.lotomania.PontuadorBasico;
import br.com.enio.silva.loterias.util.ListaUtils;

public class PontuadorLotomania80 extends PontuadorBasico {

	private static List<List<Integer>> getEspelhos(List<List<Integer>> jogos) {
		List<List<Integer>> retorno = new ArrayList<>();

		jogos.forEach(j -> {
			List<Integer> full = ListaUtils.iterateStream(1, 1, 100);
			full.removeAll(j);
			if (full.size() != 50) {
				String msg = "Tamanho do espelho inválido.";
				String err = String.format("% [%s, %s]", msg, full, j);
				System.err.println(err);
			}
			retorno.add(j);
			retorno.add(full);

		});

		return new ArrayList<>(new HashSet<>(retorno));
	}

	public PontuadorLotomania80() {
		super();
		mapPontos.put(0, (int) Math.pow(4, 7));
		mapPontos.put(1, -10);
		mapPontos.put(2, -10);
		mapPontos.put(3, -10);
		mapPontos.put(4, -10);
		mapPontos.put(5, -10);
		mapPontos.put(6, -10);
		mapPontos.put(7, -10);
		mapPontos.put(8, -10);
		mapPontos.put(9, -10);
		mapPontos.put(10, 0);
		mapPontos.put(11, 1);
		mapPontos.put(12, 2);
		mapPontos.put(13, 3);
		mapPontos.put(14, 4);
		mapPontos.put(15, (int) Math.pow(2, 3));
		mapPontos.put(16, (int) Math.pow(2, 5));
		mapPontos.put(17, (int) Math.pow(2, 6));
		mapPontos.put(18, (int) Math.pow(2, 6));
		mapPontos.put(19, (int) Math.pow(3, 8));
		mapPontos.put(20, (int) Math.pow(2, 7));
	}

	public void pontuar(List<List<Integer>> todosJogos, JogoAb meuJogo,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso) {
		List<Integer> lista80 = meuJogo.getNumerosAsList();
		try {
			List<List<Integer>> jogos = EsquemaLotomania.esquema80n0f10a(lista80, mapResultado,
			        mapAtraso);
			jogos = getEspelhos(jogos);
			jogos.forEach(j -> {
				JogoAb mj = new JogoLotomania(j);
				super.pontuar(todosJogos, mj);
				meuJogo.addPontuacao(mj.getPontuacao());
			});
		} catch (InvalidLenghtException e) {
			e.printStackTrace();
		}

	}

}
