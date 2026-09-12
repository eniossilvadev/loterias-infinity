package br.com.enio.silva.loterias.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public abstract class LoteriaModelAb {

	protected String nome;

	protected List<Integer> premia;

	protected static String base = "src/com/enio/silva/money/json/";

	protected static String jsonFile;

	public LoteriaModelAb() {
		Gson gson = new Gson();

		try {

			BufferedReader br = new BufferedReader(new FileReader(getJsonPath()));

			// convert the json string back to object
			LoteriaModelAb loteria = gson.fromJson(br, this.getClass());
			this.setNome(loteria.getNome());
			this.setPremia(loteria.getPremia());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getJsonFile() {
		return jsonFile;
	}

	public String getJsonPath() {
		return new File(base + getJsonFile()).getAbsolutePath();
	}

	public String getNome() {
		return nome;
	}

	public List<Integer> getPremia() {
		return premia;
	}

	public void setJsonFile(String jsonFile) {
		this.jsonFile = jsonFile;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setPremia(List<Integer> premia) {
		this.premia = premia;
	}

}
