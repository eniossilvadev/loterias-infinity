package br.com.enio.silva.loterias.cliente.megasena;

import java.io.IOException;
import java.util.ArrayList;
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
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoMega;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosMegaLoopIncluirExcluirNovo extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		int times = 13;

		String base = CaminhoResultados.MEGA_SENA.getBasePath() + "\\config\\";

		String pathListaDeJogos = base + "meu_jogos.txt";
		List<List<Integer>> listaDeJogos = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaDeJogos);

		String pathListaJogosCorrentes = base + "novos_jogos.txt";
		List<List<Integer>> listaJogosCorrentes = ArquivoUtil
				.obterLinhasComoListasUnique(pathListaJogosCorrentes);

		int mrmj = 1;
		int marr = 4;

		System.out.println(listaDeJogos);

		String path = CaminhoResultados.MEGA_SENA.getPath();
		List<List<Integer>> resultados = GerarListaMega.getInstance().gerarArquivoResultado(path);

		listaJogosCorrentes.removeAll(resultados);

		String sn = base + "SNMS.txt";
		List<List<Integer>> somenteNovos = new ArrayList<>();
		Set<List<Integer>> top = new HashSet<>();
		int topN = 2000;

		int tamPadraoExcluir = 7;
		int tamPadraoIncluir = 2;
		int ajusteMaximo = 0;

		for (int count = 0; count < times; count++) {

			try {
				int diff = count % 2;
				int maxRepeditosMeusJogos = mrmj + diff;
				int maxRepeditosResultados = marr;

				MegaSenaConfigAb config = new MegaSenaConfig6();

				topN = new Random().nextInt(topN * 2) + 100;

				if (count % 2 == 0) {
					config = new MegaSenaConfig6_2();
				}

				config.setQttInicial(50026);

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove, "\t", 2);
				}

				Set<Integer> listaExcl = new HashSet<>();

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				String jogosAtuais = config.getCaminhoJogoAtual();
				List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);
				int parMax = (jogosAtuaisFlat.size() / 6 * 40 / 100) + 1 - ajusteMaximo;
				int maximo = 0;
				String msg = String.format("parMax %s => ajusteMaximo %s", parMax, ajusteMaximo);
				System.out.println(msg);
				if (parMax > 0) {
					int tamExcl = 0;
					StringBuilder norm = new StringBuilder();
					for (int i = 1; i <= config.getMaxNum(); i++) {
						int qttEl = Collections.frequency(jogosAtuaisFlat, i);
						if (qttEl > maximo) {
							maximo = qttEl;
						}
						norm.append(String.format("(%s, %s) ", i, qttEl));
						if (qttEl > parMax) {
							// System.out.println("Normalizando: " + i);
							listaExcl.add(i);
						}
					}

					System.out.println(norm.toString());
					tamExcl = listaExcl.size();
					if (tamExcl > tamPadraoExcluir) {
						msg = String.format("Máximo:\t%s, Exclu�dos:\t%s", maximo, listaExcl);
						System.out.println(msg);
						List<Integer> excl = new ArrayList<>(listaExcl);
						Collections.shuffle(excl);
						excl = excl.subList(0, tamPadraoExcluir);
						listaExcl = new HashSet<>(excl);
					}
					if (tamExcl == 0) {
						ajusteMaximo += 1;
					} else {
						ajusteMaximo = Math.max(0, ajusteMaximo - 1);
					}
					msg = String.format("Máximo:\t%s, Exclu�dos:\t%s", maximo, listaExcl);
					System.out.println(msg);
				}

				System.out.println(repeated50);

				StringBuilder strMinIgual = new StringBuilder();
				StringBuilder strMinMenor = new StringBuilder();

				int parMin = (jogosAtuaisFlat.size() / 6 * 25 / 100) + 1;
				int minimo = parMin;
				Set<Integer> listaIncl = new HashSet<>();
				if (parMin > 0) {
					int tamIncl = 0;
					StringBuilder norm = new StringBuilder();
					for (int i = 1; i <= config.getMaxNum(); i++) {
						int qttEl = Collections.frequency(jogosAtuaisFlat, i);
						norm.append(String.format("(%s, %s) ", i, qttEl));
						if (qttEl == minimo) {
							listaIncl.add(i);
							strMinIgual.append(i);
						} else if (qttEl < minimo) {
							minimo = qttEl;
							listaIncl = new HashSet<>();
							listaIncl.add(i);
							strMinMenor.append(i);
						}
					}

					System.out.println("strMinIgual\t" + strMinIgual);
					System.out.println("strMinMenor\t" + strMinMenor);
					System.out.println(norm.toString());
					tamIncl = listaIncl.size();
					if (tamIncl > tamPadraoIncluir) {
						System.out.println("Inclu�dos:\t" + listaIncl);
						List<Integer> incl = new ArrayList<>(listaIncl);
						Collections.shuffle(incl);
						incl = incl.subList(0, tamPadraoIncluir);
						listaIncl = new HashSet<>(incl);
					}
					System.out.println("Inclu�dos:\t" + listaIncl);
				}
				System.out.println(repeated50);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
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

				} else {

					System.out.println("Não há resultados com os filtros aplicados");
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
