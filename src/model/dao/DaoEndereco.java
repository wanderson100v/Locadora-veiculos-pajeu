package model.dao;

import model.entidade.Endereco;
public class DaoEndereco extends Dao<Endereco> implements IDaoEndereco{

	public DaoEndereco() {
		super(Endereco.class);
	}

}
