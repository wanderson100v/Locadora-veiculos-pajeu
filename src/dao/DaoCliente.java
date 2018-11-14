package dao;

import entidade.Cliente;

public class DaoCliente extends Dao<Cliente> implements IDaoCliente{

	public DaoCliente() {
		super(Cliente.class);
	}

}
