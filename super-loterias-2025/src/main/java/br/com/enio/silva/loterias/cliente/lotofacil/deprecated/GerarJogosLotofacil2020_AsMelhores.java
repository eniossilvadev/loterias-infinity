package br.com.enio.silva.loterias.cliente.lotofacil.deprecated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoInicio;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoFim;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.CombinationUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotofacil2020_AsMelhores {

	private static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	public static void logMe(String... strings) {
		StringBuilder str = new StringBuilder();
		for (String s : strings) {
			str.append(s).append("\t\t");
		}
		System.out.println(str.toString());
	}

	public static void main(String[] args) throws IOException {

		int gerados = 0;
		int naoGerados = 0;
		String melhores = "C:\\loterias\\gerador-apostas\\lotofacil\\config\\MELHORES.txt";

		List<List<Integer>> soAsMelhores = ArquivoUtil.obterLinhasComoListasUnique(melhores);

		int topN = 10;
		int iniParcial = 170000;
		int[] repeticaoMelhores = { 11, 12 };

		int sequenciaMaxima = 8;
		int maximoInicio = 4;
		int minimoFim = 21;

		LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		String jogosAtuais = config.getCaminhoJogoAtual();
		String jogosCorrentes = config.getCaminhoJogoCorrente();

		String destino = config.getFullPath();

		int divide = 1;
		int count = 0;
		String cont = StringUtils.EMPTY;

		Collections.shuffle(soAsMelhores);

		for (List<Integer> l : soAsMelhores) {

			try {

				topN = new Random().nextInt(topN * 3) + 26;

				count++;
				cont = String.valueOf(count);

				List<List<Integer>> listaJogosAtuais = ArquivoUtil
						.obterLinhasComoListasUnique(jogosAtuais);
				ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, "\t");

				List<List<Integer>> listaJogosCorrentes = ArquivoUtil
						.obterLinhasComoListasUnique(jogosCorrentes);

				List<List<Integer>> combs = new ArrayList<>();

				for (int rm : repeticaoMelhores) {
					combs.addAll(CombinationUtils.gerarCombinacao(l, rm));
				}

				config.setQttInicial(iniParcial / combs.size() + 1);

				if (divide % 3 != 0) {
					divide = new Random().nextInt(3) + 1;
				} else {
					divide = new Random().nextInt(20) + 1;
				}
				divide++;

				List<List<Integer>> resultados = config.getTodosResultados();

				listaJogosCorrentes.removeAll(resultados);

				divide = 75;

				int ultimos = resultados.size() / divide;

				Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
				ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");

				Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
				ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");

				logMe(cont, "Esperer um pouco! Número de concursos: " + resultados.size());

				List<Integer> last = resultados.get(resultados.size() - 1);

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

				for (List<Integer> combCurr : combs) {

					List<Integer> listaExcl = new ArrayList<>(l);
					listaExcl.removeAll(combCurr);

					logMe(cont, "Números excluídos: " + listaExcl);

					config.setPre(new Integer[] {});
					config.setExcluir(new ArrayList<>(listaExcl));
					config.setIncluir(Arrays.asList());

					List<Integer> preJogo = new ArrayList<>(Arrays.asList(config.getPre()));
					for (int i = 0; i < config.getQttInicial(); i++) {
						preJogos.add(preJogo);
					}
				}

				Collections.shuffle(preJogos);

				logMe(cont, "Total de jogos a ser preenchidos: " + preJogos.size());

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				logMe(cont, "Total de jogos a efetivamente preenchidos: " + preJogos.size());

				for (List<Integer> t : top) {
					@SuppressWarnings("unchecked")
					List<Integer> inter = ListUtils.intersection(t, excluir);
					if (inter == null || inter.isEmpty()) {
						preJogos.add(new ArrayList<>(t));
					}
				}

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);

				FiltroIf filtro;

				if (isNotEmpty(preJogos)) {
					filtro = new FiltroRemoverIntersecao(listaJogosAtuais);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isNotEmpty(preJogos)) {
					filtro = new FiltroRemoverIntersecao(resultados);
					preJogos = filtro.filtrarListas(preJogos);
				}

				boolean filtrar = true;

				if (filtrar && isNotEmpty(preJogos)) {

					logMe(cont, "Antes: " + preJogos.size());

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
						filtro = new FiltroUltimoSorteio(5, 13, last);
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

				logMe(cont, "\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(resultados, meuJogo);
				}
				Collections.sort(jogosLM);

				if (!jogosLM.isEmpty()) {

					topN = Math.min(topN, jogosLM.size() - 1);
					List<JogoLotofacil> jogosTop = jogosLM.subList(0, topN);
					for (JogoLotofacil jj : jogosTop) {
						top.add(jj.getNumerosAsList());
					}

					jogosLM = jogosLM.subList(0, config.getNrosJogos());

					int c = 0;
					for (JogoAb meuJogo : jogosLM) {
						logMe(cont, c++ + "\t" + meuJogo);
					}

					List<List<Integer>> jogos = new ArrayList<List<Integer>>();
					for (JogoAb jj : jogosLM) {
						List<Integer> novo = jj.getNumerosAsList();

						jogos.add(novo);
						logMe(cont, novo + "\t" + jj.getMapConta());
						listaJogosAtuais.add(novo);
						listaJogosCorrentes.add(novo);

						last = new ArrayList<>(novo);
					}

					logMe(cont, "Ordenado");
					Collector<CharSequence, ?, String> clt = Collectors.joining("\t");
					StringBuilder str = new StringBuilder();
					for (List<Integer> jogo : jogos) {
						logMe(cont, jogo.toString());
						String atual = jogo.stream().map(Object::toString).collect(clt);
						logMe(cont, atual);
						str.append(atual);
					}

					List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
					jogosOut.addAll(jogos);
					listaJogosCorrentes.addAll(jogos);
					listaJogosAtuais.addAll(jogos);

					ArquivoUtil.saveLists(jogosOut, destino, "\t");

					listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
					List<List<Integer>> parcial = new ArrayList<>(listaJogosCorrentes);

					logMe(cont, "Parcial Antes", parcial.toString());

					Collections.sort(parcial, new Comparator<List<Integer>>() {

						@Override
						public int compare(List<Integer> o1, List<Integer> o2) {
							int diff = o1.size() - o2.size();
							if (diff != 0) {
								return diff;
							}
							for (int i = 0; i < o1.size(); i++) {
								int dif = o1.get(i) - o2.get(i);
								if (dif != 0) {
									return dif;
								}
							}
							return 0;
						}
					});
					ArquivoUtil.saveLists(parcial, jogosCorrentes, "\t");

					logMe(cont, "Parcial Depois", parcial.toString());

					ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, "\t");

					String jc = String.valueOf(count);
					String sf = "Concurso: %s, ";
					logMe(cont, String.format(sf + "TopN: %s", jc, topN));
					logMe(count + String.format(sf + "Jogos Correntes: ", jc, jogosCorrentes));
					logMe(count + String.format(sf + "Jogo Milion�rio: %s", jc, str.toString()));
					logMe(cont, "\n\n\n");
					gerados++;
				} else {
					logMe(cont, "Ops! Não há jogos para a rodada atual: " + l);
					naoGerados++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				count--;
				naoGerados++;
			}

			logMe(cont, "Gerados: " + gerados);
			logMe(cont, "Não Gerados: " + naoGerados);
		}
	}
}
