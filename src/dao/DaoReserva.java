package dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import adapter.ReservaDisponibilidade;
import banco.ReservaHoje;
import banco.ReservaPendente;
import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Reserva;
import enumeracoes.TipoAutomovel;
import excecoes.DaoException;
import sql.ConnectionFactory;

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
			TypedQuery<ReservaHoje> query = em.createQuery("select r from banco.ReservaHoje as r",ReservaHoje.class);
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
			TypedQuery<ReservaPendente> query = em.createNamedQuery(RESERVA_PENDENTE_POR_CLIENTE,ReservaPendente.class);
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
				e.setPrevisto(e.getReceber()+ e.getReservavel() - e.getReservado());
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
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(CaminhonetaCarga caminhonetaCarga,Long filialId,LocalDateTime horario) throws DaoException {
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
				e.setPrevisto(e.getReceber()+ e.getReservavel() - e.getReservado());
			return elementos;

		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR DISPONIBILIDADE DE RESERVAS PARA CATEGORIAS SUPERIORES DE CAMINHONETAS DE CARGA ");
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ReservaDisponibilidade> reservaDisponibilidadeSuperior(Automovel automovel, Long filialId,LocalDateTime horario) throws DaoException {
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
				e.setPrevisto(e.getReceber()+ e.getReservavel() - e.getReservado());
			return elementos;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR DISPONIBILIDADE DE RESERVAS PARA CATEGORIAS SUPERIORES DE AUTOMOVEIS ");
		}finally {
			em.close();
		}
	}
}
