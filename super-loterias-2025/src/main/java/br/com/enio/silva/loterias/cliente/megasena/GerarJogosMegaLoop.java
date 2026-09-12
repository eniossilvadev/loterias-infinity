package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfig6_2;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosMegaLoop extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static void main(String[] args) throws IOException {

		int times = 20;
		List<Integer> last = Collections.emptyList();

		String base = "E:\\loterias\\mega_sena\\especial\\x\\";

		String pathListaDeJogos = base + "meu_jogos.txt";
		List<List<Integer>> listaDeJogos = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		int mrmj = 2;
		int marr = 3;

		List<Integer> incl = Arrays.asList();
		int n = 1;

		System.out.println(listaDeJogos);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();
		Set<List<Integer>> top = new HashSet<>();
		int topN = 2000;

		for (int count = 0; count < times; count++) {

			try {
				int diff = count % 2;
				int maxRepeditosMeusJogos = mrmj + diff;
				int maxRepeditosResultados = marr - diff;

				MegaSenaConfigAb config = new MegaSenaConfig6();

				topN = new Random().nextInt(topN * 2) + 100;

				if (count % 2 == 0) {
					config = new MegaSenaConfig6_2();
				}

				config.setQttInicial(250000);

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				List<Integer> excl = getExclusions(last, listaDeJogos, 25);
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

				if (incl != null && incl.size() >= n && n > 0) {
					Collections.sort(incl);
					arrIncluir = incl.subList(0, n);
					Integer[] myArray = new Integer[arrIncluir.size()];
					arrIncluir.toArray(myArray);
					config.setPre(myArray);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				for (List<Integer> t : top) {
					@SuppressWarnings("unchecked")
					List<Integer> inter = ListUtils.intersection(t, excluir);
					if (inter == null || inter.isEmpty()) {
						preJogos.add(new ArrayList<>(t));
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

					topN = Math.min(topN, jogosLM.size() - 1);
					List<JogoMega> jogosTop = jogosLM.subList(0, topN);
					for (JogoMega jj : jogosTop) {
						top.add(jj.getNumerosAsList());
					}

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();
						jogos.add(novo);
						listaDeJogos.add(novo);
						listaJogosCorrentes.add(novo);
						somenteNovos.add(novo);
						last = new ArrayList<>(novo);
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
					ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t");
					ArquivoUtil.saveLists(somenteNovos, sn, "\t");

				} else {

					System.out.println("Não há resultados com os filtros aplicados");
					count--;
				}
			} catch (Exception e) {
				e.printStackTrace();
				count--;
				ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t");
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t");
				ArquivoUtil.saveLists(somenteNovos, sn, "\t");
			}
		}
	}

}
