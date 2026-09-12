package br.com.enio.silva.loterias.gerador.enumeration;

public enum CaminhoResultados {

	MEGA_SENA("Mega-Sena", "MS", "C:\\loterias\\python-utils\\full-results\\data\\megasena.txt",
			"C:\\loterias\\gerador-apostas\\mega_sena\\"),
	LOTOMANIA("Lotomania", "LM",
			"C:\\loterias\\python-utils\\full-results\\data\\lotomania.txt"),
	DUPLA_SENA("Dupla-Sena", "DS",
			"C:\\loterias\\python-utils\\full-results\\dupla_sena_01.txt",
			"E:\\loterias\\dupla_sena\\"),
	DUPLA_SENA2("Dupla-Sena", "DS",
			"C:\\loterias\\python-utils\\full-results\\dupla_sena_resultado_02.txt",
			"E:\\loterias\\dupla_sena\\"),
	QUINA("Quina", "QN",
			"C:\\loterias\\python-utils\\full-results\\data\\quina.txt",
			"C:\\loterias\\gerador-apostas\\quina\\"),

	LOTOFACIL("Lotofacil", "LF",
			"C:\\loterias\\python-utils\\full-results\\data\\lotofacil.txt",
			"C:\\loterias\\gerador-apostas\\lotofacil\\"),

	TIMEMANIA("Timemania", "TM", "E:\\loterias\\timemania\\zip\\RESULTADO_TM.txt",
			"E:\\loterias\\timemania\\");

	private final String nome;

	private final String sigla;

	private final String path;

	private String basePath;

	private CaminhoResultados(String nome, String sigla, String path) {
		this.nome = nome;
		this.sigla = sigla;
		this.path = path;
	}

	private CaminhoResultados(String nome, String sigla, String path, String base) {
		this.nome = nome;
		this.sigla = sigla;
		this.path = path;
		this.basePath = base;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getNome() {
		return nome;
	}

	public String getPath() {
		return path;
	}

	public String getSigla() {
		return sigla;
	}
}
