/**
 * 
 */
package main;

import gui.GUI;
import interfazUsuario.DisplayModelConsole;
import interfazUsuario.LoadModel;
import modelo.IModel;
import modelo.IModel;
import modelo.Model;
import modeloM.ModelM;
import twoPhaseMethod.TwoPhaseMethod;

import javax.swing.*;

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
		
		IModel model = new TwoPhaseMethod(new LoadModel().ingresarDatos());
		new DisplayModelConsole().showDates(model.getLinearModel());
		
		System.out.println("\n\n\tModelo Estandarizaci�n\n\t----------------------\n");
		model = model.standardize();
		new DisplayModelConsole().showDates(model.getLinearModel());
		System.out.println("\n\n");	
		
		System.out.println("El resultado del metodo simple : "+model.metodoSimple());
		System.out.println("\n\n\tFin del M�todo Simplex\n");
		new DisplayModelConsole().showDates(model.getLinearModel());


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});
	}	
}
