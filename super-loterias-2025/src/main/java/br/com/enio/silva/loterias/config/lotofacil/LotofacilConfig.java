package br.com.enio.silva.loterias.config.lotofacil;

import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.cliente.mix.LimpaListaSimples;
import br.com.enio.silva.loterias.config.ConfigJogosComum;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.pontuador.PontuadorLotofacil15Simples;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public abstract class LotofacilConfig extends ConfigJogosComum {

	public enum Esquema {
		PADRAO, E19, E21;
	}

	public static final String CAMINHO_DOWNLOAD = "C:\\loterias\\gerador-apostas\\lotofacil\\zip\\";

	public static final String CAMINHO_BASE = "C:\\loterias\\gerador-apostas\\lotofacil\\";

	private static List<List<Integer>> todosOsResultados = new ArrayList<>();

	public static String getPathListaMax() {
		return "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LISTA_MAX.txt";
	}

	private int nrosApostados = 15;

	private int maxAnterior = 0;

	private int maxNum = 25;

	private Pontuador pontuador = new PontuadorLotofacil15Simples();

	private String prefixo;

	private String sufixo = "_";

	private final String currTime = String.valueOf(System.currentTimeMillis());

	private List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

	private boolean pontuacaoExtra = false;

	public LotofacilConfig() {
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
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public String getCaminhoDownload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaminhoJogoAtual() {
		return "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJA.txt";
	}

	@Override
	public String getCaminhoJogoCorrente() {
		return "C:\\loterias\\gerador-apostas\\lotofacil\\config\\LJC.txt";
	}

	public String getCaminhoPastaAtual() {
		return "C:\\loterias\\gerador-apostas\\lotofacil\\pasta_jogos\\";
	}

	@Override
	public String getCaminhoResultados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaminhoTodosResultados() {
		return CAMINHO_DOWNLOAD + "RESULTADO_LF.txt";
	}

	public String getDefaultName() {
		String nome = String.format(getPrefixo() + getNrosApostados() + "_" + currTime + "%s" + EXT,
				getSufixo());
		return nome;
	}

	public String getDefaultNameParams() {
		String nome = String.format(
				getPrefixo() + getNrosApostados() + "_" + currTime + "_params" + "%s" + EXT,
				getSufixo());
		return nome;
	}

	@Override
	public int getDefaultSize() {
		return 15;
	}

	public String getFullPath() {
		return CAMINHO_BASE + "config\\jogos\\" + getDefaultName();
	}

	public String getFullPathParams() {
		return CAMINHO_BASE + "config\\jogos\\params" + getDefaultNameParams();
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

	public String getSufixo() {
		return sufixo;
	}

	@Override
	public List<List<Integer>> getTodosResultados() {
		try {
			String pathResultados = "C:\\loterias\\python-utils\\full-results\\data\\lotofacil.txt";
			todosOsResultados = ArquivoUtil.obterLinhasComoListas(pathResultados);
			LimpaListaSimples.saveListSimples(pathResultados);
			return todosOsResultados;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public boolean isPontuacaoExtra() {
		return pontuacaoExtra;
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

	public void setPontuacaoExtra(boolean pontuacaoExtra) {
		this.pontuacaoExtra = pontuacaoExtra;
	}

	public void setPontuador(Pontuador pontuador) {
		this.pontuador = pontuador;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public void setSufixo(String sufixo) {
		this.sufixo = sufixo;
	}

	@Override
	public String toString() {
		return "";
	}
}
