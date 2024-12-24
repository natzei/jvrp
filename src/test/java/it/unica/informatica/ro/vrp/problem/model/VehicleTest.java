package it.unica.informatica.ro.vrp.problem.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unica.informatica.ro.vrp.problem.CostMatrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VehicleTest {

	private static Route route;
	private static CostMatrix costMatrix;
	
	private static float routeDemand = 100;
	private static double routeCost = 10;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		route = mock(Route.class);
		costMatrix = mock(CostMatrix.class); 
		
		when(route.demand()).thenReturn(routeDemand);
		when(route.cost(any(CostMatrix.class))).thenReturn(routeCost);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void constructorTest() {
		new Vehicle(-10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setRouteTest() {
		new Vehicle(10).setRoute(null);
	}
	
	@Test
	public void validityTest() {
		
		when(route.isValid()).thenReturn(true);		//valid route
		
		Vehicle v = new Vehicle(routeDemand*2);		//vehicle covers the demand
		
		v.setRoute(route);							//route, cap/demand == ok, ok
		assertTrue(v.isValid());
		
		when(route.isValid()).thenReturn(false);	//route, cap/demand == no, ok
		assertFalse(v.isValid());
		
		
		v = new Vehicle(routeDemand/2);				//vehicle NOT covers the demand
		
		v.setRoute(route);							//route, cap/demand == ok, no
		assertFalse(v.isValid());
		
		when(route.isValid()).thenReturn(false);	//route, cap/demand == no, no
		assertFalse(v.isValid());
	}
	
	@Test
	public void costTest() {
		
		Vehicle v = new Vehicle(routeDemand);
		v.setRoute(route);
		
		assertEquals(routeCost, v.cost(costMatrix), 0);		//vehicle cost is equals to route cost
		
		verify(route).cost(costMatrix);						//verify the call of route#cost()
	}

}
