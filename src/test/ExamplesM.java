package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.ILinearModel;
import modelo.IModel;
import modelo.LinearModel;
import modeloM.ModelM;

public class ExamplesM {

	/**
	 * @param null
	 * @return
	 * z = 4+1 (minimize)<br>
	 *&emsp;3+1=< 3 <br>
	 *&emsp;4+3=< 6<br>
	 *&emsp;1+2=<4<br>
	 *&emsp;Solution z=17/5 ; x1=2/5 ; x2=9/5 ; x3=1 ; x4=0 ; x5=0; x6=0
	 * 
	 */
	public static ILinearModel ingresoEjemplo01M() {
		
		ILinearModel modelo = new LinearModel(2,3);
		
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
	 * z = 9+2+5 (minimize)<br>
	 *&emsp;4+3+6=< 50 <br>
	 *&emsp;1+2-3=> 8<br>
	 *&emsp;2-4+1=<5<br>
	 *&emsp;Solution z=8 ; x1=0 ; x2=4 ; x3=0 ; x4=38 ; x5=0; x6=21; x7=0;
	 * 
	 */
	public static ILinearModel ingresoEjemplo02M() {
		
		ILinearModel modelo = new LinearModel(3,3);
		
		modelo.setListZ(new ArrayList<Double>() {{add(9.0);add(2.0);add(5.0);}});
		List<Double> a = new ArrayList<Double>();a.add(4.0);a.add(3.0);a.add(6.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(1.0);b.add(2.0);b.add(-3.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(2.0);c.add(-4.0);c.add(1.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(50.0,8.0,5.0));		
		modelo.setListaDesigualdad( Arrays.asList("=<", "=>", "=<") );
		
		return modelo;	
	}
	
	/**
	 * @param null
	 * @return
	 * z = 163+120+220 (minimize)<br>
	 *&emsp;2+1+4 =< 2 <br>
	 *&emsp;3+2+2=> 3/2<br>
	 *&emsp;Solution z=163/2 ; x1=1/2 ; x2=0 ; x3=0 ; x4=1 ; x5=0;x6=0;
	 * 
	 */
	public static ILinearModel ingresoEjemplo03M() {
		
		ILinearModel modelo = new LinearModel(3,2);
		
		modelo.setListZ(new ArrayList<Double>() {{add(163.0);add(120.0);add(220.0);}});
		List<Double> a = new ArrayList<Double>();a.add(2.0);a.add(1.0);a.add(4.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(3.0);b.add(2.0);b.add(2.0);
		modelo.getListaX().set(1,b);
		modelo.setResources(Arrays.asList(2.0,1.5));		
		modelo.setListaDesigualdad( Arrays.asList("=<", "=>"));
		
		return modelo;	
	}
	
	
	/**
	 * @param null
	 * @return
	 * z = 25+22 (minimize)<br>
	 *&emsp;0,45+0,35 =< 126 <br>
	 *&emsp;0,18+0,36 => 9<br>
	 *&emsp;0,30+0,20 => 3<br>
	 *&emsp;Solution z=7000 ; x1=280 ; x2=0 ; x3=0 x4=207/5; x5=81 ; x6=0;
	 * 
	 */
	public static ILinearModel ingresoEjemplo05M() {
		
		ILinearModel modelo = new LinearModel(2,3);
		
		modelo.setListZ(new ArrayList<Double>() {{add(25.0);add(22.0);}});
		List<Double> a = new ArrayList<Double>();a.add(0.45);a.add(0.35);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(0.18);b.add(0.36);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(0.30);c.add(0.20);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(126.0,9.0,3.0));		
		modelo.setListaDesigualdad( Arrays.asList("=>", "=>", "=>"));
		
		return modelo;	
	}
}
