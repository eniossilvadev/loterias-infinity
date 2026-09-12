package br.com.enio.silva.loterias.cliente.bingodasorte.gen;

import java.util.Comparator;
import java.util.List;

public class ComparatorBingo implements Comparator<ModeloBingoAb> {

	@Override
	public int compare(ModeloBingoAb arg0, ModeloBingoAb arg1) {
		int c = arg0.getPontuacao() - arg1.getPontuacao();
		if (c == 0) {
			try {
				c = arg0.getNome().compareToIgnoreCase(arg1.getNome());
				if (c == 0) {
					c = getComp(arg0.getRestantes(), arg1.getRestantes());

					if (c == 0) {
						c = (int) (arg0.getId() - arg1.getId());
					}
				}
			} catch (Exception e) {
				System.out.println(arg0);
				System.out.println(arg1);
				return 0;
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

}
