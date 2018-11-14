package dao;

import entidade.Automovel;

public class DaoAutomovel extends Dao<Automovel> implements IDaoAutomovel {

	public DaoAutomovel() {
		super(Automovel.class);
	}

}
