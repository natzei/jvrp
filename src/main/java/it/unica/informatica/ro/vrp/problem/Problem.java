package it.unica.informatica.ro.vrp.problem;

import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

public class Problem {
	
	private Depot depot;
	private List<Customer> customers= new ArrayList<>();
	private CostMatrix costMatrix;
	private float vehicleCapacity;

	
	//----------------------------- Methods ----------------------------------//

	/**
	 * Perform some tests to check problem validity.
	 * @return	true if pass all tests, false otherwise.
	 */
	public boolean isValid() {
		
		Validate.notNull(depot, "depot must be defined");
		Validate.notEmpty(customers, "list of customers must be be defined");
		Validate.notNull(costMatrix, "cost matrix must be defined");
		
		if (customers.size()+1 != costMatrix.size())
			return false;
		
		for (Customer c : customers) {
			if (c.getDemand()>vehicleCapacity)
				return false;
		}
		
		return true;
	}

	
	//-------------------------- Getters / Setters ---------------------------//

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public CostMatrix getCostMatrix() {
		return costMatrix;
	}

	public void setCostMatrix(CostMatrix costMatrix) {
		this.costMatrix = costMatrix;
	}
	
	public float getVehicleCapacity() {
		return vehicleCapacity;
	}
	
	public void setVehicleCapacity(float vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}
}
