package br.com.enio.silva.loterias.cliente.bingodasorte;

import java.io.IOException;
import java.util.List;

import br.com.enio.silva.loterias.cliente.bingodasorte.gen.ModeloBingoAb;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class BingoUtils {

	public static int getFimEfetivo() {
		List<List<Integer>> resultados;
		try {
			resultados = getResultados();
			return resultados.size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static <T> MaxMin getMaxMin(List<? extends ModeloBingoDaSorte> lista2) {
		MaxMin maxMin = new MaxMin();
		maxMin.min = ((ModeloBingoAb) lista2.get(0)).getPontuacao();
		maxMin.max = ((ModeloBingoAb) lista2.get(lista2.size() - 1)).getPontuacao();
		System.out.println(maxMin);
		return maxMin;
	}

	public static List<List<Integer>> getResultados() throws IOException {
		String pathResultados = CaminhoResultados.QUINA.getPath();
		return ArquivoUtil.obterLinhasComoListas(pathResultados);
	}

}
