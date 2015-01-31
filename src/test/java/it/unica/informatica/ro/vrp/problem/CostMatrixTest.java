package it.unica.informatica.ro.vrp.problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import it.unica.informatica.ro.vrp.problem.exceptions.UndefinedCostException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CostMatrixTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void setterGetterCostTest() {
		
		int matrixSize = 4;
		double cost = 5;
		
		CostMatrix c = new CostMatrix(matrixSize);
		c.setCost(0, 1, cost);
		
		assertEquals(cost, c.getCost(0, 1), 0);
		assertEquals(cost, c.getCost(1, 0), 0);
		
		assertEquals(CostMatrix.INFINITE, c.getCost(0, 0), 0);
		assertEquals(CostMatrix.INFINITE, c.getCost(1, 1), 0);
		
		/*
		 * setter
		 */
		try {
			c.setCost(0, 0, cost);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			c.setCost(-1, 0, cost);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			c.setCost(0, matrixSize, cost);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		
		/*
		 * getter		
		 */
		try {
			c.getCost(0, 2);	//cost is undefined between 0 and 2
			fail();
		}
		catch (UndefinedCostException e) {}
	}

	@Test
	public void nearestTest() {
		int matrixSize = 6;
		
		CostMatrix c = new CostMatrix(matrixSize);
		c.setCost(0, 1, 5.0F);
		c.setCost(0, 2, 15.0F);
		c.setCost(0, 3, 1.0F);
		c.setCost(0, 4, 100.0F);
		c.setCost(0, 5, 57.0F);
		
		assertEquals(3, c.nearest(0));
	}
	
		
	
}
