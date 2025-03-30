package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import modelo.IModel;

class ModelMTest {

	@Test
	void testMetodoMSimple01() {
		IModel modelo = TestM.ingresoEjemplo01M();
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add((double) 17/5);
		optimalSolution.add((double) 2/5);
		optimalSolution.add((double) 9/5);
		optimalSolution.add(1.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}
	
	@Test
	void testMetodoMSimple02() {
		IModel modelo = TestM.ingresoEjemplo02M();
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add(8.0);
		optimalSolution.add(0.0);
		optimalSolution.add(4.0);
		optimalSolution.add(0.0);
		optimalSolution.add(38.0);
		optimalSolution.add(0.0);
		optimalSolution.add(21.0);
		optimalSolution.add(0.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}
	
	
	@Test
	void testMetodoMSimple03() {
		IModel modelo = TestM.ingresoEjemplo03M();
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add((double) 163/2);
		optimalSolution.add((double) 1/2);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		optimalSolution.add(1.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}
	
	@Test
	void testMetodoMSimple05() {
		IModel modelo = TestM.ingresoEjemplo05M();
		
		modelo = modelo.standardize();
		List<Double> modelResult = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());
				
		final List<Double> optimalSolution = new ArrayList<Double>();
		optimalSolution.add(7000.0);
		optimalSolution.add(280.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		optimalSolution.add((double) 207/5);
		optimalSolution.add(81.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		optimalSolution.add(0.0);
		
		System.out.println(optimalSolution);
		assertEquals(optimalSolution, modelResult);
	}

}
