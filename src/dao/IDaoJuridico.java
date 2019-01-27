package dao;


import java.util.List;

import entidade.Juridico;
import excecoes.DaoException;

public interface IDaoJuridico extends IDao<Juridico>{
	
	public List<Juridico> buscaPorBuscaAbrangente(String busca, Juridico juridico) throws DaoException;
}
