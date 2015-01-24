package it.unica.informatica.ro.vrp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.problem.model.Route;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class RouteTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

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
		
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(20);
		Customer c3 = new Customer(30);
		
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
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(20);
		Customer c3 = new Customer(30);
		Customer c4 = new Customer(10);
		Customer c5 = new Customer(10);
		
		Route route = new Route();
		int[] expected;
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		
		assertTrue(route.isValid());
		
		assertFalse( route.swap(0, route.size()-1) );	// both depots
		assertFalse( route.swap(0, 2) );				// one is depot
		assertFalse( route.swap(2, route.size()-1) );	// one is depot
		assertFalse( route.swap(2, 2) );				// same node
		assertFalse( route.swap(3, 2) );				// invalid indexes
		
		
		
		
		assertTrue( route.swap(2, 5) );			// ok
		assertTrue( route.isValid() );			// is a valid route
		
		
				
		expected = new int[] {0,1,5,4,3,2,0};
		for (int i=0; i<route.size(); i++) {
			assertEquals(expected[i], route.get(i).getId());
		}
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		assertTrue( route.swap(2, 4) );					// ok
		expected = new int[] {0,1,4,3,2,5,0};
		for (int i=0; i<route.size(); i++) {
			assertEquals(expected[i], route.get(i).getId());
		}
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		assertTrue( route.swap(1, 5) );					// ok
		expected = new int[] {0,5,4,3,2,1,0};
		for (int i=0; i<route.size(); i++) {
			assertEquals(expected[i], route.get(i).getId());
		}
		
		route = new Route(d0,c1,c2,c3,c4,c5,d0);		// valid route
		assertTrue( route.swap(1, 5) );				// ok
		assertTrue( route.swap(1, 5) );				// revert swapped nodes
		expected = new int[] {0,1,2,3,4,5,0};
		for (int i=0; i<route.size(); i++) {
			assertEquals(expected[i], route.get(i).getId());
		}
	}

	@Test
	public void costTest() {
		
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(5);
		
		CostMatrix costMatrix = mock(CostMatrix.class);
		when( costMatrix.getCost(0, 1) ).thenReturn(10.);
		when( costMatrix.getCost(0, 2) ).thenReturn(10.);
		when( costMatrix.getCost(1, 0) ).thenReturn(10.);
		when( costMatrix.getCost(1, 2) ).thenReturn(50.);
		when( costMatrix.getCost(2, 1) ).thenReturn(50.);
		when( costMatrix.getCost(2, 0) ).thenReturn(10.);
		
		Route r = new Route();
		
		assertEquals(0, r.cost(costMatrix), 0);
		
		r.add(d0,c1,d0);
		assertEquals(10+10, r.cost(costMatrix), 0);
		
		r = new Route(d0,c1,c2);
		assertEquals(10+50, r.cost(costMatrix), 0);
		
		r = new Route(d0,c1,c2,d0);
		assertEquals(10+50+10, r.cost(costMatrix), 0);
	}
	
	
	@Test
	public void demandTest() {
		
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(5);
		
		Route r = new Route();
		assertEquals(0, r.demand(), 0);
		
		r.add(d0,c1,d0);
		assertEquals(10, r.demand(), 0);
		
		r = new Route(d0,c1,c2);
		assertEquals(15, r.demand(), 0);
		
		r = new Route(d0,c1,c2,d0);
		assertEquals(15, r.demand(), 0);
	}
}
