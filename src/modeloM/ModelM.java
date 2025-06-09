package modeloM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import interfazUsuario.DisplayTableConsole;
import modelo.IModel;
import modelo.IModel;
import modelo.LinearModel;
import modelo.ILinearModel;

/**
 * @author IO
 *
 */
public class ModelM implements IModel {
	
	
	private ILinearModel linearModel;
	
	public ILinearModel getLinearModel() {
		return this.linearModel;
	}
	
	private List<Integer> listVarBasic;
	
	public List<Integer> getListVarBasic() {
		return listVarBasic;
	}
	
	

	/**
	 * @param linearModel
	 */
	public ModelM(ILinearModel linearModel) {
		super();
		System.out.println("Metodo M");
		this.linearModel = linearModel;
		linearModel.setSolucion(0.0);
		listVarBasic = new ArrayList<Integer>();
	}



	public ModelM(int countVar, int countEcu, String optimization) {
		
		System.out.println("Metodo M");
		linearModel = new LinearModel(countVar, countEcu, optimization);
		linearModel.setSolucion(0.0);
		listVarBasic = new ArrayList<Integer>();
	}		


	private ArrayList<Integer> penalizacion = new ArrayList<Integer>();
	final double M = 10000;

	public ModelM standardize() {
				
		for (int row = 0; row < linearModel.getListaDesigualdad().size(); row++) {	
			
			if(linearModel.getListaDesigualdad().get(row) == "=") {
				penalizacion.add(row);
				listVarBasic.add(++row);
			}

			if(linearModel.getListaDesigualdad().get(row) == "=<") {				
				int countVarBasic = linearModel.getListaX().get(0).size(); 
				restriccionMenorQue(row);
				listVarBasic.add(++countVarBasic);
				linearModel.setCountVar(linearModel.getListaX().get(0).size());
			}

			if(linearModel.getListaDesigualdad().get(row) == "=>") {				
				int countVarBasic = linearModel.getListaX().get(0).size(); 
				restriccionMayorQue(row);
				penalizacion.add(row);
				listVarBasic.add(++countVarBasic);
				linearModel.setCountVar(linearModel.getListaX().get(0).size());
			}
		}			
		
		for(int row = 0; row < penalizacion.size(); row++) {
			restriccionPenalizacion(penalizacion.get(row));
			linearModel.setCountVar(linearModel.getListaX().get(0).size());
			int i = listVarBasic.indexOf(listVarBasic.get( penalizacion.get(row)));
			listVarBasic.set(i, linearModel.getCountVar());
		}
		

		restricccionLadoDerechoNoNegativo();
		return this;
	}
	
	final private void eliminarInconcistencia(ArrayList<Integer> p) {
		for(int column = 0; column < linearModel.getListZ().size(); column++) {

			double newZ = linearModel.getListZ().get(column);
			
			for(int i=0; i<p.size();i++) {
				newZ = newZ + M * linearModel.getListaX().get(p.get(i)).get(column);
			}
			
			linearModel.getListZ().set(column, newZ);
		}
		
		double ff=0;
		for(int i=0; i<p.size();i++) {
			ff = ff+ linearModel.getResources().get(p.get(i))*M;
		}
		
		linearModel.setSolucion(ff);
	}
	
	final private void restricccionLadoDerechoNoNegativo() {
		
		IntStream.range(0, linearModel.getCountEcu())
				 .forEach(row -> {		
					 if(linearModel.getResources().get(row) < 0) {
						 linearModel.getResources().set(row, linearModel.getResources().get(row)*-1);
						 linearModel.getListaX().get(row).replaceAll(x -> x*-1);
					 }
		});
	}

	final private void restriccionMenorQue(int row) {
		
		linearModel.getListaX().replaceAll(x -> { x.add(0.0); return x;});
		linearModel.getListaX().get(row).set(linearModel.getListaX().get(row).size()-1 , 1.0);
		linearModel.getListZ().add(0.0);
		linearModel.getListaDesigualdad().set(row, "=");			
	}
	
	final private void restriccionMayorQue(int row) {
		
		linearModel.getListaX().replaceAll(x -> { x.add(0.0); return x;});
		linearModel.getListaX().get(row).set(linearModel.getListaX().get(row).size()-1 , -1.0);
		linearModel.getListZ().add(0.0);
		linearModel.getListaDesigualdad().set(row, "=");			
	}
	
