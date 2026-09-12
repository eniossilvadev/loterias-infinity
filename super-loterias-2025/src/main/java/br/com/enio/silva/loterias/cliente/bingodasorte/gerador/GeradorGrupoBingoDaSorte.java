package br.com.enio.silva.loterias.cliente.bingodasorte.gerador;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.enio.silva.loterias.cliente.quina.GerarJogosQuinaLoopExcluirIncluirGenerico10;
import br.com.enio.silva.loterias.lotomania.ContaNumerosResultados;
import br.com.enio.silva.loterias.util.MapUtil;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;

public class GeradorGrupoBingoDaSorte {

	private final List<List<Integer>> resultados;

	private final List<List<Integer>> jogosDoPovo;

	private final String base;

	private final LocalDateTime date = LocalDateTime.now();

	private final int size = 10;

	private boolean fixos = false;

	private final ContaNumerosResultados contador = ContaNumerosResultados.getInstance();

	// Números que mais sairam -> Práximos elimam anteriores, se pssivel
	private final List<List<Integer>> numerosQueMaisSairamNosSorteios = new ArrayList<>();

	private List<Integer> numerosQueMaisSairamNosSorteiosRodada = new ArrayList<>();

	// Números que mais foram jogados -> Práximos eliminam anteriores
	private final List<List<Integer>> numerosMaisJogados = new ArrayList<>();

	private List<Integer> numerosMaisJogadosRodada = new ArrayList<>();

	// Números que menos foram jogados -> Práximos eliminam anteriores
	private final List<List<Integer>> numerosMenosJogados = new ArrayList<>();

	private List<Integer> numerosMenosJogadosRodada = new ArrayList<>();

	// Meu Jogo Simples
	private final List<List<Integer>> meuJogoSimples = new ArrayList<>();

	private List<Integer> meuJogoSimplesRodada = new ArrayList<>();

	// Meu Jogo Excluindo anteriores -> Considera apenas a Rodada
	private final List<List<Integer>> meuJogoSimplesExcluidosAnteriores = new ArrayList<>();

	private List<Integer> meuJogoSimplesExcluidosAnterioresDaRodada = new ArrayList<>();

	// Mix Anterior
	private final List<List<Integer>> mixAnteriores = new ArrayList<>();

	private List<Integer> mixRodada = new ArrayList<>();

	public GeradorGrupoBingoDaSorte(List<List<Integer>> resultados, List<List<Integer>> jogosDoPovo,
	        String base) {
		super();
		this.resultados = resultados;
		this.jogosDoPovo = jogosDoPovo;
		this.base = base;

		this.init();
	}

	public void addMeuJogoSimples(List<Integer> meuJogoSimples) {
		this.meuJogoSimples.add(meuJogoSimples);
	}

	public void addMeuJogoSimplesExcluidosAnteriores(
	        List<Integer> meuJogoSimplesExcluidosAnteriores) {
		this.meuJogoSimplesExcluidosAnteriores.add(meuJogoSimplesExcluidosAnteriores);
	}

	public void addMixAnteriores(List<Integer> mixAnteriores) {
		this.mixAnteriores.add(mixAnteriores);
	}

	public void addNumerosMaisJogados(List<Integer> numerosMaisJogados) {
		this.numerosMaisJogados.add(numerosMaisJogados);
	}

	public void addNumerosMenosJogados(List<Integer> numerosMenosJogados) {
		this.numerosMenosJogados.add(numerosMenosJogados);
	}

	public void addNumerosQueMaisSairamNosSorteios(List<Integer> numerosQueMaisSairamNosSorteios) {
		this.numerosQueMaisSairamNosSorteios.add(numerosQueMaisSairamNosSorteios);
	}

	public void cleanAll() {
		this.cleanLists();
		this.cleanRodada();
	}

	public void cleanLists() {
		numerosQueMaisSairamNosSorteios.clear();
		numerosMaisJogados.clear();
		numerosMenosJogados.clear();
		meuJogoSimples.clear();
		meuJogoSimplesExcluidosAnteriores.clear();
		mixAnteriores.clear();
	}

