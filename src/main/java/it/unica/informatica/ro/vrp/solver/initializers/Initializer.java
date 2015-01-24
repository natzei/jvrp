package it.unica.informatica.ro.vrp.solver.initializers;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;


public interface Initializer {

	public Solution initialSolution(Problem problem);
}
