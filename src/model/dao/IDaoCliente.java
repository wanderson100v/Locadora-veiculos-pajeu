package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.entidade.Cliente;

public interface IDaoCliente extends IDao<Cliente>{

	String BUSCA_POR_BUSCA = "cliente.buscaPorBusca";
	
	public List<Cliente> buscaPorBusca(Cliente cliente) throws DaoException;
}
