package dao;

import entidade.Endereco;
public class DaoEndereco extends Dao<Endereco> implements IDaoEndereco{

	public DaoEndereco() {
		super(Endereco.class);
	}

}
