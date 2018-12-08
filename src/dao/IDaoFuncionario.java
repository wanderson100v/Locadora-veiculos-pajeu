package dao;

import java.util.List;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.DaoException;

public interface IDaoFuncionario extends IDao<Funcionario>{
	
	String BUSCA_POR_BUSCA = "funcionario.buscaPorBusca";
	
	public List<Funcionario> buscaPorBusca(Funcionario funcionario) throws DaoException;
	
	public void cadastrar(Funcionario funcionario ,String login,String senha, Cargo cargo) throws DaoException;
	
	public void excluir(Funcionario funcionario, String login) throws DaoException;
	
	public void editar(Funcionario funcionario, String oldLogin, String newLogin)throws DaoException;
	
	public void editaSenha(Funcionario funcionario, String login,String novaSenha) throws DaoException;
	
	public List<String> requisitarGralDeAcesso(String login) throws DaoException;
	
	public void utilizarGralAcesso(Cargo cargo) throws DaoException;
	
	public void alterarGralAcesso(String login,Cargo oldCargo,Cargo newCargo)throws DaoException;
	
}
