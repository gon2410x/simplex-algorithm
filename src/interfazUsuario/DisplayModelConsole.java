package interfazUsuario;

import java.util.stream.IntStream;
import modelo.ILinearModel;

final public class DisplayModelConsole {
	
	private ILinearModel linearModel;
	
	public void showDates(ILinearModel linearModel){
		this.linearModel = linearModel;
		showFunctionObjZ();
		showRestriction();
	}
	
	final private void showFunctionObjZ() {
		
		System.out.print(" z =");	
		IntStream.range(0, linearModel.getCountVar())
		 		 .forEach(x -> System.out.printf("%1s %9.3f %s", x > 0 && linearModel.getListZ().get(x) >= 0 ? "+"
		 																					   :"", linearModel.getListZ().get(x) , " X" + (x+1) + " "));
		
		System.out.println();	
	}
	
	
	final private void showRestriction() {
		
		IntStream.range(0, linearModel.getCountEcu())
		.forEach( row -> {	
			System.out.print("    ");	
			IntStream.range(0, linearModel.getCountVar())
					 .forEach( column -> {
						System.out.printf("%1s %9.3f %s", column > 0 && getRest(row,column) >= 0 ? "+"
																						         : "",getRest(row,column) , " X" + (column+1) + " ");
						});
			System.out.print(" " + linearModel.getListaDesigualdad().get(row) + " ");
			System.out.printf("%9.3f \n" , linearModel.getResources().get(row));
		});
	}
	
	final private double getRest(int row, int column) {
		return linearModel.getListaX().get(row).get(column);
	}

}
