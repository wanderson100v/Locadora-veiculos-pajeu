package dao;

import java.util.List;

import entidade.Cliente;
import excecoes.DaoException;

public interface IDaoCliente extends IDao<Cliente>{

	String BUSCA_POR_BUSCA = "cliente.buscaPorBusca";
	
	public List<Cliente> buscaPorBusca(Cliente cliente) throws DaoException;
}
