package controller;

import model.FachadaModel;
import model.FuncionarioObservavel;
import model.IObservadorFuncionario;
import view.Alerta;

public abstract class Controller implements IObservadorFuncionario{

    protected FachadaModel fachadaModel;
    
    protected Alerta alerta;
    
    public Controller() {
    	this.fachadaModel = FachadaModel.getInstance();
    	this.alerta = Alerta.getInstance();
    	FuncionarioObservavel.getIntance().addObservadorFuncionario(this);
    }
    
	
}
