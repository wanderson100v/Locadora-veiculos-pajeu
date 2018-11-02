package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import entidade.Entidade;
import sql.ConnectionFactory;

public class Dao<T extends Entidade>{
	private Class<T> tipoDaClasse;
	
	public Dao(Class<T> tipoDaClasse) {
		this.tipoDaClasse = tipoDaClasse;
	}

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
	
	public T buscarId(Long id) {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		T t = null;
		try{
			t = em.find(tipoDaClasse,id);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			em.close();
		}
		return t;
	}
	
	
	public List<T> buscarAll() {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		List<T> t = new ArrayList<>();
		try{
			t =  em.createQuery("from "+tipoDaClasse.getSimpleName()+" elemento",tipoDaClasse).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			em.close();
		}
		return t;
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
