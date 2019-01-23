package dao;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import entidade.Cliente;
import excecoes.BoException;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoCliente extends Dao<Cliente> implements IDaoCliente{

	public DaoCliente() {
		super(Cliente.class);
	}

	@Override
	public List<Cliente> buscaPorBusca(Cliente cliente) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<Cliente> typedQuery = em.createNamedQuery(BUSCA_POR_BUSCA, Cliente.class);
			typedQuery.setParameter("nome","%"+cliente.getNome()+"%");
			typedQuery.setParameter("codigo","%"+cliente.getCodigo()+"%");
			typedQuery.setParameter("email","%"+cliente.getEmail()+"%");
			typedQuery.setParameter("telefone","%"+cliente.getTelefone()+"%");
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR FUNCIONARIOS POR BUSCA - CONTATE ADM");
		}finally {
			em.close();
		}
	}

}
