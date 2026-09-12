package br.com.silva.enio.loterias.model.mapper;

import java.io.IOException;
import java.util.List;

public class DuplaSenaMapper extends GenericMapper {

	@Override
	public List<List<Integer>> getNumerosSorteados(String file) throws IOException {

		return super.getNumerosSorteadosFlex(file, 16, 6);
	}

	public List<List<Integer>> getNumerosSorteio1(String file) throws IOException {

		return super.getNumerosSorteados(file, 2, 7);
	}

	public List<List<Integer>> getNumerosSorteio2(String file) throws IOException {

		return super.getNumerosSorteadosFlex(file, 16, 6);
	}

}
