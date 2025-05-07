package modelo;

import java.util.List;

public interface IModel {

	ILinearModel getLinearModel();
	
	List<Integer> getListVarBasic();
	
	IModel standardize();
	
	List<Double> metodoSimple();
}
