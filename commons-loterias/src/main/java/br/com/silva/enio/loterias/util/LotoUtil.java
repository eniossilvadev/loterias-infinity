package util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;

public class LotoUtil {
	
	private static Transformer sequencias = new Transformer() {
		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			
			List<List<Integer>> resultado = new ArrayList<List<Integer>>();
			
			if(ListUtil.isListOfInteger(input)){
				
				List<Integer> lista = (List<Integer>) input;
				List<Integer> interna = new ArrayList<Integer>();
				for(Integer i: lista){					
					if(interna.isEmpty()){
						interna.add(i);
					} else {
						if(interna.get(interna.size() - 1) + 1 == i){
							interna.add(i);
						} else {
							resultado.add(interna);
							interna = new ArrayList<Integer>();
							interna.add(i);
						}
					}
				}
				resultado.add(interna);
			}
			return resultado;
		}
	};
	
	private static Transformer lpad = new Transformer() {
		
		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			List<String> resultado = new ArrayList<String>();
			if(ListUtil.isListOfInteger(input)){
				List<Integer> lista = (List<Integer>) input;
				for(Integer i: lista){
					resultado.add(StringUtils.leftPad(i.toString(), 2));
				}
			}
			return resultado;
		}
	};
	
	private static Transformer maxSeq = new Transformer() {
		
		@SuppressWarnings("rawtypes")
		@Override
		public Object transform(Object input) {
			int max = 0;
			if(input instanceof List){
				List listaExterna = (List) input;
				for(Object objListaInterna : listaExterna){
					if(objListaInterna instanceof List){
						max = Math.max(max, ((List) objListaInterna).size());
					}
				}
			}
			return max;
		}
	};
	
	public static Integer maiorSequencia(List<Integer> list){
		//TODO calcular maior sequencia.
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static List<List<Integer>> sequencias(List<Integer> lista){		
		return (List<List<Integer>>) sequencias.transform(lista);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> leftPad(List<Integer> lista){
		return (List<String>) lpad.transform(lista);
	}
	
	public static Integer getMaxSequencia(List<List<Integer>> lista){
		return (Integer) maxSeq.transform(lista);
	}

}
