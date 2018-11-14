package dao;

import entidade.Reserva;

public class DaoReserva extends Dao<Reserva> implements IDaoReserva{

	public DaoReserva() {
		super(Reserva.class);
	}

}
