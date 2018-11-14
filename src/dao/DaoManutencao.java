package dao;

import entidade.Manutencao;

public class DaoManutencao extends Dao<Manutencao> implements IDaoManutencao{

	public DaoManutencao() {
		super(Manutencao.class);
	}

}
