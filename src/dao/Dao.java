package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import entidade.Entidade;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class Dao<T extends Entidade>{
	private Class<T> tipoDaClasse;
	
	public Dao(Class<T> tipoDaClasse) {
		this.tipoDaClasse = tipoDaClasse;
	}

	public void cadastrar(T t) throws DaoException{
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		try{
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO CADASTRAR "+tipoDaClasse.getSimpleName().toUpperCase()+" CONTATE O ADM.");
		}finally {
			em.close();
		}
	}
	
	public void editar(T t) throws DaoException {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO EDITAR "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM.");
		}finally {
			em.close();
		}
	}
	
	public T buscarId(Long id) throws DaoException {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		T t = null;
		try{
			t = em.find(tipoDaClasse,id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO BUSCAR "+tipoDaClasse.getSimpleName().toUpperCase()+" POR ID, CONTATE O ADM.");
		}finally {
			em.close();
		}
		return t;
	}
	
	public void excluir(T t) throws DaoException {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		try{
			em.getTransaction().begin();
			em.remove(t);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO EXCLUIR "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM");
		}finally {
			em.close();
		}
	}
	
	public List<T> buscarAll() throws DaoException {
		EntityManager em = ConnectionFactory.getConnection().createEntityManager();
		List<T> t = new ArrayList<>();
		try{
			t =  em.createQuery("from "+tipoDaClasse.getSimpleName()+" elemento",tipoDaClasse).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO BUSCAR TODOS "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM.");
		}finally {
			em.close();
		}
		return t;
	}
}
