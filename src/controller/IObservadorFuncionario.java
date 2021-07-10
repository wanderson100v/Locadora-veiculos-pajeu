package controller;

import model.enumeracoes.Cargo;
import model.vo.Funcionario;

public interface IObservadorFuncionario {
	
	public void atualizar(Funcionario funcionario, Cargo cargo);
}
