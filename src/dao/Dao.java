package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.transform.Transformers;

import entidade.Entidade;
import enumeracoes.Tabela;
import excecoes.BoException;
import excecoes.DaoException;
import sql.ConnectionFactory;

public abstract class Dao<T extends Entidade>{
	private Class<T> tipoDaClasse;
	protected EntityManager em;
	
	public Dao(Class<T> tipoDaClasse) {
		this.tipoDaClasse = tipoDaClasse;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static List<Map<String,Object>> buscarLog(LocalDate de , LocalDate ate, Tabela tabela) throws DaoException {
		EntityManager em = null;
		try {
			em = ConnectionFactory.getConnection();
			Session s = em.unwrap(Session.class);
			List<Map<String,Object>> elementos = s.createSQLQuery("select * from log_"+tabela.getBackEnd()
					+" where date(data_acao) between '"+de+"' and '"+ate+"'")
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return elementos;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR HISTÓRICO NA TABELA "+tabela.toString().toUpperCase());
		}finally {
			em.close();
		}
	}
	
	
	public static void main(String[] args) {
		try {
			ConnectionFactory.setUser("c987654321", "123456");
			List<Map<String,Object>> dados = Dao.buscarLog(LocalDate.of(2019, 1, 22), LocalDate.now(),Tabela.MANUTENCAO);
			System.out.println(dados);
		} catch (DaoException | BoException e) {
			e.printStackTrace();
		}
	}

	public void cadastrar(T t) throws DaoException{
		
		try{
			em = ConnectionFactory.getConnection();
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
		try{
			em = ConnectionFactory.getConnection();
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
	
	public T buscarID(Long id) throws DaoException {
		T t = null;
		try{
			em = ConnectionFactory.getConnection();
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
		try{
			em = ConnectionFactory.getConnection();
			em.getTransaction().begin();
			Entidade entidade = em.merge(t);
			em.remove(entidade);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO EXCLUIR "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM",e);
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<T> buscarPorExemplo(T exemploEntidade)throws DaoException{  
		try {
			em =  ConnectionFactory.getConnection();
			Session session  =  em.unwrap(Session.class);
			Criteria crit = session.createCriteria(tipoDaClasse);
			Example example =  Example.create(exemploEntidade);  
			example.ignoreCase();
			example.enableLike(MatchMode.ANYWHERE);
			crit.add(example);  
			return crit.list();  
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO BUSCAR A PARTIR DE EXEMPLO "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM");
		}finally {
			em.close();
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscaPorBuscaAbrangente(String busca) throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(tipoDaClasse, tipoDaClasse.getSimpleName().toLowerCase()), tipoDaClasse)
			.setParameter("busca","%"+busca+"%")
			.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR "+tipoDaClasse.getSimpleName().toUpperCase()+" POR BUSCA ABRANGENTE- CONTATE ADM");
		}finally {
			em.close();
		}
	}
	
	public List<T> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			return em.createNativeQuery(Util.gerarHqlBuscaAbrangente(tipoDaClasse, restricoes), tipoDaClasse)
			.setParameter("busca","%"+busca+"%")
			.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR "+tipoDaClasse.getSimpleName().toUpperCase()+" POR BUSCA ABRANGENTE- CONTATE ADM");
		}finally {
			em.close();
		}
	}
	
	public List<T> buscarAll() throws DaoException {
		EntityManager em = ConnectionFactory.getConnection();
		List<T> t = new ArrayList<>();
		try{
			t =  em.createQuery("from entidade."+tipoDaClasse.getSimpleName()+" elemento",tipoDaClasse).getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO BUSCAR TODOS "+tipoDaClasse.getSimpleName().toUpperCase()+", CONTATE O ADM.");
		}finally {
			em.close();
		}
		return t;
	}
}
