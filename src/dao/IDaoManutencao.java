package dao;

import java.util.List;

import entidade.Manutencao;
import excecoes.DaoException;

public interface IDaoManutencao  extends IDao<Manutencao>{

	public int checarManutencao() throws DaoException;
	
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Manutencao manutencao) throws DaoException;
	
}
