package br.com.enio.silva.loterias.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaLotomania;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.pontuador.lotomania.PontuadorLotomania80;

import br.com.silva.enio.loterias.controller.LotomaniaRN;

public abstract class LotomaniaConfigAb extends ConfigJogosComum {

	public static final String CAMINHO_DOWNLOAD = "C:\\loterias\\gerador-apostas\\lotomania\\zip\\";

	public static final String CAMINHO_BASE = "C:\\loterias\\gerador-apostas\\lotomania\\";

	private int nrosApostados = 50;

	private int maxAnterior = 0;

	private int maxNum = LotomaniaRN.MAIOR_NUMERO;

	private Pontuador pontuador = new PontuadorLotomania80();

	private String prefixo;

	private final String currTime = String.valueOf(System.currentTimeMillis());

	private List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

	public LotomaniaConfigAb() {

	}

	/**
	 *
	 */
	private void configPreJogos() {
		preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		preJogo = getArrayListPre();
		for (int i = 0; i < getQttInicial(); i++) {
			preJogos.add(preJogo);
		}
	}

	public String getBasePath() {
		return CAMINHO_BASE;
	}

	@Override
	public String getCaminhoDefaultOutput() {
		return "C:\\loterias\\gerador-apostas\\lotomania\\info\\MJLM.txt";

	}

	@Override
	public String getCaminhoDownload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaminhoJogoAtual() {
		return CAMINHO_BASE + "config\\LJA.txt";
	}

	@Override
	public String getCaminhoJogoCorrente() {
		return CAMINHO_BASE + "config\\LJC.txt";
	}

	@Override
	public String getCaminhoResultados() {
		return CaminhoResultados.LOTOMANIA.getPath();
	}

	@Override
	public String getCaminhoTodosResultados() {
		return CaminhoResultados.LOTOMANIA.getPath();
	}

	public String getDefaultName() {
		return getPrefixo() + getNrosApostados() + "_" + currTime;
	}

	@Override
	public int getDefaultSize() {
		return 50;
	}

	public String getFullPath() {
		return CAMINHO_BASE + "jogos\\" + getDefaultName();
	}

	public int getMaxAnterior() {
		return maxAnterior;
	}

	public int getMaxNum() {
		return maxNum;
	}

	@Override
	public int getNrosApostados() {
		return nrosApostados;
	}

	public Pontuador getPontuador() {
		return pontuador;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public List<List<Integer>> getPreJogos() {
		if (preJogos == null || preJogos.isEmpty()) {
			configPreJogos();
		}
		return preJogos;
	}

	@Override
	public List<List<Integer>> getTodosResultados() {
		try {
			return GerarListaLotomania.getInstance()
					.gerarArquivoResultado(getCaminhoTodosResultados());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	@Override
	public List<List<Integer>> obterTodosResultados() {

		return getTodosResultados();
	}

	public void resetPreJogos() {
		configPreJogos();
	}

	public void setMaxAnterior(int maxAnterior) {
		this.maxAnterior = maxAnterior;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	@Override
	public void setNrosApostados(int nrosApostados) {
		this.nrosApostados = nrosApostados;
	}

	public void setPontuador(Pontuador pontuador) {
		this.pontuador = pontuador;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}
}
