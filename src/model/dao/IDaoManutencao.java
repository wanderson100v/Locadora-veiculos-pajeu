package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.vo.Manutencao;

public interface IDaoManutencao  extends IDao<Manutencao>{

	public int checarManutencao() throws DaoException;
	
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Manutencao manutencao) throws DaoException;
	
}
