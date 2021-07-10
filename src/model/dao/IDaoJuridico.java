package model.dao;


import java.util.List;

import model.excecoes.DaoException;
import model.vo.Juridico;

public interface IDaoJuridico extends IDao<Juridico>{
	
	public List<Juridico> buscaPorBuscaAbrangente(String busca, Juridico juridico) throws DaoException;
}
