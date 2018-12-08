package dao;

import java.util.List;

import javax.persistence.TypedQuery;

import entidade.Juridico;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoJuridico extends Dao<Juridico> implements IDaoJuridico{

	public DaoJuridico() {
		super(Juridico.class);
	}

	@Override
	public List<Juridico> buscaPorBusca(Juridico juridico) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<Juridico> typedQuery = em.createNamedQuery(BUSCA_POR_BUSCA, Juridico.class);
			typedQuery.setParameter("codigo","%"+juridico.getCodigo()+"%");
			typedQuery.setParameter("nome","%"+juridico.getNome()+"%");
			typedQuery.setParameter("email","%"+juridico.getEmail()+"%");
			typedQuery.setParameter("telefone","%"+juridico.getTelefone()+"%");
			typedQuery.setParameter("inscricaoEstadual","%"+juridico.getInscricaoEstadual()+"%");
			typedQuery.setParameter("cnpj","%"+juridico.getCnpj()+"%");
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CLIENTES JURIICOS POR BUSCA - CONTATE ADM");
		}finally {
			em.close();
		}
	}
	

}
