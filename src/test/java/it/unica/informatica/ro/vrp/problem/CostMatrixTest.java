package it.unica.informatica.ro.vrp.problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		
		int matrixSize = 2;
		
		CostMatrix c = new CostMatrix(matrixSize);
		c.setCost(0, 1, 5.0F);
		assertEquals(5.0F, c.getCost(0, 1), 0);
		
		assertEquals(Double.MAX_VALUE, c.getCost(0, 0), 0);
		assertEquals(Double.MAX_VALUE, c.getCost(1, 1), 0);
		
		try {
			c.setCost(0, 0, 5);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			c.setCost(-1, 0, 5);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			c.setCost(0, matrixSize, 5);
			fail();
		}
		catch (IllegalArgumentException e) {}
	}

}
