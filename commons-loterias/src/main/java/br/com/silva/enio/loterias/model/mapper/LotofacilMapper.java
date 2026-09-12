package br.com.silva.enio.loterias.model.mapper;

import java.io.IOException;
import java.util.List;

public class LotofacilMapper extends GenericMapper {

	@Override
	public List<List<Integer>> getNumerosSorteados(String file) throws IOException {

		return super.getNumerosSorteados(file, 2, 16);
	}

}
