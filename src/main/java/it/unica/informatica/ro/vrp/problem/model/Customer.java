package it.unica.informatica.ro.vrp.problem.model;

public class Customer extends Node {

	public static int CUSTOMER_ID_COUNT = 1;
	
	
	//---------------------------- Constructor -------------------------------//

	public Customer(float demand) {
		super(CUSTOMER_ID_COUNT++);
		super.setDemand(demand);
	}

	
	//----------------------------- Overrides --------------------------------//

	@Override
	public boolean isDepot() {
		return false;
	}
}
