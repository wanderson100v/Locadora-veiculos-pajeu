package dao;

import java.util.List;

import entidade.Entidade;
import excecoes.DaoException;

public interface IDao <T extends Entidade>{
	
	public void cadastrar(T t) throws DaoException;
	
	public void editar(T t) throws DaoException;
	
	public T buscarId(Long id) throws DaoException;
	
	public void excluir(T t) throws DaoException;
	
	public List<T> buscarAll() throws DaoException ;
	
}
