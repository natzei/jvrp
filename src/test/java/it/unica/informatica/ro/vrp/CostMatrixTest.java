package it.unica.informatica.ro.vrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import it.unica.informatica.ro.vrp.problem.CostMatrix;

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
		
		CostMatrix c = new CostMatrix(2);
		c.setCost(0, 1, 5.0F);
		assertEquals(5.0F, c.getCost(0, 1), 0);
		
		assertEquals(Double.MAX_VALUE, c.getCost(0, 0), 0);
		assertEquals(Double.MAX_VALUE, c.getCost(1, 1), 0);
		
		try {
			c.setCost(0, 0, 5);
			fail("You never come here");
		}
		catch (Exception e) {
			
		}
	}

}
