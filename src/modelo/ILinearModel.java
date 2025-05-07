/**
 * 
 */
package modelo;

import java.util.List;

/**
 * 
 */
public interface ILinearModel {
	
	int getCountVar();
	void setCountVar(int countVar);
	
	int getCountEcu();
	
	Double getSolution();
	void setSolucion(Double solucion);

	List<Double> getListZ();
	void setListZ(List<Double> listaZ);
	
	List<List<Double>> getListaX();
	void setListaX(List<List<Double>> listaX);
	
	List<Double> getResources();
	void setResources(List<Double> resources);
	
	List<String> getListaDesigualdad();
	void setListaDesigualdad(List<String> listaDesigualdad);

}
