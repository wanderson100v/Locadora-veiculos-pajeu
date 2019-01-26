package dao;

import entidade.Manutencao;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoManutencao extends Dao<Manutencao> implements IDaoManutencao{

	public DaoManutencao() {
		super(Manutencao.class);
	}
	
	public int checarManutencao() throws DaoException {
		int quantidade = 0;
		try {
			em = ConnectionFactory.getConnection();
			em.getTransaction().begin();
			quantidade = em.createNativeQuery("UPDATE Manutencao  set estado = 2 "
					+ " where estado = 1 "
					+ " and data_hora_inicio + make_interval(hours \\:= custo_horas) <= current_timestamp")
				.executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("ERRO AO CHECAR MANUTENÇÕOES");
		}finally {
			em.close();
		}
		return quantidade;
	}
	
}


