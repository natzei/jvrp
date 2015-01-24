package it.unica.informatica.ro.vrp.solver.strategies;

import it.unica.informatica.ro.vrp.problem.Solution;

/**
 * A Strategy try to minimize the cost of a given solution.
 * 
 * @see Solution
 * 
 * @author nicola
 */
public interface Strategy {

	public void minimize(Solution solution) ;
	
}
