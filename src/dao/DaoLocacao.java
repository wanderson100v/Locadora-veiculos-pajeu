package dao;

import java.util.Date;

import javax.persistence.Query;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoLocacao extends Dao<Locacao> implements  IDaoLocacao{

	public DaoLocacao() {
		super(Locacao.class);
	}
	
	@Override
	public int totalLocacoePrevisaoEntrega(Filial filialEntrega, CategoriaVeiculo categoriaVeiculo, Date dataLimite) throws DaoException {
		int total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_PREVISAO_ENTREGA);
			query.setParameter("filialEntrega", filialEntrega);
			query.setParameter("categoria", categoriaVeiculo);
			query.setParameter("dataLimite", dataLimite);
			total =(Integer) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE LOCAÇÕES POR FILIAL, CATEGORIA DE VEICULO E DATA DE ENTREGA LIMITE");
		}
		return total;
	}

}
