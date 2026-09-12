package br.com.enio.silva.loterias.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.duplasena.GerarListaDuplaSena;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig6A2;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfig6A3;
import br.com.enio.silva.loterias.duplasena.config.DuplaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoDuplaSena;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosDuplaLoop extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static void main(String[] args) throws IOException {

		int times = 50;

		int excluirDaRodada = 15;

		int topN = 1000;
		Set<List<Integer>> top = new HashSet<>();

		List<Integer> last = Collections.emptyList();

		String base = CaminhoResultados.DUPLA_SENA.getBasePath() + "\\especial\\resultados\\";

		String pathListaDeJogos = "E:\\loterias\\dupla_sena\\config\\todos_jogos.txt";
		List<List<Integer>> listaDeJogos = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaDeJogosNovos = "E:\\loterias\\dupla_sena\\config\\novos_jogos.txt";
		List<List<Integer>> listaDeJogosNovos = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogosNovos);

		int mrmj = 3;
		int marr = 4;

		System.out.println(listaDeJogos);

		try {

			String path = CaminhoResultados.DUPLA_SENA.getPath();
			List<List<Integer>> resultados = GerarListaDuplaSena.getInstance()
					.gerarArquivoResultado(path);

			List<Integer> arrIncluir = Arrays.asList();
			List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
			System.out.println("Include:\t" + include);

			for (int count = 0; count < times; count++) {
				int diff = count % 2;
				int maxRepeditosMeusJogos = mrmj + diff;
				int maxRepeditosResultados = marr - diff;

				DuplaSenaConfigAb config = new DuplaSenaConfig6A2();
				if (count % 3 == 0) {
					config = new DuplaSenaConfig6A3();
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				List<Integer> excl = getExclusions(last, listaDeJogos, excluirDaRodada);
				Collections.sort(excl);

				System.out.println(repeated50);
				System.out.println(repeated20 + excl + repeated20);
				System.out.println(repeated50);

				config.setPre(new Integer[] {});
				config.setExcluir(excl);

				config.setIncluir(include);

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove, "\t");
				}

				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int max = config.getMaxNum();
				List<Integer> exc = config.getExcluir();
				List<Integer> inc = config.getIncluir();
				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, max, exc, inc);

				for (List<Integer> t : top) {
					List<Integer> inter = ListUtils.intersection(t, exc);
					if (inter == null || inter.isEmpty()) {
						preJogos.add(new ArrayList<>(t));
					} else {
						System.out.println("Top removido: " + t);
					}
				}

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

					List<JogoDuplaSena> jogosLM = new ArrayList<JogoDuplaSena>();
					JogoDuplaSena jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoDuplaSena(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(resultados, meuJogo);
					}

					Collections.sort(jogosLM);

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					topN = new Random().nextInt(450) + 50;
					List<JogoDuplaSena> jogosTop = jogosLM.subList(0, topN);
					for (JogoDuplaSena jj : jogosTop) {
						top.add(jj.getNumerosAsList());
					}

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						last = jj.getNumerosAsList();
						jogos.add(last);
						listaDeJogos.add(last);
						listaDeJogosNovos.add(last);
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
					ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t");
					ArquivoUtil.saveLists(listaDeJogosNovos, pathListaDeJogosNovos, "\t");

				} else {

					System.out.println("Não há resultados com os filtros aplicados");
					count--;
				}
			}

		} finally {
			ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t");
			ArquivoUtil.saveLists(listaDeJogosNovos, pathListaDeJogosNovos, "\t");
		}
	}

}
