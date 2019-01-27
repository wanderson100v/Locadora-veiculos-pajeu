package dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entidade.Fisico;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoFisico extends Dao<Fisico> implements IDaoFisico {
	
	public DaoFisico() {
		super(Fisico.class);
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
	
	@SuppressWarnings("unchecked")
	public List<Fisico> buscaPorBuscaAbrangente(String busca, Fisico fisico) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Map<String,String> restricoes = new HashMap<>();
			if(fisico.isAtivo()!= null)
				restricoes.put("cliente.ativo","="+fisico.isAtivo());
			if(fisico.getSexo() != null)
				restricoes.put("fisico.sexo","="+fisico.getSexo().ordinal());
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(Fisico.class, restricoes),Fisico.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR CLIENTES FÍSICO DE FORMA ABRANGENTE RESTRITIVA");
		}finally {
			em.close();
		}
	}
}
