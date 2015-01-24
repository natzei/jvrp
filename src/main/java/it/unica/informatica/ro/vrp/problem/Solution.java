package it.unica.informatica.ro.vrp.problem;

import it.unica.informatica.ro.vrp.problem.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

public class Solution {
	
	private List<Vehicle> vehicles = new ArrayList<>();


	//----------------------------- Methods ----------------------------------//
	
	/**
	 * Return the cost of the solution.
	 * @param costMatrix
	 * @return
	 */
	public double cost(CostMatrix costMatrix) {
		double cost = 0;
		
		for (Vehicle v : vehicles) {
			cost+=v.getRoute().cost(costMatrix);
		}
		
		return cost;
	}
	
	/**
	 * Check if the solution is valid
	 * @return
	 */
	public boolean isValid() {
		Validate.notEmpty(vehicles, "vehicles must be defined");
		
		for (Vehicle v : vehicles) {
			if (!v.isValid()) {
				return false;
			}
		}
		return true;
	}
	
	
	//----------------------------- Overrides --------------------------------//
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Vehicle v : vehicles)
			sb.append(v.getRoute().toString())
			.append(" (d=").append(v.getRoute().demand()).append(")")
			.append("\n");
			
		return sb.toString();
		
	}
	
	public String toString(CostMatrix costMatrix) {
		StringBuilder sb = new StringBuilder();
		
		for (Vehicle v : vehicles)
			sb.append(v.getRoute().toString())
			.append(" (d=").append(v.getRoute().demand()).append(")")
			.append(" {c=").append(v.cost(costMatrix)).append("}")
			.append("\n");
			
		return sb.toString();
		
	}
	
	
	//-------------------------- Getters / Setters ---------------------------//
	
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
}
