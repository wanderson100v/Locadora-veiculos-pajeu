package dao;

import java.util.List;

import javax.persistence.TypedQuery;

import entidade.Fisico;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoFisico extends Dao<Fisico> implements IDaoFisico {
	
	public DaoFisico() {
		super(Fisico.class);
	}

	@Override
	public List<Fisico> buscaPorBusca(Fisico fisico) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<Fisico> typedQuery = em.createNamedQuery(BUSCA_POR_BUSCA, Fisico.class);
			typedQuery.setParameter("codigo","%"+fisico.getCodigo()+"%");
			typedQuery.setParameter("nome","%"+fisico.getNome()+"%");
			typedQuery.setParameter("identificacaoMotorista","%"+fisico.getCpf()+"%");
			typedQuery.setParameter("numeroHabilitacao","%"+fisico.getCpf()+"%");
			typedQuery.setParameter("email","%"+fisico.getEmail()+"%");
			typedQuery.setParameter("telefone","%"+fisico.getTelefone()+"%");
			typedQuery.setParameter("cpf","%"+fisico.getCpf()+"%");
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CLIENTES FISICOS POR BUSCA - CONTATE ADM");
		}finally {
			em.close();
		}
		
	}
}
