package interfazUsuario;

import java.util.stream.IntStream;

import modelo.Model;

final public class InterfaceUsuarioMostrarDatos {
	
	private Model model;
	
	public void showDates(Model model){
		this.model = model;
		showFunctionObjZ();
		showRestriction();
	}
	
	final private void showFunctionObjZ() {
		
		System.out.print(" z =");	
		IntStream.range(0, model.getCountVar())
		 		 .forEach(x -> System.out.printf("%1s %9.3f %s", x > 0 && model.getListZ().get(x) >= 0 ? "+"
		 																					   :"", model.getListZ().get(x) , " X" + (x+1) + " "));
//		int count = 0;
//		while(count < model.getCountVar()) {
//			System.out.printf("%1s %9.3f %s", count > 0 && model.getListZ().get(count) >= 0 ? "+" :"", model.getListZ().get(count) , " X" + (count+1) + " ");
//			count++;
//		}
		
		System.out.println();	
	}
	
	
	final private void showRestriction() {

		IntStream.range(0, model.getCountEcu())
		.forEach( row -> {	
			System.out.print("    ");	
			IntStream.range(0, model.getCountVar())
					 .forEach( column -> {
						System.out.printf("%1s %9.3f %s", column > 0 && getRest(row,column) >= 0 ? "+"
																						         : "",getRest(row,column) , " X" + (column+1) + " ");
						});
			System.out.print(" " + model.getListaDesigualdad().get(row) + " ");
			System.out.printf("%9.3f \n" , model.getResources().get(row));
		});
	}
	
	final private double getRest(int row, int column) {
		return model.getListaX().get(row).get(column);
	}

}
