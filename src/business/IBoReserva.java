package business;


import java.util.List;

import banco.ReservaHoje;
import banco.ReservaPendente;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Reserva;
import excecoes.BoException;

public interface IBoReserva extends IBussines<Reserva>{
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException;
	
	public List<ReservaHoje> buscarReservaHoje() throws BoException;
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente) throws BoException;
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente, Filial filial)throws BoException; 
	
}
