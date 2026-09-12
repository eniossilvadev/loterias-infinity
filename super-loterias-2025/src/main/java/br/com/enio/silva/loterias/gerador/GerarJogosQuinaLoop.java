package br.com.enio.silva.loterias.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.config.quina.QuinaConfig5_1;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5_2;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5_3;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosQuinaLoop extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 10;
		List<Integer> last = Collections.emptyList();

		int mrmj = 2;
		int marr = 2;

		List<Integer> incl = Arrays.asList();
		int n = 1;
		int qttInicialFixo = 150000;

		String base = "C:\\loterias\\gerador-apostas\\quina\\config\\";

		String pathListaDeJogos = base + "meu_jogos.txt";
		List<List<Integer>> listaDeJogos = ArquivoUtil
		        .obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
		        .obterLinhasComoListasUnique(pathListaJogosCorrentes);

		System.out.println(listaDeJogos);

		String path = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNQ.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();
		Set<List<Integer>> top = new HashSet<>();

		String sep = ", ";

		for (int count = 0; count < totalDeJogos; count++) {

			try {
				int maxRepeditosMeusJogos = mrmj;
				int maxRepeditosResultados = marr;

				QuinaConfigAb config = new QuinaConfig5_2();

				if (count == 0) {
					String mc = config.getFrequencia(listaJogosCorrentes);
					ArquivoUtil.save(mc, base + "correntes.txt");
					String ma = config.getFrequencia(listaDeJogos);
					ArquivoUtil.save(ma, base + "atuais.txt");
				}

				if (count % 5 == 1) {
					config = new QuinaConfig5_1();

				} else if (count % 3 == 1) {
					config = new QuinaConfig5_3();
				}

				if (qttInicialFixo > 0) {
					config.setQttInicial(qttInicialFixo);
				}

				if (count % 3 == 0 || count % 5 == 0) {

					mrmj = 3;
					int menosSaiu = getMenosSaiu(listaDeJogos, 3, config.getMaxNum());
					incl = Arrays.asList(menosSaiu);
					System.out.println("Menos saiu: " + incl);

				} else if (count % 2 == 0) {

					mrmj = 2;
					int menosSaiu = getMenosSaiu(listaDeJogos, 1, config.getMaxNum());
					incl = Arrays.asList(menosSaiu);
					System.out.println("Menos saiu: " + incl);
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				List<Integer> excl = getExclusions(last, listaDeJogos, 33, incl);

				int maisSaiu = getMaisSaiu(listaDeJogos, 1, 80);
				if (maisSaiu > 0) {
					if (!excl.contains(maisSaiu)) {
						excl.add(maisSaiu);
					}
				}
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
					ArquivoUtil.saveLists(remover, remove, sep, 2);
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

				List<List<Integer>> fullList = new ArrayList<>();
				fullList.addAll(listaDeJogos);
				fullList.addAll(listaJogosCorrentes);
				fullList.addAll(somenteNovos);
				fullList.addAll(resultados);

				preJogos.removeAll(fullList);

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

				filtro = new FiltroMaxConsecutivos(3);
				preJogos = filtro.filtrarListas(preJogos);

				filtro = new FiltroMaximoLinhas(6, 10, 4);
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && preJogos.size() > 0) {

					List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
					JogoQuina jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoQuina(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(resultados, meuJogo);
					}

					Collections.sort(jogosLM);

					List<JogoQuina> jogosTop = new ArrayList<>();
					for (JogoQuina jj : jogosTop) {
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

					listaDeJogos = CombinationUtils.gerarCombinacoes(listaDeJogos,
					        config.getNrosApostados());

					listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosCorrentes,
					        config.getNrosApostados());

					listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

					int size = somenteNovos.size();
					somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
					if (somenteNovos.size() < size) {
						count = count - (size - somenteNovos.size());
					}

					ArquivoUtil.saveLists(jogos, output, sep, 2);
					ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, sep, 2);
					ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
					ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);

					String mc = config.getFrequencia(listaJogosCorrentes);
					ArquivoUtil.save(mc, base + "correntes.txt");
					String ma = config.getFrequencia(listaDeJogos);
					ArquivoUtil.save(ma, base + "atuais.txt");

				} else {

					for (int i = 0; i < 6; i++) {
						System.out.println("Não há resultados com os filtros aplicados");
						Thread.sleep(10000);
					}
					count--;
				}
			} catch (Exception e) {
				e.printStackTrace();
				count--;
				ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, sep, 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
				ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
			}
		}
	}

}
