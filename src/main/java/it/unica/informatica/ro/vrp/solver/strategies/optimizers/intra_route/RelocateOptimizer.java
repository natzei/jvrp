package it.unica.informatica.ro.vrp.solver.strategies.optimizers.intra_route;

import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Route;
import it.unica.informatica.ro.vrp.problem.model.Vehicle;
import it.unica.informatica.ro.vrp.utils.collections.Pair;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class implements various types of optimizations on {@link Vehicle}s trying
 * to move {@link Customer}s between related {@link Route}s, minimizing the total 
 * cost and respecting {@link Vehicle}'s capacity.
 * 
 * @see Route
 * @see Vehicle
 * @see Customer
 * @author nicola
 * 
 */
public class RelocateOptimizer {
	
	public static final Logger log = LoggerFactory.getLogger(RelocateOptimizer.class);
	
	public enum RelocateOption { BEST_IMPROVEMENT, FIRST_IMPROVEMENT }
	
	public static double GAIN_DELTA = 0.000_001;
	
	private CostMatrix costMatrix;
	
	public RelocateOptimizer(CostMatrix costMatrix) {
		this.costMatrix = costMatrix;
	}
	
	
	/**
	 * Try to move a customer from vehicle B to vehicle A (respecting A capacity)
	 * minimizing the total cost of the two routes.
	 * You can try to move some customer into the same route passing A and B such that A == B.
	 * @param a
	 * @param b
	 * @param option
	 * @return true if improvements occurs, false otherwise.
	 */
	public boolean relocate (Vehicle a, Vehicle b, RelocateOption option) {
		log.debug("option: {}", option);
		log.debug("expanding {}, shrinking {}", a, b);
		
		switch (option) {
			case BEST_IMPROVEMENT: return relocate(Collections.singletonList(a), b, true);
			case FIRST_IMPROVEMENT: return relocate(Collections.singletonList(a), b, false);
		}
		
		return false;
	}
	
	/**
	 * Try to move a customer from vehicle B to some other vehicle into the specified list.
	 * @param list
	 * @param b
	 * @param option
	 * @return true if improvements occurs, false otherwise.
	 */
	public boolean relocate (List<Vehicle> list, Vehicle b, RelocateOption option) {
		log.debug("option: {}", option);
		log.debug("shrinking {} over some route", b);
		
		switch (option) {
			case BEST_IMPROVEMENT: return relocate(list, b, true);
			case FIRST_IMPROVEMENT: return relocate(list, b, false);
		}
		
		return false;
	}
	
	
	/**
	 * Try to move a customer from a vehicle to some other vehicle.
	 * @param solution
	 * @param option
	 * @return true if improvements occurs, false otherwise.
	 */
	public boolean relocate (Solution solution, RelocateOption option) {
		log.debug("option: {}", option);
		
		switch (option) {
			case BEST_IMPROVEMENT: return relocate(solution.getVehicles(), true);
			case FIRST_IMPROVEMENT: return relocate(solution.getVehicles(), false);
		}
		
		return false;
	}
	
	
	
	
	private boolean relocate (List<Vehicle> aList, Vehicle b, boolean best) {
		
		double gain;
		double bestGain = 0;
		
		Vehicle bestVehicle = null;
		Pair<Integer,Integer> bestRelocate = new Pair<>();
		
		
		for (Vehicle a : aList) {
		
			float remainingCapacity = a.getCapacity() - a.getRoute().demand();
			
			for (int i=1; i< b.getRoute().size()-1; i++) {	// iterate on customers (no depots!)
				
				if (b.getRoute().demand()<remainingCapacity) {	// customer i can fit into route a
					
					/*
					 * for each possible position in route a
					 * find a position that decrement the amount cost of the two routes
					 */
					for (int j=0; j< a.getRoute().size()-1; j++) {
						
						gain = calculateGain(a.getRoute(), b.getRoute(), j, i);
						
						log.debug("node {}, between {} {}, gain = {}", i, j, j+1, gain);
						
						if (gain > bestGain) {
							if (best) {
								bestVehicle = a;
								bestRelocate.set(i,j);		// save indexes and move at the end
							}
							else {
								move(a.getRoute(), b.getRoute(), j, i);		// move and return
								return true;
							}
	
							bestGain = gain;			// update best gain
						}
					}
				}
			}
		}
		
		
		if (bestGain>0 && bestVehicle!=null) {		// move and return
			move(bestVehicle.getRoute(), b.getRoute(), bestRelocate.getSecond(), bestRelocate.getFirst());
			return true;
		}
		else {
			log.debug("no improvements");
			return false;
		}
		
	}
	
	
	
