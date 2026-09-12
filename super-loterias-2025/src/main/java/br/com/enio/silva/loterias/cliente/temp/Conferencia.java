package br.com.enio.silva.loterias.cliente.temp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class Conferencia {

	private static List<Integer> getConcurso(int concurso) {
		LotofacilConfig config = new LotofacilConfig15();
		List<List<Integer>> resultados = config.getTodosResultados();
		if (resultados != null && resultados.size() >= concurso) {
			return resultados.get(concurso - 1);
		}
		return null;
	}

	public static void main(String[] args) throws IOException {

		String pathMeusJogos = "D:\\Meus Documentos\\�rea de Trabalho\\a.txt";
		int concurso = 2574;

		List<Integer> resultadoConcurso = getConcurso(concurso);

		List<List<Integer>> meusJogos = ArquivoUtil.obterLinhasComoListasUnique(pathMeusJogos);

		StringBuilder premiados = new StringBuilder();
		int count = 0;
		int contaPremiados = 0;
		int contaQuize = 0;
		int contaQuatorze = 0;
		int contaTreza = 0;
		int contaDoze = 0;
		int contaOnze = 0;

		for (List<Integer> meuJogo : meusJogos) {
			List<Integer> inter = ListUtils.intersection(resultadoConcurso, meuJogo);
			count++;
			if (inter != null && inter.size() >= 11) {
				int pontos = inter.size();
				premiados.append("(" + pontos + ", " + count + ")\t").append(meuJogo + "\t")
				        .append(inter + "\n");

				contaPremiados++;
				if (pontos == 11) {
					contaOnze++;
				} else if (pontos == 12) {
					contaDoze++;
				} else if (pontos == 13) {
					contaTreza++;
				} else if (pontos == 14) {
					contaQuatorze++;
				} else {
					contaQuize++;
				}
			}
		}

		premiados.append("\n\n");
		premiados.append("Total de Jogos Premiados: ").append(contaPremiados).append("\n");
		if (contaQuize > 0) {
			premiados.append("15 pontos ").append(contaQuize).append("\n");
		}
		if (contaQuatorze > 0) {
			premiados.append("14 pontos ").append(contaQuatorze).append("\n");
		}
		if (contaTreza > 0) {
			premiados.append("13 pontos ").append(contaTreza).append("\n");
		}
		if (contaDoze > 0) {
			premiados.append("12 pontos ").append(contaDoze).append("\n");
		}
		if (contaOnze > 0) {
			premiados.append("11 pontos ").append(contaOnze).append("\n");
		}

		String nome = "conf.txt";
		Path path = Paths.get(pathMeusJogos);
		path = Paths.get(path.getParent().toString(), nome);
		ArquivoUtil.save(premiados.toString(), path.toString());
	}

}
