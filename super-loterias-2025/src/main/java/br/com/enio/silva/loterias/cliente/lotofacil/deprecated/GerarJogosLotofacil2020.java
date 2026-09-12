package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_2;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15_3;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoInicio;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoFim;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotofacil2020 {

	private static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	public static void main(String[] args) throws IOException {

		//		try {
		//			new DownloadUtil().execute("lotofacil");
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}

		List<Integer> preExcl = Arrays.asList();

		int total = 1;
		int ultimos = -1;
		int sequenciaMaxima = 7;
		int maximoInicio = 4;
		int minimoFim = 21;

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();
		int topN = 3000;
		int randomExcl = 3;

		String jogosAtuais = config.getCaminhoJogoAtual();
		String jogosCorrentes = config.getCaminhoJogoCorrente();

		int maximoRepetidosLista = 5;
		String pathListaMax = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LISTA_MAX.txt";

		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(pathListaMax);

		String destino = config.getFullPath();

		int divide = 1;
		for (int count = 0; count < total; count++) {

			try {

				try {
					topN = new Random().nextInt(topN * 3) + 1000;
				} catch (Exception e) {
					e.printStackTrace();
					topN = 1000;
				}

				if (count % 2 == 0) {
					config = new LotofacilConfig15();
					maximoRepetidosLista = 10;
				} else if (count % 3 != 0) {
					config = new LotofacilConfig15_2();
					maximoRepetidosLista = 12;
				} else {
					config = new LotofacilConfig15_3();
					maximoRepetidosLista = 13;
				}

				config.setQttInicial(100000);

				List<List<Integer>> listaJogosAtuais = ArquivoUtil
						.obterLinhasComoListasUnique(jogosAtuais);
				ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, "\t");

				List<List<Integer>> listaJogosCorrentes = ArquivoUtil
						.obterLinhasComoListasUnique(jogosCorrentes);

				List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);

				int tamExcl = 0;
				Set<Integer> listaExcl = new HashSet<>();
				if (count < 0) {
					tamExcl = 2 + (new Random().nextInt(randomExcl));

					if (preExcl != null && !preExcl.isEmpty()) {
						listaExcl.addAll(preExcl);
					}
					if (jogosAtuaisFlat != null && jogosAtuaisFlat.size() > 0) {

						int i = 0;
						int parOuImpar = 1;
						while (listaExcl.size() < tamExcl) {
							Collections.shuffle(jogosAtuaisFlat);
							int next = jogosAtuaisFlat.get(i++ % jogosAtuaisFlat.size());
							while (next % 2 == parOuImpar) {
								next = jogosAtuaisFlat.get(i++ % jogosAtuaisFlat.size());
							}
							parOuImpar = (parOuImpar + 1) % 2;
							listaExcl.add(next);
						}
					}
				} else {

					// int par = count * 15 / 25 + 1;
					// int par = jogosAtuaisFlat.size() / 25 + 1;
					int par = (jogosAtuaisFlat.size() / 15 * 8 / 10) + 1;

					StringBuilder norm = new StringBuilder();
					for (int i = 1; i <= 25; i++) {
						int qttEl = Collections.frequency(jogosAtuaisFlat, i);
						norm.append(String.format("(%s, %s) ", i, qttEl));
						if (qttEl > par) {
							System.out.println("Normalizando: " + i);
							listaExcl.add(i);
						}
					}

					System.out.println(norm.toString());
					tamExcl = listaExcl.size();
					if (tamExcl > 5) {
						List<Integer> excl = new ArrayList<>(listaExcl);
						Collections.shuffle(excl);
						excl = excl.subList(0, 5);
						listaExcl = new HashSet<>(excl);
					}
					System.out.println("");
				}

				System.out.println("Números excluídos: " + listaExcl);

				config.setPre(new Integer[] {});
				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());
				List<Integer> qtt = Arrays.asList();

				boolean filtrarQtt = qtt != null && qtt.size() > 0;
				// boolean filtrarMaximoRepetidosLista = filtrarQtt &&
				// maximoRepetidosLista > 0;
				boolean filtrarMaximoRepetidosLista = true && maximoRepetidosLista > 0;

				List<List<Integer>> resultados = config.getTodosResultados();

				listaJogosCorrentes.removeAll(resultados);

				if (divide % 3 != 0) {
					divide = new Random().nextInt(2) + 1;
				} else if (divide % 2 != 0) {
					divide = new Random().nextInt(10) + 1;
				} else {
					divide = new Random().nextInt(100) + 1;
				}

				if (ultimos <= 10) {
					ultimos = resultados.size();
				}

				Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
				ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");

				Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
				ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");

				System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

				List<Integer> last = resultados.get(resultados.size() - 1);

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = new ArrayList<>(Arrays.asList(config.getPre()));

				config.setQttInicial(
						config.getQttInicial() - (config.getQttInicial() / 25 * tamExcl));
				for (int i = 0; i < config.getQttInicial(); i++) {
					preJogos.add(preJogo);
				}

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);

				for (List<Integer> t : top) {
					@SuppressWarnings("unchecked")
					List<Integer> inter = ListUtils.intersection(t, excluir);
					if (inter == null || inter.isEmpty()) {
						preJogos.add(new ArrayList<>(t));
					}
				}

				FiltroIf filtro;

				if (isNotEmpty(preJogos)) {
					filtro = new FiltroRemoverIntersecao(listaJogosAtuais);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isNotEmpty(preJogos)) {
					filtro = new FiltroRemoverIntersecao(resultados);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isNotEmpty(preJogos)) {
					if (filtrarQtt) {
						int mxQtt = config.getNrosApostados() - (config.getMaxNum() - qtt.size());
						filtro = new FiltroQuantidadeDaLista(mxQtt, qtt);
						preJogos = filtro.filtrarListas(preJogos);
					}
				}

				boolean filtrar = true;

				if (filtrar && isNotEmpty(preJogos)) {

					System.out.println("Antes: " + preJogos.size());

					filtrarMaximoRepetidosLista = filtrarMaximoRepetidosLista
							&& isNotEmpty(preJogos);

					if (filtrarMaximoRepetidosLista && !listaMax.isEmpty()) {
						filtro = new FiltroMaxLista(listaMax, maximoRepetidosLista);
						preJogos = filtro.filtrarListas(preJogos);
						ArquivoUtil.saveLists(listaMax, pathListaMax, "\t");
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMaximaSequencia(sequenciaMaxima);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMaximoInicio(maximoInicio);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMinimoFim(minimoFim);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroParImpar(5);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMinimoLinhas(5, 5, 1);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroUltimoSorteio(7, 12, last);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMinimoColunas(5, 1);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMaximoLinhas(5, 5, 4);
						preJogos = filtro.filtrarListas(preJogos);
					}
				}

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (

						List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(resultados, meuJogo);
				}
				Collections.sort(jogosLM);

				topN = Math.min(topN, jogosLM.size() - 1);
				List<JogoLotofacil> jogosTop = jogosLM.subList(0, topN);
				for (JogoLotofacil jj : jogosTop) {
					top.add(jj.getNumerosAsList());
				}

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(c++ + "\t" + meuJogo);
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {

					List<Integer> novo = jj.getNumerosAsList();

					System.out.println(novo + "\t" + jj.getMapConta());

					jogos.add(novo);
					listaJogosAtuais.add(novo);
					listaJogosCorrentes.add(novo);
					last = new ArrayList<>(novo);
				}

				System.out.println("Ordenado");
				Collector<CharSequence, ?, String> clt = Collectors.joining("\t");
				StringBuilder str = new StringBuilder();
				for (List<Integer> jogo : jogos) {
					System.out.println(jogo);
					String atual = jogo.stream().map(Object::toString).collect(clt);
					System.out.println(atual);
					str.append(atual);
				}

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);
				listaJogosCorrentes.addAll(jogos);
				listaJogosAtuais.addAll(jogos);

				ArquivoUtil.saveLists(jogosOut, destino, "\t");

				listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
				List<List<Integer>> parcial = new ArrayList<>(listaJogosCorrentes);
				Collections.reverse(parcial);

				ArquivoUtil.saveLists(parcial, jogosCorrentes, "\t");

				ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, "\t");

				String jc = String.valueOf(count + 1);
				String sf = "Concurso: %s, ";
				System.out.println(String.format(sf + "TopN: %s", jc, topN));
				System.out.println(String.format(sf + "Jogos Correntes: ", jc, jogosCorrentes));
				System.out.println(
						String.format(sf + "Não participaram dessa rodada: %s", jc, listaExcl));
				System.out
				.println(String.format(sf + "Meu Jogo Milion�rio: %s", jc, str.toString()));
				System.out.println("\n\n\n");
			} catch (Exception e) {
				e.printStackTrace();
				count--;
			}
		}
	}
}
