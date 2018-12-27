package dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import adapter.ReservaDisponibilidade;
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
	
	@SuppressWarnings("unchecked")
	public List<ReservaDisponibilidade> buscarReservaDisponibilidade(Long filialId, LocalDateTime horario)throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			List<ReservaDisponibilidade> elementos = em.createNativeQuery(
					"select" + 
					" cate.tipo as tipo_categoria" + 
					" ,(select count(*) from veiculo as v"+ 
					" inner join filial as f on(f.id = v.filial_id)" + 
					" left join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
					" where v.ativo = true" + 
					" and v.locado = false"+ 
					" and f.id = fili.id" + 
					" and c.id = cate.id" + 
					" ) as reservavel" +
					" ,(select count(*) from locacao as l" + 
					" inner join veiculo as v on(v.id = l.veiculo_id)" + 
					" inner join categoria_veiculo as c on(c.id = v.categoriaveiculo_id)" + 
					" inner join filial as f on(f.id = l.filialentrega_id)" + 
					" where l.data_devolucao <= :horario" + 
					" and f.id = fili.id" + 
					" and c.id = cate.id" + 
					" ) as a_receber " +
					" ,(select count(*) from reserva as r" + 
					" inner join categoria_veiculo as c on(c.id = r.categoriaveiculo_id)" + 
					" inner join filial as f on(f.id = r.filial_id)" + 
					" where r.estado_reserva = 1" + 
					" and f.id = fili.id" + 
					" and c.id = cate.id" + 
					" ) as reservado"+ 
					" from categoria_veiculo as cate" + 
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
}
