package br.com.enio.silva.loterias.config.megasena;

import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.config.ConfigJogosComum;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.GerarListaMega;
import br.com.enio.silva.loterias.lotomania.Pontuador;
import br.com.enio.silva.loterias.lotomania.PontuadorMega;

public abstract class MegaSenaConfigAb extends ConfigJogosComum {

	public static final String CAMINHO_DOWNLOAD = "C:\\loterias\\gerador-apostas\\mega_sena\\zip\\";

	private int nrosApostados = 6;

	private int maxAnterior = 0;

	private int maxNum = 60;

	private Pontuador pontuador = new PontuadorMega();

	private String prefixo;

	private final String currTime = String.valueOf(System.currentTimeMillis());

	private List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

	private int maxIgualAnteriores = 6;

	public MegaSenaConfigAb() {

	}

	/**
	 *
	 */
	private void configPreJogos() {
		preJogos = new ArrayList<List<Integer>>();
		List<Integer> preJogo = null;
		preJogo = getArrayListPre();
		System.out.println(">>>> " + preJogo);
		for (int i = 0; i < getQttInicial(); i++) {
			preJogos.add(preJogo);
		}
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
		return "C:\\loterias\\gerador-apostas\\mega_sena\\config\\todos_jogos.txt";
	}

	@Override
	public String getCaminhoJogoCorrente() {
		return "C:\\loterias\\gerador-apostas\\mega_sena\\config\\novos_jogos.txt";
	}

	public String getCaminhoPastaAtual() {
		return "C:\\loterias\\gerador-apostas\\mega_sena\\pasta_jogos\\";
	}

	@Override
	public String getCaminhoResultados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaminhoTodosResultados() {
		return CaminhoResultados.MEGA_SENA.getPath();
	}

	public String getDefaultName() {
		String pref = getPrefixo();
		pref = pref != null ? pref : "";
		return pref + getNrosApostados() + "_" + currTime;
	}

	@Override
	public int getDefaultSize() {
		return 6;
	}

	public int getMaxAnterior() {
		return maxAnterior;
	}

	public int getMaxIgualAnteriores() {
		return maxIgualAnteriores;
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
		configPreJogos();
		return preJogos;
	}

	@Override
	public List<List<Integer>> getTodosResultados() {
		try {
			return GerarListaMega.getInstance().gerarArquivoResultado();
		} catch (Exception e) {
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

	public void setMaxIgualAnteriores(int maxIgualAnteriores) {
		this.maxIgualAnteriores = maxIgualAnteriores;
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
