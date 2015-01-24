package it.unica.informatica.ro.vrp.solver;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.solver.initializers.Initializer;
import it.unica.informatica.ro.vrp.solver.strategies.Strategy;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProblemSolver {
	
	private static Logger log = LoggerFactory.getLogger(ProblemSolver.class);
	
	private Strategy strategy;
	private Initializer init;
	
	public ProblemSolver(Initializer init, Strategy strategy) {
		this.init = init;
		this.strategy = strategy;
	}
	
	public Solution solve(Problem problem) {
		
		/*
		 * Check if problem is valid
		 */
		Validate.isTrue(problem.isValid(), "invalid problem. Check the parameters.");
		
		/*
		 * Get an initial solution that respect the problem constrains
		 */
		Solution solution = init.initialSolution(problem);
		
		Validate.isTrue(solution.isValid(), "invalid initial solution");
		
		
		double currentCost = 0;
		double cost = solution.cost(problem.getCostMatrix());
		
		log.info("initial solution:\n{}", solution);
		log.info("initial cost: {}", cost);
		
		int i=0;
		do {
			log.info("*********************************");
			log.info("iteration {}", i++);
			log.info("cost {}", cost);
			currentCost = cost;
			
			strategy.minimize(solution);
			
			cost = solution.cost(problem.getCostMatrix());
			log.info("newCost {}", cost);
		}
		while(cost<currentCost);
		
		log.info("end");
		
		return solution;
	}
	
}
