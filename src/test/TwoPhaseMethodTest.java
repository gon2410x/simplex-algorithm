/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import modelo.IModel;
import twoPhaseMethod.TwoPhaseMethod;

/**
 * 
 */
public class TwoPhaseMethodTest {
	
	@Test
	void testTwoPhaseMethod02() {
		IModel modelo = new TwoPhaseMethod(ExamplesTwoPhaseMethod.ingresoEjemplo02TwoPhaseMethod());
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add((double) 21/4);
		optimalSolution.add((double) 15/2);
		optimalSolution.add((double) 9/2);
		optimalSolution.add(0.0);
		optimalSolution.add((double) 23/10);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}

	@Test
	void testTwoPhaseMethod03() {
		IModel modelo = new TwoPhaseMethod(ExamplesTwoPhaseMethod.ingresoEjemplo03TwoPhaseMethod());
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add(86.0);
		optimalSolution.add(2.0);
		optimalSolution.add(4.0);
		optimalSolution.add(0.0);
		optimalSolution.add(8.0);
		optimalSolution.add(0.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}
	
	@Test
	void testTwoPhaseMethod04() {
		IModel modelo = new TwoPhaseMethod(ExamplesTwoPhaseMethod.ingresoEjemplo04TwoPhaseMethod());
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add(100.0);
		optimalSolution.add((double) 1/4);
		optimalSolution.add((double) 1/2);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}
	
	@Test
	void testTwoPhaseMethod05() {
		IModel modelo = new TwoPhaseMethod(ExamplesTwoPhaseMethod.ingresoEjemplo05TwoPhaseMethod());
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add(-10.0);
		optimalSolution.add(2.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		optimalSolution.add(1.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}
}
