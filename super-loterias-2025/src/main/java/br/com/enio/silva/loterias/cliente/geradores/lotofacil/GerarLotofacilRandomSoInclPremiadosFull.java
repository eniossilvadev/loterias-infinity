package br.com.enio.silva.loterias.cliente.geradores.lotofacil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.lotofacil.deprecated.GerarJogosLotofacil2020Ab;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15Simples;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15_13;
import br.com.enio.silva.loterias.util.CombinationUtils;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarLotofacilRandomSoInclPremiadosFull extends GerarJogosLotofacil2020Ab {

	private static int qttInicial = 50000;

	public static int ultimos = -1;

	public static int sequenciaMaxima = 12;

	public static int maximoInicio = 7;

	public static int minimoFim = 18;

	public static int quantidadeDaListaPremiados = 12;

	private static String sep = ", ";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		int[] ultimosJogos = { 0, 0, 2000, 0, 75, 26, 0, 125, 50, 100, 0, 260, 500, 10, 0, 1000, };

		int[] variation = { -1, 0, 1};

		// LotofacilConfig config = new LotofacilConfig15();

		Set<List<Integer>> top = new HashSet<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		init();

		String sn = "D:\\Meus Documentos\\Downloads\\y.txt";
		String liPremiados = "D:\\Meus Documentos\\Downloads\\x.txt";

		List<List<Integer>> listasInclusao = ArquivoUtil.obterLinhasComoListasUnique(liPremiados);

		Collections.shuffle(listasInclusao);
		ArquivoUtil.saveLists(listasInclusao, liPremiados, sep, 2);
		Collections.shuffle(listasInclusao);

		List<List<Integer>> resultados = config.getTodosResultados();

		int randomic = Math.abs(new Random().nextInt()) % 26;

		int count = randomic;

		for (List<Integer> listaSelInclusao: listasInclusao) {
			config = new LotofacilConfig15();
			if (count % 3 == 0) {
				config.setPontuador(new PontuadorLotofacil15());
			} else if (count % 2 == 0) {
				config.setPontuador(new PontuadorLotofacil15Simples());
			} else {
				config.setPontuador(new PontuadorLotofacil15_13());
			}

			try {

				int tamPadraoIncluir = quantidadeDaListaPremiados + variation[randomic % variation.length];

				config = getConfig(count);

				config.setQttInicial(qttInicial);

				List<Integer> listaSelecaoInclusao = new ArrayList<>(listaSelInclusao);
				Set<Integer> listaIncl = ListaUtils.getFromList(listaSelecaoInclusao,
						tamPadraoIncluir);

				List<Integer> lista = ListaUtils.iterateStream(1, 1, 25);
				lista.removeAll(listaSelecaoInclusao);

				Set<Integer> listaExcl = new HashSet<>(listaSelecaoInclusao);
				listaExcl.removeAll(listaIncl);

				print(count, "Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				listaJogosCorrentes.removeAll(resultados);

				if (count % 3 == 1) {
					ultimos = ultimosJogos[count % ultimosJogos.length];
				} else {
					int rand = (count + Math.abs(new Random().nextInt()));
					ultimos = ultimosJogos[rand % ultimosJogos.length];
				}

				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
						: ultimos;
				List<List<Integer>> ultimosResultados = config.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

				saveMapResultado(config, resultados, ultimos);

				saveMapAtraso(config, resultados);

				print(count, "Esperer um pouco! Número de concursos: " + resultados.size());

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = Arrays.asList(config.getPre());

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = preJogo;

				int totalIncluirExcluir = excluir.size() + incluir.size();
				if (totalIncluirExcluir >= 4) {
					preJogos = CombinationUtils.gerarCombinacao(tam, 1, maxNum, excluir, incluir);
				} else {
					for (int i = 0; i < config.getQttInicial(); i++) {
						preJogos.add(preJogo);
					}
					preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
							incluir);
				}

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

				preJogos.removeAll(listaJogosCorrentes);
				preJogos.removeAll(listaJogosAtuais);
				preJogos.removeAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				Set<List<Integer>> filtroIntersecao = new HashSet<>(listaJogosAtuais);
				filtroIntersecao.addAll(resultados);
				filtroIntersecao.addAll(listaJogosCorrentes);

				preJogos = aplicarFiltroRemoverIntersecao(new ArrayList<>(filtroIntersecao),
						preJogos);

				List<JogoLotofacil> jogosLM = new ArrayList<JogoLotofacil>();

				JogoLotofacil jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoLotofacil(pj);
					jogosLM.add(jlm);
				}

				print(count, "\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(ultimosResultados, meuJogo);
				}
				Collections.sort(jogosLM);

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					print(count, c++ + "\t" + meuJogo);
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (JogoAb jj : jogosLM) {

					List<Integer> novo = jj.getNumerosAsList();

					print(count, novo + "\t" + jj.getMapConta());

					jogos.add(novo);
					if (listaJogosAtuais.contains(novo) || listaJogosCorrentes.contains(novo)) {
						throw new Exception("J� tem esse jogo: " + novo);
					}
					listaJogosAtuais.add(novo);
					listaJogosCorrentes.add(novo);

					somenteNovos.add(novo);
				}

				StringBuilder str = printOrdenado(jogos);

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				listaJogosAtuais = CombinationUtils.gerarCombinacoes(listaJogosAtuais,
						config.getNrosApostados());

				listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));

				int size = somenteNovos.size();
				somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
				if (somenteNovos.size() < size) {
					count = count - (size - somenteNovos.size());
				}

				print(count, "Tamanho padrão incluir: " + tamPadraoIncluir);
				print(count, "Lista Incluir: " + listaSelInclusao);
				saveAll(config, somenteNovos, listaJogosAtuais, listaJogosCorrentes, sn, count,
						Collections.emptySet(), str, jogosOut);

			} catch (Exception e) {
				e.printStackTrace();
				count--;
				save(listaJogosAtuais, config.getCaminhoJogoAtual());
				save(listaJogosCorrentes, config.getCaminhoJogoCorrente());
				save(somenteNovos, sn);
			}
		}
	}

}
