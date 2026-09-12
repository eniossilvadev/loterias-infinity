package br.com.enio.silva.loterias.commons;

import java.util.List;

public interface SaveIf {

	public List<List<Integer>> gc(List<List<Integer>> theList);

	public abstract String getCaminhoAtuais();

	public abstract String getCaminhoBackup();

	public abstract String getCaminhoBackup(String sufixo);

	public abstract String getCaminhoCorrentes();

	public abstract String getCaminhoIndividual(String comp);

	public abstract String getCaminhoSomenteNovos();

	public abstract String getCaminhoStatsAtuais();

	public abstract String getCaminhoStatsCorrentes();

	public String getConfig();

	public abstract String getCurr();

	public Integer getMaxNumber();

	public void safetSave(List<List<Integer>> lista, String path);

	public void safetSaveString(String content, String path);

	public void saveAtuais(final List<List<Integer>> lista);

	public void saveCaminhoAtuais(final List<List<Integer>> lista);

	public void saveCaminhoBackup(final List<List<Integer>> lista);

	public void saveCaminhoCorrentes(final List<List<Integer>> lista);

	public void saveCaminhoSomenteNovos(final List<List<Integer>> lista);

	public void saveCaminhoStatsAtuais(final List<List<Integer>> lista);

	public void saveCaminhoStatsCorrentes(final List<List<Integer>> lista);

	public void saveCorrentes(final List<List<Integer>> lista);

	public void saveIndividual(final List<List<Integer>> lista, String comp);

	public void saveNovos(final List<List<Integer>> lista);
}
