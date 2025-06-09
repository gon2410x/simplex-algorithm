/**
 * 
 */
package twoPhaseMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import interfazUsuario.DisplayTableConsole;
import modelo.ILinearModel;
import modelo.IModel;
import modelo.LinearModel;

/**
 * 
 */
public class TwoPhaseMethod implements IModel {
		
	private ILinearModel linearModel;
	final private ArrayList<Double> ZOrigin;
	private List<Integer> listVarBasic;
	
	private List<Integer> listVarArtificial;
	private List<Double> listCj;
	

	/**
	 * @param linearModel
	 */
	public TwoPhaseMethod(ILinearModel linearModel) {
		super();
		System.out.println("Two Phase Method");
		this.linearModel = linearModel;
		linearModel.setSolucion(0.0);
		listVarBasic = new ArrayList<Integer>();
		listVarArtificial = new ArrayList<Integer>();
		listCj = new ArrayList<Double>();
		ZOrigin = new ArrayList<Double>();
		linearModel.getListZ().forEach(z -> ZOrigin.add(z)); 
	}
	
	/**
	 * @param countVar
	 * @param countEcu
	 */
	public TwoPhaseMethod(int countVar, int countEcu, String optimization) {
		super();
		System.out.println("Two Phase Method");
		linearModel = new LinearModel(countVar, countEcu, optimization);
		linearModel.setSolucion(0.0);
		listVarBasic = new ArrayList<Integer>();
		listVarArtificial = new ArrayList<Integer>();
		listCj = new ArrayList<Double>();
		ZOrigin = new ArrayList<Double>();
	}
	
	

	@Override
	public ILinearModel getLinearModel() {
		return this.linearModel;
	}

	@Override
	public List<Integer> getListVarBasic() {
		return listVarBasic;
	}

	private ArrayList<Integer> penalty = new ArrayList<Integer>();
	
	@Override
	public IModel standardize() {
		restriccionLadoDerechoNoNegativo();
		
		final int numberOfRestrictions = linearModel.getCountEcu();
		for (int row = 0; row < numberOfRestrictions; row++) {	
			
			if(linearModel.getListaDesigualdad().get(row) == "=") {
				penalty.add(row);
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
				penalty.add(row);
				listVarBasic.add(++countVarBasic);
				linearModel.setCountVar(linearModel.getListaX().get(0).size());
			}
		}		

		linearModel.getListZ().forEach(z -> listCj.add(z));
		linearModel.getListZ().replaceAll(x -> 0.0);

		
		for(int row = 0; row < penalty.size(); row++) {
			restriccionPenalizacion(penalty.get(row));
			linearModel.setCountVar(linearModel.getListaX().get(0).size());
			int i = listVarBasic.indexOf(listVarBasic.get( penalty.get(row)));
			listVarBasic.set(i, linearModel.getCountVar());
			listVarArtificial.add(linearModel.getCountVar());  // <----------------
		}
		

//		restriccionLadoDerechoNoNegativo();
		return this;
	}
	
	
	final private void restriccionMenorQue(int row) {
		
		linearModel.getListaX().forEach(x -> x.add(0.0));
		linearModel.getListaX().get(row).set(linearModel.getListaX().get(row).size()-1 , 1.0);
		linearModel.getListZ().add(0.0);
		linearModel.getListaDesigualdad().set(row, "=");			
	}
	
	final private void restriccionMayorQue(int row) {
		
		linearModel.getListaX().forEach(x -> x.add(0.0));
		linearModel.getListaX().get(row).set(linearModel.getListaX().get(row).size()-1 , -1.0);
		linearModel.getListZ().add(0.0);
		linearModel.getListaDesigualdad().set(row, "=");			
	}
	
	final private void restriccionPenalizacion(int row) {
		
		linearModel.getListaX().forEach(x -> x.add(0.0));
		linearModel.getListaX().get(row).set(linearModel.getListaX().get(row).size()-1 , 1.0);
		linearModel.getListZ().add( 1.0 );
		linearModel.getListaDesigualdad().set(row, "=");			
	}
	
	final private void restriccionLadoDerechoNoNegativo() {
		
		IntStream.range(0, linearModel.getCountEcu())
				 .forEach(row -> {		
					 if(linearModel.getResources().get(row) < 0) {
						 linearModel.getResources().set(row, linearModel.getResources().get(row)*-1);
						 linearModel.getListaX().get(row).replaceAll(x -> -x);
						 
						 if(linearModel.getListaDesigualdad().get(row) == "=>")
							 linearModel.getListaDesigualdad().set(row, "=<");
						 else if(linearModel.getListaDesigualdad().get(row) == "=<")
							 linearModel.getListaDesigualdad().set(row, "=>");
					 }
		});
		
//		linearModel.getResources().forEach(y -> {if(y>0) {y = -y; System.out.println("dd");}});
		
	}
	

