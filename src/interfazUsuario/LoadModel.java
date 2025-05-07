/**
 * 
 */
package interfazUsuario;

import modelo.IModel;
import modelo.IModel;
import modelo.ILinearModel;
import modelo.Model;
import modelo.LinearModel;
import test.Examples;
import test.ExamplesM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author personal
 *
 */
final public class LoadModel {
	
	private int countVar;
	private int countEcu;
	private IModel modelo;
	private Scanner in = new Scanner(System.in);

	public ILinearModel ingresarDatos() {
		
		System.out.println("\n\n\n");
//		System.out.println("\tIngresar datos Manualmente presione\t- 1 ");
//		System.out.print("\tIngresar datos Automaticamente presione\t- 2\n ");

//		ingresarModeloPorTeclado();
//		ingresoModeloEspecifico();
//		ingresarModeloAutomaticamente();
		return Examples.ingresoEjemplo03();
//		return this.modelo;
	}
	
	public int getCountVar() {
		return countVar;
	}
	
	
	public void setCountVar(int countVar) {
		this.countVar = countVar;
	}
	
	
	public int getCountEcu() {
		return countEcu;
	}
	
	
	public void setCountEcu(int countEcu) {
		this.countEcu = countEcu;
	}


	private void ingresoModeloEspecifico() {
		
		ILinearModel modelo = new LinearModel(getCountVar(), getCountEcu());
		
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
		modelo.setListaDesigualdad(IntStream.range(0, getCountEcu())
				.mapToObj(x->"=<")
				.collect(Collectors.toCollection(ArrayList::new)));
		
		System.out.println(modelo.getListaDesigualdad().size());
		
	}


	private void ingresarExtructuraDelModeloPorTeclado() {

		System.out.print("\tIngresar cantidad de Variables : ");
		final int cantidadDeVariables = in.nextInt();
		setCountVar(cantidadDeVariables);
		System.out.print("\tIngresar cantidad de Restricciones : ");
		final int cantidadDeRestricciones = in.nextInt();
		setCountEcu(cantidadDeRestricciones);
	}
	
//	private void ingresarModeloPorTeclado() {
//		
//		modelo = new Modelo(this.countVar, this.countEcu);
//		
//		for (int i = 0; i < countVar; i++) {
//			System.out.print("ingresar el coeficiente de X" + (i+1) + " del Objetivo : " );
//			final int aux = in.nextInt();
//			modelo.z(i,aux);
//		}
//		
//		for (int j = 0; j < countEcu; j++) {
//			for (int i = 0; i < countVar; i++) {
//				System.out.print("ingresar el coeficiente de X" + (i+1) + " de la Restriccion "+ (j+1) +" : ");
//				final int aux = in.nextInt();
//				modelo.x(j, i, aux);
//			}
//			
//			
//			System.out.print("ingresar desigualdad de la restriccion " + (j+1) + " : ");
//			final String desigualdad = in.next();
//			modelo.addDesigualdad(j, desigualdad);
//
//			System.out.print("ingresar el recurso de restriccion " + (j+1) + " : ");
//			final int aux = in.nextInt();
//			modelo.rec(j, aux);
//		}
//		
//		System.out.println("\n\n");
//		modelo.mostrarModelo();
//	}
	
	private IModel ingresarModeloAutomaticamente() {
		
		ILinearModel modelo = new LinearModel(getCountVar(), getCountEcu());
		
		modelo.setListZ(arrayIntRandom(countVar));
		
		List<List<Double>> listX =	IntStream.range(0, countEcu)
											 .mapToObj( row -> arrayIntRandom(countVar))
											 .collect(Collectors.toCollection(ArrayList::new));
		
		modelo.setListaX(listX);	
		
		modelo.setResources(arrayIntRandom(countEcu));	
		
		for (int i = 0; i < modelo.getResources().size(); i++) {
			modelo.getResources().set(i, modelo.getResources().get(i) * 1000);
		}
		
		modelo.setListaDesigualdad(IntStream.range(0, countEcu)
											.mapToObj(x->"=<")
											.collect(Collectors.toCollection(ArrayList::new))
		);
		
		
		System.out.println("\n\n");	
		Model m = new Model(modelo);
		return m;
	}
	
	final private ArrayList<Double> arrayIntRandom(int size){
		return IntStream.range(0, size)
						.mapToDouble(x -> crearIntRandom(-90, 90))
						.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	final private int crearIntRandom(int x, int y) {return (int) (x+Math.random() * (Math.abs(x-y)));}
		
}
