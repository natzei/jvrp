package it.unica.informatica.ro.vrp.solver.strategies.optimizers.intra_route;

import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Route;
import it.unica.informatica.ro.vrp.problem.model.Vehicle;
import it.unica.informatica.ro.vrp.utils.collections.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements various types of optimizations into a {@link Route}
 * trying to change the order of {@link Customer}s. So, the total amount of
 * demand does not change after optimization, only the total cost of the route changes.
 * 
 * @see Route
 * @see Customer
 * @author nicola
 *
 */
public class TwoOptOptimizer {
	
	public enum TwoOptOption { BEST_IMPROVEMENT, FIRST_IMPROVEMENT }
	
	public static final Logger log = LoggerFactory.getLogger(TwoOptOptimizer.class);
	public static double GAIN_DELTA = 0.000_001;
	
	private CostMatrix costMatrix;
	
	
	//---------------------------- Constructor -------------------------------//
	
	public TwoOptOptimizer(CostMatrix costMatrix) {
		this.costMatrix = costMatrix;
	}

	
	//----------------------------- Methods ----------------------------------//
	
	/**
	 * <p>Perform 2-opt algorithm on the given route.</p> 
	 * <p>You can choose between two options:
	 * <ul>
	 * <li><code>TwoOptOption.BEST_IMPROVEMENT</code>
	 * <li><code>TwoOptOption.FIRST_IMPROVEMENT</code>
	 * </ul>
	 * </p> 
	 * @param route
	 * @param option
	 * @return	true if improvements occurs, false otherwise
	 */
	public boolean twoOpt(Route route, TwoOptOption option) {
		
		log.debug("option: {}", option);
		
		switch (option) {
			case BEST_IMPROVEMENT: return twoOptImprovement(route, true);
			case FIRST_IMPROVEMENT: return twoOptImprovement(route, false);
		}
		return false;
	}
	
	/**
	 * <p>Perform 2-opt algorithm on the given solution.</p> 
	 * <p>You can choose between two options:
	 * <ul>
	 * <li><code>TwoOptOption.BEST_IMPROVEMENT</code>
	 * <li><code>TwoOptOption.FIRST_IMPROVEMENT</code>
	 * </ul>
	 * </p>
	 * <p>This method is equivalent on calling {@link #twoOpt(Route, TwoOptOption)} for
	 * each route of {@link Solution#getVehicles()}</p>
	 * @param solution
	 * @param option
	 * @return	true if improvements occurs, false otherwise
	 */
	public boolean twoOpt(Solution solution, TwoOptOption option) {
		
		boolean improv = false;
		
		for (Vehicle v : solution.getVehicles()){
			improv = improv || this.twoOpt(v.getRoute(), option);
		}
		
		return improv;	// return true if some route were optimized
	}
	
	private boolean twoOptImprovement(Route r, boolean best) {
		
		double gain;
		double bestGain = 0;
		Pair<Integer,Integer> bestSwap = new Pair<>();
		
		for (int i = 1; i< r.size()-2; i++) {
			for (int k = i+1; k < r.size()-1; k++) {	// for each customer pair (i,j)
				
				gain = calculateGain(r, i, k);
				
				log.debug("({},{}) gain = {}", i, k, gain);
				
				if (gain > bestGain) {
					if (best)
						bestSwap.set(i,k);		// save indexes and swap at the end
					else {
						r.swap(i, k);			// swap and return the route
						return true;
					}
					
					bestGain = gain;			// update best gain
				}
			}
		}
		
		if (bestGain>0) {
			r.swap(bestSwap.getFirst(), bestSwap.getSecond());
			return true;
		}
		else {
			log.debug("no improvements");
			return false;
		}
		
		
	}
	
	
	/**
	 * Calculate the gain obtained swapping node i and j.
	 * This may increase performance because avoid the swap operation that is O(n), 
	 * while this check is O(1). In particular returns 
	 * <code>c(i-1, i) + c(j, j+1) - c(i-1,j) - c(i, j+1)</code>, where <code>c</code>
	 * is the cost matrix.
	 * @param route
	 * @param i
	 * @param j
	 * @return the gain
	 */
	private double calculateGain(Route route, int i, int j) {
		int n1 = route.get(i-1).getId();
		int n1Next = route.get(i).getId();
		int n2 = route.get(j).getId();
		int n2Next = route.get(j+1).getId();
		
		double cost = costMatrix.getCost(n1, n1Next) + costMatrix.getCost(n2, n2Next) - 
			costMatrix.getCost(n1, n2) - costMatrix.getCost(n1Next, n2Next);
		
		return cost>GAIN_DELTA? cost: 0.;
	}
	
}
