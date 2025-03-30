package modeloM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import interfazUsuario.InterfazUsuarioMostrarTablaSimple;
import modelo.IModel;

/**
 * @author IO
 *
 */
public class ModelM implements IModel {
	
	private int countVar;
	private int countEcu;
	private Double solution;
	/**
	 * objective function
	 */
	private List<Double> functionZ;
	private List<List<Double>> listX ;
	private List<String> listDesigualdad;
	/**
	 * list of resources
	 */
	private List<Double> resources;
	private List<Integer> listVarBasic;
	
	public List<Integer> getListVarBasic() {
		return listVarBasic;
	}

	public ModelM(int countVar, int countEcu) {
		
		System.out.println("Metodo M");
		this.countVar = countVar; 
		this.countEcu = countEcu;
		this.solution = 0.0;	
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

	
	public Double getSolution() {
		return solution;
	}


	public void setSolution(Double solution) {
		this.solution = solution;
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


	private ArrayList<Integer> penalizacion = new ArrayList<Integer>();
	final double M = 10000;

	public ModelM standardize() {
				
		for (int row = 0; row < listDesigualdad.size(); row++) {	
			
			if(listDesigualdad.get(row) == "=") {
				penalizacion.add(row);
				listVarBasic.add(++row);
			}

			if(listDesigualdad.get(row) == "=<") {				
				int countVarBasic = listX.get(0).size(); 
				restriccionMenorQue(row);
				listVarBasic.add(++countVarBasic);
				this.countVar = listX.get(0).size();
			}

			if(listDesigualdad.get(row) == "=>") {				
				int countVarBasic = listX.get(0).size(); 
				restriccionMayorQue(row);
				penalizacion.add(row);
				listVarBasic.add(++countVarBasic);
				this.countVar = listX.get(0).size();
			}
		}			
		
		for(int row = 0; row < penalizacion.size(); row++) {
			restriccionPenalizacion(penalizacion.get(row));
			this.countVar = listX.get(0).size();
			int i = listVarBasic.indexOf(listVarBasic.get( penalizacion.get(row)));
			listVarBasic.set(i, countVar);
		}
		

		restricccionLadoDerechoNoNegativo();
		return this;
	}
	
	final private void eliminarInconcistencia(ArrayList<Integer> p) {
		for(int column = 0; column < functionZ.size(); column++) {

			double newZ = functionZ.get(column);
			
			for(int i=0; i<p.size();i++) {
				newZ = newZ + M * listX.get(p.get(i)).get(column);
			}
			
			functionZ.set(column, newZ);
		}
		
		double ff=0;
		for(int i=0; i<p.size();i++) {
			ff = ff+resources.get(p.get(i))*M;
		}
		
		setSolution(ff);
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

	final private void restriccionMenorQue(int row) {
		
		listX.replaceAll(x -> { x.add(0.0); return x;});
		listX.get(row).set(listX.get(row).size()-1 , 1.0);
		functionZ.add(0.0);
		listDesigualdad.set(row, "=");			
	}
	
	final private void restriccionMayorQue(int row) {
		
		listX.replaceAll(x -> { x.add(0.0); return x;});
		listX.get(row).set(listX.get(row).size()-1 , -1.0);
		functionZ.add(0.0);
		listDesigualdad.set(row, "=");			
	}
	
	final private void restriccionPenalizacion(int row) {
		
		listX.replaceAll(x -> { x.add(0.0); return x;});
		listX.get(row).set(listX.get(row).size()-1 , 1.0);
		functionZ.add( M );
		listDesigualdad.set(row, "=");			
	}
	
	public List<Double> metodoSimple() {
		int numberOfIteration = 0;
		functionZ.replaceAll(z -> z * -1);
		eliminarInconcistencia(penalizacion);
		new InterfazUsuarioMostrarTablaSimple().MostrarDatos(this, numberOfIteration++);
				
		int varIn = 0;
		int varOut = 0;
		
		while( true ) { // Iteration

			varIn = getVarIn(functionZ);
			System.out.print("-------- : "+varIn+"\n");
			
			if ( varIn < 0) { break;} //condicion de parada del Método Simplex
			
			varOut = getVarOut(varIn);
			System.out.println("este valor es :: "+varOut);
			if( varOut < 0) break;
			
			System.out.print("Variable de Entrada : X"+(varIn+1)+"   -    Variable de Salida : X"+(listVarBasic.get(varOut)));
			operacionDeRenglonGaussJordan(varIn,varOut);
			listVarBasic.set(varOut, varIn+1);
			new InterfazUsuarioMostrarTablaSimple().MostrarDatos(this, numberOfIteration++);
			
		}
		
		
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(getSolution());
		
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
		
		setSolution( getSolution() - functionZ.get(varIn)*getResources().get(varOut));

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
		if(list.stream().anyMatch(x -> x > 0)) {
			final double aux = list.stream().max(Double::compare).get();
			return list.indexOf(aux);
		}
		return -1;
	}
	
	private int getVarOut(final int varIn) {//prueba de optimalidad
	
		if(listX.stream().allMatch(x -> x.get(varIn) < 0)) {
			System.out.println("La soluci�n no esta Acotada, columna de la variable X" + (varIn+1) + " son todas negativas -> no hay variable de salida");
		}		
					
		return IntStream.rangeClosed(0, countEcu-1)
				.filter(x -> listX.get(x).get(varIn) != 0) 
				.filter(z -> resources.get(z) / listX.get(z).get(varIn) >= 0)
				.reduce((accumulator,y) -> resources.get(accumulator) / listX.get(accumulator).get(varIn) > resources.get(y) / listX.get(y).get(varIn) ? y : accumulator)
				.orElse(-1);		
	}

}