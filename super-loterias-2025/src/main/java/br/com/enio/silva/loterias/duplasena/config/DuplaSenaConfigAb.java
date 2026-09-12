package br.com.enio.silva.loterias.duplasena.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.commons.SaveDuplaSena;
import br.com.enio.silva.loterias.config.ConfigJogosComum;
import br.com.enio.silva.loterias.duplasena.GerarListaDuplaSena;
import br.com.enio.silva.loterias.duplasena.PontuadorDuplaSenaP2;
import br.com.enio.silva.loterias.gerador.enumeration.CaminhoResultados;
import br.com.enio.silva.loterias.lotomania.Pontuador;

public abstract class DuplaSenaConfigAb extends ConfigJogosComum {

	public static final String CAMINHO_DOWNLOAD = "E:\\loterias\\dupla_sena\\zip\\";

	private final SaveDuplaSena save = new SaveDuplaSena();

	private int maxAnterior = 0;

	private int maxNum = 50;

	private Pontuador pontuador = new PontuadorDuplaSenaP2();

	private String prefixo;

	private final String currTime = String.valueOf(System.currentTimeMillis());

	private List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

	private int maxIgualAnteriores = 6;

	public DuplaSenaConfigAb() {
		super.setNrosApostados(6);
		setQttInicial(80000);
		setMaxAnterior(1);
		setPrefixo("DS");
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
		return save.getCaminhoAtuais();
	}

	@Override
	public String getCaminhoJogoCorrente() {
		return save.getCaminhoCorrentes();
	}

	@Override
	public String getCaminhoResultados() {
		return null;
	}

	@Override
	public String getCaminhoTodosResultados() {
		return CaminhoResultados.DUPLA_SENA.getPath();
	}

	public String getDefaultName() {
		return getPrefixo() + getNrosApostados() + "_" + currTime;
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
			return GerarListaDuplaSena.getInstance()
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

	public void setMaxIgualAnteriores(int maxIgualAnteriores) {
		this.maxIgualAnteriores = maxIgualAnteriores;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public void setPontuador(Pontuador pontuador) {
		this.pontuador = pontuador;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

}
