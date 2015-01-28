package it.unica.informatica.ro.vrp.solver.initializers;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.solver.ProblemSolver;

/**
 * <p>An Initializer provide a valid solution for the given problem. It's used by
 * the {@link ProblemSolver#solve(Problem)} to get an initial solution.</p>
 * 
 * @see Solution
 * @see Problem
 * @see ProblemSolver
 * 
 * @author nicola
 *
 */
public interface Initializer {

	/**
	 * Return a valid solution for the given problem.
	 * @param problem
	 * @return a valid solution
	 * @see Solution
	 * @see Problem
	 */
	public Solution initialSolution(Problem problem);
}
