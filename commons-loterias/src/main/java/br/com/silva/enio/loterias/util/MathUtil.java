package util;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.NotPredicate;


public class MathUtil {
	
	private static Predicate isEven = new Predicate( ) {
	    public boolean evaluate(Object input) {
	        Integer number = (Integer) input;
	        return( number.intValue( ) % 2 == 0 );
	    }
	};
	
	private static Predicate isOdd = new NotPredicate(isEven);
	
	private static Transformer evenCount = new Transformer() {
		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			int count = 0;
			if(input instanceof List){
				List<Object> list = (List<Object>) input;
				for(Object l: list){
					if(l instanceof Integer){
						if(isEven.evaluate(l)){
							count++;
						}
					}
				}
			}
			return count;
		}
	};
	
	private static Transformer getEven = new Transformer() {
		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			List<Integer> even = new ArrayList<Integer>();
			if(input instanceof List){
				List<Object> list = (List<Object>) input;
				for(Object l: list){
					if(l instanceof Integer){
						if(isEven.evaluate(l)){
							even.add((Integer)l);
						}
					}
				}
			}
			return even;
		}
	};
	
	private static Transformer getOdd = new Transformer() {
		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			List<Integer> odd = new ArrayList<Integer>();
			if(input instanceof List){
				List<Object> list = (List<Object>) input;
				for(Object l: list){
					if(l instanceof Integer){
						if(isOdd.evaluate(l)){
							odd.add((Integer)l);
						}
					}
				}
			}
			return odd;
		}
	};
	
	private static Transformer oddCount = new Transformer() {		
		@SuppressWarnings("unchecked")
		@Override
		public Object transform(Object input) {
			int count = 0;
			if(input instanceof List){
				List<Object> list = (List<Object>) input;
				for(Object l: list){
					if(l instanceof Integer){
						if(isOdd.evaluate(l)){
							count++;
						}
					}
				}
			}
			return count;
		}
	};
	
	public static Boolean isEven(Integer number){
		return isEven.evaluate(number);
	}
	
	public static Boolean isOdd(Integer number){
		return isOdd.evaluate(number);
	}
	
	public static Integer countEven(List<Integer> elems){
		return (Integer) evenCount.transform(elems);
	}
	
	public static Integer countOdd(List<Integer> elems){
		return (Integer) oddCount.transform(elems);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getAllEven(List<Integer> elem){
		return (List<Integer>) getEven.transform(elem);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getAllOdd(List<Integer> elem){
		return (List<Integer>) getOdd.transform(elem);
	}

}
