package br.com.enio.silva.loterias.gerador;

import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.lotomania.ContaAtrasosResultados;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.util.MapUtil;

public abstract class GerarJogosBase extends Base {

	protected static String gerarArquivoBasicStats(List<List<Integer>> resultados, int ultimos,
			int maiorNumero) {

		StringBuilder str = new StringBuilder();

		Map<Integer, Integer> mapResultado = getMapResultado(resultados, ultimos);

		str.append("Mais Frequentes");
		str.append(MapUtil.getAllKeyValues(mapResultado, "\n"));
		str.append("\n\n");

		Map<Integer, Integer> mapAtraso = getMapAtraso(resultados, maiorNumero);

		str.append("Mais Tempo Sem Sair");
		str.append(MapUtil.getAllKeyValues(mapAtraso, "\n"));
		str.append("\n\n");

		return str.toString();
	}

	protected static Map<Integer, Integer> getMapAtraso(List<List<Integer>> resultados,
			int maiorNumero) {
		ContaAtrasosResultados contaNum = ContaAtrasosResultados.getInstance(maiorNumero);
		Map<Integer, Integer> mapAtraso = contaNum.getMapContaResultados(resultados);
		mapAtraso = MapUtil.sortByValueDesc(mapAtraso);
		return mapAtraso;
	}

	protected static Map<Integer, Integer> getMapResultado(List<List<Integer>> resultados,
			int ultimos) {
		ContaNumerosResultados contaNum = ContaNumerosResultados.getInstance();
		Map<Integer, Integer> mapResultado = contaNum.getMapContaResultados(resultados, ultimos);
		mapResultado = MapUtil.sortByValueDesc(mapResultado);
		return mapResultado;
	}

}
