package dao;

import java.time.LocalDateTime;

import javax.persistence.Query;

import entidade.CategoriaVeiculo;
import entidade.Reserva;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoReserva extends Dao<Reserva> implements IDaoReserva{

	public DaoReserva() {
		super(Reserva.class);
	}

	@Override
	public int totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo, LocalDateTime dataRetirada) throws DaoException {
		int total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_DATA_RETIRADA);
			query.setParameter("categoriaVeiculo", categoriaVeiculo);
			query.setParameter("dataRetirada", dataRetirada);
			total = (Integer) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE RESERVAS POR CATEGORIA E DATA DE RETIRADA ");
		}
		return total;
	}

}
