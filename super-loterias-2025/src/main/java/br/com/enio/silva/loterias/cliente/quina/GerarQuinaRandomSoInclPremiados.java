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

import br.com.enio.silva.loterias.config.quina.QuinaConfig5;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.util.ListaUtils;

import util.ListUtil;

public class GerarQuinaRandomSoInclPremiados extends BaseQuina {

	private static int quatidadeDeJogos = 4;

	private static int qttInicial = 80000;

	public static int ultimos = -1;

	public static int tamIncluir = 5;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		int[] ultimosJogos = { 0, 100, 0, 50, 260, 500, 0, 1000, 2000, 75, 26, 125 };

		QuinaConfigAb config = new QuinaConfig5();

		Set<List<Integer>> top = new HashSet<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		String sn = config.getCaminhoDefaultOutput();

		String path = CaminhoResultados.QUINA.getPath();
		List<List<Integer>> resultados = GerarListaQuina.getInstance().gerarArquivoResultado(path);

		List<List<Integer>> listasInclusao = config.getTodosResultados();

		Collections.shuffle(listasInclusao);

		for (int count = 0; count < quatidadeDeJogos; count++) {

			try {

				int tamPadraoIncluir = tamIncluir - Math.abs(new Random().nextInt()) % 2;

				List<Integer> listaSelInclusao = listasInclusao.get(count % listasInclusao.size());

				config.setQttInicial(qttInicial);

				List<Integer> listaSelecaoInclusao = new ArrayList<>(listaSelInclusao);
				Set<Integer> listaIncl = ListaUtils.getFromList(listaSelecaoInclusao,
				        tamPadraoIncluir);

				List<Integer> lista = ListaUtils.iterateStream(1, 1, 80);
				lista.removeAll(listaSelecaoInclusao);

				Set<Integer> listaExcl = new HashSet<>(listaSelecaoInclusao);
				listaExcl.removeAll(listaIncl);

				System.out.println("Números incluídos: " + listaIncl);

				if (listaIncl != null && !listaIncl.isEmpty()) {
					Integer[] myArray = new Integer[listaIncl.size()];
					listaIncl.toArray(myArray);
					config.setPre(myArray);
				}

				config.setExcluir(new ArrayList<>(listaExcl));
				config.setIncluir(Arrays.asList());

				ultimos = ultimosJogos[count % ultimosJogos.length];

				int ultimos = ultimosJogos[count % ultimosJogos.length];
				ultimos = ultimos <= 10 || ultimos > resultados.size() - 1 ? resultados.size()
				        : ultimos;
				List<List<Integer>> ultimosResultados = config.getTodosResultados();
				Collections.reverse(ultimosResultados);
				ultimosResultados = ultimosResultados.subList(0, ultimos);

				System.out.println("Esperer um pouco! Número de concursos: " + resultados.size());

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = Arrays.asList(config.getPre());

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = preJogo;

				for (int i = 0; i < config.getQttInicial(); i++) {
					preJogos.add(preJogo);
				}
				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
				        incluir);

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

				preJogos.removeAll(resultados);

				preJogos = new ArrayList<>(new HashSet<>(preJogos));

				List<JogoQuina> jogosLM = new ArrayList<JogoQuina>();

				JogoQuina jlm = null;
				for (List<Integer> pj : preJogos) {
					jlm = new JogoQuina(pj);
					jogosLM.add(jlm);
				}

				System.out.println("\n");

				Pontuador pontuador = config.getPontuador();
				for (JogoAb meuJogo : jogosLM) {
					pontuador.pontuar(ultimosResultados, meuJogo);
				}
				Collections.sort(jogosLM);

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

					somenteNovos.add(novo);
				}

				StringBuilder str = printOrdenado(jogos);

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				save(somenteNovos, sn);

				System.out.println("Tamanho padr�o incluir: " + tamPadraoIncluir);
				System.out.println("Lista Incluir: " + listaSelInclusao);

			} catch (Exception e) {
				e.printStackTrace();
				count--;
				save(somenteNovos, sn);
			}
		}
	}

}
