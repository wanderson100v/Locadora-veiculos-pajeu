package business;


import java.util.List;

import banco.ReservaHoje;
import entidade.CategoriaVeiculo;
import entidade.Reserva;
import excecoes.BoException;

public interface IBoReserva extends IBussines<Reserva>{
	
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws BoException;
	
	public List<ReservaHoje> buscarReservaHoje() throws BoException;
	
}
