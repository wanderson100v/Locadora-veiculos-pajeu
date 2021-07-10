package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.vo.Entidade;

public interface IDao <T extends Entidade>{
	
	String CAMINHO_CLASSE = "model.vo.";
	
	public void cadastrar(T t) throws DaoException;
	
	public void editar(T t) throws DaoException;
	
	public T buscarID(Long id) throws DaoException;
	
	public void excluir(T t) throws DaoException;
	
	public List<T> buscarAll() throws DaoException ;
	
	public List<T> buscarPorExemplo(T exemploEntidade)throws DaoException;
	
	public List<T> buscaPorBuscaAbrangente(String busca) throws DaoException;
	
}
