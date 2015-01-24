package it.unica.informatica.ro.vrp.problem.model;

public class Depot extends Node {

	public static int DEPOT_ID_COUNT = 0;
	
	
	//---------------------------- Constructor -------------------------------//

	public Depot() {
		super(DEPOT_ID_COUNT);
		this.setDemand(0);
	}

	
	//----------------------------- Overrides --------------------------------//

	@Override
	public boolean isDepot() {
		return true;
	}
}
