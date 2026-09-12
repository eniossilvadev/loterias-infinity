package br.com.enio.silva.loterias.cliente.mix;

import java.util.ArrayList;
import java.util.List;

import br.com.enio.silva.loterias.util.ListaUtils;

public class RemoverUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<List<Integer>> remover(final List<List<Integer>> input, final String path,
			final int size) {
		List<List<Integer>> listaRetorno = new ArrayList<>(input);
		listaRetorno = ListaUtils.gc(listaRetorno, size);

		List<List<Integer>> listaRemover = ListaUtils.getAll(path, size);

		listaRetorno.removeAll(listaRemover);

		return listaRetorno;
	}

}
