package model.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import model.excecoes.DaoException;
import model.vo.Cliente;
import model.dao.sql.ConnectionFactory;

public class DaoCliente extends Dao<Cliente> implements IDaoCliente{

	public DaoCliente() {
		super(Cliente.class);
	}

	@Override
	public List<Cliente> buscaPorBusca(Cliente cliente) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<Cliente> typedQuery = em.createQuery(BUSCA_POR_BUSCA, Cliente.class);
			typedQuery.setParameter("nome","%"+cliente.getNome()+"%");
			typedQuery.setParameter("codigo","%"+cliente.getCodigo()+"%");
			typedQuery.setParameter("email","%"+cliente.getEmail()+"%");
			typedQuery.setParameter("telefone","%"+cliente.getTelefone()+"%");
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CLIENTES - CONTATE ADM");
		}finally {
			em.close();
		}
	}

}
