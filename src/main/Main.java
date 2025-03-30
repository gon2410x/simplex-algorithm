/**
 * 
 */
package main;

import interfazUsuario.InterfaceUsuarioMostrarDatos;
import interfazUsuario.InterfazUsuarioCargarDatos;
import modelo.IModel;
import modelo.Model;
import modeloM.ModelM;

/**
 * @author personal
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("\tInicio del Metodo Simple\n\t------------------------\n\n\n");
					
		IModel model = new InterfazUsuarioCargarDatos().ingresarDatos();
		new InterfaceUsuarioMostrarDatos().showDates(model);
		
		System.out.println("\n\n\tModelo Estandarizaci�n\n\t----------------------\n");
		model = model.standardize();
		new InterfaceUsuarioMostrarDatos().showDates(model);
		System.out.println("\n\n");	
		
		System.out.println("El resultado del metodo simple : "+model.metodoSimple());
		System.out.println("\n\n\tFin del M�todo Simplex\n");
		new InterfaceUsuarioMostrarDatos().showDates(model);
 
	}	
}
