package br.com.enio.silva.loterias.gerador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

import br.com.enio.silva.loterias.cliente.megasena.GerarJogosMegaLoopAb;
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
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarMegaLoopExcluirIncluirRandom extends GerarJogosMegaLoopAb {

	public static void main(String[] args) throws IOException {

		int totalDeJogos = 23;

		boolean includeTop = false;
		int qttInicialFixo = -1;

		int tamPadraoExcluir = 0;
		int varIncluir = 10;
		int tamPadraoIncluir = 1;
		// int minPercent = 10;
		// int maxPercent = 20;

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

		System.out.println(listaJogosAtuais);

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
		Set<List<Integer>> top = new HashSet<>();

		MegaSenaConfigAb config = getConfig(0);

		String mcb = config.getFrequencia(listaJogosCorrentes);
		ArquivoUtil.save(mcb, CaminhoResultados.MEGA_SENA.getBasePath() + "correntes.txt");
		String mab = config.getFrequencia(listaJogosAtuais);
		ArquivoUtil.save(mab, CaminhoResultados.MEGA_SENA.getBasePath() + "atuais.txt");

		getJogosCorrentes(config, listaJogosAtuais, listaJogosCorrentes);

		listaJogosAtuais = getJogosAtuais(config, listaJogosAtuais);

		List<Integer> last = null;

		for (int count = 0; count < totalDeJogos; count++) {

			includeTop = includeTop && new Random().nextBoolean();

			config = getConfig(count);

			try {
				int maxRepeditosMeusJogos = getMrmj(config);
				int maxRepeditosResultados = getMrmj(config, maxRepeditosMeusJogos) + 1;

				tamPadraoIncluir = 4 - count % 4;

				if (qttInicialFixo < 0) {
					int mod2 = count % 2;
					int mod3 = count % 3;

					if (mod2 == 0) {
						qttInicialFixo = 150000;
					} else if (mod3 == 1) {
						qttInicialFixo = 126000;
					} else {
						qttInicialFixo = 175000;
					}
				}
				config.setQttInicial(qttInicialFixo / 2);

				String output = base + "individual\\" + config.getDefaultName() + ".txt";

				String remove = config.getCaminhoJogoAtual();
				List<List<Integer>> remover = ArquivoUtil.obterLinhasComoListasUnique(remove);
				if (count == 0) {
					ArquivoUtil.saveLists(remover, remove);
				}

				System.out.println(repeated50);
				System.out.println(repeated20 + (count + 1) + repeated20);

				Set<Integer> listaExcl = new HashSet<>();
				Set<Integer> listaIncl = new HashSet<>();
				tamPadraoExcluir = 10 + new Random().nextInt(varIncluir);

				List<Integer> li = ListaUtils.iterateStream(1, 1, 60);
				Collections.shuffle(li);
				listaIncl = new HashSet<>(li.subList(0, tamPadraoIncluir));

				List<Integer> le = ListaUtils.iterateStream(1, 1, 60);
				le.removeAll(listaIncl);
				Collections.shuffle(le);
				listaExcl = new HashSet<>(le.subList(0, tamPadraoExcluir));

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

				if (includeTop) {
					for (List<Integer> t : top) {
						@SuppressWarnings("unchecked")
						List<Integer> inter = ListUtils.intersection(t, excluir);
						if (inter == null || inter.isEmpty()) {
							preJogos.add(new ArrayList<>(t));
						}
					}
				}

				FiltroIf filtro = null;

				if (isFiltrarAtual(preJogos)) {
					filtro = new FiltroRemoverIntersecao(remover);
					preJogos = filtro.filtrarListas(preJogos);
				}

				if (isFiltrarAtual(preJogos)) {
					// Filtrar pela lista de jogos correntes
					filtro = new FiltroMaxLista(listaJogosAtuais, maxRepeditosMeusJogos);
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
	}

}
