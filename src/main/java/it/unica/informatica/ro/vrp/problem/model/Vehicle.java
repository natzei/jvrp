package it.unica.informatica.ro.vrp.problem.model;

import it.unica.informatica.ro.vrp.problem.CostMatrix;

public class Vehicle implements Comparable<Vehicle>{
	
	private final float capacity;
	private Route route;
	
	
	//---------------------------- Constructor -------------------------------//
	
	public Vehicle (float capacity) {
		this.capacity = capacity;
	}
	
	//----------------------------- Methods ----------------------------------//
	
	/**
	 * Check if the vehicle is valid. In particular, route must be valid and the
	 * vehicle capacity must be positive and greater than the amount of demand.
	 * @return
	 */
	public boolean isValid() {
		return route.isValid() && capacity>0 && route.demand() <= capacity;
	}
	
	/**
	 * Return the cost of the vehicle's route.
	 * @param costMatrix
	 * @return
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
		this.route = route;
	}
	
	public float getCapacity() {
		return capacity;
	}

}
