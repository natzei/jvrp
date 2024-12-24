# Jvrp
Java solver for capacitated vehicle routing problem (vrp). This is an academic project that implements two simple algorithms, 2-opt and relocate respectively for intra-route and 
inter-route improvements.

## Installation notes

##### Requirements
- [maven](http://maven.apache.org/)
- [git](http://git-scm.com/)
- [jdk 17+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

##### Download the project
```bash
git clone https://github.com/atzeinicola/jvrp.git
cd jvrp
```
##### Create executable jar
```bash
#create jvrp-0.0.1.jar
mvn clean package assembly:single

#display usage
java -jar jvrp-0.0.2.jar --help

#interactive mode (Christofides-Mingozzi-Toth instances)
java -jar jvrp-0.0.2.jar

#resolve your instances (with the same file format of Christofides-Mingozzi-Toth instances)
java -jar jvrp-0.0.2.jar <file_1> ... <file_n>
```
[See below](https://github.com/atzeinicola/jvrp#instances) for instances' format.

##### Intall on local repository
You can use jvrp as java library installing it on maven's local repository (`mvn clean install`) and putting in your pom.xml
```xml
<dependency>
	<groupId>it.unica.informatica.ro</groupId>
	<artifactId>jvrp</artifactId>
	<version>0.0.2</version>
</dependency>
```
## Example
```java
import ...

public class Main {

  public static void main(String[] args) throws IOException {
    String instanceFilename = 
    ClassLoader.getSystemResource("vrp/Christofides-Mingozzi-Toth_1979/vrpnc1.txt").getFile();
    
    /*
     * Problem loading
     */
    Loader loader = ChristofidesLoader.getInstance();
    Problem problem = loader.load(instanceFilename);
    
    /*
     * Define 
     * - Initializer: to obtain an initial solution
     * - Strategy: to minimize the solution at each step
     */
    Initializer initializer = new BasicInitializer();
    Strategy strategy = new SimpleStrategy(
        problem, 
        TwoOptOption.FIRST_IMPROVEMENT,
        RelocateOption.BEST_IMPROVEMENT,
        false
    );
    
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
    Solution sol = solver.solve(problem);
    
    /*
     * Output
     */
    System.out.println("instance: "+instanceFilename);
    System.out.println("number of customers: "+problem.getCustomers().size());
    System.out.println("vehicle capacity: "+problem.getVehicleCapacity());
    System.out.println("solution: \n"+sol.toString(problem.getCostMatrix()));
    System.out.println("cost: "+sol.cost(problem.getCostMatrix()));
  } 
}
```
Please note that `Loader#load()` method return the `Problem` class. If you prefer, you can manually create a problem instance and validate it with `Problem#isValid()` method.

Expected output:
```
instance: (project-dir)/target/classes/vrp/Christofides-Mingozzi-Toth_1979/vrpnc1.txt
number of customers: 50
vehicle capacity: 160.0
solution: 
[0, 12, 5, 49, 9, 38, 46, 0] (demand=99.0) {cost=55.04878606977536}
[0, 1, 28, 31, 26, 8, 48, 27, 0] (demand=94.0) {cost=79.45534162757446}
[0, 11, 2, 20, 35, 36, 3, 22, 32, 0] (demand=136.0) {cost=93.86146681253084}
[0, 4, 17, 15, 45, 33, 39, 10, 30, 34, 50, 21, 29, 16, 0] (demand=158.0) {cost=142.4532549575219}
[0, 37, 44, 42, 19, 40, 41, 13, 25, 14, 0] (demand=153.0) {cost=114.04452792191931}
[0, 23, 7, 43, 24, 6, 0] (demand=71.0) {cost=77.84653205966423}
[0, 18, 47, 0] (demand=66.0) {cost=32.261061940588554}
cost: 594.9709713895746
```

## `ProblemSolver`, `Initializer` and `Strategy`
The `Initializer` and `Strategy` interfaces are uses by `ProblemSolver`, that solve the problem heuristically.

An `Initializer` have the method `initialSolution(Problem)` that return a valid `Solution` to the given problem. This is the first solution to minimize.

A `Strategy` have the method `minimize(Solution)` that minimize the given solution and is called repetitively by the solver as long as no improvements occurs between two iterations.

#### `BasicInitializer` and `SimpleStrategy`
The `BasicInitializer` implements `Initializer` and returns a valid solution in which each customer is served by one vehicle. This solution is always valid, because the vehicle's capacity is always greater or equal to customer's demand, otherwise the problem is invalid.

The `SimpleStrategy` implements `Strategy` and minimize the solution appling 2-opt improvements on each route and trying to move one customer to another route.

`SimpleStrategy` can be instantiated using:
- `TwoOptOption` (intra-route improvements)
  - `TwoOptOption.FIRST_IMPROVEMENT`: perform 2-opt swap on the first pair that produce an improvement;
  - `TwoOptOption.BEST_IMPROVEMENT`: calculate the gain obtained swapping on each possible pair and perform the swap action on the pair with the best gain;
- `RelocateOption` (inter-route improvements)
  - `RelocateOption.FIRST_IMPROVEMENT`: relocate a customer on the first route that can receive it and that produce an improvement;
  - `RelocateOption.BEST_IMPROVEMENT`: calculate the gain obtained relocating all customers in all possible route and in all possible route's positions, then moved the customer with the best gain;
- `boolean shuffle`: if true, the route list (i.e. a valid solution) is shuffled before appling the preceding algorithms. This parameter make sense when you use `RelocateOption.FIRST_IMPROVEMENT`, otherwise the `shuffle` option does not change the program output.

## Instances
Jvrp can load problem's instances directly from file. Currently is only supported **_Christofides-Mingozzi-Toth_** (1979) instance file format:
```
number_of_customers vehicle_capacity maximum_route_time drop_time
depot_x-coordinate depot_y-coordinate
x-coordinate y-coordinate quantity    #customer 1
x-coordinate y-coordinate quantity    #customer 2
...
x-coordinate y-coordinate quantity    #customer N
```
**Note**: `maximum_route_time` and `drop_time` are ignored.
All instances can be found into `src/main/resources/vrp/Christofides-Mingozzi-Toth_1979` and the related loader is `ChristofidesLoader`.

#### Other loaders
You can implement how many loaders you want. You must extend the abstract class `Loader` and implement the abstract method `load(Reader input)`, that return a valid `Problem`. The `Loader` class contains only overloaded `load()` methods for different input type, that will call your implementation.
