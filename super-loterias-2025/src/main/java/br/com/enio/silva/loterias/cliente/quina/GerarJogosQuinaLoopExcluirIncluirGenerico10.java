package br.com.enio.silva.loterias.cliente.quina;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.enio.silva.loterias.config.quina.QuinaConfig10;
import br.com.enio.silva.loterias.config.quina.QuinaConfigAb;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.gerador.Base;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoQuina;

import util.ListUtil;

public class GerarJogosQuinaLoopExcluirIncluirGenerico10 extends Base {

	public static String repeated50 = new String(new char[50]).replace("\0", "-");

	public static String repeated20 = new String(new char[20]).replace("\0", "-");

	public static List<Integer> getNumerosDaSorte(List<List<Integer>> resultados,
			List<List<Integer>> listaDeJogosCorrentes, List<Integer> excluirLista) {
		return getNumerosDaSorte(resultados, listaDeJogosCorrentes, excluirLista, 60000, null);
	}

	public static List<Integer> getNumerosDaSorte(List<List<Integer>> resultados,
			List<List<Integer>> listaDeJogosCorrentes, List<Integer> excluirLista, final int qttInicialFixo, List<List<Integer>> listaFiltro) {

		final List<Integer> retorno = new ArrayList<>();

		QuinaConfigAb config = new QuinaConfig10();

		List<List<Integer>> ultimosResultados = new ArrayList<>(resultados);

		try {
			if (qttInicialFixo > 0) {
				config.setQttInicial(qttInicialFixo);
			}

			Set<Integer> listaExcl = excluirLista != null ? new HashSet<>(excluirLista)
					: Collections.emptySet();

			List<List<Integer>> preJogos = config.getPreJogos();

			if(listaFiltro == null || listaFiltro.isEmpty()) {
				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				config.setExcluir(new ArrayList<>(listaExcl));
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir, incluir);
			} else {
				final List<List<Integer>> listaTmp = new ArrayList<>();
				listaFiltro.forEach(el -> listaTmp.add(el));
				preJogos = listaTmp;
			}

			FiltroIf filtro = null;

			if (!preJogos.isEmpty()) {
				filtro = new FiltroMaxConsecutivos(4);
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
					retorno.addAll(novo);
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					System.out.println(jogo);
				}

				return retorno;

			} else {

				for (int i = 0; i < 6; i++) {
					System.out.println("Não há resultados com os filtros aplicados");
					Thread.sleep(10000);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Collections.emptyList();

	}

	public static void main(String[] args) throws IOException {

	}

}
