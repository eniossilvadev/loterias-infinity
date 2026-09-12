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

import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotomania;
import br.com.enio.silva.loterias.config.LotomaniaConfig80;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaximoInicio;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoFim;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotomania_FrequenciaMaisMenosFull {

	private static Set<Integer> gerarExcluirMax(LotomaniaConfigAb config, int tamPadraoIncluir,
			List<Integer> jogosAtuaisFlat) {

		if (jogosAtuaisFlat == null || jogosAtuaisFlat.isEmpty()) {
			return Collections.emptySet();
		}

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValueDesc(map);

		return getSublistaMap(map, tamPadraoIncluir);
	}

	private static Set<Integer> gerarIncluirMinimo(LotomaniaConfigAb config, int tamPadraoIncluir,
			List<Integer> jogosAtuaisFlat) {

		if (jogosAtuaisFlat == null || jogosAtuaisFlat.isEmpty()) {
			return Collections.emptySet();
		}

		Map<Integer, Integer> map = MapUtil.getMapFrequencia(jogosAtuaisFlat, 1,
				config.getMaxNum());

		map = MapUtil.sortByValue(map);

		return getSublistaMap(map, tamPadraoIncluir);
	}

	/**
	 * @param jogosAtuais
	 * @param jogosCorrentes
	 * @param count
	 * @return
	 */
	protected static List<Integer> getFlat(String jogosAtuais, String jogosCorrentes, int count) {

		List<Integer> jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosAtuais);
		if (count % 4 == 0) {
			jogosAtuaisFlat = ArquivoUtil.obterLinhasComoLista(jogosCorrentes);
		} else if (count % 4 == 1 || count % 4 == 3) {
			jogosAtuaisFlat.addAll(ArquivoUtil.obterLinhasComoLista(jogosCorrentes));
		}
		return jogosAtuaisFlat;
	}

	private static Set<Integer> getSublistaMap(Map<Integer, Integer> map, int size) {
		Set<Integer> resultado = new HashSet<>();
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			resultado.add(entry.getKey());
			if (resultado.size() >= size) {
				return resultado;
			}
		}
		return resultado;
	}

	private static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		int total = 2;
		int ultimos = -1;
		int maximoInicio = 10;
		int minimoFim = 90;

		int tamPadraoIncluir = 10;
		int tamPadraoExcluir = 10;

		LotomaniaConfigAb config = new LotomaniaConfig80();

		Set<List<Integer>> top = new HashSet<>();
		int topN = 2000;

		String jogosAtuais = config.getCaminhoJogoAtual();
		String jogosCorrentes = config.getCaminhoJogoCorrente();

		String destino = config.getFullPath();

		String jd = "E:\\loterias\\lotomania\\config\\LJCD.txt";

		int divide = 1;
		for (int count = 0; count < total; count++) {

			try {

				config.setQttInicial(50000);

				List<List<Integer>> listaJogosDesdobrados = ArquivoUtil
						.obterLinhasComoListasUnique(jd);
				ArquivoUtil.saveLists(listaJogosDesdobrados, jogosAtuais, "\t");

				List<List<Integer>> listaJogosAtuais = ArquivoUtil
						.obterLinhasComoListasUnique(jogosAtuais);
				ArquivoUtil.saveLists(listaJogosAtuais, jogosAtuais, "\t");

				List<List<Integer>> listaJogosCorrentes = ArquivoUtil
						.obterLinhasComoListasUnique(jogosCorrentes);

				List<Integer> jogosAtuaisFlat = getFlat(config.getCaminhoJogoAtual(),
						config.getCaminhoJogoCorrente(), count);

				Set<Integer> listaIncl = gerarIncluirMinimo(config, tamPadraoIncluir,
						jogosAtuaisFlat);

				int tmpTamPadraoExcluir = tamPadraoExcluir + tamPadraoIncluir - listaIncl.size();

				Set<Integer> listaExcl = gerarExcluirMax(config, tmpTamPadraoExcluir,
						jogosAtuaisFlat);

				System.out.println("Números excluídos: " + listaExcl);
				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}
				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());
				List<Integer> qtt = Arrays.asList();

				boolean filtrarQtt = qtt != null && qtt.size() > 0;

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
				List<Integer> preJogo = Arrays.asList(config.getPre());

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

				List<Integer> listaPre = Arrays.asList(config.getPre());
				boolean validarPre = listaPre != null && !listaPre.isEmpty();
				for (List<Integer> t : top) {
					List<Integer> inter = ListUtils.intersection(t, excluir);

					if (inter == null || inter.isEmpty()) {
						if (validarPre) {
							inter = ListUtils.intersection(t, listaPre);
							if (inter != null && inter.size() == listaPre.size()) {
								preJogos.add(new ArrayList<>(t));
							}
						} else {
							preJogos.add(new ArrayList<>(t));
						}
					}
				}

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

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

				boolean filtrar = (count % 5) != 0;

				if (filtrar && isNotEmpty(preJogos)) {

					System.out.println("Antes: " + preJogos.size());

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMaximoInicio(maximoInicio);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMinimoFim(minimoFim);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroParImpar(10);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMinimoLinhas(10, 10, 1);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroUltimoSorteio(7, 15, last);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMinimoColunas(10, 1);
						preJogos = filtro.filtrarListas(preJogos);
					}
				}

				List<JogoLotomania> jogosLM = new ArrayList<JogoLotomania>();

				JogoLotomania jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotomania(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(resultados, meuJogo);
				}
				Collections.sort(jogosLM);

				topN = Math.min(topN, Math.max(0, jogosLM.size() - 1));
				List<JogoLotomania> jogosTop = jogosLM.subList(0, topN);
				for (JogoLotomania jj : jogosTop) {
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

				List<List<Integer>> retorno = EsquemaLotomania
						.converterEsquema80n0f10a(jogosOut.get(0));
				listaJogosDesdobrados.addAll(retorno);
				ArquivoUtil.saveLists(listaJogosDesdobrados, jd, "\t");

				String jc = String.valueOf(count + 1);
				String sf = "Concurso: %s, ";
				System.out.println(String.format(sf + "TopN: %s", jc, topN));
				System.out.println(String.format(sf + "Jogos Correntes: ", jc, jogosCorrentes));
				System.out.println(
						String.format(sf + "Não participaram dessa rodada: %s", jc, listaExcl));
				System.out
				.println(String.format(sf + "Meu Jogo Milion�rio: %s", jc, str.toString()));
				System.out.println("\n\n\n");

				String mc = config.getFrequencia(listaJogosCorrentes);
				ArquivoUtil.save(mc, config.getBasePath() + "correntes.txt");
				String ma = config.getFrequencia(listaJogosAtuais);
				ArquivoUtil.save(ma, config.getBasePath() + "atuais.txt");

			} catch (Exception e) {
				e.printStackTrace();
				count--;
			}
		}
	}
}
