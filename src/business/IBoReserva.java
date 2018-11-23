package business;

import java.time.LocalDateTime;

import entidade.CategoriaVeiculo;
import entidade.Reserva;
import excecoes.BoException;

public interface IBoReserva extends IBussines<Reserva>{
	
	public int totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo, LocalDateTime dataRetirada) throws BoException;
	
}
