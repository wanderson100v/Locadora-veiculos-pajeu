package model.dao;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.sql.ConnectionFactory;
import model.excecoes.DaoException;
import model.vo.CaminhonetaCarga;

public class DaoCaminhonetaCarga extends Dao<CaminhonetaCarga> implements IDaoCaminhonetaCarga{

	public DaoCaminhonetaCarga() {
		super(CaminhonetaCarga.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<CaminhonetaCarga> buscaPorBuscaAbrangente(String busca) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Map<String,String> restricoes = new HashMap<>();
			restricoes.put("veiculo.ativo","= TRUE");
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(CaminhonetaCarga.class, restricoes),CaminhonetaCarga.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CAMINHONETAS DE CARGA");
		}finally {
			em.close();
		}
	}

}
