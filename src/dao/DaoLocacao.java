package dao;

import java.time.LocalDateTime;

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
	public long totalLocacoePrevisaoEntrega(Filial filialEntrega, CategoriaVeiculo categoriaVeiculo, LocalDateTime dataLimite) throws DaoException {
		long total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_PREVISAO_ENTREGA);
			query.setParameter("filialEntrega", filialEntrega);
			query.setParameter("categoria", categoriaVeiculo);
			query.setParameter("dataLimite", dataLimite);
			total = (Long) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR TOTAL DE LOCAÇÕES POR FILIAL, CATEGORIA DE VEICULO E DATA DE ENTREGA LIMITE");
		}
		return total;
	}

}
