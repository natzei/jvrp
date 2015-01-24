package it.unica.informatica.ro.vrp.model;

import static org.junit.Assert.*;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.problem.model.Route;
import it.unica.informatica.ro.vrp.problem.model.Vehicle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VehicleTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void test() {
		
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(20);
		Customer c3 = new Customer(30);
		Customer c4 = new Customer(40);
		Customer c5 = new Customer(50);
		
		Vehicle v = new Vehicle(100);
		v.setRoute(new Route());
		assertFalse(v.isValid());
		
		v.setRoute(new Route(d0,c1,d0));
		assertTrue(v.isValid());
		
		v.setRoute(new Route(d0,c5,c4,c1,d0));
		assertTrue(v.isValid());
		
		v.setRoute(new Route(d0,c1,c2,c3,c4,c5,d0));
		assertFalse(v.isValid());
	}

}
