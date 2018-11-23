package dao;

import java.time.LocalDateTime;

import entidade.CategoriaVeiculo;
import entidade.Reserva;
import excecoes.DaoException;

public interface IDaoReserva extends IDao<Reserva>{
	
	String TOTAL_DATA_RETIRADA = "totalDataRetirada";
	
	public int totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo, LocalDateTime dataRetirada) throws DaoException;
		
}
