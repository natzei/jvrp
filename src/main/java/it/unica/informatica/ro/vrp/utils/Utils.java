package it.unica.informatica.ro.vrp.utils;

import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.utils.collections.Pair;
import it.unica.informatica.ro.vrp.utils.collections.PairList;


public class Utils {

	public static CostMatrix calculateCostMatrix(PairList<Integer, Integer> coordinates) {
		CostMatrix costMatrix = new CostMatrix( coordinates.size() );
		
		double tmp;
		
		for (int i=0; i<coordinates.size()-1; i++)
			for (int j=i+1; j<coordinates.size(); j++) {
				tmp = euclideanDistance(coordinates.get(i), coordinates.get(j));
				costMatrix.setCost(i, j, tmp);
			}
		
		return costMatrix;
	}
	
	public static double euclideanDistance(Pair<Integer,Integer> a, Pair<Integer,Integer> b) {
		return
			Math.sqrt(
				Math.pow( a.getFirst() - b.getFirst(), 2 ) +
				Math.pow( a.getSecond() - b.getSecond(), 2 )
			);
		
	}
	
}
