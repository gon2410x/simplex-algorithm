/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.ILinearModel;
import modelo.LinearModel;

/**
 * 
 */
public class ExamplesTwoPhaseMethod {

	/**
	 * @param null
	 * @return
	 * z = 4+1 (minimize)<br>
	 *&emsp;3+1= 3 <br>
	 *&emsp;4+3=> 6<br>
	 *&emsp;1+2=<4<br>
	 *&emsp;Solution z=17/5 ; x1=2/5 ; x2=9/5 ; x3=1 ; x4=0 ; x5=0; x6=0
	 * 
	 */
	public static ILinearModel ingresoEjemplo01TwoPhaseMethod() {
		
		ILinearModel modelo = new LinearModel(2,3, "MINIMIZE");
		
		modelo.setListZ(new ArrayList<Double>() {{add(4.0);add(1.0);}});
		List<Double> a = new ArrayList<Double>();a.add(3.0);a.add(1.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(4.0);b.add(3.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(1.0);c.add(2.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(3.0,6.0,4.0));		
		modelo.setListaDesigualdad( Arrays.asList("=", "=>", "=<") );
		
		return modelo;	
	}
	
	
	
	/**
	 * @param null
	 * @return
	 * z = 0.4 + 0.5 (minimize)<br>
	 *&emsp;0.3 + 0.1 =< 2.7 <br>
	 *&emsp;0.5 + 0.5 = 6<br>
	 *&emsp;0.6 + 0.4 => 4<br>
	 *&emsp;Solution z=21/4 ; x1=15/2 ; x2=9/2 ; x3=0 ; x4=23/10 ;
	 * 
	 */
	public static ILinearModel ingresoEjemplo02TwoPhaseMethod() {
		
		ILinearModel modelo = new LinearModel(2,3, "MINIMIZE");
		
		modelo.setListZ(new ArrayList<Double>() {{add(0.4);add(0.5);}});
		List<Double> a = new ArrayList<Double>();a.add(0.3);a.add(0.1);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(0.5);b.add(0.5);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(0.6);c.add(0.4);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(2.7,6.0,4.0));		
		modelo.setListaDesigualdad( Arrays.asList("=<", "=", "=>") );
		
		return modelo;	
	}
	
	
	/**
	 * @param null
	 * @return
	 * z = 11 + 16 (Maximization)<br>
	 *&emsp;1 + 1 =< 6 <br>
	 *&emsp;3 + 2 => 6<br>
	 *&emsp;1 + 2 =< 10<br>
	 *&emsp;Solution z=86 ; x1=2 ; x2=4 ; x3=0 ; x4=8 ; x5=0;
	 * 
	 */
	public static ILinearModel ingresoEjemplo03TwoPhaseMethod() {
		
		ILinearModel modelo = new LinearModel(2,3, "MAXIMIZE");
		
		modelo.setListZ(new ArrayList<Double>() {{add(11.0);add(16.0);}});
		List<Double> a = new ArrayList<Double>();a.add(1.0);a.add(1.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(3.0);b.add(2.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(1.0);c.add(2.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(6.0,6.0,10.0));		
		modelo.setListaDesigualdad( Arrays.asList("=<", "=>", "=<") );
		
		return modelo;	
	}
	
	
	
	/**
	 * @param null
	 * @return
	 * z = 160 + 120 + 280(Minimization)<br>
	 *&emsp;2 + 1 + 4 => 1 <br>
	 *&emsp;2 + 2 + 2 => 1.5 <br>
	 *&emsp;Solution z=100 ; x1=1/4 ; x2=1/2 ; x3=0 ; x4=0 ; x5=0;
	 * 
	 */
	public static ILinearModel ingresoEjemplo04TwoPhaseMethod() {
		
		ILinearModel modelo = new LinearModel(3,2, "MINIMIZE");
		
		modelo.setListZ(new ArrayList<Double>() {{add(160.0);add(120.0);add(280.0);}});
		List<Double> a = new ArrayList<Double>();a.add(2.0);a.add(1.0);a.add(4.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(2.0);b.add(2.0);b.add(2.0);
		modelo.getListaX().set(1,b);
		modelo.setResources(Arrays.asList(1.0,1.5));		
		modelo.setListaDesigualdad( Arrays.asList("=>", "=>") );
		
		return modelo;	
	}
	
	
	/**
	 * @param null
	 * @return
	 * z = -5 + 2 + (Maximize)<br>
	 *&emsp;-1 + 1 =< -2 <br>
	 *&emsp;2 + 3  =< 5 <br>
	 *&emsp;Solution z=-10 ; x1=2 ; x2=0 ; x3=0 ; x4=1 ;
	 * 
	 */
	public static ILinearModel ingresoEjemplo05TwoPhaseMethod() {
		
		ILinearModel modelo = new LinearModel(2,2, "MAXIMIZE");
		
		modelo.setListZ(new ArrayList<Double>() {{add(-5.0);add(2.0);}});
		List<Double> a = new ArrayList<Double>();a.add(-1.0);a.add(1.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(2.0);b.add(3.0);
		modelo.getListaX().set(1,b);
		modelo.setResources(Arrays.asList(-2.0,5.0));		
		modelo.setListaDesigualdad( Arrays.asList("=<", "=<") );
		
		return modelo;	
	}
}
