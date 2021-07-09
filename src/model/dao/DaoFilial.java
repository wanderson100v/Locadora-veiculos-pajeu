package model.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import model.excecoes.DaoException;
import model.entidade.Filial;
import model.sql.ConnectionFactory;

public class DaoFilial extends Dao<Filial> implements IDaoFilial{

	public DaoFilial() {
		super(Filial.class);
	}
	
	@Override
	public List<Filial> buscaPorBusca(Filial filial) throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<Filial> typedQuery = em.createNamedQuery(BUSCA_POR_BUSCA, Filial.class);
			typedQuery.setParameter("nome","%"+filial.getNome()+"%");
			typedQuery.setParameter("rua","%"+filial.getEndereco().getRua()+"%");
			typedQuery.setParameter("bairro","%"+filial.getEndereco().getBairro()+"%");
			typedQuery.setParameter("cidade","%"+filial.getEndereco().getCidade()+"%");
			typedQuery.setParameter("numero","%"+filial.getEndereco().getNumero()+"%");
			typedQuery.setParameter("cep","%"+filial.getEndereco().getCep()+"%");
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR FUNCIONARIOS POR BUSCA - CONTATE ADM");
		}finally {
			em.close();
		}
	}
	

}
