package dao;

import entidade.Acessorio;

public class DaoAcessorio extends Dao<Acessorio> implements IDaoAcessorio {
	
	public DaoAcessorio() {
		super(Acessorio.class);
	}
}
