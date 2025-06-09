/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import modelo.ILinearModel;
import modelo.Model;
import modelo.LinearModel;

/**
 * @author personal
 *
 */
class ModelTest {

	/**
	 * Test method for {@link modelo.Model#metodoSimple()}.
	 * 
	 *<br>z = 5+4 (maximizar)<br>
	 *&emsp;6+4=< 24 <br>
	 *&emsp;1+2=< 6<br>
	 *&emsp;-1+1=<1<br>
	 *&emsp;0+1=<2<br>
	 *&emsp;Solucion z=21 ; x1=3 ; x2=1.5 ; x3=0 ; x4=0 ; x5=2.5; x6=0,5
	 * 
	 */
	@Test
	void testMetodoSimple01() {
				
		Model model = new Model(Examples.ingresoEjemplo01());
		model = model.standardize();
		List<Double> modelResult = model.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());

		assertEquals(new ArrayList<Double>() {{add(21.0);add(3.0);add(1.5);add(0.0);add(0.0);add(2.5);add(0.5);}}, modelResult);
	}
	
	

	/**
	 * 
	 * z = 2+5+9  (maximizar)<br>
	 *&emsp;1+6+8=< 23 <br>
	 *&emsp;52+6+20=< 7<br>
	 *&emsp;7+9+11=<20<br>
	 *&emsp;Solution z=5,833 ; (0 ; 1.167 ; 0 ; 16 ; 0 ; 9.5)
	 * 
	 */
	@Test
	void testMetodoSimple02() {
		
		Model model = new Model(Examples.ingresoEjemplo02());
		model = model.standardize();
		List<Double> modelResult = model.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());

		assertEquals(new ArrayList<Double>() {{add(5.83);add(0.0);add(1.17);add(0.0);add(16.0);add(0.0);add(9.5);}}, modelResult);
	}
	
	/**
	 * 
	 * z = 82+53+54  (maximizar)<br>
	 *&emsp;36+72+63=< 89 <br>
	 *&emsp;8+30+51=< 20<br>
	 *&emsp;61+48+8=<70<br>
	 *&emsp;Solution z=103,466 ; (1.119 ; 0 ; 0.217 ; 35.0649819 ; 0 ; 0)
	 * 
	 */
	@Test
	void testMetodoSimple03() {
		ILinearModel modelo = new LinearModel(3,3, "MAXIMIZE");
		
		Model model = new Model(Examples.ingresoEjemplo03());
		model = model.standardize();
		List<Double> modelResult = model.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());

		assertEquals(new ArrayList<Double>() {{add(103.47);add(1.12);add(0.0);add(0.22);add(35.06);add(0.0);add(0.0);}}, modelResult);
	}
	
	/**
	 * @param modelo
	 * @return
	 * z = 17+1-67  (maximizar)<br>
	 *&emsp;9-18-75=< -6200 <br>
	 *&emsp;-5-44-46=< -2600<br>
	 *&emsp;-82+56-2=<-7900<br>
	 *&emsp;Solution no esta acotada
	 * 
	 */
	@Test
	void testMetodoSimple05() {	

		Model model = new Model(Examples.ingresoEjemplo05());
		model = model.standardize();
		List<Double> modelResult = model.metodoSimple();

		assertEquals(new ArrayList<Double>(Arrays.asList(Double.POSITIVE_INFINITY)) , modelResult);
	}
	
	
	/**
	 * z = 1+3  (maximizar)<br>
	 *&emsp;1+1=< 8 <br>
	 *&emsp;1+3=< 12<br>
	 *&emsp;-1+2=<4<br>
	 *&emsp;Solution z=12 ; (2.4 ; 3.2 ; 2.4 ; 0 ; 0)
	 * 
	 */
	@Test
	void testMetodoSimple07() {	
		
		Model model = new Model(Examples.ingresoEjemplo07());
		model = model.standardize();
		List<Double> modelResult = model.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());

		assertEquals(new ArrayList<Double>() {{add(12.0);add(2.4);add(3.2);add(2.4);add(0.0);add(0.0);}}, modelResult);
	}

}
