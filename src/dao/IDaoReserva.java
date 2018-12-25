package dao;

import java.util.List;

import banco.ReservaHoje;
import banco.ReservaPendente;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Reserva;
import excecoes.DaoException;

public interface IDaoReserva extends IDao<Reserva>{
	
	String TOTAL_DATA_RETIRADA = "reserva.totalDataRetirada";
	String TUDO_RESERVA_HOJE = "reservaHoje.buscarTudo";
	String RESERVA_PENDENTE_POR_CLIENTE = "reservaPendente.buscarPorCliente";
	String RESERVA_PENDENTE_POR_CLIENTE_FILIAL = "reservaPendente.buscarPorCliente_Filial";
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws DaoException;
		
	public List<ReservaHoje> buscarReservaHoje() throws DaoException;
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente) throws DaoException ;
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente, Filial filial) throws DaoException ;

}
