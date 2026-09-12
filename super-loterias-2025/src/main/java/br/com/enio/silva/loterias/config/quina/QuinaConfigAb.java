package br.com.enio.silva.loterias.config.quina;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.config.ConfigJogosComum;
import br.com.enio.silva.loterias.config.pontuador.PontuadorQuina5_1;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaQuina;
import br.com.enio.silva.loterias.lotomania.Pontuador;

public abstract class QuinaConfigAb extends ConfigJogosComum {

	public static final String CAMINHO_BASE = "C:\\loterias\\gerador-apostas\\quina\\";

	public static final String CAMINHO_DOWNLOAD = CAMINHO_BASE + "zip\\";

	public static final int DEFAULT_SIZE = 5;

	private int nrosApostados = 5;

	private int maxAnterior = 0;

	private int maxNum = 80;

	private List<Integer> excluir = new ArrayList<Integer>();

	private Pontuador pontuador = new PontuadorQuina5_1();

	private String prefixo;

	private final String currTime = String.valueOf(System.currentTimeMillis());

	private Integer[] pre = new Integer[] {};

	private List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

	public QuinaConfigAb() {

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

	@Override
	public ArrayList<Integer> getArrayListPre() {
		return new ArrayList<>(Arrays.asList(getPre()));
	}

	public String getCaminhoDefaultFolder() {
		return CAMINHO_BASE;

	}

	@Override
	public String getCaminhoDefaultOutput() {
		return "C:\\loterias\\gerador-apostas\\quina\\config\\SNQ.txt";
	}

	@Override
	public String getCaminhoDownload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaminhoJogoAtual() {
		return "C:\\loterias\\gerador-apostas\\quina\\config\\todos_jogos.txt";
	}

	@Override
	public String getCaminhoJogoCorrente() {
		return "C:\\loterias\\gerador-apostas\\quina\\config\\novos_jogos.txt";
	}

	public String getCaminhoPastaAtual() {
		return "C:\\loterias\\gerador-apostas\\quina\\pasta_jogos\\";
	}

	@Override
	public String getCaminhoResultados() {
		return CaminhoResultados.QUINA.getPath();
	}

	@Override
	public String getCaminhoTodosResultados() {
		return getCaminhoResultados();
	}

	public String getDefaultName() {
		return getPrefixo() + getNrosApostados() + "_" + currTime;
	}

	@Override
	public int getDefaultSize() {
		return 5;
	}

	@Override
	public List<Integer> getExcluir() {
		List<Integer> exc = new ArrayList<>(excluir);
		if (excluir != null) {
			exc.removeAll(getArrayListPre());
			exc.sort((i1, i2) -> Integer.compare(i1, i2));
		}
		return exc;
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

	@Override
	public Integer[] getPre() {
		return pre;
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
		return GerarListaQuina.getInstance().gerarArquivoResultado();
	}

	@Override
	public List<List<Integer>> obterTodosResultados() {
		return getTodosResultados();
	}

	public void resetPreJogos() {
		configPreJogos();
	}

	@Override
	public void setExcluir(List<Integer> excluir) {
		Collections.sort(excluir);
		this.excluir = excluir;
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

	@Override
	public void setPre(Integer[] pre) {
		this.pre = pre;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}
}
