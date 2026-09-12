package br.com.enio.silva.loterias.cliente.bingodasorte.gerador;

public enum GeradorBingoConstantes {

	NUMEROS_QUE_MAIS_SAIRAM_NOS_SORTEIOS("NUMEROS_QUE_MAIS_SAIRAM_NOS_SORTEIOS"),
	NUMEROS_MAIS_JOGADOS("NUMEROS_MAIS_JOGADOS"),
	NUMEROS_MENOS_JOGADOS("NUMEROS_MENOS_JOGADOS"),
	MEU_JOGO_SIMPLES("MEU_JOGO_SIMPLES"),
	MEU_JOGO_SIMPLES_EXCLUIDOS_ANTERIORES("MEU_JOGO_SIMPLES_EXCLUIDOS_ANTERIORES"),
	MIX_ANTERIORES("MIX_ANTERIORES");

	private String nome;

	private GeradorBingoConstantes(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
