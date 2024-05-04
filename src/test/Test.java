package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import modelo.Model;

/**
 * @author personal
 *
 */
public final class Test {
	
	/**
	 * @param modelo
	 * @return
	 * z = 5+4 (maximizar)<br>
	 *&emsp;6+4=< 24 <br>
	 *&emsp;1+2=< 6<br>
	 *&emsp;-1+1=<1<br>
	 *&emsp;0+1=<2<br>
	 *&emsp;Solucion z=21 ; x1=3 ; x2=1.5 ; x3=0 ; x4=0 ; x5=2.5; x6=0,5
	 * 
	 */
	public static Model ingresoEjemplo01(Model modelo) {
		
		modelo = new Model(2,4);
		
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
		
		return modelo;	
	}
	
	
	/**
	 * @param modelo
	 * @return
	 * z = 2+5+9  (maximizar)<br>
	 *&emsp;1+6+8=< 23 <br>
	 *&emsp;52+6+20=< 7<br>
	 *&emsp;7+9+11=<20<br>
	 *&emsp;Solution z=5,833 ; (0 ; 1.167 ; 0 ; 16 ; 0 ; 9.5)
	 * 
	 */
	public static Model ingresoEjemplo02(Model modelo) {
		
		modelo = new Model(3,3);
		
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
		
		return modelo;	
	}
	
	/**
	 * @param modelo
	 * @return
	 * z = 82+53+54  (maximizar)<br>
	 *&emsp;39+72+63=< 89 <br>
	 *&emsp;8+30+51=< 20<br>
	 *&emsp;61+48+8=<70<br>
	 *&emsp;Solution z=103.466 ; (1.119 ; 0 ; 0.217 ; 31.708 ; 0 ; 0)
	 * 
	 */
	public static Model ingresoEjemplo03(Model modelo) {
		
		modelo = new Model(3,3);
		
		modelo.setListZ(new ArrayList<Double>() {{add(82.0);add(53.0);add(54.0);}});
		List<Double> a = new ArrayList<Double>();a.add(39.0);a.add(72.0);a.add(63.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(8.0);b.add(30.0);b.add(51.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(61.0);c.add(48.0);c.add(8.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(89.0,20.0,70.0));
		modelo.setListaDesigualdad(IntStream.range(0, modelo.getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		return modelo;	
	}
	
	/**
	 * @param modelo
	 * @return
	 * z = 69-75+62  (maximizar)<br>
	 *&emsp;45+30-23=< 7900 <br>
	 *&emsp;37+39+56=< -2100<br>
	 *&emsp;63+68-27=<-7700<br>
	 *&emsp;Solution no exite solucion posible
	 * 
	 */
	public static Model ingresoEjemplo04(Model modelo) {
		
		modelo = new Model(3,3);
		
		modelo.setListZ(new ArrayList<Double>() {{add(69.0);add(-75.0);add(62.0);}});
		List<Double> a = new ArrayList<Double>();a.add(45.0);a.add(30.0);a.add(23.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(37.0);b.add(39.0);b.add(56.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(63.0);c.add(68.0);c.add(-27.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(7900.0,-2100.0,-7700.0));
		modelo.setListaDesigualdad(IntStream.range(0, modelo.getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		return modelo;	
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
	public static Model ingresoEjemplo05(Model modelo) {
		
		modelo = new Model(3,3);
		
		modelo.setListZ(new ArrayList<Double>() {{add(17.0);add(1.0);add(-67.0);}});
		List<Double> a = new ArrayList<Double>();a.add(9.0);a.add(-18.0);a.add(-75.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(-5.0);b.add(-44.0);b.add(-46.0);
		modelo.getListaX().set(1,b);
		List<Double> c = new ArrayList<Double>();c.add(-82.0);c.add(56.0);c.add(-2.0);
		modelo.getListaX().set(2,c);
		modelo.setResources(Arrays.asList(-6200.0,-2600.0,-7900.0));
		modelo.setListaDesigualdad(IntStream.range(0, modelo.getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		return modelo;	
	}
	
	/**
	 * @param modelo
	 * @return
	 * z = -13000-47000  (maximizar)<br>
	 *&emsp;68000+2000=< -27000 <br>
	 *&emsp;6000-43000=< 12000<br>
	 *&emsp;Solution no esta acotada
	 * 
	 */
	public static Model ingresoEjemplo06(Model modelo) {
		
		modelo = new Model(2,2);
		
		modelo.setListZ(new ArrayList<Double>() {{add(-13000.0);add(-47000.0);}});
		List<Double> a = new ArrayList<Double>();a.add(68000.0);a.add(2000.0);
		modelo.getListaX().set(0, a);
		List<Double> b = new ArrayList<Double>();b.add(6000.0);b.add(-43000.0);
		modelo.getListaX().set(1,b);
		modelo.setResources(Arrays.asList(-27000.0,12000.0));
		modelo.setListaDesigualdad(IntStream.range(0, modelo.getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		return modelo;	
	}
}


