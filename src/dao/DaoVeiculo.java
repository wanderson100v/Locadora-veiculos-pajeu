package dao;

import javax.persistence.Query;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoVeiculo extends Dao<Veiculo> implements IDaoVeiculo {

	public DaoVeiculo() {
		super(Veiculo.class);
	}

	@Override
	public int totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException {
		int total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_DISPONIVEL);
			query.setParameter("filial",filial);
			query.setParameter("categoria", categoria);
			total =(Integer) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE VEICULOS DISPONIVEIS POR FILIAL E CATEGORIA DE VEICULO ");
		}
		return total;
	}

}