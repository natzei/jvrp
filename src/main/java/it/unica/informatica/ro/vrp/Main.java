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


public class Main {
	
	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws IOException {
		
		String instanceFilename = Main.class.getClassLoader().getResource("vrp/vrpnc1.txt").getFile();
		Loader loader = ChristofidesLoader.getInstance();
		Problem problem = loader.load(instanceFilename);

		log.info("Loaded problem {}", instanceFilename);
		log.info("number of customers: {}", problem.getCustomers().size());
		log.info("capacity: {}", problem.getVehicleCapacity());
		log.info("problem is valid? {}", problem.isValid());
		
		Initializer initializer = new BasicInitializer();
		Strategy strategy = new SimpleStrategy(
			problem.getCostMatrix(), 
			TwoOptOption.BEST_IMPROVEMENT,
			RelocateOption.BEST_IMPROVEMENT
		);
		
		ProblemSolver solver = new ProblemSolver(
			initializer,
			strategy
		);
		
		Solution sol = solver.solve(problem);
		log.info("found solution \n{}", sol.toString(problem.getCostMatrix()));
	}

}
