package dao;

import java.util.List;
import java.util.Map;

import entidade.Entidade;
import excecoes.DaoException;

public interface IDao <T extends Entidade>{
	
	public void cadastrar(T t) throws DaoException;
	
	public void editar(T t) throws DaoException;
	
	public T buscarID(Long id) throws DaoException;
	
	public void excluir(T t) throws DaoException;
	
	public List<T> buscarAll() throws DaoException ;
	
	public List<T> buscarPorExemplo(T exemploEntidade)throws DaoException;
	
	public List<T> buscaPorBuscaAbrangente(String busca) throws DaoException;
	
	public List<T> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws DaoException;
	
}
