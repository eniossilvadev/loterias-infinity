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
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.commons.SaveLotofacil;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig;
import br.com.enio.silva.loterias.config.lotofacil.LotofacilConfig15;
import br.com.enio.silva.loterias.filtro.FiltroDiffMaximaEntreNumerosConsecutivos;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMaximaSequencia;
import br.com.enio.silva.loterias.filtro.FiltroMaximoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroMaxmoIgualAnterior;
import br.com.enio.silva.loterias.filtro.FiltroMinimoColunas;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroQuantidadeDaLista;
import br.com.enio.silva.loterias.filtro.FiltroRemoverIntersecao;
import br.com.enio.silva.loterias.filtro.FiltroUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotofacil;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.lotomania.PontuadorBasico;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarJogosLotofacilNovoLoopInverte {

	private static <T> boolean isNotEmpty(List<T> lista) {
		return lista != null && !lista.isEmpty();
	}

	public static void main(String[] args) throws IOException {

		int nJogos = 10;

		LotofacilConfig config = new LotofacilConfig15();

		config.setQttInicial(150000);

		Set<List<Integer>> top = new HashSet<>();
		int topN = 3000;

		String f = config.getCaminhoJogoCorrente();
		List<List<Integer>> atual = ArquivoUtil.obterLinhasComoListasUnique(f);

		System.out.println("Tamanho da lista de origem (sem repeti��es): " + atual.size());

		String lm = config.getCaminhoJogoCorrente();
		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(lm);

		String jogosCorrentes = config.getCaminhoJogoCorrente();

		List<Integer> todos = ArquivoUtil.obterLinhasComoLista(lm);
		int tamRemover = 3;

		List<List<Integer>> erro = new ArrayList<>();

		List<List<Integer>> somenteNovos = new ArrayList<>();

		int ultimos = 50;
		int count = 0;
		int divide = 1;
		while (count < nJogos) {

			count++;
			Collections.shuffle(listaMax);
			List<Integer> la = listaMax.get(0);

			topN = new Random().nextInt(topN * 2) + 1500;

			try {

				List<List<Integer>> listaJogosCorrentes = ArquivoUtil
						.obterLinhasComoListasUnique(jogosCorrentes);

				config.setPre(new Integer[] {});

				Integer[] pre = config.getPre();
				if (pre != null && pre.length > 0) {
					todos.removeAll(Arrays.asList(pre));
				}

				if (tamRemover > 0) {
					Set<Integer> set = new TreeSet<>();
					Collections.shuffle(todos);
					while (set.size() < tamRemover) {
						set.add(todos.remove(0));
					}
					config.setExcluir(Arrays.asList());
				}
				config.setIncluir(Arrays.asList());
				List<Integer> qtt = la;

				int maximoRepetidosLista = 13;

				String destino = config.getFullPath();
				String params = config.getFullPathParams();

				System.out.println(config.toJson());

				int maxAnterior = 15;

				List<List<Integer>> resultados = config.getTodosResultados();

				listaJogosCorrentes.removeAll(resultados);

				if (ultimos <= 0) {
					if (divide % 3 == 0) {
						divide = new Random().nextInt(2) + 1;
					} else {
						divide = new Random().nextInt(20) + 1;

					}
					ultimos = resultados.size() / divide;
				}

				Map<Integer, Integer> mapResultado = config.getMapResultado(resultados, ultimos);
				ArquivoUtil.save(mapResultado.toString(), config.getBasePath() + "RESULT.txt");

				Map<Integer, Integer> mapAtraso = config.getMapAtraso(resultados);
				ArquivoUtil.save(mapAtraso.toString(), config.getBasePath() + "ULTIMA_VEZ.txt");

				List<Integer> last = resultados.get(resultados.size() - 1);

				List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
				List<Integer> preJogo = new ArrayList<>(Arrays.asList(config.getPre()));

				for (int i = 0; i < config.getQttInicial(); i++) {
					preJogos.add(preJogo);
				}

				int tam = config.getNrosApostados();
				int maxNum = config.getMaxNum();
				List<Integer> excluir = config.getExcluir();
				List<Integer> incluir = config.getIncluir();

				preJogos = ListUtil.completarExcluirIncluir(preJogos, tam, maxNum, excluir,
						incluir);

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> rem = ArquivoUtil.obterLinhasComoListasUnique(remove);
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				remover.addAll(resultados);

				for (List<Integer> t : top) {
					@SuppressWarnings("unchecked")
					List<Integer> inter = ListUtils.intersection(t, excluir);
					if (inter == null || inter.isEmpty()) {
						preJogos.add(new ArrayList<>(t));
					}
				}

				FiltroIf filtro;

				if (isNotEmpty(preJogos)) {
					filtro = new FiltroRemoverIntersecao(remover);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isNotEmpty(preJogos)) {
					if (isNotEmpty(qtt)) {
						int mxQtt = config.getNrosApostados() - (config.getMaxNum() - qtt.size());
						System.out.println("FiltroQuantidadeDaLista: " + mxQtt);
						filtro = new FiltroQuantidadeDaLista(mxQtt, qtt);
						preJogos = filtro.filtrarListas(preJogos);
					} else {
						filtro = new FiltroUltimoSorteio(8, 11, last);
						// preJogos = filtro.filtrarListas(preJogos);
					}
				}

				boolean filtrar = true;

				if (filtrar && isNotEmpty(preJogos)) {
					System.out.println("Antes: " + preJogos.size());

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMaximaSequencia(11);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroDivide(3, config.getMaxNum());
						// preJogos = filtro.filtrarListas(preJogos);

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
						filtro = new FiltroMinimoColunas(5, 1);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroMaximoLinhas(5, 5, 4);
						// preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(preJogos)) {
						filtro = new FiltroDiffMaximaEntreNumerosConsecutivos(4);
						preJogos = filtro.filtrarListas(preJogos);
					}

					if (isNotEmpty(listaMax) && isNotEmpty(preJogos) && maximoRepetidosLista > 0) {
						filtro = new FiltroMaxLista(listaMax, maximoRepetidosLista);
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
					Integer before = meuJogo.getPontuacao();
					((PontuadorBasico) pontuador).pontuarPorPosicao(resultados, meuJogo, 1);
					Integer after = meuJogo.getPontuacao();
					List<Integer> lista = meuJogo.getNumerosAsList();

					String msg = String.format("[%s, %s] %s", before, after, lista);
					System.out.println(msg);
				}

				if (config.isPontuacaoExtra()) {
					for (JogoAb meuJogo : jogosLM) {

						Integer before = meuJogo.getPontuacao();
						pontuador.pontuarMap(meuJogo, mapResultado, 25, 1);

						Integer after = meuJogo.getPontuacao();
						List<Integer> lista = meuJogo.getNumerosAsList();

						String msg = String.format("[%s, %s] %s", before, after, lista);
						System.out.println(msg);
					}
				}

				Collections.sort(jogosLM);

				topN = Math.min(topN, jogosLM.size() - 1);

				List<JogoLotofacil> jogosTop = jogosLM.subList(0, topN);
				for (JogoLotofacil jj : jogosTop) {
					top.add(jj.getNumerosAsList());
				}

				filtro = new FiltroMaxmoIgualAnterior(maxAnterior);
				// preJogos = filtro.filtrarListas(preJogos);

				jogosLM = jogosLM.subList(0, config.getNrosJogos());

				int c = 0;
				for (JogoAb meuJogo : jogosLM) {
					System.out.println(meuJogo.getPontuacao() + "[" + c++ + "]: "
							+ meuJogo.getNumerosAsList());
				}

				List<List<Integer>> jogos = new ArrayList<List<Integer>>();
				for (

						JogoAb jj : jogosLM) {
					jogos.add(jj.getNumerosAsList());
					System.out.println(jj.getNumerosAsList() + "\t" + jj.getMapConta());
				}

				System.out.println("Ordenado");
				for (List<Integer> jogo : jogos) {
					todos.addAll(jogo);
					System.out.println(jogo);
					System.out.println(
							jogo.stream().map(Object::toString).collect(Collectors.joining("\t")));
				}

				List<List<Integer>> jogosOut = new ArrayList<List<Integer>>();
				jogosOut.addAll(jogos);

				ArquivoUtil.saveLists(jogosOut, destino, "\t");

				ArquivoUtil.save(config.toString(), params);

				rem.addAll(jogosOut);
				ArquivoUtil.saveLists(rem, remove, "\t");

				listaJogosCorrentes.addAll(jogos);
				somenteNovos.addAll(jogos);
				int size = listaJogosCorrentes.size();
				listaJogosCorrentes = new ArrayList<>(new HashSet<>(listaJogosCorrentes));
				if (listaJogosCorrentes.size() < size) {
					count = count - (size - listaJogosCorrentes.size());
				}

				SaveLotofacil saveLotofacil = new SaveLotofacil();
				String base = saveLotofacil.getConfig();
				String sn = base + "LSN.txt";

				ArquivoUtil.saveLists(listaJogosCorrentes, jogosCorrentes, "\t");

				somenteNovos = new ArrayList<>(new HashSet<>(somenteNovos));
				ArquivoUtil.saveLists(somenteNovos, sn, "\t");

				config.setSufixo("_shuffle");
				Collections.shuffle(jogosOut);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Não foi processar lista: " + la);
				erro.add(la);
				count--;
			}
		}

		if (!erro.isEmpty()) {
			System.out.println("Favor reprocessar :" + erro);
		}
	}
}
