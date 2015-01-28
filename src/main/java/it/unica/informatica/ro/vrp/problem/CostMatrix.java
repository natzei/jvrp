package it.unica.informatica.ro.vrp.problem;

import org.apache.commons.lang.Validate;

public class CostMatrix {

	private double[][] costMatrix;
	
	
	//---------------------------- Constructor -------------------------------//

	public CostMatrix(int n) {
		
		costMatrix = new double[n][n];
		
		for (int i=0; i<n; i++)
			costMatrix[i][i] = Double.MAX_VALUE;
	}

	
	//----------------------------- Methods ----------------------------------//
	
	public void setCost(int a, int b, double cost) {
		Validate.isTrue(a>=0 && a<this.size(), "index out of bound");
		Validate.isTrue(b>=0 && b<this.size(), "index out of bound");
		Validate.isTrue(a!=b, "you cannot set cost between a node and itself");
		
		costMatrix[a][b]=cost;
		costMatrix[b][a]=cost;
	}
	
	public double getCost(int a, int b) {
		return costMatrix[a][b];
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
				if (i==j)
					sb.append("inf").append(" ");
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
