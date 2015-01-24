package it.unica.informatica.ro.vrp.problem.model;

public abstract class Node {

	private final int id;
	private float demand;
	
	
	//---------------------------- Constructor -------------------------------//

	public Node(int id) {
		this.id = id;
	}
	
	
	//--------------------------Abstract Methods -----------------------------//
	
	public abstract boolean isDepot();

	
	//----------------------------- Methods ----------------------------------//

	public int getId() {
		return id;
	}
	
	public float getDemand() {
		return demand;
	}
	
	public void setDemand(float demand) {
		this.demand = demand;
	}
	
	
	//----------------------------- Overrides --------------------------------//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}
}