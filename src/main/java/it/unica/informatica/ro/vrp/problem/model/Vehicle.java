package it.unica.informatica.ro.vrp.problem.model;

import org.apache.commons.lang.Validate;

import it.unica.informatica.ro.vrp.problem.CostMatrix;

public class Vehicle implements Comparable<Vehicle>{
	
	private final float capacity;
	private Route route;
	
	
	//---------------------------- Constructor -------------------------------//
	
	public Vehicle (float capacity) {
		Validate.isTrue(capacity>0, "vehicle capacity must be greater than 0");
		this.capacity = capacity;
	}
	
	//----------------------------- Methods ----------------------------------//
	
	/**
	 * Check if the vehicle is valid. In particular, route must be valid and the
	 * vehicle capacity must be positive and greater than the amount of demand.
	 * @return true if the vehicle is valid, false otherwise
	 */
	public boolean isValid() {
		return route!=null && route.isValid() && route.demand() <= capacity;
	}
	
	/**
	 * Return the cost of the vehicle's route.
	 * @param costMatrix
	 * @return the cost of the vehicle's route
	 */
	public double cost(CostMatrix costMatrix) {
		return route.cost(costMatrix);
	}

	
	//----------------------------- Overrides --------------------------------//
	
	@Override
	public int compareTo(Vehicle o) {
		return Float.compare(this.capacity, o.getCapacity());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(capacity);
		builder.append(") ");
		builder.append(route);
		return builder.toString();
	}
	
	
	//-------------------------- Getters / Setters ---------------------------//

	public Route getRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		Validate.notNull(route, "cannot set null route");
		this.route = route;
	}
	
	public float getCapacity() {
		return capacity;
	}

}
