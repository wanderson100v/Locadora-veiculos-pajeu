package dao;

import java.util.List;

import entidade.Filial;
import excecoes.DaoException;

public interface IDaoFilial extends IDao<Filial>{
	
	String BUSCA_POR_BUSCA = "filial.buscaPorBusca";
	
	public List<Filial> buscaPorBusca(Filial filial) throws DaoException;
	
}