	public List<Double> firstPhase() {
		int numberOfIteration = 0;
		linearModel.getListZ().replaceAll(z -> z == 0 ? 0 : z * -1);
		eliminarInconcistencia(penalty);
		new DisplayTableConsole().MostrarDatos(this, numberOfIteration++);
				
		int varIn = 0;
		int varOut = 0;
		
		while( true ) { // Iteration

			varIn = getVarInMinimization(linearModel.getListZ());
			System.out.print("-------- : "+varIn+"\n");
			
			if ( varIn < 0) { break;} 
			
			varOut = getVarOut(varIn);
			System.out.println("este valor es :: "+varOut);
			if( varOut < 0) break;
			
			System.out.print("Variable de Entrada : X"+(varIn+1)+"   -    Variable de Salida : X"+(listVarBasic.get(varOut)));
			operacionDeRenglonGaussJordan(varIn,varOut);
			listVarBasic.set(varOut, varIn+1);
			linearModel.getListZ().forEach(z -> System.out.println(z));
			new DisplayTableConsole().MostrarDatos(this, numberOfIteration++);
			
		}
		
		System.out.println("este es el resultado : "+22222);
		return null;
	}
	
	
	@Override
	public List<Double> metodoSimple() {
		firstPhase();
		System.out.println("##### Second Phase #####");

		for (int i = (listVarArtificial.size()-1); i >= 0; i--) {
			final int e = listVarArtificial.get(i) - 1;
			linearModel.getListZ().remove(e);
			linearModel.getListaX().forEach(x -> x.remove(e) );
			linearModel.setCountVar(linearModel.getCountVar() - 1);
		}
		
		linearModel.setListZ(listCj);
		
		int numberOfIteration = 0;
		linearModel.getListZ().replaceAll(z -> z == 0 ? 0 : z * -1);
		eliminarInconcistencia2(penalty);
		new DisplayTableConsole().MostrarDatos(this, numberOfIteration++);
				
		int varIn = 0;
		int varOut = 0;
		
		while( true ) { // Iteration

			if(linearModel.getOptimization() == "MAXIMIZE") {
				varIn = getVarIn(linearModel.getListZ());				
			}else {				
				varIn = getVarInMinimization(linearModel.getListZ());
			}
			
			System.out.print("-------- : "+varIn+"\n");
			
			if ( varIn < 0) { break;}
			
			varOut = getVarOut(varIn);
			System.out.println("este valor es :: "+varOut);
			if( varOut < 0) break;
			
			System.out.print("Variable de Entrada : X"+(varIn+1)+"   -    Variable de Salida : X"+(listVarBasic.get(varOut)));
			operacionDeRenglonGaussJordan(varIn,varOut);
			listVarBasic.set(varOut, varIn+1);
			linearModel.getListZ().forEach(z -> System.out.println(z));
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
	
	final private void eliminarInconcistencia(ArrayList<Integer> p) {
		for(int column = 0; column < linearModel.getListZ().size(); column++) {

			double newZ = linearModel.getListZ().get(column);
			
			for(int i=0; i<p.size();i++) {
				newZ += 1 * linearModel.getListaX().get(p.get(i)).get(column);
			}
			
			linearModel.getListZ().set(column, newZ);
		}
		
		double ff=0;
		for(int i=0; i<p.size();i++) {
			ff += linearModel.getResources().get(p.get(i))*1;
		}
//		p.forEach(x -> { ff = linearModel.getResources().get(x) * 1; });
		
		linearModel.setSolucion(ff);
	}
	
	
	
	
	final private void eliminarInconcistencia2(ArrayList<Integer> p) {
		
		ArrayList<Integer> aux = new ArrayList<Integer>();
		this.listVarBasic.forEach( v -> { if( v <= ZOrigin.size() ) { aux.add(v);} });
		
		for(int column = 0; column < linearModel.getListZ().size(); column++) {

			double newZ = linearModel.getListZ().get(column);
			
			for(int row = 0; row < aux.size(); row++) {
				final int pos = this.listVarBasic.indexOf(aux.get(row));
				newZ += ZOrigin.get(aux.get(row)-1) * linearModel.getListaX().get(pos).get(column);
			}
						
			linearModel.getListZ().set(column, newZ);
		}
		
		double ff=0;
		for(int i = 0; i < aux.size(); i++) {
			final int pos = this.listVarBasic.indexOf(aux.get(i));
			ff += linearModel.getResources().get(pos) * ZOrigin.get(aux.get(i)-1);
		}
		
		linearModel.setSolucion(ff);
	}
	
	
	/**
	 * @param list
	 * @return int que representa la posicion del elemento con valor mas negativo de la lista, retorna -1 si la lista no contiene valores negativos.
	 * @Throws
	 */
	private int getVarInMinimization(List<Double> list) {

		if(list.stream().anyMatch(x -> x > 0)) {
			final double aux = list.stream().max(Double::compare).get();
			return list.indexOf(aux);
		}
		return -1;
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
	
		if(linearModel.getListaX().stream().allMatch(x -> x.get(varIn) < 0)) {
			System.out.println("La soluci�n no esta Acotada, columna de la variable X" + (varIn+1) + " son todas negativas -> no hay variable de salida");
		}		
					
		return IntStream.rangeClosed(0, linearModel.getCountEcu()-1)
				.filter(x -> linearModel.getListaX().get(x).get(varIn) != 0) 
				.filter(z -> linearModel.getResources().get(z) / linearModel.getListaX().get(z).get(varIn) >= 0)
				.reduce((accumulator,y) -> linearModel.getResources().get(accumulator) / linearModel.getListaX().get(accumulator).get(varIn) > linearModel.getResources().get(y) / linearModel.getListaX().get(y).get(varIn) ? y : accumulator)
				.orElse(-1);		
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

}
