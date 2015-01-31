package it.unica.informatica.ro.vrp.problem.exceptions;


public class UndefinedCostException extends RuntimeException {

	private static final long serialVersionUID = -4255481876034750045L;

	public UndefinedCostException(int i, int j) {
		super("Undefined cost between indexes "+i+" and "+j);
	}
	
}