	public void cleanRodada() {
		numerosQueMaisSairamNosSorteiosRodada.clear();
		numerosMaisJogadosRodada.clear();
		numerosMenosJogadosRodada.clear();
		meuJogoSimplesRodada.clear();
		meuJogoSimplesExcluidosAnterioresDaRodada.clear();
		mixRodada.clear();
	}

	public void gerar() {

		this.cleanRodada();

		// Números que mais sairam -> Práximos elimam anteriores, se pssivel
		this.gerarNumerosQueMaisSairam();

		// Números que mais foram jogados -> Práximos eliminam anteriores
		this.gerarNumerosMaisJogados();

		// Números que menos foram jogados -> Práximos eliminam anteriores
		this.gerarNumerosMenosJogados();

		if (!this.isFixos()) {
			// Meu Jogo Simples
			this.gerarMeuJogo();

			// Meu Jogo Excluindo anteriores -> Considera apenas a Rodada
			this.gerarMeuJogoComExclusao();

			// Mix Anterior
			this.gerarMix();
		}
	}

	private void gerarMeuJogo() {
		List<List<Integer>> listaDeJogosCorrentes = this.getAll();
		final List<Integer> lista = GerarJogosQuinaLoopExcluirIncluirGenerico10
		        .getNumerosDaSorte(resultados, listaDeJogosCorrentes, Collections.emptyList());
		this.setMeuJogoSimplesRodada(lista);
	}

	private void gerarMeuJogoComExclusao() {
		List<List<Integer>> listaDeJogosCorrentes = this.getAll();
		List<Integer> excluirLista = this.toPlainList(this.getAllRodada());
		final List<Integer> lista = GerarJogosQuinaLoopExcluirIncluirGenerico10
		        .getNumerosDaSorte(resultados, listaDeJogosCorrentes, excluirLista);
		this.setMeuJogoSimplesExcluidosAnterioresDaRodada(lista);
	}

	private void gerarMix() {
		List<Integer> theList = this.toPlainList(this.getAllRodada());
		Collections.shuffle(theList);
		List<Integer> retorno = theList.subList(0, size);
		this.setMixRodada(retorno);
	}

	private void gerarNumerosMaisJogados() {

		Map<Integer, Integer> mapMaisJogados = contador.getMapContaResultados(jogosDoPovo);

		Map<Integer, Integer> mapTmp = MapUtil.sortByValueDescShuffe(mapMaisJogados);

		Map<Integer, Integer> map = new HashMap<>(mapTmp);

		if (map.size() > 20) {
			map = MapUtil.removeFromList(map, this.toPlainList(this.getNumerosMaisJogados()));
		}

		map = MapUtil.sortByValueDescShuffe(map);

		List<Integer> lista = MapUtil.getElements(map, size);

		this.setNumerosMaisJogadosRodada(lista);
	}

	private void gerarNumerosMenosJogados() {
		Map<Integer, Integer> mapMenosJogados = contador.getMapContaResultados(jogosDoPovo);

		Map<Integer, Integer> mapTmp = MapUtil.sortByValueShuffe(mapMenosJogados);

		Map<Integer, Integer> map = new HashMap<>(mapTmp);

		if (map.size() > 20) {
			map = MapUtil.removeFromList(map, this.toPlainList(this.getNumerosMenosJogados()));
		}

		map = MapUtil.sortByValueShuffe(map);

		List<Integer> lista = MapUtil.getElements(map, size);

		this.setNumerosMenosJogadosRodada(lista);
	}

