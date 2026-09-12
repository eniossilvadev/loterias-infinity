package br.com.enio.silva.loterias.cliente.quina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.config.quina.QuinaConfig5_1;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5_2;
import br.com.enio.silva.loterias.config.quina.QuinaConfig5_3;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.diversos.QuadrantesQuina;
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
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosQuinaLoopExcluirIncluirQuadrante extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	private static QuinaConfigAb getConfig(int count) {
		QuinaConfigAb config = new QuinaConfig5_2();

		if (count % 4 == 1) {
			config = new QuinaConfig5_1();

		} else if (count % 4 == 2) {
			config = new QuinaConfig5_3();
		}

		return config;
	}

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 1;

		int[] ultimosJogos = { 0, 50, 100, 260, 500, 1000 };

		// int topN = 2000;
		int qttInicialFixo = 50000;
		int tamanhoDoJogo = 5;
		int tamPadraoExcluir = 20;
		List<Integer> theList = Arrays.asList(05,10,70);
		int tamanhoIncluirLista = theList.size();

		Set<Integer> listaInclFixa = new HashSet<>(Arrays.asList());

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

		Random rand = new Random();
		int magicNumber = rand.nextInt(1000);

		for (int count = 0; count < totalDeJogos; count++) {

			QuinaConfigAb config = getConfig(count);

			List<List<Integer>> ultimosResultados = config.getTodosResultados();
			Collections.reverse(ultimosResultados);

			int ultimos = ultimosJogos[count % ultimosJogos.length];
			ultimos = ultimos <= 10 || ultimos > ultimosResultados.size() - 1 ? ultimosResultados.size()
					: ultimos;

			ultimosResultados = ultimosResultados.subList(0, ultimos);

			try {
				int maxRepeditosMeusJogos = getMrmj(config);
				int maxRepeditosResultados = getMrmj(config, maxRepeditosMeusJogos);

				Set<Integer> listaIncl = new HashSet<>(listaInclFixa);
				if (theList != null && !theList.isEmpty()) {
					int el = magicNumber % theList.size();
					int sorte = theList.get(el);
					listaIncl.add(sorte);

					System.out.println("el: " + el + "\t" + sorte);
				}

				config.setQttInicial(qttInicialFixo);

				if (qttInicialFixo > 0) {
					config.setQttInicial(qttInicialFixo);
				}

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove, "\t");
				}

				System.out.println(repeated50);

				String jogosAtuais = config.getCaminhoJogoAtual();

				List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);

				List<Integer> listaOrdenada = MapUtil.getListaOrdenada(jogosAtuaisFlat, 1,
						config.getMaxNum());

				listaIncl.addAll(listaOrdenada.subList(0, tamanhoIncluirLista));

				Collections.reverse(listaOrdenada);
				Set<Integer> listaExcl = new HashSet<>(listaOrdenada.subList(0, tamPadraoExcluir));

				listaExcl.removeAll(listaIncl);


				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}
				List<List<Integer>> preJogos = config.getPreJogos();

				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = QuadrantesQuina.generateList(qttInicialFixo, tamanhoDoJogo, excluir, incluir);
				preJogos.removeAll(listaDeJogos);

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
				// preJogos = filtro.filtrarListas(preJogos);

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

				//				filtro = new FiltroQuantidadeDaLista(8, null);
				//				preJogos = filtro.filtrarListas(preJogos);

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
					ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t", 2);
					ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
					ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);

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
				ArquivoUtil.saveLists(listaDeJogos, pathListaDeJogos, "\t", 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
				ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);
			}
		}
	}

}
