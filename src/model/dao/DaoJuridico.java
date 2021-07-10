package model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.excecoes.DaoException;
import model.vo.Juridico;
import model.dao.sql.ConnectionFactory;

public class DaoJuridico extends Dao<Juridico> implements IDaoJuridico{

	public DaoJuridico() {
		super(Juridico.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Juridico> buscaPorBuscaAbrangente(String busca, Juridico juridico) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Map<String,String> restricoes = new HashMap<>();
			if(juridico.isAtivo()!= null)
				restricoes.put("cliente.ativo","="+juridico.isAtivo());
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(Juridico.class, restricoes),Juridico.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CLIENTES FÃ?SICO DE FORMA ABRANGENTE RESTRITIVA");
		}finally {
			em.close();
		}
	}
}