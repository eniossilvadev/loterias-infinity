package br.com.enio.silva.loterias.cliente.quina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.commons.SaveQuina;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5_1;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosQuinaLoopExcluirIncluirPreJogo_BKP extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 3;

		int[] ultimosJogos = { 0, 30, 50, 100, 0, 260, 0, 500, 0, 1000 };

		List<Integer> theList = Arrays.asList(29, 48, 77);

		Set<Integer> listaExcl = new HashSet<>();
		Set<Integer> listaIncl = new HashSet<>(theList);

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

		for (int count = 0; count < totalDeJogos; count++) {

			QuinaConfigAb config = new QuinaConfig5_1();

			int ultimos = ultimosJogos[count % ultimosJogos.length];
			ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size() - 1
					: ultimos - 1;
			List<List<Integer>> ultimosResultados = GerarListaQuina.getInstance()
					.gerarArquivoResultado(path);
			Collections.reverse(ultimosResultados);
			ultimosResultados = ultimosResultados.subList(0, ultimos);

			try {
				int maxRepeditosResultados = 4;

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);

				List<List<Integer>> jogosLista = ArquivoUtil
						.obterLinhasComoListasUnique(pathListaDeJogos);
				List<List<Integer>> novosLista = ArquivoUtil
						.obterLinhasComoListasUnique(pathListaJogosCorrentes);

				remover.addAll(jogosLista);
				remover.addAll(novosLista);
				remover = new ArrayList<>(new HashSet<>(remover));

				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove, "\t");
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				System.out.println(repeated50);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				List<Integer> excluir = config.getExcluir();

				List<Integer> listaRange = ListaUtils.getListaRange(1, 80);
				listaRange.removeAll(listaIncl);

				List<Integer> listaFixos = new ArrayList<>(listaIncl);

				int size = config.getNrosApostados() - listaFixos.size();

				preJogos = CombinationUtils.gerarCombinacaoComFixos(listaFixos, listaRange, size);

				for (List<Integer> t : top) {
					@SuppressWarnings("unchecked")
					List<Integer> inter = ListUtils.intersection(t, excluir);
					if (inter == null || inter.isEmpty()) {
						preJogos.add(new ArrayList<>(t));
					}
				}

				preJogos.removeAll(remover);

				FiltroIf filtro = null;

				// Remover se for igual jogos j� existentes (correntes e
				// anteriores)
				filtro = new FiltroRemoverIntersecao(remover);
				preJogos = filtro.filtrarListas(preJogos);

				// Filtrar por resultados anteriores
				filtro = new FiltroMaxLista(resultados, maxRepeditosResultados);
				if (!preJogos.isEmpty()) {
					// preJogos = filtro.filtrarListas(preJogos);
				}

				if (!preJogos.isEmpty()) {
					filtro = new FiltroMaxConsecutivos(3);
				}
				preJogos = filtro.filtrarListas(preJogos);

				if (!preJogos.isEmpty()) {
					filtro = new FiltroMaximoLinhas(6, 10, 4);
				}
				preJogos = filtro.filtrarListas(preJogos);

				if (preJogos != null && preJogos.size() > 0) {

					List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();
					JogoQuina jlm = null;
					for (List<Integer> pj : preJogos) {
						jlm = new JogoQuina(pj);
						jogosLM.add(jlm);
					}

					for (JogoAb meuJogo : jogosLM) {
						config.getPontuador().pontuar(ultimosResultados, meuJogo);
					}

					Collections.sort(jogosLM);
					if (random.nextBoolean() && random.nextBoolean()) {
						Collections.reverse(jogosLM);
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
					}

					for (List<Integer> jogo : jogos) {
						Collections.replaceAll(jogo, 0, 0);
						Collections.sort(jogo);
					}

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo);
					}

					ArquivoUtil.saveLists(jogos, output, "\t", 2);
					// ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos,
					// "\t", 2);
					// ArquivoUtil.saveLists(listaJogosCorrentes,
					// pathListaJogosCorrentes, "\t", 2);
					// ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);

					SaveQuina sq = new SaveQuina();
					sq.saveAtuais(listaDeJogos);
					sq.saveCorrentes(listaJogosCorrentes);
					sq.saveCaminhoSomenteNovos(somenteNovos);
					// sq.saveCaminhoStatsAtuais(listaDeJogos);
					// sq.saveCaminhoStatsCorrentes(listaJogosCorrentes);

					//					String mc = config.getFrequencia(listaJogosCorrentes);
					//					ArquivoUtil.save(mc, base + "correntes.txt");
					//					String ma = config.getFrequencia(listaDeJogos);
					//					ArquivoUtil.save(ma, base + "atuais.txt");

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
				ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t", 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
				ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);
			}
		}
	}

}
