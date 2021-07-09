package controller;

import mode.enumeracoes.Cargo;
import model.entidade.Funcionario;

public interface IObservadorFuncionario {
	
	public void atualizar(Funcionario funcionario, Cargo cargo);
}
