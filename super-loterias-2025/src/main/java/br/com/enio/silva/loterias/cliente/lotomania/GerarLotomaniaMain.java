package br.com.enio.silva.loterias.cliente.lotomania;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import br.com.enio.silva.loterias.config.LotomaniaConfig80;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.filtro.FiltroDivide;
import br.com.enio.silva.loterias.filtro.FiltroExcluirTemUm;
import br.com.enio.silva.loterias.filtro.FiltroIf;
import br.com.enio.silva.loterias.filtro.FiltroMaxLista;
import br.com.enio.silva.loterias.filtro.FiltroMinimoLinhas;
import br.com.enio.silva.loterias.filtro.FiltroParImpar;
import br.com.enio.silva.loterias.filtro.FiltroSubstituirUltimoSorteio;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomania80;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import util.ListUtil;

public class GerarLotomaniaMain extends GerarJogosLotomaniaBase {

	public static void main(String[] args) throws IOException, InvalidLenghtException {

		LotomaniaConfigAb conf = new LotomaniaConfig80();
		conf.setQttInicial(conf.getQttInicial() / 4);

		List<List<Integer>> resultados = GerarListaLotomania.getInstance()
		        .gerarArquivoResultado(conf);

		String pathMaxLista = conf.getBasePath() + "MAX_LISTA.txt";
		List<List<Integer>> listaMax = ArquivoUtil.obterLinhasComoListasUnique(pathMaxLista);

		int ultimos = resultados.size();

		Map<Integer, Integer> mapResultado = getMapResultado(resultados, ultimos);
		Map<Integer, Integer> mapAtraso = getMapAtraso(resultados, conf.getMaxNum());

		salvarStats(mapResultado, mapAtraso);

		List<Integer> last = resultados.get(resultados.size() - 1);

		List<List<Integer>> preJogos = new ArrayList<List<Integer>>();
		for (int i = 0; i < conf.getQttInicial(); i++) {
			List<Integer> lista = new ArrayList<>();
			preJogos.add(lista);
		}

		preJogos = ListUtil.completar(preJogos, conf.getNrosApostados(), conf.getMaxNum());

		FiltroIf filtro = null;

		System.out.println("Antes: " + preJogos.size());

		filtro = new FiltroSubstituirUltimoSorteio(last, conf.getMaxNum(), 15);
		preJogos = filtro.filtrarListas(preJogos);

		// filtro = new FiltroUltimoSorteio(0, 5, last);

		if (listaMax != null && !listaMax.isEmpty()) {
			filtro = new FiltroMaxLista(listaMax, conf.getNrosApostados() - 10);
			preJogos = filtro.filtrarListas(preJogos);
		}

		filtro = new FiltroDivide(10, conf.getMaxNum());
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroParImpar(10);
		preJogos = filtro.filtrarListas(preJogos);

		filtro = new FiltroMinimoLinhas(10, 10, 2);
		preJogos = filtro.filtrarListas(preJogos);

		// filtro = new FiltroMaximoLinhas(10, 10, max);
		// preJogos = filtro.filtrarListas(preJogos);

		Set<Integer> set = new HashSet<Integer>(Arrays.asList(-1));
		List<Integer> temUm = new ArrayList<Integer>(set);
		filtro = new FiltroExcluirTemUm(temUm);

		List<JogoLotomania> jogosLM = new ArrayList<JogoLotomania>();
		JogoLotomania jlm = null;
		for (List<Integer> pj : preJogos) {
			jlm = new JogoLotomania(pj);
			jogosLM.add(jlm);
		}

		System.out.println("\n");
		Pontuador pontuador = new PontuadorLotomania80();

		int count = 0;
		int max = 0;
		Random r = new Random();
		boolean isMaior = false;
		String up = ">";
		for (JogoAb meuJogo : jogosLM) {

			pontuador.pontuar(resultados, meuJogo);
			Integer before = meuJogo.getPontuacao();
			((PontuadorLotomania80) pontuador).pontuar(resultados, meuJogo, mapResultado,
			        mapAtraso);
			Integer after = meuJogo.getPontuacao();
			List<Integer> list = meuJogo.getNumerosAsList();
			isMaior = after > max;
			if (isMaior) {
				max = after;
				up = ">";
			} else {
				up = "-";
			}
			if (isMaior || (count % (r.nextInt(2000) + 1)) == 0) {
				// diff = max - after;
				System.out.println(count + "\t" + max + "," + up + "\t[" + before + ", " + after
				        + "] " + list);
			}
			count++;
		}

		Collections.sort(jogosLM);

		jogosLM = jogosLM.subList(0, conf.getNrosJogos());
		int c = 0;
		for (JogoAb meuJogo : jogosLM) {
			System.out.println(
			        meuJogo.getPontuacao() + "[" + c++ + "]: " + meuJogo.getNumerosAsList());
		}

		List<List<Integer>> jogos = new ArrayList<List<Integer>>();
		for (JogoAb jj : jogosLM) {
			jogos.add(jj.getNumerosAsList());
		}

		System.out.println("Ordenado");
		for (List<Integer> jogo : jogos) {
			System.out.println(jogo);
		}
		Map<String, String> replace = new HashMap<String, String>();
		// replace.put("100", "0");

		List<List<Integer>> original = new ArrayList<>(jogos);
		ArquivoUtil.saveLists(original, pathMaxLista, "\t", replace, 2);

		jogos = getEsquemas(jogos, mapResultado, mapAtraso);

		jogos = getEspelhos(jogos);

		ArquivoUtil.saveLists(jogos, conf.getCaminhoDefaultOutput(), "\t", replace, 2);
		// }
	}

	public static void salvarStats(Map<Integer, Integer> mapResultado,
	        Map<Integer, Integer> mapAtraso) throws IOException {

		// StringBuilder str1 = new StringBuilder();
		// StringBuilder str2 = new StringBuilder();
		//
		// str1.append("Mais Tempo Sem Sair\n");
		// str1.append(MapUtil.getAllKeyValues(mapAtraso, "\n"));
		// str1.append("\n\n");
		//
		// ArquivoUtil.save(str1.toString(),
		// "C:\\loterias\\gerador-apostas\\lotofacil\\tempo.txt");
		//
		// str2.append("Mais Frequentes\n");
		// str2.append(MapUtil.getAllKeyValues(mapResultado, "\n"));
		// str2.append("\n\n");
		//
		// ArquivoUtil.save(str2.toString(),
		// "C:\\loterias\\gerador-apostas\\lotofacil\\frequencia.txt");

	}

}
