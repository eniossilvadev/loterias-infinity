package br.com.enio.silva.loterias.cliente.mix;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.enio.silva.loterias.util.ListaUtils;

public class SorteioSimples {

	public static void main(String[] args) {
		List<Integer> list = ListaUtils.getListaRange(1, 60);
		for(int i = 0; i < 10; i++) {
			Collections.shuffle(list);
			List<Integer> subLista = list.subList(0, 6);
			Collections.sort(subLista);
			System.out.println(subLista.stream().map(e -> e.toString() + "\t").collect(Collectors.joining()));
			list.removeAll(subLista);

		}
	}

}
