package dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Locacao> buscaPorBuscaAbrangente(String busca, Locacao locacao,LocalDate de , LocalDate ate) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			
			Map<String,String> restricoes = new HashMap<>();
			if(locacao.getCliente()!= null)
				restricoes.put("cliente.id","="+locacao.getCliente().getId());
			if(locacao.getFuncionario() != null)
				restricoes.put("funcionario.id","="+locacao.getFuncionario().getId());
			if(locacao.isFinalizado() != null)
				restricoes.put("locacao.finalizado", " = "+locacao.isFinalizado());
			if(locacao.getMotorista()!= null) 
				restricoes.put("motorista.id"," ="+locacao.getMotorista().getId());
			if(locacao.getFilialRetirada() != null) 
				restricoes.put("filialRetirada.id"," ="+locacao.getFilialRetirada().getId());
			if(locacao.getFilialEntrega() != null) 
				restricoes.put("filialEntrega.id"," ="+locacao.getFilialEntrega().getId());
			if(de != null)
				restricoes.put("date(locacao.data_retirada)"," >= '"+de+"'");
			if(ate != null)
				restricoes.put("date(locacao.data_devolucao)"," <= '"+ate+"'");
			
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(Locacao.class, restricoes),Locacao.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR LOCAÇÕES ABRANGENTE RESTRITIVA");
		}finally {
			em.close();
		}
	}

}
