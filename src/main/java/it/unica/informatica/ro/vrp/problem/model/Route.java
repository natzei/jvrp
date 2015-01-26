package it.unica.informatica.ro.vrp.problem.model;

import it.unica.informatica.ro.vrp.problem.CostMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.Validate;

public class Route implements Cloneable, Iterable<Node>{
	
	private ArrayList<Node> route = new ArrayList<Node>();
	private HashSet<Node> nodes = new HashSet<Node>();

	
	//---------------------------- Constructor -------------------------------//

	public Route(Node... customers) {
		this.add(customers);
	}

	
	//----------------------------- Methods ----------------------------------//

	public void add(Node... nodes) {
		this.add(0, nodes);
	}
	
	public void add(int index, Node... nodes) {
		Collection<Node> coll = Arrays.asList(nodes);
		this.route.addAll(index, coll);
		this.nodes.addAll(coll);
	}
	
	public void remove(int index) {
		if (index==0 || index==route.size()-1)
			throw new IllegalArgumentException("cannot remove depots");
		
		Node n = this.route.get(index);
		this.route.remove(index);			// remove from list
		this.nodes.remove(n);				// remove from hashset
	}
	
	public Node get(int index) {
		return route.get(index);
	}
	
	public int size() {
		return this.route.size();
	}

	public boolean isEmpty() {
		return this.route.isEmpty();
	}
	
	/**
	 * Return the total amount of demand of route's customers.
	 * @return
	 */
	public float demand() {
		float amount = 0;
		for (Node node : route)
			amount+=node.getDemand();
		return amount;
	}
	
	/**
	 * Calculate the total cost of the route.
	 * @param costMatrix
	 * @return
	 */
	public double cost(CostMatrix costMatrix) {
		double cost = 0;
		int pred = -1;
		
		for (Node node : route) {
			if (pred!=-1)
				cost+=costMatrix.getCost(pred, node.getId() );
			
			pred = node.getId();
		}
		
		return cost;
	}
	
	/**
	 * Check if the route is valid. A valid route starts and ends in a depot,
	 * contain at least one customer and a customer can appear only once.
	 * @return
	 */
	public boolean isValid() {
		// the route contains at least 1 customer
		// D ==> c ==> D
		if (route.size() < 3)
			return false;
		
		// first and last node MUST be a depot
		if (!route.get(0).isDepot() || !route.get(route.size()-1).isDepot())
			return false;
		
		// each node appear once (except depot)
		if (route.size()-1 != nodes.size())
			return false;
		
		return true;
	}
	
	/**
	 * Swap nodes specified by the given indexes. This involves all nodes between
	 * a and b, because the route is reversed. <br>
	 * <pre>
	 * Example. The route 0 => 1 => 2 => 3 => 4 => 5 => 6
	 * with indexes a=1 and b=5 becomes 0 => 5 => 4 => 3 => 2 => 1 => 6
	 * </pre>
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean swap(int a, int b) {
		Validate.isTrue(this.isValid(), "check route validity: "+this.toString());
		
		if (a==0 || b==route.size()-1)	// you cannot move depots
			return false;
		
		if (b<=a)						// index must not be consecutive and b > a
			return false;
		
		Collections.reverse(route.subList(a, b+1));
		
		return true;
	}
	
	/**
	 * Move the node from a position to another one.
	 * @param i		the position of the node to be moved
	 * @param j 	the new position of the node is between j and j+1
	 */
	public void moveNode(int i, int j) {
		Validate.isTrue(this.isValid(), "check route validity: "+this.toString());
		
		if (i<=j) {
			Node n = this.route.remove(i);
			this.route.add(j, n);
		}
		else {
			Node n = this.route.remove(i);
			this.route.add(j+1, n);
		}
	}
	
	
	//----------------------------- Overrides --------------------------------//

	@Override 
	public String toString() {
		return route.toString();
	}

	@Override
	public Iterator<Node> iterator() {
		return this.route.iterator();
	}
}
