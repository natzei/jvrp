package it.unica.informatica.ro.vrp;

import it.unica.informatica.ro.vrp.console.ConsoleParameters;
import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.Solution;
import it.unica.informatica.ro.vrp.solver.ProblemSolver;
import it.unica.informatica.ro.vrp.solver.initializers.BasicInitializer;
import it.unica.informatica.ro.vrp.solver.initializers.Initializer;
import it.unica.informatica.ro.vrp.solver.strategies.SimpleStrategy;
import it.unica.informatica.ro.vrp.solver.strategies.Strategy;
import it.unica.informatica.ro.vrp.solver.strategies.optimizers.inter_route.RelocateOptimizer.RelocateOption;
import it.unica.informatica.ro.vrp.solver.strategies.optimizers.intra_route.TwoOptOptimizer.TwoOptOption;
import it.unica.informatica.ro.vrp.utils.loaders.ChristofidesLoader;
import it.unica.informatica.ro.vrp.utils.loaders.Loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.beust.jcommander.JCommander;


public class Main {
	
	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(logo);
		
		/*
		 * Get args parameters
		 */
		ConsoleParameters parameters = new ConsoleParameters();
		JCommander commander = new JCommander(parameters);
		commander.parse(args);
		commander.setProgramName("jvrp");
		
		if (parameters.isHelp()) {
			commander.usage();
			System.exit(0);
		}
		
		/*
		 * Set logging level. 
		 * Level.DEBUG adds significant overhead compared to Level.INFO.
		 */
		setLoggingLevel(Level.valueOf(parameters.getLogLevel()));
		

		if (parameters.getFiles().isEmpty()) {
			List<URL> instances = menu();
			for (URL i : instances) {
				solve(i);
			}
		}
		else {
			for (String file : parameters.getFiles()) {
				solve(new File(file).toURI().toURL());
			}
		}
		System.out.println("exit");
	}
	
	
	
	private static List<URL> menu() {
		
		System.out.println("\n Select an instace:");
		System.out.println("[0] all instances");
		System.out.println("[1] vrpnc1.txt");
		System.out.println("[2] vrpnc2.txt");
		System.out.println("[3] vrpnc3.txt");
		System.out.println("[4] vrpnc4.txt");
		System.out.println("[5] vrpnc5.txt");
		System.out.println("[6] vrpnc11.txt");
		System.out.println("[7] vrpnc12.txt");
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		Integer input = 0;
		try {
			input = Integer.valueOf(scanner.next("[0-7]"));
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid input");
			System.exit(0);
		}
		finally {
			scanner.close();
		}
		
		List<URL> instances = new ArrayList<>();
		
		switch (input) {
			case 0:
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc1.txt"));
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc2.txt"));
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc3.txt"));
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc4.txt"));
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc5.txt"));
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc11.txt"));
				instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc12.txt"));
				break;
			case 1: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc1.txt"));
				break;
			case 2: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc2.txt"));
				break;
			case 3: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc3.txt"));
				break;
			case 4: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc4.txt"));
				break;
			case 5: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc5.txt"));
				break;
			case 6: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc11.txt"));
				break;
			case 7: instances.add(Main.class.getClassLoader().getResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc12.txt"));
				break;
		}
		
		return instances;
	}
	
	
	
	private static void solve(URL instanceFilename) throws IOException {
		
		log.info("*************************************************************************************************************************");
		log.info("*");
		log.info("* Solving '{}' ", instanceFilename);
		log.info("*");
		log.info("*************************************************************************************************************************");
		
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
			problem, 
			TwoOptOption.BEST_IMPROVEMENT,
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
		log.info("total cost: {}", sol.cost(problem.getCostMatrix()));
		log.info("time elapsed: {}ms", t1-t0);
		
		System.out.println();
	}
	
	private static void setLoggingLevel(Level level) {
	    ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    root.setLevel(level);
	}

	
	
	private static final String logo = 
			"                                                                            \n" + 
			"                           _/                                               \n" + 
			"                          _/  _/      _/  _/  _/_/  _/_/_/                  \n" + 
			"                         _/  _/      _/  _/_/      _/    _/                 \n" + 
			"                  _/    _/    _/  _/    _/        _/    _/                  \n" + 
			"                   _/_/        _/      _/        _/_/_/                     \n" + 
			"                                                _/                          \n" + 
			"                                               _/                           \n" +
			" Java solver for the Vehicle Routing Problem                                \n"
			;
}
