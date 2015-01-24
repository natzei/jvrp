package it.unica.informatica.ro.vrp.solver.strategies;

import java.util.Iterator;

import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.problem.model.Vehicle;
import it.unica.informatica.ro.vrp.solver.opt.InterRouteOptimizer;
import it.unica.informatica.ro.vrp.solver.opt.IntraRouteOptimizer;
import it.unica.informatica.ro.vrp.solver.opt.InterRouteOptimizer.RelocateOption;
import it.unica.informatica.ro.vrp.solver.opt.IntraRouteOptimizer.TwoOptOption;


public class SimpleStrategy implements Strategy {

	private IntraRouteOptimizer intraOpt;
	private InterRouteOptimizer interOpt;
	
	private TwoOptOption twoOptOption;
	private RelocateOption relocateOptOption;
	
	public SimpleStrategy(CostMatrix costMatrix, TwoOptOption twoOptOption, RelocateOption relocateOptOption) {
		
		this.twoOptOption = twoOptOption;
		this.relocateOptOption = relocateOptOption;
		
		this.intraOpt = new IntraRouteOptimizer(costMatrix);
		this.interOpt = new InterRouteOptimizer(costMatrix);
		
		double delta = 0.000_001;
		IntraRouteOptimizer.GAIN_DELTA = delta;
		InterRouteOptimizer.GAIN_DELTA = delta;
	}
	
	
	@Override
	public void minimize(Solution solution) {
		// intra-route improvements on all routes
		intraOpt.twoOpt(solution, twoOptOption);
		
		if (!solution.isValid())
			throw new RuntimeException("found invalid solution "+solution);
		
		// inter-route improvements
		interOpt.relocate(solution, relocateOptOption);
		
		cleanSolution(solution);
		
		if (!solution.isValid())
			throw new RuntimeException("found invalid solution "+solution);
	}
	
	
	
	private void cleanSolution(Solution solution) {
		Iterator<Vehicle> it = solution.getVehicles().iterator();
		
		while (it.hasNext()) {
			Vehicle v = it.next();
			
			if (v.getRoute().demand()==0) {
				it.remove();
			}
		}
	}

}
