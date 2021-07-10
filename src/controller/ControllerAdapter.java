package controller;

import model.FuncionarioObservavel;
import model.enumeracoes.Cargo;
import model.vo.Funcionario;

public class ControllerAdapter extends Controller {

	public ControllerAdapter() {
		super();
		FuncionarioObservavel.getIntance().removerObservadorFuncionario(this);
	}
	
	@Override
	public void atualizar(Funcionario funcionario, Cargo cargo) {}

}
