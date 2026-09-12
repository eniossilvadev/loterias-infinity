package br.com.enio.silva.loterias.cliente.bingodasorte.gerador;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GeradorQuinaSuperCampeaBingoDaSorte {

	// Usar Gerador e Grupo Quina da Sorte

	// Números que mais sa�ram -> Práximos elimam anteriores, se pss�vel

	// Números que mais foram jogados -> Práximos eliminam anteriores

	// Números que menos foram jogados -> Práximos eliminam anteriores

	// Meu Jogo Simples

	// Meu Jogo Exclu�ndo anteriores -> Considera apenas a Rodada

	// Mix Anterior

	private static String folder = "E:\\loterias\\bingo-da-sorte\\20221001\\";

	private static String output = folder + "\\jogos-do-povo.txt";

	private static String pathJogosDoPovo = folder + "\\202210_01_02.csv";

	private static final boolean SOMENTE_FIXOS = false;

	private static final int COMPLEMENTO = 2;

	private static List<List<Integer>> getjogosDoPovo() throws IOException {

		return ArquivoUtil.obterLinhasComoListasUnique(pathJogosDoPovo);
	}

	private static List<List<Integer>> getResultados() throws IOException {

		String pathResultado = CaminhoResultados.QUINA.getPath();
		return GerarListaQuina.getInstance().gerarArquivoResultado(pathResultado);
	}

	public static void main(String[] args) throws IOException {

		List<List<Integer>> all = new ArrayList<>();

		String date = LocalDateTime.now().toString().replaceAll("[^\\d]", "");
		String currTime = String.valueOf(System.currentTimeMillis());

		StringBuilder path = new StringBuilder();
		path.append(folder).append(date).append(currTime).append(".txt");

		// *** Usar Gerador e Grupo Quina da Sorte ***

		final List<List<Integer>> resultados = getResultados();

		final List<List<Integer>> numberosQuePovoJogou = getjogosDoPovo();

		GeradorGrupoBingoDaSorte gerador = new GeradorGrupoBingoDaSorte(resultados,
				numberosQuePovoJogou, folder);

		saveJogosDoPovo(numberosQuePovoJogou);

		gerador.setFixos(SOMENTE_FIXOS);

		for (int i = 0; i < 1; i++) {

			gerador.gerar();
			System.out.println(gerador.getNumerosQueMaisSairamNosSorteiosRodada());
			System.out.println(gerador.getNumerosQueMaisSairamNosSorteios());

			System.out.println(gerador.getNumerosMaisJogadosRodada());
			System.out.println(gerador.getNumerosMaisJogados());

			System.out.println(gerador.getNumerosMenosJogadosRodada());
			System.out.println(gerador.getNumerosMenosJogados());

			System.out.println(gerador.getMeuJogoSimplesRodada());
			System.out.println(gerador.getMeuJogoSimples());

			System.out.println(gerador.getMeuJogoSimplesExcluidosAnterioresDaRodada());
			System.out.println(gerador.getMeuJogoSimplesExcluidosAnteriores());

			System.out.println(gerador.getMixRodada());
			System.out.println(gerador.getMixAnteriores());

			gerador.save(path.toString());
		}

		System.out.println();

		gerador.getAll()
		.forEach(jogo -> System.out
				.println(jogo.stream().map(el -> StringUtils.leftPad(el.toString(), 2, "0"))
						.collect(Collectors.joining("\t"))));

		all.addAll(gerador.getAll());

		GeradorGrupoBingoDaSorteNovo g2 = new GeradorGrupoBingoDaSorteNovo(COMPLEMENTO, path.toString());
		g2.gerar();

		all.addAll(g2.getGerados().getAll());

		ArquivoUtil.saveLists(all, path.toString(), ", ", 2);

	}

	private static void saveJogosDoPovo(List<List<Integer>> jogos) throws IOException {
		ArquivoUtil.saveLists(jogos, output, "\t", 2);
	}

}
