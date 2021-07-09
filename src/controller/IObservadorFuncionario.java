package controller;

import entidade.Funcionario;
import enumeracoes.Cargo;

public interface IObservadorFuncionario {
	
	public void atualizar(Funcionario funcionario, Cargo cargo);
}
