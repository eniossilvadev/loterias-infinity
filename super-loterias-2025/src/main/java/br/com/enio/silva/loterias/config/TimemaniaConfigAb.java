package br.com.enio.silva.loterias.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.config.pontuador.PontuadorTimemania;
import br.com.enio.silva.loterias.lotomania.GerarListaTimemania;
import br.com.enio.silva.loterias.lotomania.Pontuador;

public abstract class TimemaniaConfigAb extends ConfigJogosComum {

	public static final String CAMINHO_BASE = "E:\\loterias\\timemania\\";

	public static final String CAMINHO_DOWNLOAD = CAMINHO_BASE + "zip\\";

	private int nrosApostados = 10;

	private int maxAnterior = 0;

	private int maxNum = 80;

	private List<Integer> excluir = new ArrayList<Integer>();

	private Pontuador pontuador = new PontuadorTimemania();

	private String prefixo;

	private final String currTime = String.valueOf(System.currentTimeMillis());

	private Integer[] pre = new Integer[] {};

	private List<List<Integer>> preJogos = new ArrayList<List<Integer>>();

	public TimemaniaConfigAb() {

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
		return "E:\\loterias\\timemania\\config\\SNQ.txt";
	}

	@Override
	public String getCaminhoDownload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCaminhoJogoAtual() {
		return "E:\\loterias\\timemania\\config\\meu_jogos.txt";
	}

	@Override
	public String getCaminhoJogoCorrente() {
		return "E:\\loterias\\timemania\\config\\novos_jogos.txt";
	}

	@Override
	public String getCaminhoResultados() {
		return CAMINHO_DOWNLOAD + "RESULTADO_TM.txt";
	}

	@Override
	public String getCaminhoTodosResultados() {
		return CAMINHO_DOWNLOAD + "RESULTADO_TM.txt";
	}

	public String getDefaultName() {
		return getPrefixo() + getNrosApostados() + "_" + currTime;
	}

	@Override
	public int getDefaultSize() {
		return 10;
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
		try {
			List<List<Integer>> resultados = GerarListaTimemania.getInstance()
					.gerarArquivoResultado(getCaminhoTodosResultados());
			List<List<Integer>> retorno = new ArrayList<>();
			resultados.forEach(r -> {
				if (r.size() > 10) {
					retorno.add(r);
				}
			});
			return retorno;
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
