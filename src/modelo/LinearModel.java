/**
 * 
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 */
public class LinearModel implements ILinearModel {

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

	public LinearModel(int countVar, int countEcu) {
		super();
		this.countVar = countVar;
		this.countEcu = countEcu;
		functionZ = new ArrayList<Double>();
		listX = IntStream.range(0, this.countEcu).mapToObj( x -> new ArrayList<Double>()).collect(Collectors.toCollection(ArrayList::new));
		listDesigualdad = new ArrayList<String>();		
		resources = new ArrayList<Double>();
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
	
}