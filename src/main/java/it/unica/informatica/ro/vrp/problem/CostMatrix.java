package it.unica.informatica.ro.vrp.problem;

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
		if (a == b)
			throw new RuntimeException("you cannot set cost between a node and itself");
		
		costMatrix[a][b]=cost;
		costMatrix[b][a]=cost;
	}
	
	public double getCost(int a, int b) {
		return costMatrix[a][b];
	}

	public int size() {
		return costMatrix.length;
	}
}
