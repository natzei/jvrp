package it.unica.informatica.ro.vrp.opt;

import static org.junit.Assert.assertTrue;
import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.problem.model.Route;
import it.unica.informatica.ro.vrp.solver.opt.IntraRouteOptimizer;
import it.unica.informatica.ro.vrp.solver.opt.IntraRouteOptimizer.TwoOptOption;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IntraRouteOptimizerTest {

	private static final Logger log = LoggerFactory.getLogger(IntraRouteOptimizerTest.class);
	
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
	public void bestTest() {
		Depot d0 = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(5);
		Customer c3 = new Customer(12);
		Customer c4 = new Customer(7);
		
		
		CostMatrix costMatrix = new CostMatrix(5);
		costMatrix.setCost( d0.getId(),  c1.getId(), 10);
		costMatrix.setCost( d0.getId(),  c2.getId(), 10);
		costMatrix.setCost( d0.getId(),  c3.getId(), 10);
		costMatrix.setCost( d0.getId(),  c4.getId(), 10);
		costMatrix.setCost( c1.getId(),  c2.getId(), 20);
		costMatrix.setCost( c1.getId(),  c3.getId(), 50);
		costMatrix.setCost( c1.getId(),  c4.getId(), 20);
		costMatrix.setCost( c2.getId(),  c3.getId(), 20);
		costMatrix.setCost( c2.getId(),  c4.getId(), 50);
		costMatrix.setCost( c3.getId(),  c4.getId(), 20);
		
		IntraRouteOptimizer opt = new IntraRouteOptimizer(costMatrix);
		
		Route r = new Route(d0,c1,c3,c2,c4,d0);
		
		assertTrue( r.isValid() );
		
		long t0 = System.currentTimeMillis();
		log.debug("route0: {} - cost0: {}", r, r.cost(costMatrix));
		opt.twoOpt(r, TwoOptOption.BEST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("route1: {} - cost1: {}", r, r.cost(costMatrix));
		
		t0 = System.currentTimeMillis();
		log.debug("route0: {} - cost0: {}", r, r.cost(costMatrix));
		opt.twoOpt(r, TwoOptOption.FIRST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("route1: {} - cost1: {}", r, r.cost(costMatrix));
		
		// route with no improvements
		r = new Route(d0,c1,c2,c3,c4,d0);
		
		t0 = System.currentTimeMillis();
		log.debug("route0: {} - cost0: {}", r, r.cost(costMatrix));
		opt.twoOpt(r, TwoOptOption.BEST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("route1: {} - cost1: {}", r, r.cost(costMatrix));
		
		
		t0 = System.currentTimeMillis();
		log.debug("route0: {} - cost0: {}", r, r.cost(costMatrix));
		opt.twoOpt(r, TwoOptOption.FIRST_IMPROVEMENT);
		log.debug("execution time: {}",System.currentTimeMillis()-t0);
		log.debug("route1: {} - cost1: {}", r, r.cost(costMatrix));
		
	}

}
