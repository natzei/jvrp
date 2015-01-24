package it.unica.informatica.ro.vrp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.solver.initializers.BasicInitializer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SolutionTest {
	
	private static final Logger log = LoggerFactory.getLogger(SolutionTest.class);
	
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
	public void basicSolutionTest() {
		
		Depot depot = new Depot();
		Customer c1 = new Customer(10);
		Customer c2 = new Customer(5);
		Customer c3 = new Customer(12);
		Customer c4 = new Customer(7);
		
		CostMatrix costMatrix = new CostMatrix(5);
		costMatrix.setCost( depot.getId(),  c1.getId(), 10);
		costMatrix.setCost( depot.getId(),  c2.getId(), 10);
		costMatrix.setCost( depot.getId(),  c3.getId(), 10);
		costMatrix.setCost( depot.getId(),  c4.getId(), 10);
		costMatrix.setCost( c1.getId(),  c2.getId(), 20);
		costMatrix.setCost( c1.getId(),  c3.getId(), 50);
		costMatrix.setCost( c1.getId(),  c4.getId(), 20);
		costMatrix.setCost( c2.getId(),  c3.getId(), 20);
		costMatrix.setCost( c2.getId(),  c4.getId(), 50);
		costMatrix.setCost( c3.getId(),  c4.getId(), 20);
		
		Problem problem = new Problem();
		problem.setDepot( depot );
		problem.getCustomers().add(c1);
		problem.getCustomers().add(c2);
		problem.getCustomers().add(c3);
		problem.getCustomers().add(c4);
		problem.setCostMatrix(costMatrix);
		problem.setVehicleCapacity(1000);
		
		Solution solution = new BasicInitializer().initialSolution(problem);
		
		assertEquals( 4, solution.getVehicles().size() );
		assertTrue( solution.isValid() );
		
		log.debug("SOLUTION");
		log.debug(solution.toString());
		log.debug("cost:"+solution.cost(costMatrix));
	}

}
