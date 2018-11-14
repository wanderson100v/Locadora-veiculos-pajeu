package dao;

import entidade.Fisico;

public class DaoFisico extends Dao<Fisico> implements IDaoFisico {
	
	public DaoFisico() {
		super(Fisico.class);
	}
}
