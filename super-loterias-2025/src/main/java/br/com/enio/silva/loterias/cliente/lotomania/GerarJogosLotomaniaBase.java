package br.com.enio.silva.loterias.cliente.lotomania;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import br.com.enio.silva.loterias.cliente.lotofacil.esquemas.EsquemaLotomania;
import br.com.enio.silva.loterias.config.LotomaniaConfigAb;
import br.com.enio.silva.loterias.exception.InvalidLenghtException;
import br.com.enio.silva.loterias.gerador.GerarJogosBase;
import br.com.enio.silva.loterias.lotomania.JogoAb;
import br.com.enio.silva.loterias.lotomania.JogoLotomania;
import br.com.enio.silva.loterias.util.ListaUtils;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public abstract class GerarJogosLotomaniaBase extends GerarJogosBase {

	public static String ESQUEMANETSORTE100N30F21A = "ESQUEMANETSORTE100N30F21A";

	public static String ESQUEMA80N10A = "GETESQUEMA80N10A";

	public static String ESQUEMA65N14A = "GETESQUEMA65N15A";

	private static LotomaniaConfigAb config;

	public static List<JogoLotomania> filtroSkip(List<JogoLotomania> jogos, int sk) {

		System.out.println("Skipping... " + sk + "!");
		List<JogoLotomania> retorno = new ArrayList<JogoLotomania>();
		int skip = sk;
		for (JogoLotomania lista : jogos) {
			if (skip++ % sk == 0) {
				retorno.add(lista);
			}
		}
		return retorno;
	}

	public static LotomaniaConfigAb getConfig() {
		return config;
	}

	protected static List<List<Integer>> getEspelhos(List<List<Integer>> jogos) {
		List<List<Integer>> retorno = new ArrayList<>();

		jogos.forEach(j -> {
			List<Integer> full = ListaUtils.iterateStream(1, 1, 100);
			full.removeAll(j);
			if (full.size() != 50) {
				try {
					String msg = "Tamanho do espelho inv�lido.";
					String err = String.format("%s\n%s - %s\n%s - %s", msg, full, full.size(), j,
					        j.size());
					System.err.println(err);
					String base = getConfig().getBasePath().toString();
					String fileName = "err" + System.currentTimeMillis() + ".txt";
					Path path = Paths.get(base, "err", fileName);
					ArquivoUtil.save(err, path.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			retorno.add(j);
			retorno.add(full);

		});

		return new ArrayList<>(new HashSet<>(retorno));
	}

	protected static List<JogoAb> getEspelhosLotomania(List<List<Integer>> jogos) {
		List<JogoAb> retorno = new ArrayList<>();

		jogos.forEach(j -> {
			List<Integer> full = ListaUtils.iterateStream(1, 1, 100);
			full.removeAll(j);
			if (full.size() != 50) {
				String msg = "Tamanho do espelho inv�lido.";
				String err = String.format("% [%s, %s]", msg, full, j);
				System.err.println(err);
			}

			retorno.add(new JogoLotomania(j.toArray(new Integer[j.size()])));
			retorno.add(new JogoLotomania(full.toArray(new Integer[j.size()])));

		});

		return new ArrayList<>(new HashSet<>(retorno));
	}

	public static List<List<Integer>> getEsquema80(List<Integer> jogo,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso)
	        throws InvalidLenghtException {
		return EsquemaLotomania.esquema80n0f10a(jogo, mapResultado, mapAtraso);
	}

	public static List<List<Integer>> getEsquemas(List<List<Integer>> jogos,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso)
	        throws InvalidLenghtException {
		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		for (List<Integer> jogo : jogos) {

			if (jogo.size() == 30) {
				retorno.addAll(EsquemaLotomania.esquemaNetsorte100n30f21a(jogo, mapAtraso));

			} else if (jogo.size() == 80) {
				retorno.addAll(EsquemaLotomania.esquema80n0f10a(jogo, mapResultado, mapAtraso));

			} else if (jogo.size() == 65) {
				retorno.addAll(EsquemaLotomania.esquema65n0f14a(jogo));

			}
		}

		return retorno;
	}

	public static List<List<Integer>> getEsquemas(List<List<Integer>> jogos,
	        Map<Integer, Integer> mapResultado, Map<Integer, Integer> mapAtraso, String esquema)
	        throws InvalidLenghtException {
		List<List<Integer>> retorno = new ArrayList<List<Integer>>();

		for (List<Integer> jogo : jogos) {
			if (ESQUEMANETSORTE100N30F21A.equals(esquema)) {
				retorno.addAll(EsquemaLotomania.esquemaNetsorte100n30f21a(jogo, mapAtraso));

			} else if (ESQUEMA80N10A.equals(esquema)) {
				retorno.addAll(EsquemaLotomania.esquema80n0f10a(jogo, mapResultado, mapAtraso));

			} else if (ESQUEMA65N14A.equals(esquema)) {
				retorno.addAll(EsquemaLotomania.esquema65n0f14a(jogo));

			}
		}

		return retorno;
	}

	protected static List<JogoAb> getJogosLotomania(List<List<Integer>> jogos) {
		List<JogoAb> retorno = new ArrayList<>();

		jogos.forEach(j -> {
			retorno.add(new JogoLotomania(j.toArray(new Integer[j.size()])));
		});

		return new ArrayList<>(new HashSet<>(retorno));
	}

	public static void setConfig(LotomaniaConfigAb conf) {
		config = conf;
	}

}
