package it.unica.informatica.ro.vrp.utils.loaders;

import static org.junit.Assert.*;
import it.unica.informatica.ro.vrp.problem.Problem;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ChristofidesLoaderTest {

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
		
		ClassLoader cl = this.getClass().getClassLoader();
		String instanceFilename = cl.getResource("vrp/vrpnc1.txt").getFile();
		
		Loader loader = ChristofidesLoader.getInstance();
		
		Problem problem = null;
		
		try {
			problem = loader.load(instanceFilename);
		}
		catch (IOException e) {
			fail();
		}
		
		assertNotNull(problem);
		
		assertEquals( 50, problem.getCustomers().size() );
		assertEquals( problem.getCostMatrix().size(), 1+problem.getCustomers().size() );
		
	}

}
