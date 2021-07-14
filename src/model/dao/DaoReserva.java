package model.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import model.excecoes.DaoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;
import model.vo.Cliente;
import model.vo.Filial;
import model.vo.Reserva;
import model.adapter.ReservaDisponibilidade;
import model.banco.ReservaHoje;
import model.banco.ReservaPendente;
import model.dao.sql.ConnectionFactory;
import model.enumeracoes.TipoAutomovel;

public class DaoReserva extends Dao<Reserva> implements IDaoReserva{

	public DaoReserva() {
		super(Reserva.class);
	}

	@Override
	public long totalReservaDataRetirada(CategoriaVeiculo categoriaVeiculo) throws DaoException {
		long total = 0;
		try {
			em = ConnectionFactory.getConnection();
			Query query = em.createNamedQuery(TOTAL_DATA_RETIRADA);
			query.setParameter("categoriaVeiculo", categoriaVeiculo);
			total = (Long) query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR RESERVAS DE HOJE ");
		}finally {
			em.close();
		}
		
		return total;
	}

	@Override
	public List<ReservaHoje> buscarReservaHoje() throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			em.getTransaction().begin();
			em.createNativeQuery("update reserva set estado_reserva = 3 where estado_reserva = 1 and current_timestamp > (data_retirada + interval '1 hours')  ").executeUpdate();
			em.getTransaction().commit();
			TypedQuery<ReservaHoje> query = em.createQuery("select r from model.banco.ReservaHoje as r",ReservaHoje.class);
			return query.getResultList();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR RESERVAS PARA HOJE ");
		}finally {
			em.close();
		}
	
	}
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<ReservaPendente> query = em.createNamedQuery(RESERVA_PENDENTE_POR_CLIENTE, ReservaPendente.class);
			query.setParameter("nome","%"+cliente.getNome()+"%");
			query.setParameter("codigo","%"+cliente.getCodigo()+"%");
			query.setParameter("telefone","%"+cliente.getTelefone()+"%");
			query.setParameter("email","%"+cliente.getEmail()+"%");
			return query.getResultList();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR RESERVAS PENDENTES POR CLIENTE ");
		}finally {
			em.close();
		}
	}
	
	public List<ReservaPendente> buscarReservaPendente(Cliente cliente, Filial filial) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<ReservaPendente> query = em.createNamedQuery(RESERVA_PENDENTE_POR_CLIENTE_FILIAL,ReservaPendente.class);
			query.setParameter("filial_id",filial.getId());
			query.setParameter("nome","%"+cliente.getNome()+"%");
			query.setParameter("codigo","%"+cliente.getCodigo()+"%");
			query.setParameter("telefone","%"+cliente.getTelefone()+"%");
			query.setParameter("email","%"+cliente.getEmail()+"%");
			return query.getResultList();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR RESERVAS PENDENTES POR CLIENTE E FILIAL");
		}finally {
			em.close();
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			List<ReservaDisponibilidade> elementos = em.createNativeQuery(
					RESERVA_DISPONIBILIDADE_PARCIAL+
					" categoria_veiculo as cate" + 
					" cross join filial fili"+
					" where fili.id = :id" ,"reservaDisponibilidade")
			.setParameter("id",filialId)
			.setParameter("horario",horario).getResultList();
			for(ReservaDisponibilidade e : elementos)
				e.setDisponivel(e.getTotalVeiculo() - e.getTotalReserva() - e.getTotalLocado() - e.getTotalManter()
						+e.getPrevisaoLocacaoAcumulada() + e.getPrevisaoManutencaoAcumulada());
			return elementos;
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("ERRO AO REQUISITAR DISPONIBILIDADE DE RESERVAS EM FILIAL PARA O HORARIO ");
		}finally {
			em.close();
		}
	}
	
	/**
	 * Busca a disponibilidade de reservas para as caregorias superiores a categoria innformada para 
	 * deperminada filial e determinado dia/hora
	 * @param caminhonetaCarga
	 * @param filialId
	 * @param horario
	 * @return 
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<ReservaDisponibilidade> buscarReservaDisponibilidadeSuperior(CaminhonetaCarga caminhonetaCarga,Long filialId,LocalDateTime horario) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			List<ReservaDisponibilidade> elementos = em.createNativeQuery(
					RESERVA_DISPONIBILIDADE_PARCIAL
					+" (select c.* from categoria_veiculo as c"
					+" join veiculo as veiculoExemplo on(veiculoExemplo.id = c.veiculoexemplo_id)"
					+" join caminhoneta_carga as caminhonetaCarga on(caminhonetaCarga.id = veiculoExemplo.id)"
					+" where caminhonetaCarga.potencia_motor >= :potencia"
					+" and caminhonetaCarga.desenpenho >= :desenpenho"
					+" and caminhonetaCarga.capacidade_carga >= :capacidadeCarga"
					+" and caminhonetaCarga.tipo_acionamento_e >= :tipoAcionamentoEmbreagem"
					+" and caminhonetaCarga.distancia_eixos >= :distanciaEixos"
					+" and caminhonetaCarga.capacidade_combustivel >= :capacidadeCombustivel"
					+" and veiculo.torque_motor >= :torqueMotor"
					+") as cate"
					+" cross join filial as fili"
					+" where fili.id = :id" 
					,"reservaDisponibilidade")
			.setParameter("id",filialId)
			.setParameter("horario",horario)
			.setParameter("potencia",caminhonetaCarga.getPotencia())
			.setParameter("desenpenho",caminhonetaCarga.getDesenpenho())
			.setParameter("capacidadeCarga",caminhonetaCarga.getCapacidadeCarga())
			.setParameter("tipoAcionamentoEmbreagem",caminhonetaCarga.getTipoAcionamentoEmbreagem().ordinal())
			.setParameter("distanciaEixos",caminhonetaCarga.getDistanciaEixos())
			.setParameter("capacidadeCombustivel",caminhonetaCarga.getCapacidadeCombustivel())
			.setParameter("torqueMotor",caminhonetaCarga.getTorqueMotor()).getResultList();
			for(ReservaDisponibilidade e : elementos)
				e.setDisponivel(e.getTotalVeiculo() - e.getTotalReserva() - e.getTotalLocado() - e.getTotalManter()
						+e.getPrevisaoLocacaoAcumulada() + e.getPrevisaoManutencaoAcumulada());
			return elementos;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR DISPONIBILIDADE DE RESERVAS PARA CATEGORIAS SUPERIORES DE CAMINHONETAS DE CARGA ");
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservaDisponibilidade> buscarReservaDisponibilidadeSuperior(Automovel automovel, Long filialId,LocalDateTime horario) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			Query query =  em.createNativeQuery(
					RESERVA_DISPONIBILIDADE_PARCIAL
					+" (select c.* from categoria_veiculo as c"
					+" inner join veiculo as exemplo on(exemplo.id = c.veiculoexemplo_id)"
					+" inner join automovel as automovel on(automovel.id = exemplo.id)"
					+" where automovel.tipo = :tipo"
					+((automovel.getTipoAutomovel() == TipoAutomovel.CAMINHONETA_PASSAGEIRO)? " and auto.tipo_airbag >= :tipoAirBag": "")
					+" and automovel.tipo_cambio >= :tipoCambio"
					+" and automovel.tipo_tamanho >= :tamanhoVeiculo"
					+" and exemplo.qtd_porta >= :quantidadePortas"
					+" and exemplo.qtd_passageiro >= :quantidadePassageiro"
					+" and exemplo.tipo_combustivel >= :tipoCombustivel"
					+" ) as cate"
					+" cross join filial as fili"
					+" where fili.id = :id" 
					,"reservaDisponibilidade")
			.setParameter("id",filialId)
			.setParameter("horario",horario)
			.setParameter("tipo", automovel.getTipoAutomovel().ordinal())
			.setParameter("tipoCambio",automovel.getTipoCambio().ordinal())
			.setParameter("tamanhoVeiculo",automovel.getTamanhoVeiculo().ordinal())
			.setParameter("quantidadePortas",automovel.getQuantidadePortas())
			.setParameter("quantidadePassageiro",automovel.getQuantidadePassageiro())
			.setParameter("tipoCombustivel",automovel.getTipoCombustivel().ordinal());
			if(automovel.getTipoAutomovel() == TipoAutomovel.CAMINHONETA_PASSAGEIRO)
				query.setParameter("tipoAirBag", automovel.getTipoAirBag().ordinal());
			List<ReservaDisponibilidade> elementos = query.getResultList();
			for(ReservaDisponibilidade e : elementos)
				e.setDisponivel(e.getTotalVeiculo() - e.getTotalReserva() - e.getTotalLocado() - e.getTotalManter()
						+e.getPrevisaoLocacaoAcumulada() + e.getPrevisaoManutencaoAcumulada());
			return elementos;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR DISPONIBILIDADE DE RESERVAS PARA CATEGORIAS SUPERIORES DE AUTOMOVEIS ");
		}finally {
			em.close();
		}
	}
	
	public ReservaDisponibilidade buscarReservaDisponibilidade(Long categoriaVeiculoId, Long filialId,LocalDateTime horario) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			ReservaDisponibilidade reservaDisponibilidade= (ReservaDisponibilidade) em.createNativeQuery(
					RESERVA_DISPONIBILIDADE_PARCIAL
					+" categoria_veiculo as cate" 
					+" cross join filial as fili"
					+" where fili.id = :filialId"
					+" and cate.id = :categoriaVeiculoId"
					,"reservaDisponibilidade")
			.setParameter("filialId",filialId)
			.setParameter("categoriaVeiculoId",categoriaVeiculoId)
			.setParameter("horario",horario).getSingleResult();
			reservaDisponibilidade.setDisponivel(reservaDisponibilidade.getTotalVeiculo() - reservaDisponibilidade.getTotalReserva() - reservaDisponibilidade.getTotalLocado() - reservaDisponibilidade.getTotalManter()
					+reservaDisponibilidade.getPrevisaoLocacaoAcumulada() + reservaDisponibilidade.getPrevisaoManutencaoAcumulada());
			return reservaDisponibilidade;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR DISPONIBILIDADE DE RESERVAS PARA CATEGORIAS SUPERIORES DE AUTOMOVEIS ");
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Reserva> buscaPorBuscaAbrangente(String busca, Reserva reserva,LocalDate de , LocalDate ate) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			
			Map<String,String> restricoes = new HashMap<>();
			if(reserva.getCliente()!= null)
				restricoes.put("cliente.id","="+reserva.getCliente().getId());
			if(reserva.getFuncionario() != null)
				restricoes.put("funcionario.id","="+reserva.getFuncionario().getId());
			if(reserva.getEstadoReserva() != null)
				restricoes.put("reserva.estado_reserva", " = "+reserva.getEstadoReserva().ordinal());
			if(reserva.getFilial()!= null) 
				restricoes.put("filial.id"," ="+reserva.getFilial().getId());
			if(de != null)
				restricoes.put("date(locacao.data_retirada)"," >= '"+de+"'");
			if(ate != null)
				restricoes.put("date(locacao.data_devolucao)"," <= '"+ate+"'");
			
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(Reserva.class, restricoes),Reserva.class)
					.setParameter("busca","%"+busca+"%")
					.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR LOCAÇÕES ABRANGENTE RESTRITIVA");
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> buscarReservasOrigemLocacaoFinalizada(LocalDate de , LocalDate ate, String agruparPor) throws DaoException{
		EntityManager em = null;
		try {
			em = ConnectionFactory.getConnection();
			Session s = em.unwrap(Session.class);
			String sql = "";
			if(agruparPor == null || (agruparPor != null && agruparPor.equals("Dia")))
				sql = "select * from reserva_origem   "
						+ " where devolucao between '"+de+"' and '"+ate+"'";
			else 
				sql = "select extract(year from devolucao) \\:\\: int as ano"
						+ ", extract(month from devolucao) \\:\\: int as  mes"
						+ ", sum(locacoes_geradas) as locacoes_geradas"
						+ ", sum(valor_real) as valor_real"
						+ ", sum(valor_pago) as valor_pago "
						+ ", sum(restante) as restante"
						+ " from reserva_origem"
						+ " where devolucao between '"+de+"' and '"+ate+"'"
						+ " group by ano , mes"
						+ " order by ano , mes";
			
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> elementos = s.createSQLQuery(sql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return elementos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO GERAR RELATORIO FINANCEIRO DE LOCAÇÕES FINALIZADAS POR PÉRIODO");
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> buscarReservasImpedidas(LocalDate de , LocalDate ate) throws DaoException{
		EntityManager em = null;
		try {
			em = ConnectionFactory.getConnection();
			Session s = em.unwrap(Session.class);
			String sql = "";
			sql = "select * from reserva_impedida   "
					+ " where date(planejada_devolucao) between '"+de+"' and '"+ate+"'";
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> elementos = s.createSQLQuery(sql)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return elementos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO GERAR RELATORIO FINANCEIRO DE LOCAÇÕES FINALIZADAS POR PÉRIODO");
		}finally {
			em.close();
		}
	}
}
