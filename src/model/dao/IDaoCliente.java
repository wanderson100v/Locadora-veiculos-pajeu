package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.vo.Cliente;

public interface IDaoCliente extends IDao<Cliente>{

	String BUSCA_POR_BUSCA = "select c from "+CAMINHO_CLASSE+"Cliente as c"
		+ " where c.ativo = true and ("
		+ " upper(c.nome) like upper(:nome)"
		+ " or upper(c.codigo) like upper(:codigo)"
		+ " or upper(c.email) like upper(:email)"
		+ " or upper(c.telefone) like upper(:telefone))";
	
	public List<Cliente> buscaPorBusca(Cliente cliente) throws DaoException;
}
