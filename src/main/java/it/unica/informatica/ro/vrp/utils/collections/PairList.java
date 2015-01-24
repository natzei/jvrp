package it.unica.informatica.ro.vrp.utils.collections;

import java.util.ArrayList;
import java.util.List;


public class PairList<A extends Comparable<A>,B extends Comparable<B>> extends ArrayList<Pair<A,B>> {
	
	private static final long serialVersionUID = 74738694303982297L;
	
	public boolean add(A first, B second){
		return this.add(new Pair<A,B>(first, second));
	}
	
	public List<A> getFirstList(){
		ArrayList<A> returnValue = new ArrayList<A>();
		
		for (Pair<A,B> obj : this)
			returnValue.add(obj.getFirst());
		
		return returnValue;		
	}
	
	public List<B> getSecondList(){
		ArrayList<B> returnValue = new ArrayList<B>();
		
		for (Pair<A,B> obj : this)
			returnValue.add(obj.getSecond());
		
		return returnValue;		
	}
	
}
