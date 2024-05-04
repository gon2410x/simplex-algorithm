package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sun.net.httpserver.Authenticator.Result;

import interfazUsuario.InterfazUsuarioMostrarTablaSimple;

/**
 * @author personal
 *
 */
public class Model {
	
	private int countVar;
	private int countEcu;
	private Double solucion;
	/**
	 * funcion Objetivo
	 */
	private List<Double> functionZ;
	private List<List<Double>> listX ;
	private List<String> listDesigualdad;
	/**
	 * lista de Recursos
	 */
	private List<Double> resources;
	private List<Integer> listVarBasic;
	
	public List<Integer> getListVarBasic() {
		return listVarBasic;
	}


	public Model(int countVar, int countEcu) {
		
		this.countVar = countVar;
		this.countEcu = countEcu;
		this.solucion = 0.0;	
		functionZ = new ArrayList<Double>();		
		listX = IntStream.range(0, this.countEcu).mapToObj( x -> new ArrayList<Double>()).collect(Collectors.toCollection(ArrayList::new));
		listDesigualdad = new ArrayList<String>();		
		resources = new ArrayList<Double>();
		listVarBasic = new ArrayList<Integer>();
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

	
	public Double getSolucion() {
		return solucion;
	}


	public void setSolucion(Double solucion) {
		this.solucion = solucion;
	}


	public List<Double> getListZ() {
		return functionZ;
	}
	
	public void setListZ(List<Double> listaZ) {
		this.functionZ = listaZ;
	}
	
	public List<List<Double>> getListaX() {
		return listX;
	}

	public void setListaX(List<List<Double>> listaX) {
		this.listX = listaX;
	}	
	
	public List<String> getListaDesigualdad() {
		return listDesigualdad;
	}

	public void setListaDesigualdad(List<String> listaDesigualdad) {
		this.listDesigualdad = listaDesigualdad;
	}	
	
	public List<Double> getResources() {
		return resources;
	}

	public void setResources(List<Double> resources) {
		this.resources = resources;
	}


	public Model standardize() {
		RestriccionMenorQue();
		restricccionLadoDerechoNoNegativo();
		return this;
	}
	
	final private void restricccionLadoDerechoNoNegativo() {
		
		IntStream.range(0, getCountEcu())
				 .forEach(row -> {		
					 if(resources.get(row) < 0) {
						 resources.set(row, resources.get(row)*-1);
						 listX.get(row).replaceAll(x -> x*-1);
					 }
		});
	}

	final private void RestriccionMenorQue() {
		
		int countVarBasic = listX.get(0).size(); 
		for (int row = 0; row < listDesigualdad.size(); row++) {	
			if(listDesigualdad.get(row) == "=<") {		
				listX.replaceAll(x -> { x.add(0.0); return x;});
				listX.get(row).set(listX.get(row).size()-1 , 1.0);
				functionZ.add(0.0);
				listDesigualdad.set(row, "=");
				listVarBasic.add(++countVarBasic);
			}	
		}			
		this.countVar = listX.get(0).size();
	}
	
	public List<Double> metodoSimple() {
		int numberOfIteration = 0;
		functionZ.replaceAll(z -> z * -1);
		new InterfazUsuarioMostrarTablaSimple().MostrarDatos(this, numberOfIteration++);
		
//		int varIn = functionZ.indexOf(Collections.min(functionZ));
		
//		int varIn = getVarIn(functionZ);
//		System.out.println("------------ " + varIn);
//		int varOut = getVarOut(varIn);
//		
//		while(functionZ.get(varIn) < 0) { // Iteration
//
//			System.out.print("Variable de Entrada : X"+(varIn+1)+"   -    Variable de Salida : X"+(listVarBasic.get(varOut)));
//			operacionDeRenglonGaussJordan(varIn,varOut);
//			listVarBasic.set(varOut, varIn+1);
//			new InterfazUsuarioMostrarTablaSimple().MostrarDatos(this, numberOfIteration++);
//			
//			varIn = getVarIn(functionZ);
//			System.out.print("-------- : "+varIn);
//			varOut = getVarOut(varIn);
//		}
		
		
		int varIn = 0;
		int varOut = 0;
		
		while( true ) { // Iteration

			varIn = getVarIn(functionZ);
			System.out.print("-------- : "+varIn+"\n");
			
			if ( varIn < 0) break; //condicion de parada del Método Simplex
			
			varOut = getVarOut(varIn);
			
			System.out.print("Variable de Entrada : X"+(varIn+1)+"   -    Variable de Salida : X"+(listVarBasic.get(varOut)));
			operacionDeRenglonGaussJordan(varIn,varOut);
			listVarBasic.set(varOut, varIn+1);
			new InterfazUsuarioMostrarTablaSimple().MostrarDatos(this, numberOfIteration++);
			
		}
		
		
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(solucion);
		
		for(int i=0; i<countVar;i++) {
			result.add(0.0);
		}

		for(int i = 0; i < resources.size(); i++) {
			result.set(listVarBasic.get(i), resources.get(i));
		}
		
		System.out.println("este es el resultado : "+result);
		return result;
	}

	private void operacionDeRenglonGaussJordan(final int varIn, final int varOut) {
		
		final double elementoPivot = listX.get(varOut).get(varIn);
		
		resources.set(varOut, resources.get(varOut) / elementoPivot);
		listX.get(varOut).replaceAll(x -> x / elementoPivot);
		
		setSolucion( getSolucion() - functionZ.get(varIn)*getResources().get(varOut));

		List<Double> z = IntStream.range(0, countVar)
								  .mapToObj( x -> functionZ.get(x) - (functionZ.get(varIn) * listX.get(varOut).get(x)))//rActual-suCoefColuPiv*NueRenPivo
								  .collect(Collectors.toCollection(ArrayList::new));
		
		setListZ(z);
		
		List<List<Double>> x = IntStream.range(0, countEcu)
										.mapToObj(row -> {if(row == varOut) {
															  return listX.get(varOut);
														  } else {
															  resources.set(row, resources.get(row)-listX.get(row).get(varIn)*resources.get(varOut));
															  return aux(listX.get(row), listX.get(varOut), varIn);
														  }
										})
										.collect(Collectors.toCollection(ArrayList::new));
		setListaX(x);
	}

	private List<Double> aux(List<Double> list, List<Double> listAux, int varIn) {
		return IntStream.range(0, countVar)
									.mapToObj( x -> list.get(x) - list.get(varIn) * listAux.get(x))
									.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * @param list
	 * @return int que representa la posicion del elemento con valor mas negativo de la lista, retorna -1 si la lista no contiene valores negativos.
	 * @Throws
	 */
	private int getVarIn(List<Double> list) {
		if(list.stream().anyMatch(x -> x < 0)) {
			final double aux = list.stream().min(Double::compare).get();
			return list.indexOf(aux);
		}
		return -1;
	}
	
	private int getVarOut(final int varIn) {//prueba de optimalidad
	
		if(listX.stream().allMatch(x -> x.get(varIn) < 0)) {
			System.out.println("La solución no esta Acotada, columna de la variable X" + (varIn+1) + " son todas negativas -> no hay variable de salida");
		}		
					
		return IntStream.rangeClosed(0, countEcu-1)
				.filter(x -> listX.get(x).get(varIn) != 0) 
				.filter(z -> resources.get(z) / listX.get(z).get(varIn) >= 0)
				.reduce((accumulator,y) -> resources.get(accumulator) / listX.get(accumulator).get(varIn) > resources.get(y) / listX.get(y).get(varIn) ? y : accumulator)
				.orElse(-1);
		
//		return listAux.indexOf(varSalida2);
	}

}
