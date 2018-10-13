package dao;

import javax.persistence.EntityManager;

import entidade.Entidade;
import sql.ConnectionFactory;

public class Dao<T extends Entidade>{
	
	public void transacao(T t) {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		try{
			em.getTransaction().begin();
			if(t.getId() != null)
				em.merge(t);
			else
				em.persist(t);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			em.close();
		}
	}
	
	public void excluir(T t) {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		try{
			em.getTransaction().begin();
			em.remove(t);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			em.close();
		}
	}
	
}
