package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.entidade.Filial;

public interface IDaoFilial extends IDao<Filial>{
	
	String BUSCA_POR_BUSCA = "filial.buscaPorBusca";
	
	public List<Filial> buscaPorBusca(Filial filial) throws DaoException;
	
}
