/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import modelo.Model;

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
	void testMetodoSimple() {
		Model modelo = new Model(2,4);
		
		modelo.setListZ(new ArrayList<Double>() {{add(5.0);add(4.0);}});
		List<Double> a = new ArrayList<Double>();a.add(6.0);a.add(4.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(1.0);b.add(2.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(-1.0);c.add(1.0);
		modelo.getListaX().set(2,c);
		List<Double> d = new ArrayList<Double>();d.add(0.0);d.add(1.0);
		modelo.getListaX().set(3,d);
		modelo.setResources(Arrays.asList(24.0,6.0,1.0,2.0));
		modelo.setListaDesigualdad(IntStream.range(0, modelo.getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		modelo = modelo.standardize();
		List<Double> l = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());

		assertEquals(new ArrayList<Double>() {{add(21.0);add(3.0);add(1.5);add(0.0);add(0.0);add(2.5);add(0.5);}}, l);
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
	void testMetodoSimple001() {
		Model modelo = new Model(3,3);
		
		modelo.setListZ(new ArrayList<Double>() {{add(2.0);add(5.0);add(9.0);}});
		List<Double> a = new ArrayList<Double>();a.add(1.0);a.add(6.0);a.add(8.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(52.0);b.add(6.0);b.add(20.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(7.0);c.add(9.0);c.add(11.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(23.0,7.0,20.0));
		modelo.setListaDesigualdad(IntStream.range(0, modelo.getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		modelo = modelo.standardize();
		List<Double> l = modelo.metodoSimple().stream().map(x -> Math.round(x*100.0)/100.0).collect(Collectors.toList());

		assertEquals(new ArrayList<Double>() {{add(5.83);add(0.0);add(1.17);add(0.0);add(16.0);add(0.0);add(9.5);}}, l);
	}

}
