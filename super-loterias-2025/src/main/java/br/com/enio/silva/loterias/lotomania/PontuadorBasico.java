package br.com.enio.silva.loterias.lotomania;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.enio.silva.loterias.util.MapUtil;
import com.google.common.collect.Sets;

public class PontuadorBasico implements Pontuador {

	protected Map<Integer, Integer> mapConta = new HashMap<>();

	protected Map<Integer, Integer> mapPontos = new HashMap<Integer, Integer>();

	public void inicializaMapConta() {
		mapConta = new HashMap<>();
		mapConta.put(0, 0);
	}

	private Integer obterPontuacao(JogoAb meuJogo, Map<Integer, Integer> map, Integer limite) {

		Integer pontos = Integer.valueOf(0);

		if (limite == null) {
			limite = map.size();
		}

		List<Integer> mj = meuJogo.getNumerosAsList();
		int count = 0;
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

			if (mj.contains(entry.getKey())) {
				pontos += entry.getValue();
			}

			if (count == limite) {
				break;
			}
			count++;
		}

		return pontos;

	}

	@Override
	public void pontuar(List<List<Integer>> todosJogos, JogoAb meuJogo) {

		//		Integer total2 = 0;
		List<Integer> meuJogoList = meuJogo.getNumerosAsList();
		int intersec = 0;
		//		int intersec2 = 0;
		inicializaMapConta();

		Set<Integer> setA = Sets.newHashSet(meuJogoList);

		int min = MapUtil.getMinimo(mapPontos);
		int max = MapUtil.getMaximo(mapPontos);

		for (List<Integer> numerosSorteados : todosJogos) {
			// System.out.println(numerosSorteados);
			// System.out.println(meuJogoList);
			//			intersec2 = ListUtils.intersection(numerosSorteados, meuJogoList).size();

			intersec = Sets.intersection(setA, Sets.newHashSet(numerosSorteados)).size();

			//			if(intersec != intersec2) {
			//				throw new RuntimeException("Sets.intersection doesn't work!");
			//			}

			if(intersec >= min && intersec <= max) {
				Integer atual = mapConta.get(intersec);
				atual = atual != null ? atual : 0;
				mapConta.put(intersec, atual + 1);
			}
			//			Integer tt = mapPontos.get(intersec);
			//			if (tt != null) {
			//				total += tt;
			//			}
		}

		int total = 0;
		Set<Entry<Integer, Integer>> entrySet = mapConta.entrySet();
		for(Entry<Integer, Integer> e: entrySet) {
			int key = e.getKey();
			int value = e.getValue();
			Integer pontos = mapPontos.get(key);
			int pt =  pontos != null? pontos: 0;
			total += pt * value;
		}

		//		if(total2 != total) {
		//			System.err.println("total2: " + total2 + "\ttotal: " + total);
		//			throw new RuntimeException("incorrect!");
		//		}


		meuJogo.setMapConta(mapConta);
		meuJogo.addPontuacao(total);

		//		System.out.println(meuJogo.toString());
	}

	public void pontuar(List<List<Integer>> todosJogos, JogoAb meuJogo, Integer multiplicador) {
		for (int i = 0; i < multiplicador; i++) {
			pontuar(todosJogos, meuJogo);
		}
	}

	@Override
	public void pontuar(List<List<Integer>> resultados, List<JogoAb> agrupamentosJogos,
			JogoAb main) {
		for (JogoAb jogo : agrupamentosJogos) {
			pontuar(resultados, jogo);
			main.addPontuacao(jogo.getPontuacao());
		}
	}

	@Override
	public void pontuarMap(JogoAb meuJogo, Map<Integer, Integer> map, Integer limite) {

		meuJogo.addPontuacao(obterPontuacao(meuJogo, map, limite));
	}

	@Override
	public void pontuarMap(JogoAb meuJogo, Map<Integer, Integer> map, Integer limite,
			Integer multiplicador) {

		meuJogo.addPontuacao(obterPontuacao(meuJogo, map, limite) * multiplicador);

	}

	public void pontuarPorPosicao(List<List<Integer>> todosJogos, JogoAb meuJogo) {

		List<Integer> mj = meuJogo.getNumerosAsList();
		boolean sameSize = todosJogos.get(0).size() == mj.size();
		if (!sameSize) {
			return;
		}
		Integer pontuacao = 0;
		for (List<Integer> jogo : todosJogos) {
			for (int i = 0; i < mj.size(); i++) {
				if (jogo.get(i).equals(mj.get(i))) {
					pontuacao++;
				}
			}
		}
		meuJogo.addPontuacao(pontuacao);
	}

	public void pontuarPorPosicao(List<List<Integer>> todosJogos, JogoAb meuJogo,
			Integer multiplicador) {
		for (int i = 0; i < multiplicador; i++) {
			pontuarPorPosicao(todosJogos, meuJogo);
		}
	}
}