	private void gerarNumerosQueMaisSairam() {
		Map<Integer, Integer> mapResultado = contador.getMapContaResultados(resultados);

		Map<Integer, Integer> mapTmp = MapUtil.sortByValueDescShuffe(mapResultado);

		Map<Integer, Integer> map = new HashMap<>(mapTmp);

		if (map.size() > 20) {
			map = MapUtil.removeFromList(map,
			        this.toPlainList(this.getNumerosQueMaisSairamNosSorteios()));
		}

		map = MapUtil.sortByValueDescShuffe(map);

		List<Integer> lista = MapUtil.getElements(map, size);

		this.setNumerosQueMaisSairamNosSorteiosRodada(lista);
	}

	public List<List<Integer>> getAll() {
		List<List<Integer>> all = new ArrayList<>();
		// all.addAll(getAllRodada());

		all.addAll(numerosQueMaisSairamNosSorteios);
		all.addAll(numerosMaisJogados);
		all.addAll(numerosMenosJogados);
		all.addAll(meuJogoSimples);
		all.addAll(meuJogoSimplesExcluidosAnteriores);
		all.addAll(mixAnteriores);

		return all;
	}

	public List<List<Integer>> getAllRodada() {
		List<List<Integer>> all = new ArrayList<>();

		all.add(numerosQueMaisSairamNosSorteiosRodada);
		all.add(numerosMaisJogadosRodada);
		all.add(numerosMenosJogadosRodada);
		all.add(meuJogoSimplesRodada);
		all.add(meuJogoSimplesExcluidosAnterioresDaRodada);
		all.add(mixRodada);

		return all;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public List<List<Integer>> getJogosDoPovo() {
		return jogosDoPovo;
	}

	public List<List<Integer>> getMeuJogoSimples() {
		return meuJogoSimples;
	}

	public List<List<Integer>> getMeuJogoSimplesExcluidosAnteriores() {
		return meuJogoSimplesExcluidosAnteriores;
	}

	public List<Integer> getMeuJogoSimplesExcluidosAnterioresDaRodada() {
		return meuJogoSimplesExcluidosAnterioresDaRodada;
	}

	public List<Integer> getMeuJogoSimplesRodada() {
		return meuJogoSimplesRodada;
	}

	public List<List<Integer>> getMixAnteriores() {
		return mixAnteriores;
	}

	public List<Integer> getMixRodada() {
		return mixRodada;
	}

	public List<List<Integer>> getNumerosMaisJogados() {
		return numerosMaisJogados;
	}

	public List<Integer> getNumerosMaisJogadosRodada() {
		return numerosMaisJogadosRodada;
	}

	public List<List<Integer>> getNumerosMenosJogados() {
		return numerosMenosJogados;
	}

	public List<Integer> getNumerosMenosJogadosRodada() {
		return numerosMenosJogadosRodada;
	}

	public List<List<Integer>> getNumerosQueMaisSairamNosSorteios() {
		return numerosQueMaisSairamNosSorteios;
	}

	public List<Integer> getNumerosQueMaisSairamNosSorteiosRodada() {
		return numerosQueMaisSairamNosSorteiosRodada;
	}

	private String getPath(GeradorBingoConstantes gbc) {
		return this.base + gbc.getNome() + ".txt";
	}

	public List<List<Integer>> getResultados() {
		return resultados;
	}

	private void init() {
		System.out.println("init() - Início.");

		List<List<Integer>> listaNumerosQueMaisSairamNosSorteios = ArquivoUtil
		        .obterLinhasComoListas(
		                this.getPath(GeradorBingoConstantes.NUMEROS_QUE_MAIS_SAIRAM_NOS_SORTEIOS));
		numerosQueMaisSairamNosSorteios.addAll(listaNumerosQueMaisSairamNosSorteios);

		List<List<Integer>> listaNumerosMaisJogados = ArquivoUtil
		        .obterLinhasComoListas(this.getPath(GeradorBingoConstantes.NUMEROS_MAIS_JOGADOS));
		numerosMaisJogados.addAll(listaNumerosMaisJogados);

		List<List<Integer>> listaNumerosMenosJogados = ArquivoUtil
		        .obterLinhasComoListas(this.getPath(GeradorBingoConstantes.NUMEROS_MENOS_JOGADOS));
		numerosMenosJogados.addAll(listaNumerosMenosJogados);

		List<List<Integer>> listaMeuJogoSimples = ArquivoUtil
		        .obterLinhasComoListas(this.getPath(GeradorBingoConstantes.MEU_JOGO_SIMPLES));
		meuJogoSimples.addAll(listaMeuJogoSimples);

		List<List<Integer>> listaMeuJogoSimplesExcluidosAnteriores = ArquivoUtil
		        .obterLinhasComoListas(
		                this.getPath(GeradorBingoConstantes.MEU_JOGO_SIMPLES_EXCLUIDOS_ANTERIORES));
		meuJogoSimplesExcluidosAnteriores.addAll(listaMeuJogoSimplesExcluidosAnteriores);

		List<List<Integer>> listaMixAnteriores = ArquivoUtil
		        .obterLinhasComoListas(this.getPath(GeradorBingoConstantes.MIX_ANTERIORES));
		mixAnteriores.addAll(listaMixAnteriores);

		System.out.println("init() - Fim.");
	}

	public boolean isFixos() {
		return fixos;
	}

	public void save(String path) throws IOException {
		ArquivoUtil.saveLists(getAll(), path, "\t", 2);
	}

	public void setFixos(boolean fixos) {
		this.fixos = fixos;
	}

	private void setMeuJogoSimplesExcluidosAnterioresDaRodada(
	        List<Integer> meuJogoSimplesExcluidosAnterioresDaRodada) {
		Collections.sort(meuJogoSimplesExcluidosAnterioresDaRodada);
		this.meuJogoSimplesExcluidosAnterioresDaRodada = meuJogoSimplesExcluidosAnterioresDaRodada;
		this.meuJogoSimplesExcluidosAnteriores
		        .add(new ArrayList<>(meuJogoSimplesExcluidosAnterioresDaRodada));
	}

	private void setMeuJogoSimplesRodada(List<Integer> meuJogoSimplesRodada) {
		Collections.sort(meuJogoSimplesRodada);
		this.meuJogoSimplesRodada = meuJogoSimplesRodada;
		this.meuJogoSimples.add(new ArrayList<>(meuJogoSimplesRodada));
	}

	private void setMixRodada(List<Integer> mixRodada) {
		Collections.sort(mixRodada);
		this.mixRodada = mixRodada;
		this.mixAnteriores.add(new ArrayList<>(mixRodada));
	}

	private void setNumerosMaisJogadosRodada(List<Integer> numerosMaisJogadosRodada) {
		Collections.sort(numerosMaisJogadosRodada);
		this.numerosMaisJogadosRodada = numerosMaisJogadosRodada;
		this.numerosMaisJogados.add(new ArrayList<>(numerosMaisJogadosRodada));
	}

	private void setNumerosMenosJogadosRodada(List<Integer> numerosMenosJogadosRodada) {
		Collections.sort(numerosMenosJogadosRodada);
		this.numerosMenosJogadosRodada = numerosMenosJogadosRodada;
		this.numerosMenosJogados.add(new ArrayList<>(numerosMenosJogadosRodada));
	}

	private void setNumerosQueMaisSairamNosSorteiosRodada(
	        final List<Integer> numerosQueMaisSairamNosSorteiosRodada) {
		Collections.sort(numerosQueMaisSairamNosSorteiosRodada);
		this.numerosQueMaisSairamNosSorteiosRodada = numerosQueMaisSairamNosSorteiosRodada;
		this.numerosQueMaisSairamNosSorteios
		        .add(new ArrayList<>(numerosQueMaisSairamNosSorteiosRodada));
	}

	private List<Integer> toPlainList(List<List<Integer>> lista) {
		if (lista == null || lista.isEmpty()) {
			return Collections.emptyList();
		}
		final Set<Integer> retorno = new HashSet<>();
		lista.forEach(el -> retorno.addAll(el));
		return new ArrayList<>(retorno);
	}

}
