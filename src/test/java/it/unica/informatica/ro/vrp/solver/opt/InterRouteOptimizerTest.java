package it.unica.informatica.ro.vrp.solver.opt;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.problem.model.Route;
import it.unica.informatica.ro.vrp.problem.model.Vehicle;
import it.unica.informatica.ro.vrp.solver.strategies.optimizers.intra_route.RelocateOptimizer;
import it.unica.informatica.ro.vrp.solver.strategies.optimizers.intra_route.RelocateOptimizer.RelocateOption;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InterRouteOptimizerTest {
	
	private static final Logger log = LoggerFactory.getLogger(InterRouteOptimizerTest.class);
	
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
	public void test() {
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(5);
		Customer c3 = new Customer(12);
		Customer c4 = new Customer(7);
		
		
		CostMatrix costMatrix = new CostMatrix(5);
		costMatrix.setCost( d0.getId(),  c1.getId(), 10.);
		costMatrix.setCost( d0.getId(),  c2.getId(), 10.);
		costMatrix.setCost( d0.getId(),  c3.getId(), 10.);
		costMatrix.setCost( d0.getId(),  c4.getId(), 10.);
		costMatrix.setCost( c1.getId(),  c2.getId(), 20.);
		costMatrix.setCost( c1.getId(),  c3.getId(), 50.);
		costMatrix.setCost( c1.getId(),  c4.getId(), 20.);
		costMatrix.setCost( c2.getId(),  c3.getId(), 20.);
		costMatrix.setCost( c2.getId(),  c4.getId(), 50.);
		costMatrix.setCost( c3.getId(),  c4.getId(), 20.);
		
		RelocateOptimizer opt = new RelocateOptimizer(costMatrix);

		Vehicle a = new Vehicle(1000);
		Vehicle b = new Vehicle(1000);
		
		a.setRoute(new Route(d0,c1,c3,c2,c4,d0));
		
		assertTrue( a.isValid() );
		
		log.debug("vehicle A: {} - cost0: {}", a, a.cost(costMatrix));
		
		long t0 = System.currentTimeMillis();
		boolean improved = opt.relocate(a, a, RelocateOption.BEST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("improved: {}", improved);
		log.debug("vehicle A: {} - cost1: {}", a, a.cost(costMatrix));
		
		a.setRoute(new Route(d0,c1,c3,c2,c4,d0));
		t0 = System.currentTimeMillis();
		improved = opt.relocate(a, a, RelocateOption.FIRST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("improved: {}", improved);
		log.debug("vehicle A: {} - cost1: {}", a, a.cost(costMatrix));
		
		
		a.setRoute(new Route(d0,c1,d0));
		b.setRoute(new Route(d0,c2,d0));
		
		costMatrix.setCost( d0.getId(),  c1.getId(), 15);
		costMatrix.setCost( d0.getId(),  c2.getId(), 35);
		costMatrix.setCost( c1.getId(),  c2.getId(), 5);
		
		log.debug("vehicle A: {} - cost1: {}", a, a.cost(costMatrix));
		log.debug("vehicle B: {} - cost1: {}", b, b.cost(costMatrix));
		t0 = System.currentTimeMillis();
		improved = opt.relocate(a, b, RelocateOption.FIRST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("improved: {}", improved);
		log.debug("vehicle A: {} - cost1: {}", a, a.cost(costMatrix));
		log.debug("vehicle B: {} - cost1: {}", b, b.cost(costMatrix));
		
		a.setRoute(new Route(d0,c1,d0));
		b.setRoute(new Route(d0,c2,d0));
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(a);
		vehicles.add(b);
		Solution solution = new Solution();
		solution.setVehicles(vehicles);
		log.debug("vehicle A: {} - cost1: {}", a, a.cost(costMatrix));
		log.debug("vehicle B: {} - cost1: {}", b, b.cost(costMatrix));
		t0 = System.currentTimeMillis();
		improved = opt.relocate(solution, RelocateOption.BEST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("improved: {}", improved);
		log.debug("vehicle A: {} - cost1: {}", a, a.cost(costMatrix));
		log.debug("vehicle B: {} - cost1: {}", b, b.cost(costMatrix));
		
	}

}
