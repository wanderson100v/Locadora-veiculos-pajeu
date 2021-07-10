package model.dao;

import model.vo.Endereco;
public class DaoEndereco extends Dao<Endereco> implements IDaoEndereco{

	public DaoEndereco() {
		super(Endereco.class);
	}

}
