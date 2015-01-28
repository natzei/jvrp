package it.unica.informatica.ro.vrp;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.solver.ProblemSolver;
import it.unica.informatica.ro.vrp.solver.initializers.BasicInitializer;
import it.unica.informatica.ro.vrp.solver.initializers.Initializer;
import it.unica.informatica.ro.vrp.solver.opt.InterRouteOptimizer.RelocateOption;
import it.unica.informatica.ro.vrp.solver.opt.IntraRouteOptimizer.TwoOptOption;
import it.unica.informatica.ro.vrp.solver.strategies.SimpleStrategy;
import it.unica.informatica.ro.vrp.solver.strategies.Strategy;
import it.unica.informatica.ro.vrp.utils.loaders.ChristofidesLoader;
import it.unica.informatica.ro.vrp.utils.loaders.Loader;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;


public class Main {
	
	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws IOException {
		
		/*
		 * Set logging level. 
		 * Level.DEBUG adds significant overhead compared to Level.INFO.
		 */
		setLoggingLevel(Level.INFO);
		
		String[] instances = new String[] {
				Main.class.getClassLoader().getResource("vrp/vrpnc1.txt").getFile(),
				Main.class.getClassLoader().getResource("vrp/vrpnc2.txt").getFile(),
				Main.class.getClassLoader().getResource("vrp/vrpnc3.txt").getFile(),
				Main.class.getClassLoader().getResource("vrp/vrpnc4.txt").getFile(),
				Main.class.getClassLoader().getResource("vrp/vrpnc5.txt").getFile(),
				Main.class.getClassLoader().getResource("vrp/vrpnc11.txt").getFile(),
				Main.class.getClassLoader().getResource("vrp/vrpnc12.txt").getFile(),
		};
		
		for (String i : instances) {
			solve(i);
		}
		
	}
	
	
	
	private static void solve(String instanceFilename) throws IOException {
		
		log.info("*****************************************************************************************");
		log.info("*****************************************************************************************");
		
		/*
		 * Problem loading
		 */
		Loader loader = ChristofidesLoader.getInstance();
		Problem problem = loader.load(instanceFilename);

		log.info("Loaded problem '{}'", instanceFilename);
		log.info("number of customers: {}", problem.getCustomers().size());
		log.info("capacity: {}", problem.getVehicleCapacity());
		log.info("problem is valid? {}", problem.isValid());
		
		/*
		 * Define 
		 * - Initializer: to obtain an initial solution
		 * - Strategy: to minimize the solution at each step
		 */
		Initializer initializer = new BasicInitializer();
		Strategy strategy = new SimpleStrategy(
			problem.getCostMatrix(), 
			TwoOptOption.FIRST_IMPROVEMENT,
			RelocateOption.BEST_IMPROVEMENT,
			false
		);
		
		log.info("strategy: {}", strategy.toString());
		
		/*
		 * Preparing solver
		 */
		ProblemSolver solver = new ProblemSolver(
			initializer,
			strategy
		);
		
		/*
		 * Solve the problem
		 */
		long t0 = System.currentTimeMillis();
		Solution sol = solver.solve(problem);
		long t1 = System.currentTimeMillis();
		
		log.info("solution found \n{}", sol.toString(problem.getCostMatrix()));
		log.info("cost: {}", sol.cost(problem.getCostMatrix()));
		log.info("time elapsed: {}", t1-t0);
		
	}
	
	private static void setLoggingLevel(Level level) {
	    ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    root.setLevel(level);
	}

}
