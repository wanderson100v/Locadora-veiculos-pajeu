package model.dao;

import model.entidade.Automovel;

public class DaoAutomovel extends Dao<Automovel> implements IDaoAutomovel {

	public DaoAutomovel() {
		super(Automovel.class);
	}

}
