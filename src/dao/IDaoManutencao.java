package dao;

import entidade.Manutencao;
import excecoes.DaoException;

public interface IDaoManutencao  extends IDao<Manutencao>{

	public int checarManutencao() throws DaoException;
	
}
