package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GerarJogosMegaLoopCombinacao extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 10;

		int tamanhoDoJogo = 6;

		String sep = ", ";

		List<Integer> theList = Arrays.asList(21, 41, 10, 28, 57, 26, 6, 38, 17, 60, 40);
		Collections.shuffle(theList);

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "todos_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
		        .obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
		        .obterLinhasComoListasUnique(pathListaJogosCorrentes);

		if (listaJogosCorrentes != null && !listaJogosCorrentes.isEmpty()) {
			for (List<Integer> jc : listaJogosCorrentes) {
				if (!listaJogosAtuais.contains(jc)) {
					listaJogosAtuais.add(jc);
				}
			}
		}

		// System.out.println(listaJogosAtuais);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);

		String sn = base + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		Random rand = new Random();

		MegaSenaConfigAb config = getConfigBySize(tamanhoDoJogo);

		String mcb = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		int magicNumber = rand.nextInt(1000);

		saveOrdered(pathListaDeJogos, listaJogosAtuais, pathListaJogosCorrentes,
		        listaJogosCorrentes);

		try {

			System.out.println("Magic Number: " + magicNumber);
			magicNumber++;

			String output = base + "individual\\" + config.getDefaultName() + ".txt";

			System.out.println(repeated50);

			System.out.println(repeated50);
			config.setIncluir(Arrays.asList());

			List<List<Integer>> preJogos = CombinationUtils.gerarCombinacao(theList,
			        config.getNrosApostados());

			List<List<Integer>> fullList = new ArrayList<>();
			fullList.addAll(listaJogosAtuais);
			fullList.addAll(listaJogosCorrentes);
			fullList.addAll(somenteNovos);
			fullList.addAll(resultados);

			preJogos.removeAll(fullList);

			FiltroIf filtro = null;

			filtro = new FiltroRemoverIntersecao(fullList);
			preJogos = filtro.filtrarListas(preJogos);

			int maxRep = 3;
			filtro = new FiltroMaxConsecutivos(maxRep);
			preJogos = filtro.filtrarListas(preJogos);

			int maxLinhas = 3;
			filtro = new FiltroMaximoLinhas(6, 10, maxLinhas);
			preJogos = filtro.filtrarListas(preJogos);

			filtro = new FiltroDivide(4, 60);
			preJogos = filtro.filtrarListas(preJogos);

			if (preJogos != null && !preJogos.isEmpty()) {

				List<JogoMega> jogosLM = new ArrayList<JogoMega>();
				JogoMega jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoMega(pj);
					jogosLM.add(jlm);
				}

				for (JogoAb meuJogo : jogosLM) {
					config.getPontuador().pontuar(resultados, meuJogo);
				}

				Collections.shuffle(jogosLM);

				filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

				jogosLM = jogosLM.subList(0, totalDeJogos);

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {
					List<Integer> novo = jj.getNumerosAsList();
					jogos.add(novo);
					listaJogosAtuais.add(novo);
					listaJogosCorrentes.add(novo);
					somenteNovos.add(novo);
				}

				for (List<Integer> jogo : jogos) {
					Collections.replaceAll(jogo, 0, 0);
					Collections.sort(jogo);
				}

				listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais, 6);
				listaJogosCorrentes = CombinationUtils.gerarCombinacoes(listaJogosAtuais, 6);

				listaJogosAtuais.sort(new ListOfListComparator());
				listaJogosCorrentes.sort(new ListOfListComparator());
				ArquivoUtil.saveLists(jogos, output, sep, 2);
				ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, sep, 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
				ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);

				String mc = config.getFrequencia(listaJogosCorrentes);
				ArquivoUtil.save(mc, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
				String ma = config.getFrequencia(listaJogosAtuais);
				ArquivoUtil.save(ma, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

				saveOrdered(pathListaDeJogos, listaJogosAtuais, pathListaJogosCorrentes,
				        listaJogosCorrentes);

			} else {

				for (int i = 0; i < 6; i++) {
					System.out.println("Não há resultados com os filtros aplicados");
					Thread.sleep(500);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, sep, 2);
			ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, sep, 2);
			ArquivoUtil.saveLists(somenteNovos, sn, sep, 2);
		}

	}

	/**
	 * @param pathListaDeJogos
	 * @param listaJogosAtuais
	 * @param pathListaJogosCorrentes
	 * @param listaJogosCorrentes
	 * @throws IOException
	 */
	private static void saveOrdered(String pathListaDeJogos, List<List<Integer>> listaJogosAtuais,
	        String pathListaJogosCorrentes, List<List<Integer>> listaJogosCorrentes)
	        throws IOException {
		listaJogosAtuais.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, ", ", 2);
		listaJogosCorrentes.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, ", ", 2);
	}

}
