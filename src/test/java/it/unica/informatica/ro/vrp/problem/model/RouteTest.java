package it.unica.informatica.ro.vrp.problem.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unica.informatica.ro.vrp.problem.CostMatrix;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RouteTest {

	private static CostMatrix costMatrix;
	private static Depot d0;
	private static Customer c1;
	private static Customer c2;
	private static Customer c3;
	private static Customer c4;
	private static Customer c5;
	
	private static float customerDemand = 100;
	private static double cost = 10;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		costMatrix = mock(CostMatrix.class);
		d0 = mock(Depot.class);
		c1 = mock(Customer.class);
		c2 = mock(Customer.class);
		c3 = mock(Customer.class);
		c4 = mock(Customer.class);
		c5 = mock(Customer.class);
		
		when( costMatrix.getCost( anyInt(), anyInt()) ).thenReturn(cost);
		when( d0.isDepot() ).thenReturn(true);
		when( d0.getId() ).thenReturn(0);
		when( c1.getId() ).thenReturn(1);
		when( c2.getId() ).thenReturn(2);
		when( c3.getId() ).thenReturn(3);
		when( c4.getId() ).thenReturn(4);
		when( c5.getId() ).thenReturn(5);
		when( c1.getDemand() ).thenReturn(customerDemand);
		when( c2.getDemand() ).thenReturn(customerDemand);
		when( c3.getDemand() ).thenReturn(customerDemand);
		when( c4.getDemand() ).thenReturn(customerDemand);
		when( c5.getDemand() ).thenReturn(customerDemand);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {
		Customer.CUSTOMER_ID_COUNT=1;
	}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void validityTest() {
		
		Route route = new Route();
		
		assertFalse( route.isValid() );
		assertTrue( route.isEmpty() );
		assertEquals( 0, route.size() );
		
		route.add(c1,c2,c3);
		assertFalse( route.isValid() );
		assertFalse( route.isEmpty() );
		assertEquals( 3, route.size() );
		
		route = new Route(d0,c1,c2,c1,c3,d0);		//repeated node c1
		assertFalse( route.isValid() );
		
		route = new Route(d0,c1,c2,c1,c3);			//repeated node c1 and not final depot
		assertFalse( route.isValid() );
		
		route = new Route(d0,d0);					//no customers
		assertFalse( route.isValid() );
		
		route = new Route(d0,c1,c2,d0,c3,d0);		//depot between customers
		assertFalse( route.isValid() );
	}
	
	@Test
	public void swapTest() {

		Route route = new Route(d0,c1,c2,c3,c4,c5,d0);
		
		assertTrue(route.isValid());
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		route.swap(2, 5);
		this.checkRoute(route, new int[] {0,1,5,4,3,2,0});
				
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		route.swap(2, 4);					// ok
		this.checkRoute(route, new int[] {0,1,4,3,2,5,0});
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		route.swap(1, 5);
		this.checkRoute(route, new int[] {0,5,4,3,2,1,0});
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		route.swap(1, 5);
		route.swap(1, 5);								// revert swapped nodes
		this.checkRoute(route, new int[] {0,1,2,3,4,5,0});
	}
	
	@Test
	public void illegalSwapTest() {
		
		Route route = new Route(d0,c1,c2,c3,c4,c5,d0);
		
		assertTrue(route.isValid());
		
		try {
			route.swap(0, route.size()-1);	// both depots
			fail();
		}
		catch (IllegalArgumentException e) {}

		try {
			route.swap(0, 2);				// one is depot
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			route.swap(2, route.size()-1);	// one is depot
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			route.swap(2, 2);				// same node
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			route.swap(3, 2);				// invalid indexes
			fail();
		}
		catch (IllegalArgumentException e) {}
		
	}

	@Test
	public void costTest() {
		
		Route r = new Route();
		assertEquals(0, r.cost(costMatrix), 0);
		
		r = new Route(d0,c1,c2);
		assertEquals(cost*2, r.cost(costMatrix), 0);
		
		r = new Route(d0,c1,c2);
		assertEquals(cost*2, r.cost(costMatrix), 0);
		
		r = new Route(d0,c1,c2,d0);
		assertEquals(cost*3, r.cost(costMatrix), 0);
	}
	
	@Test
	public void demandTest() {
		
		Route r = new Route();
		assertEquals(0, r.demand(), 0);
		
		r.add(d0,c1,d0);
		assertEquals(customerDemand, r.demand(), 0);
		
		r = new Route(d0,c1,c2);
		assertEquals(customerDemand*2, r.demand(), 0);
		
		r = new Route(d0,c1,c2,d0);
		assertEquals(customerDemand*2, r.demand(), 0);
	}
	
	@Test
	public void moveTest() {

		Route route = new Route(d0,c1,c2,c3,d0);
		
		route.moveNode(1, 3);								// move 1 between 3 and 4
		this.checkRoute(route, new int[] {0,2,3,1,0});
		
		route = new Route(d0,c1,c2,c3,c4,d0);
		route.moveNode(4, 1);								// move 4 between 1 and 2
		this.checkRoute(route, new int[] {0,1,4,2,3,0});
		
		route = new Route(d0,c1,c2,c3,c4,d0);
		route.moveNode(2, 1);								// move 2 between 1 and 2
		this.checkRoute(route, new int[] {0,1,2,3,4,0});
	}
	
	@Test
	public void illegalMoveTest() {
		
		Route route = new Route(d0,c1,c2,c3,d0);
		
		try {
			route.moveNode(-1, 3);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
		try {
			route.moveNode(0, 5);
			fail();
		}
		catch (IllegalArgumentException e) {}
		
	}
	
	
	private void checkRoute(Route route, int[] expected) {
		assertTrue( route.isValid() );
		for (int i=0; i<route.size(); i++) {
			assertEquals(expected[i], route.get(i).getId());
		}
	}
}
