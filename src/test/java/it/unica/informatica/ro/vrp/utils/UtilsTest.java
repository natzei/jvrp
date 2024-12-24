package it.unica.informatica.ro.vrp.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import it.unica.informatica.ro.vrp.problem.CostMatrix;
import it.unica.informatica.ro.vrp.utils.collections.Pair;
import it.unica.informatica.ro.vrp.utils.collections.PairList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@SuppressWarnings("unchecked")
	@Test
	public void euclideanDistanceTest() {
		
		Pair<Integer,Integer> a = mock(Pair.class);
		when(a.getFirst()).thenReturn(0);
		when(a.getSecond()).thenReturn(0);
		
		Pair<Integer,Integer> b = mock(Pair.class);
		when(b.getFirst()).thenReturn(0);
		when(b.getSecond()).thenReturn(0);
		
		//1
		assertEquals(0, Utils.euclideanDistance(a, b), 0);
		
		//2
		when(b.getFirst()).thenReturn(3);
		when(b.getSecond()).thenReturn(4);
		assertEquals(5, Utils.euclideanDistance(a, b), 0);
		
		//3
		when(b.getFirst()).thenReturn(-3);
		when(b.getSecond()).thenReturn(-4);
		assertEquals(5, Utils.euclideanDistance(a, b), 0);
		
		//4
		when(b.getFirst()).thenReturn(-3);
		when(b.getSecond()).thenReturn(4);
		assertEquals(5, Utils.euclideanDistance(a, b), 0);
		
		//5
		when(b.getFirst()).thenReturn(3);
		when(b.getSecond()).thenReturn(-4);
		assertEquals(5, Utils.euclideanDistance(a, b), 0);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void calculateCostMatrixTest() throws Exception {
		
		double expectedDistance = 5.;
		int expectedSize = 2;
		
		PairList<Integer, Integer> coordinates = mock(PairList.class);
		
		Pair<Integer,Integer> a = mock(Pair.class);
		when(a.getFirst()).thenReturn(0);
		when(a.getSecond()).thenReturn(0);
		
		Pair<Integer,Integer> b = mock(Pair.class);
		when(b.getFirst()).thenReturn(3);
		when(b.getSecond()).thenReturn(4);
		
		when(coordinates.size()).thenReturn(expectedSize);
		when(coordinates.get(0)).thenReturn(a);
		when(coordinates.get(1)).thenReturn(b);
		
		CostMatrix costMatrix = Utils.calculateCostMatrix(coordinates);
		
		assertEquals(expectedSize, costMatrix.size());
		assertEquals(expectedDistance, costMatrix.getCost(0, 1), 0);
		assertEquals(expectedDistance, costMatrix.getCost(1, 0), 0);
		
		
	}
	

}
