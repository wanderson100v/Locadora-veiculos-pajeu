package business;


import java.time.LocalDateTime;
import java.util.List;

import adapter.ReservaDisponibilidade;
import banco.ReservaHoje;
import banco.ReservaPendente;
import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Reserva;
import excecoes.BoException;

public interface IBoReserva extends IBussines<Reserva>{
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException;
	
	public List<ReservaHoje> buscarReservaHoje() throws BoException;
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente) throws BoException;
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente, Filial filial)throws BoException; 
	
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CategoriaVeiculo categoriaVeiculo, Long filialId,LocalDateTime horario) throws BoException;
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)throws BoException;
	
}