	final private void restriccionPenalizacion(int row) {
		
		linearModel.getListaX().replaceAll(x -> { x.add(0.0); return x;});
		linearModel.getListaX().get(row).set(linearModel.getListaX().get(row).size()-1 , 1.0);
		linearModel.getListZ().add( M );
		linearModel.getListaDesigualdad().set(row, "=");			
	}
	
	public List<Double> metodoSimple() {
		int numberOfIteration = 0;
		linearModel.getListZ().replaceAll(z -> z * -1);
		eliminarInconcistencia(penalizacion);
		new DisplayTableConsole().MostrarDatos(this, numberOfIteration++);
				
		int varIn = 0;
		int varOut = 0;
		
		while( true ) { // Iteration

			varIn = getVarIn(linearModel.getListZ());
			System.out.print("-------- : "+varIn+"\n");
			
			if ( varIn < 0) { break;} //condicion de parada del Método Simplex
			
			varOut = getVarOut(varIn);
			System.out.println("este valor es :: "+varOut);
			if( varOut < 0) break;
			
			System.out.print("Variable de Entrada : X"+(varIn+1)+"   -    Variable de Salida : X"+(listVarBasic.get(varOut)));
			operacionDeRenglonGaussJordan(varIn,varOut);
			listVarBasic.set(varOut, varIn+1);
			new DisplayTableConsole().MostrarDatos(this, numberOfIteration++);
			
		}
		
		
		ArrayList<Double> result = new ArrayList<Double>();
		result.add(linearModel.getSolution());
		
		for(int i=0; i < linearModel.getCountVar();i++) {
			result.add(0.0);
		}

		for(int i = 0; i < linearModel.getResources().size(); i++) {
			result.set(listVarBasic.get(i), linearModel.getResources().get(i));
		}
		
		System.out.println("este es el resultado : "+result);
		return result;
	}

	private void operacionDeRenglonGaussJordan(final int varIn, final int varOut) {
		
		final double elementoPivot = linearModel.getListaX().get(varOut).get(varIn);
		
		linearModel.getResources().set(varOut, linearModel.getResources().get(varOut) / elementoPivot);
		linearModel.getListaX().get(varOut).replaceAll(x -> x / elementoPivot);
		
		linearModel.setSolucion( linearModel.getSolution()- linearModel.getListZ().get(varIn)*linearModel.getResources().get(varOut));

		List<Double> z = IntStream.range(0, linearModel.getCountVar())
								  .mapToObj( x -> linearModel.getListZ().get(x) - (linearModel.getListZ().get(varIn) * linearModel.getListaX().get(varOut).get(x)))//rActual-suCoefColuPiv*NueRenPivo
								  .collect(Collectors.toCollection(ArrayList::new));
		
		linearModel.setListZ(z);
		
		List<List<Double>> x = IntStream.range(0, linearModel.getCountEcu())
										.mapToObj(row -> {if(row == varOut) {
															  return linearModel.getListaX().get(varOut);
														  } else {
															  linearModel.getResources().set(row, linearModel.getResources().get(row)-linearModel.getListaX().get(row).get(varIn)*linearModel.getResources().get(varOut));
															  return aux(linearModel.getListaX().get(row), linearModel.getListaX().get(varOut), varIn);
														  }
										})
										.collect(Collectors.toCollection(ArrayList::new));
		linearModel.setListaX(x);
	}

	private List<Double> aux(List<Double> list, List<Double> listAux, int varIn) {
		return IntStream.range(0, linearModel.getCountVar())
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
	
		if(linearModel.getListaX().stream().allMatch(x -> x.get(varIn) < 0)) {
			System.out.println("La soluci�n no esta Acotada, columna de la variable X" + (varIn+1) + " son todas negativas -> no hay variable de salida");
		}		
					
		return IntStream.rangeClosed(0, linearModel.getCountEcu()-1)
				.filter(x -> linearModel.getListaX().get(x).get(varIn) != 0) 
				.filter(z -> linearModel.getResources().get(z) / linearModel.getListaX().get(z).get(varIn) >= 0)
				.reduce((accumulator,y) -> linearModel.getResources().get(accumulator) / linearModel.getListaX().get(accumulator).get(varIn) > linearModel.getResources().get(y) / linearModel.getListaX().get(y).get(varIn) ? y : accumulator)
				.orElse(-1);		
	}

}