package dao;
import java.util.List;

import entidade.Fisico;
import excecoes.DaoException;

public interface IDaoFisico extends IDao<Fisico>{
	String BUSCA_POR_BUSCA = "fisico.buscaPorBusca";
	
	public List<Fisico> buscaPorBusca(Fisico fisico) throws DaoException;
}

