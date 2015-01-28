package it.unica.informatica.ro.vrp.solver.initializers;

import org.apache.commons.lang.Validate;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.problem.model.Route;
import it.unica.informatica.ro.vrp.problem.model.Vehicle;


public class BasicInitializer implements Initializer {

	/**
	 * Get a valid solution that respects the constraints. For each customer allocate a vehicle.
	 * to serve it.
	 * @see Problem
	 * @see Solution
	 * @param problem
	 * @return a valid solution for the given problem
	 */
	@Override
	public Solution initialSolution(Problem problem) {
		Solution sol = new Solution();
		
		Vehicle v;
		Depot depot = problem.getDepot();
		
		for (Customer customer : problem.getCustomers()) {
			v = new Vehicle(problem.getVehicleCapacity());
			v.setRoute( new Route(depot,customer, depot) );
			
			Validate.isTrue(v.isValid(), "invalid vehicle ", v);
			
			sol.getVehicles().add( v );
		}
		
		Validate.isTrue(sol.isValid(), "the given solution is invalid");
		
		return sol;
	}

}
