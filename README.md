# jvrp
Java solver for capacitated vehicle routing problem (vrp). This is an academic project that implements two simple algorithms, 2opt and relocate respectively for intra-route and 
inter-route improvements.

##Instances
Jvrp can load problem's instances directly from file. At now is only supported **_Christofides-Mingozzi-Toth_** (1979) instance file format:
```
number_of_customers vehicle_capacity maximum_route_time drop_time
depot_x-coordinate depot_y-coordinate
x-coordinate y-coordinate quantity    #customer 1
x-coordinate y-coordinate quantity    #customer 2
...
x-coordinate y-coordinate quantity    #customer N
```
All instances can be found into `src/main/resources/vrp/Christofides-Mingozzi-Toth_1979`

**Note**: `maximum_route_time` and `drop_time` are ignored.
