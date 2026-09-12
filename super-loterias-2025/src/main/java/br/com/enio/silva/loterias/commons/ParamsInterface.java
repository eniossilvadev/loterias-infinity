package br.com.enio.silva.loterias.commons;

import org.apache.commons.lang3.StringUtils;

import br.com.enio.silva.loterias.util.DateUtils;

public interface ParamsInterface {

	public class DuplaSena implements PadraoInterface {

		@Override
		public String getBase() {
			return "C:\\loterias\\dupla_sena\\";
		}

		@Override
		public String getComplementoPadrao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCurrent() {
			return "C:\\loterias\\gerador-apostas\\dupla_sena\\";
		}

		@Override
		public Integer getMaxNumber() {
			return Integer.valueOf(50);
		}

		@Override
		public String getNomeNovo() {
			return "SNDS";
		}
	}

	public class Lotofacil implements PadraoInterface {

		@Override
		public String getBase() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getComplementoPadrao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCurrent() {
			return "C:\\loterias\\gerador-apostas\\lotofacil\\config\\";
		}

		@Override
		public Integer getMaxNumber() {
			return 25;
		}

		@Override
		public String getNomeNovo() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public class MegaSena implements PadraoInterface {

		@Override
		public String getBase() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getComplementoPadrao() {
			return null;
		}

		@Override
		public String getCurrent() {
			return "C:\\loterias\\gerador-apostas\\mega_sena\\";
		}

		@Override
		public Integer getMaxNumber() {
			return Integer.valueOf(60);
		}

		@Override
		public String getNomeNovo() {
			return null;
		}
	}

	public class MegaSenaEspecial extends MegaSena {

		@Override
		public String getCurrent() {
			return "C:\\loterias\\gerador-apostas\\mega_sena_especial\\";
		}
	}

	public interface PadraoInterface extends ParamsInterface {

		public int sequencial = 0;

		public String currDate = DateUtils.getCurrentDefaultDate();

		public String currDateTime = DateUtils.getCurrentDefaultDateTime();

		@Override
		default String getCaminhoAtuais() {
			return getBase() + "config\\tj.txt";
		}

		@Override
		default String getCaminhoBackup() {
			// String sq = String.valueOf(System.currentTimeMillis());
			return getBase() + "config\\bkp\\" + currDate + "\\" + currDateTime + ".txt";
		}

		@Override
		default String getCaminhoBackup(String sufixo) {
			// String sq = String.valueOf(System.currentTimeMillis());
			return getBase() + "config\\bkp\\" + currDate + "\\" + currDateTime + sufixo + ".txt";
		}

		@Override
		default String getCaminhoCorrentes() {
			return getBase() + "config\\nj.txt";
		}

		@Override
		default String getCaminhoIndividual() {
			return getCaminhoIndividual(StringUtils.EMPTY);
		}

		@Override
		default String getCaminhoIndividual(final String comp) {
			return getBase() + "config\\ind\\" + getTime() + getComplemento(comp) + ".txt";
		}

		@Override
		default String getCaminhoSomenteNovos() {
			return getBase() + "config\\" + getNomeNovo() + ".txt";
		}

		@Override
		default String getCaminhoStatsAtuais() {
			return getBase() + "atuais.txt";
		}

		@Override
		default String getCaminhoStatsCorrentes() {
			return getBase() + "correntes.txt";
		}

		default String getComplemento(String comp) {
			String cp = getComplementoPadrao();
			return StringUtils.isBlank(comp) ? "_" + cp : "_" + cp + "_" + comp + "_";
		}

		public String getComplementoPadrao();

		public String getNomeNovo();
	}

	public class Quina implements PadraoInterface {

		@Override
		public String getBase() {
			return "C:\\loterias\\quina\\";
		}

		@Override
		public String getComplementoPadrao() {
			return "qui";
		}

		@Override
		public String getCurrent() {
			return "C:\\loterias\\gerador-apostas\\quina\\";
		}

		@Override
		public Integer getMaxNumber() {
			return Integer.valueOf(80);
		}

		@Override
		public String getNomeNovo() {
			return "SNQ";
		}

	}

	public String getBase();

	public String getCaminhoAtuais();

	public String getCaminhoBackup();

	public String getCaminhoBackup(String sufixo);

	public String getCaminhoCorrentes();

	public String getCaminhoIndividual();

	public String getCaminhoIndividual(String comp);

	public String getCaminhoSomenteNovos();

	public String getCaminhoStatsAtuais();

	public String getCaminhoStatsCorrentes();

	default String getConfig() {
		return getCurrent() + "config\\";
	}

	public String getCurrent();

	public Integer getMaxNumber();

	default String getTime() {
		return String.valueOf(System.currentTimeMillis());
	}
}
