package dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.TypedQuery;

import entidade.Fisico;
import excecoes.BoException;
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
	
	@SuppressWarnings("unchecked")
	public List<Fisico> buscarMotoristasValidos(Fisico fisico, LocalDate dataSuperior) throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			return em.createNativeQuery(
					"select motorista.*, cliente.* from Fisico as motorista"
					+ " inner join Cliente as cliente on(motorista.id = cliente.id)"
					+ " where motorista.data_validade_habilitacao is not null"
					+ " and motorista.identificacao_motorista is not null"
					+ " and motorista.numero_habilitacao is not null"
					+ " and motorista.data_validade_habilitacao >= :dataSuperior"
					+ " and current_date > motorista.data_nascimento + interval '21 years'"
					+ " and (upper(cliente.nome) like upper(:nome)"
					+ " or upper(motorista.cpf) like upper(:cpf)"
					+ " or upper(motorista.identificacao_motorista) like upper(:identificacaoMotorista)"
					+ " or upper(motorista.numero_habilitacao) like upper(:numeroHabilitacao)"
					+ " or upper(cliente.email) like upper(:email)"
					+ " or upper(cliente.telefone) like upper(:telefone))"
					, Fisico.class)
			.setParameter("dataSuperior",dataSuperior)
			.setParameter("nome","%"+fisico.getNome()+"%")
			.setParameter("cpf","%"+fisico.getCpf()+"%")
			.setParameter("identificacaoMotorista","%"+fisico.getCpf()+"%")
			.setParameter("numeroHabilitacao","%"+fisico.getCpf()+"%")
			.setParameter("email","%"+fisico.getEmail()+"%")
			.setParameter("telefone","%"+fisico.getTelefone()+"%")
			.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR MOTORISTAS VALIDOS");
		}finally {
			em.close();
		}
	}
}
