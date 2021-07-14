package model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.sql.ConnectionFactory;
import model.excecoes.DaoException;
import model.vo.Automovel;

public class DaoAutomovel extends Dao<Automovel> implements IDaoAutomovel {

	public DaoAutomovel() {
		super(Automovel.class);
	}

	@SuppressWarnings("unchecked")
	public List<Automovel> buscaPorBuscaAbrangente(String busca) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Map<String,String> restricoes = new HashMap<>();
			restricoes.put("veiculo.ativo","= TRUE");
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(Automovel.class, restricoes),Automovel.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR AUTOMÓVEIS");
		}finally {
			em.close();
		}
	}
}
