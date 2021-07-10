package model.business;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import model.excecoes.BoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Reserva;
import model.adapter.ReservaDisponibilidade;
import model.banco.ReservaHoje;
import model.banco.ReservaPendente;

public interface IBoReserva extends IBussines<Reserva>{
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException;
	
	public List<ReservaHoje> buscarReservaHoje() throws BoException;
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente) throws BoException;
	
	public List<ReservaPendente> buscarReservaPendente(String dadoCliente, Filial filial)throws BoException; 
	
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CategoriaVeiculo categoriaVeiculo, Long filialId,LocalDateTime horario) throws BoException;
	
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)throws BoException;
	
	public Boolean disponibilidadeCategoriaEmFilial(Long categoriaVeiculoId, Long filialId, LocalDateTime horario) throws BoException;

	public List<Reserva> buscaPorBuscaAbrangente(String busca, Reserva reserva,LocalDate de , LocalDate ate) throws BoException;

	public List<Map<String, Object>> buscarReservasOrigemLocacaoFinalizada(LocalDate de , LocalDate ate, String agruparPor) throws BoException;

	public List<Map<String, Object>> buscarReservasImpedidas(LocalDate de , LocalDate ate)throws BoException;
}