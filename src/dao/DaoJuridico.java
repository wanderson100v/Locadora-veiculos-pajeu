package dao;

import entidade.Juridico;

public class DaoJuridico extends Dao<Juridico> implements IDaoJuridico{

	public DaoJuridico() {
		super(Juridico.class);
	}

}