	private boolean relocate (List<Vehicle> aList, boolean best) {
		
		double gain;
		double bestGain = 0;
		
		Vehicle bestSourceVehicle = null;
		Vehicle bestDestVehicle = null;
		Pair<Integer,Integer> bestRelocate = new Pair<>();
		
		for (Vehicle b : aList) {
			for (Vehicle a : aList) {
			
				log.debug("expanding {}, shrinking {}", a, b);
				
				float remainingCapacity = a.getCapacity() - a.getRoute().demand();
				
				for (int i=1; i< b.getRoute().size()-1; i++) {	// iterate on customers (no depots!)
					
					if (b.getRoute().demand()<remainingCapacity) {	// customer i can fit into route a
						
						log.debug("node {} fit into route {}", i, a);
						
						/*
						 * for each possible position in route a
						 * find a position that decrement the amount cost of the two routes
						 */
						for (int j=0; j< a.getRoute().size()-1; j++) {
							
							gain = calculateGain(a.getRoute(), b.getRoute(), j, i);
							
							log.debug("node {}, between {} {}, gain = {}", i, j, j+1, gain);
							
							if (gain > bestGain) {
								if (best) {
									bestSourceVehicle = b;
									bestDestVehicle = a;
									bestRelocate.set(i,j);		// save indexes and move at the end
								}
								else {
									move(a.getRoute(), b.getRoute(), j, i);		// move and return
									return true;
								}
		
								bestGain = gain;			// update best gain
							}
						}
					}
				}
			}
		}
		
		if (bestGain>0 && bestDestVehicle!=null && bestSourceVehicle!=null) {		// move and return
			log.debug("BEST_IMPROVEMENT: move");
			log.debug("expanding {}, shrinking {}", bestDestVehicle.getRoute(), bestSourceVehicle.getRoute());
			log.debug("node {}, between {} {}", bestRelocate.getSecond(), bestRelocate.getFirst(), bestRelocate.getFirst()+1);
			log.debug("gain {}",bestGain);
			
			move(bestDestVehicle.getRoute(), bestSourceVehicle.getRoute(), 
				bestRelocate.getSecond(), bestRelocate.getFirst());
			return true;
		}
		else {
			log.debug("no improvements");
			return false;
		}
		
	}
	
	
	
	
	
	/**
	 * Is useful move node from position i (into route B) between j and j+1 (into route A)?  
	 * c(j, j+1) + c(i-1, i) + c(i, i+1) > c(j,i) + c(i, j+1) + c(i-1, i+1) 
	 * @param routeA
	 * @param routeB
	 * @param j
	 * @param i
	 * @return the gain
	 */
	private double calculateGain(Route routeA, Route routeB, int j, int i) {
		int n1 = routeB.get(i).getId();
		int n1Prev = routeB.get(i-1).getId();
		int n1Next = routeB.get(i+1).getId();
		int n2 = routeA.get(j).getId();
		int n2Next = routeA.get(j+1).getId();
		
		// this variable is for the case in which removing n1 cause an invalid route 0 ==> 0 (that have infinite cost).
		// In this case, not consider the route
		double n1Prev_n1Next_cost = n1Prev==n1Next? 0 : costMatrix.getCost(n1Prev, n1Next);
		
		// this variable is for the case in which n1 is moved in the route 0 ==> 0 (that have infinite cost).
		// In this case, not consider the route
		double n2_n2Next_cost = n2==n2Next? 0 : costMatrix.getCost(n2, n2Next);
		
		double cost = n2_n2Next_cost + costMatrix.getCost(n1Prev, n1) + costMatrix.getCost(n1, n1Next) - 
			costMatrix.getCost(n2, n1) - costMatrix.getCost(n1, n2Next) - n1Prev_n1Next_cost;
		
		return cost>GAIN_DELTA? cost: 0.;
	}
	
	
	private void move(Route routeA, Route routeB, int j, int i) {
		
		if (routeA==routeB)
			routeA.moveNode(i, j);
		else {
			routeA.add(j+1, routeB.get(i));
			routeB.remove(i);
		}
	}
	
	
}
