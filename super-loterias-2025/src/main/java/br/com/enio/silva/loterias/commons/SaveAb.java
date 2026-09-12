package br.com.enio.silva.loterias.commons;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.config.ListOfListComparator;
import br.com.enio.silva.loterias.util.CollectionsUtils;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public abstract class SaveAb implements SaveIf {

	private String sep = ",";

	private final ParamsInterface pi;

	public SaveAb(ParamsInterface pi) {
		this.pi = pi;
	}

	public SaveAb(ParamsInterface pi, String sep) {
		this.pi = pi;
		this.sep = sep;
	}

	@Override
	public List<List<Integer>> gc(List<List<Integer>> theList) {
		theList = CollectionsUtils.removeDuplicated(theList);
		theList.sort(new ListOfListComparator());
		return theList;
	}

	@Override
	public String getCaminhoAtuais() {
		return pi.getCaminhoAtuais();
	}

	@Override
	public String getCaminhoBackup() {
		return pi.getCaminhoBackup();
	}


	@Override
	public String getCaminhoBackup(String sufixo) {
		return pi.getCaminhoBackup(sufixo);
	}

	@Override
	public String getCaminhoCorrentes() {
		return pi.getCaminhoCorrentes();
	}

	@Override
	public String getCaminhoIndividual(String comp) {
		return pi.getCaminhoIndividual(comp);
	}

	@Override
	public String getCaminhoSomenteNovos() {
		return pi.getCaminhoSomenteNovos();
	}

	@Override
	public String getCaminhoStatsAtuais() {
		return pi.getCaminhoStatsAtuais();
	}

	@Override
	public String getCaminhoStatsCorrentes() {
		return pi.getCaminhoStatsCorrentes();
	}

	@Override
	public String getConfig() {
		return pi.getConfig();
	}

	@Override
	public String getCurr() {
		return pi.getCurrent();
	}

	private String getListaOrdenada(List<List<Integer>> lista) {
		List<Integer> listaOrdenadaListas = MapUtil.getListaOrdenadaListas(lista, 1, getMaxNumber());
		return listaOrdenadaListas.stream().map(el -> StringUtils.leftPad(el.toString(), 2, "0")).collect(Collectors.joining(sep));
	}

	@Override
	public Integer getMaxNumber() {
		return pi.getMaxNumber();
	}

	@Override
	public void safetSave(List<List<Integer>> lista, String path) {
		try {
			ArquivoUtil.saveLists(lista, path, sep, 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void safetSaveString(String content, String path) {
		try {
			ArquivoUtil.save(content, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param lista
	 */
	private void save(List<List<Integer>> lista, String path) {
		List<List<Integer>> save = gc(lista);
		safetSave(save, path);
	}

	@Override
	public void saveAtuais(final List<List<Integer>> lista) {
		saveCaminhoAtuais(lista);
		saveCaminhoStatsAtuais(lista);
	}

	@Override
	public void saveCaminhoAtuais(List<List<Integer>> lista) {
		save(lista, getCaminhoAtuais());
	}

	@Override
	public void saveCaminhoBackup(List<List<Integer>> lista) {
		save(lista, getCaminhoBackup());

	}

	@Override
	public void saveCaminhoCorrentes(List<List<Integer>> lista) {
		save(lista, getCaminhoCorrentes());
	}

	@Override
	public void saveCaminhoSomenteNovos(List<List<Integer>> lista) {
		save(lista, getCaminhoSomenteNovos());
	}

	@Override
	public void saveCaminhoStatsAtuais(List<List<Integer>> lista) {
		List<List<Integer>> save = gc(lista);
		String content = StatsUtils.getFrequencia(save) + "\n\n" + getListaOrdenada(lista);
		safetSaveString(content, getCaminhoStatsAtuais());
	}

	@Override
	public void saveCaminhoStatsCorrentes(List<List<Integer>> lista) {
		List<List<Integer>> save = gc(lista);
		String content = StatsUtils.getFrequencia(save) + "\n\n" + getListaOrdenada(lista);
		safetSaveString(content, getCaminhoStatsCorrentes());
	}

	@Override
	public void saveCorrentes(final List<List<Integer>> lista) {
		saveCaminhoCorrentes(lista);
		saveCaminhoStatsCorrentes(lista);
	}


	@Override
	public void saveIndividual(List<List<Integer>> lista, String comp) {
		save(lista, getCaminhoIndividual(comp));
	}


	@Override
	public void saveNovos(List<List<Integer>> lista) {
		saveCaminhoSomenteNovos(lista);
		saveCaminhoBackup(lista);
	}
}
