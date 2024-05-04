package interfazUsuario;

import java.util.stream.IntStream;

import modelo.Model;

public class InterfazUsuarioMostrarTablaSimple {

	private Model modelo;
	
	public void MostrarDatos(Model modelo, int numberOfIterations){
		this.modelo = modelo;
		
		System.out.println("\n\t\n\t\t| Tabla Simplex "+numberOfIterations+"|\n\t\t-----------------\n");
		mostrarBasica();
		mostrarFuncionObjZ();
		mostrarRestriccionesX();
		System.out.println("\n\n\t---------------------------------------------");		
	}
	
	private void mostrarBasica() {

		System.out.print("Basic  ");		
		IntStream.range(0, modelo.getCountVar())
				 .forEach(x -> System.out.printf("%16s","X"+(x+1)));
		System.out.printf("%10s\n","Solution");
		
	}

	private void mostrarFuncionObjZ() {
		System.out.print(" z\t");	
		IntStream.range(0, modelo.getCountVar())
		 		.forEach(x -> System.out.printf("%1s %9.3f %s", x > 0 && modelo.getListZ().get(x) >= 0 ? "+"
		 																					   :"", modelo.getListZ().get(x) , " X" + (x+1) + " "));
		
		System.out.printf("%9.3f", modelo.getSolucion() );
		System.out.println();	
	}
	
	
	private void mostrarRestriccionesX() {

		IntStream.range(0, modelo.getCountEcu())
			.forEach( row -> {	
				System.out.print("X"+modelo.getListVarBasic().get(row)+"\t");
				IntStream.range(0, modelo.getCountVar())
						.forEach( column -> {
							System.out.printf("%1s %9.3f %s", column > 0 && getRest(row,column) >= 0 ? "+"
																							         : "",getRest(row,column) , " X" + (column+1) + " ");
							});

				System.out.printf("%9.3f\n",modelo.getResources().get(row));
			});
	}
	
	final private double getRest(int row, int column) {
		return modelo.getListaX().get(row).get(column);
	}
}
