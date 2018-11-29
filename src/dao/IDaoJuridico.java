package dao;


import entidade.Juridico;
import excecoes.DaoException;
import java.util.List;

public interface IDaoJuridico extends IDao<Juridico>{
	
	String BUSCA_POR_BUSCA = "juridico.buscaPorBusca";
	
	public List<Juridico> buscaPorBusca(Juridico juridico) throws DaoException;
}
