package it.unica.informatica.ro.vrp.solver.strategies;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.solver.ProblemSolver;
import it.unica.informatica.ro.vrp.solver.opt.InterRouteOptimizer;
import it.unica.informatica.ro.vrp.solver.opt.IntraRouteOptimizer;

/**
 * <p>A Strategy try to minimize the cost of a given solution. It's used by
 * the {@link ProblemSolver#solve(Problem)} method at each iteration.</p>
 * <p>A Strategy can use {@link InterRouteOptimizer} and {@link IntraRouteOptimizer}
 * in order to reach its goal.</p>
 * 
 * @see Solution
 * @see Problem
 * @see ProblemSolver
 * @see IntraRouteOptimizer
 * @see InterRouteOptimizer
 * 
 * @author nicola
 */
public interface Strategy {

	/**
	 * Minimize the given solution in some way.
	 * @param solution
	 * @see Solution
	 */
	public void minimize(Solution solution) ;
	
}
