/**
 * 
 */
package modelo;

import java.util.List;

/**
 * 
 */
public interface IModel {

	int getCountVar();
	int getCountEcu();
	
	Double getSolution();
	
	List<Integer> getListVarBasic();

	List<Double> getListZ();
	void setListZ(List<Double> listaZ);
	List<List<Double>> getListaX();
	void setListaX(List<List<Double>> listaX);
	List<Double> getResources();
	void setResources(List<Double> resources);
	List<String> getListaDesigualdad();
	void setListaDesigualdad(List<String> listaDesigualdad);
	
	IModel standardize();
	List<Double> metodoSimple();
}
