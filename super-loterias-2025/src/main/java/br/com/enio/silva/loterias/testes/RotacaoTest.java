//package br.com.enio.silva.testes;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//
//import br.com.enio.silva.loterias.diversos.Rotacao;
//
//public class RotacaoTest {
//
//	@RepeatedTest(10)
//	public void testGetList() {
//		int size = 7;
//
//		List<Integer> l1 = Arrays.asList(1, 2, 3);
//		List<Integer> l2 = Arrays.asList(2, 3, 4);
//		List<Integer> l3 = Arrays.asList(4, 5, 6);
//		List<Integer> l4 = Arrays.asList(6, 7, 8);
//		List<Integer> l5 = Arrays.asList(8, 9, 10);
//
//		List<List<Integer>> listas = Arrays.asList(l1, l2, l3, l4, l5);
//
//		List<Integer> list = Rotacao.getList(listas, size);
//		System.out.println(list);
//		assertEquals(list.size(), size);
//
//	}
//
//}
