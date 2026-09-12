package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.config.megasena.MegaSenaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosMegaLoopExcluirIncluirTopezaVirada extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 20;

		int qttInicialFixo = 10000;

		int tamPadraoExcluir = 3;
		int tamPadraoIncluir = 1;

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "todos_jogos.txt";
		List<List<Integer>> listaJogosAtuais = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		//		if (listaJogosCorrentes != null && !listaJogosCorrentes.isEmpty()) {
		//			for (List<Integer> jc : listaJogosCorrentes) {
		//				if (!listaJogosAtuais.contains(jc)) {
		//					listaJogosAtuais.add(jc);
		//				}
		//			}
		//		}
		//
		//		System.out.println(listaJogosAtuais);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);

		List<Integer> arrIncluir = Arrays.asList();
		List<Integer> include = new ArrayList<>(new HashSet<>(arrIncluir));
		System.out.println("Include:\t" + include);

		String sn = base + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();

		MegaSenaConfigAb config = getConfig(0);

		String mcb = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		List<Integer> last = null;

		for (int count = 0; count < totalDeJogos; count++) {

			config = getConfig(count);
			config.setQttInicial(qttInicialFixo);

			try {
				int maxRepeditosResultados = 5;

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove);
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				String jogosAtuais = config.getCaminhoJogoAtual();

				List<Integer> jogosAtuaisFlat = getFlatMega(pathListaJogosCorrentes, count,
						jogosAtuais);

				Set<Integer> listaExcl = new HashSet<>();
				Set<Integer> listaIncl = new HashSet<>();
				Map<Integer, Integer> mapFrequencia = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
						60);
				Map<Integer, Integer> map = MapUtil.sortByValueDesc(mapFrequencia);
				int lastValue = 0;
				int currValue = 0;
				for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
					if (listaExcl == null) {
						listaExcl = new HashSet<>();
						lastValue = entry.getValue();
					}
					currValue = entry.getValue();
					if (listaExcl.size() <= tamPadraoExcluir || lastValue == currValue) {
						listaExcl.add(entry.getKey());
						lastValue = currValue;
					}
				}
				listaIncl = gerarIncluirMinimo(config, tamPadraoIncluir, jogosAtuaisFlat);
				if (count % 2 == 1) {
					listaExcl = gerarExcluirMaximo(config, tamPadraoExcluir, jogosAtuaisFlat);
				}

				System.out.println(repeated50);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				listaIncl.removeAll(listaExcl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				List<List<Integer>> preJogos = config.getPreJogos();

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				FiltroIf filtro = null;

				if (isFiltrarAtual(preJogos)) {
					filtro = new FiltroRemoverIntersecao(remover);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos) && new Random().nextBoolean()) {
					// Filtrar por resultados anteriores
					filtro = new FiltroMaxLista(resultados, maxRepeditosResultados);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos)) {
					int maxRep = 2 + (count % 4 == 0 ? 1 : 0);
					filtro = new FiltroMaxConsecutivos(maxRep);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos)) {
					int maxLinhas = 2 + count % 3;
					filtro = new FiltroMaximoLinhas(6, 10, maxLinhas);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos) && isNotEmpty(last)) {
					filtro = new FiltroUltimoSorteio(0, 1, last);
					preJogos = filtro.filtrarListas(preJogos);
				}

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

					if (random.nextBoolean() && random.nextBoolean()) {
						Collections.shuffle(jogosLM);
					} else {
						Collections.sort(jogosLM);
					}

					filtro = new FiltroMaxmoIgualAnterior(config.getMaxAnterior());

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

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

					System.out.println("Ordenado");
					for (List<Integer> jogo : jogos) {
						System.out.println(jogo);
					}

					ArquivoUtil.saveLists(jogos, output, "\t", 2);
					ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);
					ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
					ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);

					String mc = config.getFrequencia(listaJogosCorrentes);
					ArquivoUtil.save(mc,
							CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
					String ma = config.getFrequencia(listaJogosAtuais);
					ArquivoUtil.save(ma, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

				} else {

					for (int i = 0; i < 6; i++) {
						System.out.println("Não há resultados com os filtros aplicados");
						Thread.sleep(500);
					}
					count--;
				}
			} catch (Exception e) {
				e.printStackTrace();
				count--;
				ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);
				ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
				ArquivoUtil.saveLists(somenteNovos, sn, "\t", 2);
			}
		}
		listaJogosAtuais.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosAtuais, pathListaDeJogos, "\t", 2);
		listaJogosCorrentes.sort(new ListOfListComparator());
		ArquivoUtil.saveLists(listaJogosCorrentes, pathListaJogosCorrentes, "\t", 2);
	}
}
