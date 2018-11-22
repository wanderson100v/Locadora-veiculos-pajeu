package dao;

import java.util.Date;

import javax.persistence.Query;

import entidade.Reserva;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoReserva extends Dao<Reserva> implements IDaoReserva{

	public DaoReserva() {
		super(Reserva.class);
	}

	@Override
	public int totalReservaDataRetirada(Date dataRetirada) throws DaoException {
		int total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_DATA_RETIRADA);
			query.setParameter("dataRetirada", dataRetirada);
			total = (Integer) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE RESERVAS POR DATA DE RETIRADA");
		}
		return total;
	}

}
