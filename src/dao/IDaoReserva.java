package dao;

import java.util.List;

import banco.ReservaHoje;
import entidade.CategoriaVeiculo;
import entidade.Reserva;
import excecoes.DaoException;

public interface IDaoReserva extends IDao<Reserva>{
	
	String TOTAL_DATA_RETIRADA = "reserva.totalDataRetirada";
	String TUDO_RESERVA_HOJE = "reservaHoje.buscarTudo";
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws DaoException;
		
	public List<ReservaHoje> buscarReservaHoje() throws DaoException;
}
