package dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import banco.ReservaHoje;
import banco.ReservaPendente;
import entidade.CategoriaVeiculo;
import entidade.Cliente;
import entidade.Filial;
import entidade.Reserva;
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

}
