package dao;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.DaoException;

public interface IDaoFuncionario extends IDao<Funcionario>{
	
	public void cadastrar(Funcionario funcionario ,String login,String senha, Cargo cargo) throws DaoException;
	
	public void editar(Funcionario funcionario, String oldLogin, String newLogin)throws DaoException;
	
	public void editaSenha(Funcionario funcionario, String login,String novaSenha) throws DaoException;
	
	public String requisitarGralDeAcesso() throws DaoException;
	
	public void excluir(Funcionario funcionario, String login) throws DaoException;
	
}
