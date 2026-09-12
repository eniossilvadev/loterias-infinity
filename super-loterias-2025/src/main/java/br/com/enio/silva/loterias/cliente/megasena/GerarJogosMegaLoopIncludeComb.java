package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6_2;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosMegaLoopIncludeComb extends GerarJogosMegaLoopAb {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static void main(String[] args) throws IOException {

		List<Integer> last = Collections.emptyList();

		String base = "E:\\loterias\\mega_sena\\especial\\2237\\";

		String pathListaDeJogos = base + "20200226_03.txt";
		List<List<Integer>> listaDeJogos = ArquivoUtil
		        .obterLinhasComoListasUnique(pathListaDeJogos);
		int mrmj = 3;
		int marr = 4;

		try {

			String path = CaminhoResultados.MEGA_SENA.getPath();
			List<List<Integer>> resultados = GerarListaMega.getInstance()
			        .gerarArquivoResultado(path);

			Integer[] elements = {};
			List<List<Integer>> combinacoes = CombinationUtils.gerarCombinacao(elements, 2);
			Collections.shuffle(combinacoes);

			System.out.println(combinacoes);

			int count = new Random().nextInt(elements[0]);
			for (List<Integer> comb : combinacoes) {

				int diff = count % 2;
				int maxRepeditosMeusJogos = mrmj + diff;
				int maxRepeditosResultados = marr - diff;

				MegaSenaConfigAb config = new MegaSenaConfig6();
				if (count++ % 2 == 0) {
					config = new MegaSenaConfig6_2();
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				List<Integer> excl = getExclusions(last, listaDeJogos, 18, comb);
				System.out.println(repeated50);
				System.out.println(repeated20 + excl + repeated20);
				System.out.println(repeated50);

				config.setPre(comb.toArray(new Integer[comb.size()]));
				config.setExcluir(excl);
				config.setIncluir(Collections.emptyList());

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual(); // MSJA
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				remover = new ArrayList<>(new HashSet<>(remover));

				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int max = config.getMaxNum();
				List<Integer> exc = config.getExcluir();
				List<Integer> inc = config.getIncluir();
				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, max, exc, inc);

				FiltroIf filtro = null;

				// Remover se for igual jogos j� existentes (correntes e
				// anteriores)
				filtro = new FiltroRemoverIntersecao(remover);
				preJogos = filtro.filtrarListas(preJogos);

				// Filtrar pela lista de jogos correntes
				filtro = new FiltroMaxLista(listaDeJogos, maxRepeditosMeusJogos);
				preJogos = filtro.filtrarListas(preJogos);

				// Filtrar por resultados anteriores
				filtro = new FiltroMaxLista(resultados, maxRepeditosResultados);
				preJogos = filtro.filtrarListas(preJogos);

				filtro = new FiltroMaxConsecutivos(5);
				preJogos = filtro.filtrarListas(preJogos);

				filtro = new FiltroMaximoLinhas(6, 10, 4);
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && preJogos.size() > 0) {

					List<JogoMega> jogosLM = new ArrayList<JogoMega>();
					JogoMega jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoMega(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(resultados, meuJogo);
					}

					Collections.sort(jogosLM);

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						last = jj.getNumerosAsList();
						jogos.add(last);
						listaDeJogos.add(last);
					}

					for (List<Integer> jogo : jogos) {
						Collections.replaceAll(jogo, 0, 0);
						Collections.sort(jogo);
					}

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo);
					}

					ArquivoUtil.saveLists(jogos, output, "\t");

				} else {

					System.out.println("Não há resultados com os filtros aplicados");
				}
			}

		} finally {
			ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t");
		}
	}

}
