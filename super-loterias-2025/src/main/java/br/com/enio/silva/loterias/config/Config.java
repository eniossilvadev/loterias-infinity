package br.com.enio.silva.loterias.config;

import java.util.List;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public abstract class Config {

	public abstract String getCaminhoTodosResultados();

	public abstract List<List<Integer>> getTodosResultados();

	public abstract List<List<Integer>> obterTodosResultados();

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public String toString() {
		return toJson();
	}

	public String toXML() {
		XStream xml = new XStream();
		return xml.toXML(this);
	}
}
