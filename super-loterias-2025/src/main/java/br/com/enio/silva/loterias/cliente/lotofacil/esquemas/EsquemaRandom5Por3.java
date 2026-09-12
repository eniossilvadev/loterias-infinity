package br.com.enio.silva.loterias.cliente.lotofacil.esquemas;

import java.util.Collections;
import java.util.List;

import br.com.enio.silva.loterias.util.ListaUtils;

public class EsquemaRandom5Por3 {

	public static List<List<Integer>> getSorte() {
		List<Integer> lista = ListaUtils.iterateStream(1, 1, 25);
		Collections.shuffle(lista);

		return null;
	}

}
