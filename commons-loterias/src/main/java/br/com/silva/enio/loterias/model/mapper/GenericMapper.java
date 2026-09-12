package br.com.silva.enio.loterias.model.mapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.silva.enio.loterias.commons.arquivo.ArquivoUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.HTMLTagConstantesIf;

public abstract class GenericMapper {

	public List<List<String>> getLists(String file) throws IOException {

		List<List<String>> listas = new ArrayList<List<String>>();

		File input = new File(file);
		Document doc = Jsoup.parse(input, "UTF8");
		Elements tables = doc.getElementsByTag(HTMLTagConstantesIf.TABLE);
		if (!tables.isEmpty()) {
			Element table = tables.get(0);

			Elements trs = table.getElementsByTag(HTMLTagConstantesIf.TR);
			for (Element tr : trs) {
				Elements tds = tr.getElementsByTag(HTMLTagConstantesIf.TD);
				List<String> elem = new ArrayList<String>();
				for (Element td : tds) {
					if (td.text() != null && StringUtils.isNotEmpty(td.text().trim())) {
						String el = td.text().replaceAll("[a-zA-Z]", "");
						if (StringUtils.isNotEmpty(el)) {
							elem.add(td.text());
						}
					}
				}
				if (elem != null && !elem.isEmpty()) {
					listas.add(elem);
					// System.out.println(elem);
				}
			}

		}
		return listas;
	}

	public abstract List<List<Integer>> getNumerosSorteados(String file) throws IOException;

	protected List<List<Integer>> getNumerosSorteados(String file, int inicio, int fim)
			throws IOException {
		List<List<Integer>> listas = new ArrayList<List<Integer>>();
		System.out.println(">>>>> " + inicio + "\t" + fim);
		List<List<String>> retorno = this.getLists(file);
		ArquivoUtil.save(retorno.toString(), "E:\\loterias\\retorno.txt");
		int fimFixo = fim;
		for (List<String> lista : retorno) {
			if (lista.size() > 15) {
				int count = 0;
				List<Integer> listaInteiro = new ArrayList<Integer>();
				// System.err.println(lista.size() + "\t" + lista);
				fim = fimFixo;
				for (String str : lista) {
					if (count >= inicio && count <= fim) {
						try {
							Integer i = new Integer(str);
							if (i.intValue() == 0) {
								i = 100;
							}
							listaInteiro.add(i);

						} catch (Exception e) {
							// e.printStackTrace();
							System.err.println(lista.size() + "\t" + lista);
							fim++;
						}
						Collections.sort(listaInteiro);
					}
					count++;
				}
				if (listaInteiro != null && listaInteiro.size() > 0) {
					listas.add(listaInteiro);
				}
			}
		}

		return listas;
	}

	protected List<List<Integer>> getNumerosSorteados(String file, int inicio1, int fim1,
													  int inicio2, int fim2) throws IOException {
		List<List<Integer>> listas = new ArrayList<List<Integer>>();

		List<List<String>> retorno = this.getLists(file);
		for (List<String> lista : retorno) {
			int count = 0;
			List<Integer> listaInteiro = new ArrayList<Integer>();
			for (String str : lista) {
				if ((count >= inicio1 && count <= fim1) || (count >= inicio2 && count <= fim2)) {
					Integer i = new Integer(str);
					if (i.intValue() == 0) {
						i = 100;
					}
					listaInteiro.add(i);
					Collections.sort(listaInteiro);
				}
				count++;
			}
			if (listaInteiro != null && listaInteiro.size() > 0) {
				listas.add(listaInteiro);
			}
		}

		return listas;
	}

	protected List<List<Integer>> getNumerosSorteadosFlex(String file, int inicioPrevisto, int size)
			throws IOException {
		List<List<Integer>> listas = new ArrayList<List<Integer>>();
		List<List<String>> retorno = this.getLists(file);
		for (List<String> lista : retorno) {
			int count = 0;
			List<Integer> listaInteiro = new ArrayList<Integer>();
			System.out.println(lista.size() + "\t" + lista);
			int inicio = 999;
			int fim = 999;
			for (String str : lista) {
				if (count >= inicioPrevisto && isNumber(str) && count <= fim) {
					if (inicio == 999) {
						inicio = count;
						fim = count + size - 1;
					}
					try {
						Integer i = new Integer(str);
						if (i.intValue() == 0) {
							i = 100;
						}
						listaInteiro.add(i);

					} catch (Exception e) {
						// e.printStackTrace();
						System.err.println(lista.size() + "\t" + lista);
						fim++;
					}
					Collections.sort(listaInteiro);
				}
				count++;
			}
			if (listaInteiro != null && listaInteiro.size() > 0) {
				listas.add(listaInteiro);
			}
		}

		return listas;
	}

	private boolean isNumber(String str) {
		try {
			boolean result = str.length() == 3;
			if (result) {
				Integer.valueOf(str);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
