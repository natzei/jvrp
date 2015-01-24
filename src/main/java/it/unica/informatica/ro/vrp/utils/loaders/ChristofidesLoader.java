package it.unica.informatica.ro.vrp.utils.loaders;

import it.unica.informatica.ro.vrp.problem.Problem;
import it.unica.informatica.ro.vrp.problem.model.Customer;
import it.unica.informatica.ro.vrp.problem.model.Depot;
import it.unica.informatica.ro.vrp.utils.Utils;
import it.unica.informatica.ro.vrp.utils.collections.PairList;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class loader for instances of Christofides, Mingozzi and Toth (1979).
 * <br>
 * The format of these data files is:
 * <>
 * <li>number of customers, vehicle capacity, maximum route time, drop time</li>
 * <li>depot x-coordinate, depot y-coordinate</li>
 * <li>for each customer in turn: x-coordinate, y-coordinate, quantity</li>
 * <ul>
 * 
 * @author nicola
 *
 */
public class ChristofidesLoader extends Loader{

	private static Loader instance = new ChristofidesLoader();
	
	private static Logger log = LoggerFactory.getLogger(ChristofidesLoader.class);
	
	private ChristofidesLoader() {}
	
	
	public static Loader getInstance() {
		return instance;
	}
		
	@Override
	public Problem load(Reader input) throws IOException {
		
		Problem problem = new Problem();
		
		
		List<String> inputLines = IOUtils.readLines(input);
		
		PairList<Integer, Integer> coordinates = new PairList<>();
		
		/*
		 * READ input
		 */
		int i=0, x,y,d;
		String[] splittedLine;
		
		for (String line : inputLines) {
			
			splittedLine = line.trim().split(" ");

			log.debug("line {}: {}", i, Arrays.toString(splittedLine));

			if (i==0) {						// first line
				problem.setVehicleCapacity(Integer.valueOf(splittedLine[1]));
			}
			else if (i==1) {							// second line: depot coordinates
				x = Integer.valueOf(splittedLine[0]);	// x-coordinates
				y = Integer.valueOf(splittedLine[1]);	// y-coordinates
				
				coordinates.add(x, y);
				problem.setDepot( new Depot() );
			}
			else {										// other lines
				x = Integer.valueOf(splittedLine[0]);	// x-coordinates
				y = Integer.valueOf(splittedLine[1]);	// y-coordinates
				d = Integer.valueOf(splittedLine[2]);	// demand
				
				coordinates.add(x, y);		// customer coordinates
				problem.getCustomers().add( new Customer(d) );
			}
			
			i++;
		}
		
		/*
		 * calculate cost matrix
		 */
		problem.setCostMatrix( Utils.calculateCostMatrix(coordinates) );
		
		return problem;
	}	
	
	
}
