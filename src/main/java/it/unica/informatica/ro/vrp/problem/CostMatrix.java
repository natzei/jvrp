package it.unica.informatica.ro.vrp.problem;

import it.unica.informatica.ro.vrp.problem.exceptions.UndefinedCostException;

import java.util.Arrays;

import org.apache.commons.lang.Validate;

public class CostMatrix {

	private static final double UNDEFINED = Double.MIN_VALUE;
	public static final double INFINITE = Double.MAX_VALUE;
	
	private double[][] costMatrix;
	
	
	//---------------------------- Constructor -------------------------------//

	public CostMatrix(int n) {
		costMatrix = new double[n][n];

		for (int i=0; i<n; i++) {
			Arrays.fill(costMatrix[i], UNDEFINED);
			costMatrix[i][i] = INFINITE;
		}
	}

	
	//----------------------------- Methods ----------------------------------//
	
	/**
	 * Set the cost between two indexes. The cost is reflective, so 
	 * <code>CostMatrix#getCost(a,b) == CostMatrix#getCost(b,a)</code> even if you not calls
	 * <code>CostMatrix#setCost(b,a, cost)</code>.
	 * @param a	the first index
	 * @param b the second index
	 * @param cost the cost to be set
	 */
	public void setCost(int a, int b, double cost) {
		Validate.isTrue(a>=0 && a<this.size(), "index out of bound");
		Validate.isTrue(b>=0 && b<this.size(), "index out of bound");
		Validate.isTrue(a!=b, "you cannot set cost between a node and itself");
		
		costMatrix[a][b]=cost;
		costMatrix[b][a]=cost;
	}
	
	/**
	 * Return the nearest index to i, that is the one with the least cost. 
	 * @param i the index
	 * @return the nearest index
	 */
	public int nearest(int i) {
		Validate.isTrue(i>=0 && i<this.size(), "index out of bound");
		
		double min = Double.MAX_VALUE;
		int minIndex = -1;
		for (int j=0; j<costMatrix[i].length; j++) {
			if (this.getCost(i, j)<min) {
				min = this.getCost(i, j);
				minIndex = j;
			}
		}
		return minIndex;
	}
	
	/**
	 * Return the cost between two indexes.
	 * @param a
	 * @param b
	 * @return the cost
	 * @throws UndefinedCostException if the cost is undefined
	 */
	public double getCost(int a, int b) {
		Validate.isTrue(a>=0 && a<this.size(), "index out of bound");
		Validate.isTrue(b>=0 && b<this.size(), "index out of bound");
		
		double cost = costMatrix[a][b];
		if (cost==UNDEFINED)
			throw new UndefinedCostException(a, b);
		
		return cost;
	}

	public int size() {
		return costMatrix.length;
	}
	
	
	//----------------------------- Overrides --------------------------------//

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<costMatrix.length; i++)
			sb.append("____");
		sb.append("\n\n");
		
		for (int i=0; i<costMatrix.length; i++) {
			for (int j=0; j<costMatrix.length; j++) {
				if (costMatrix[i][j]==INFINITE)
					sb.append("inf").append(" ");
				else if (costMatrix[i][j]==INFINITE)
					sb.append("---").append(" ");
				else
					sb.append(costMatrix[i][j]).append(" ");
			}
			sb.append("\n");
		}
		
		for (int i=0; i<costMatrix.length; i++)
			sb.append("____");
		sb.append("\n");
		
		return sb.toString();
	}
}
