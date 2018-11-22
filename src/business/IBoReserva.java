package business;

import java.util.Date;

import entidade.Reserva;
import excecoes.BoException;

public interface IBoReserva extends IBussines<Reserva>{
	
	public int totalReservaDataRetirada(Date dataRetirada) throws BoException;
	
}
