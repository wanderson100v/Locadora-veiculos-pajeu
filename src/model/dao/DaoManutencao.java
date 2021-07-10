package model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.excecoes.DaoException;
import model.vo.Manutencao;
import model.dao.sql.ConnectionFactory;

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
			throw new DaoException("ERRO AO CHECAR MANUT��ES");
		}finally {
			em.close();
		}
		return quantidade;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Manutencao manutencao) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			
			Map<String,String> restricoes = new HashMap<>();
			if(manutencao.getVeiculo()!= null)
				restricoes.put("veiculo.id","="+manutencao.getVeiculo().getId());
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(Manutencao.class, restricoes),Manutencao.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR MANUTEN��ES DE FORMAR ABRANGENTE RESTRITIVA");
		}finally {
			em.close();
		}
	}
}


