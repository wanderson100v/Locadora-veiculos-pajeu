package dao;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.DaoException;

public interface IDaoFuncionario extends IDao<Funcionario>{
	
	public void cadastrar(Funcionario funcionario ,String senha, Cargo cargo) throws DaoException;
	
	public void editaSenha(Funcionario funcionario, String novaSenha) throws DaoException;
}
