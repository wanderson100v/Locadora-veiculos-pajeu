package dao;

import java.util.Date;

import entidade.Reserva;
import excecoes.DaoException;

public interface IDaoReserva extends IDao<Reserva>{
	
	String TOTAL_DATA_RETIRADA = "totalDataRetirada";
	
	public int totalReservaDataRetirada(Date dataRetirada) throws DaoException;
		
}
