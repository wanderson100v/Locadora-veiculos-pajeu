package model.dao;

import java.time.LocalDate;
import java.util.List;
import model.excecoes.DaoException;
import model.vo.Fisico;

public interface IDaoFisico extends IDao<Fisico>{
	String BUSCA_POR_BUSCA = "fisico.buscaPorBusca";
	
	public List<Fisico> buscarMotoristasValidos(Fisico fisico, LocalDate dataSuperior) throws DaoException;
	
	public List<Fisico> buscaPorBuscaAbrangente(String busca, Fisico fisico) throws DaoException ;
}

