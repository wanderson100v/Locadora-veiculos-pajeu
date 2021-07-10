package model.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.excecoes.DaoException;
import model.vo.Funcionario;
import model.dao.sql.ConnectionFactory;
import model.enumeracoes.Cargo;

public class DaoFuncionario  extends Dao<Funcionario> implements IDaoFuncionario{
	
	public DaoFuncionario() {
		super(Funcionario.class);
	}

	/**
	 * utilizado por terceiros
	 */
	public void cadastrar(Funcionario funcionario ,String login,String senha, Cargo cargo) throws DaoException {
		try{
			em = ConnectionFactory.getConnection();
			Query query =em.createNativeQuery("CREATE ROLE "+login+" LOGIN PASSWORD '"+senha+"'IN ROLE "+cargo);
			em.getTransaction().begin();
			em.persist(funcionario);
			query.executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO CADASTRAR FUNCIONARIO CONTATE O ADM.");
		}finally {
			em.close();
		}
	}
	
	public void editar(Funcionario funcionario, String oldLogin, String newLogin) throws DaoException {
		try{
			em = ConnectionFactory.getConnection();
			Query query =em.createNativeQuery("ALTER ROLE "+oldLogin+" RENAME TO "+newLogin);
			em.getTransaction().begin();
			em.merge(funcionario);
			query.executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO EDITAR FUNCIONARIO, CONTATE O ADM.");
		}finally {
			em.close();
		}
	}
	
	@Override
	public void excluir(Funcionario funcionario, String login) throws DaoException {
		try{
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery("DROP ROLE "+login);
			em.getTransaction().begin();
			query.executeUpdate();
			Funcionario funcionarioMarge = em.merge(funcionario);
			em.remove(funcionarioMarge);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("OCORREU UM ERRO AO EXCLUIR FUNCIONARIO, CONTATE O ADM",e);
		}finally {
			em.close();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> requisitarGralDeAcesso(String login) throws DaoException {
		try{
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery("select rolname from pg_user " + 
					"join pg_auth_members on (pg_user.usesysid=pg_auth_members.member) " + 
					"join pg_roles on (pg_roles.oid=pg_auth_members.roleid) " + 
					"where pg_user.usename = '"+login+"'");
			return (List<String>) query.getResultList();
		}
		catch (Exception e) {
			throw new DaoException("ERRO AO REQUISITAR PRIVILEGIOS DE USUARIO");
		}finally {
			if(em != null )
				em.close();
		}
	}
	
	public Funcionario buscaPorCpf(String cpf) throws DaoException {
		try{
			try {
				em = ConnectionFactory.getConnection();
				return em.createQuery(
						  " select f from "+CAMINHO_CLASSE+"Funcionario as f "
						+ " where f.ativo = true "
						+ " and upper(f.cpf) = upper(:cpf)", Funcionario.class)
				.setParameter("cpf",cpf)
				.getSingleResult();
			}catch (NoResultException nre){
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR FUNCIONÃ?RIO POR CPF");
		}finally {
			em.close();
		}
	}
	
	public void utilizarGralAcesso(Cargo cargo) throws DaoException{
		try{
			em = ConnectionFactory.getConnection();
			em.getTransaction().begin();
			em.createNativeQuery("SET ROLE "+cargo).executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new DaoException("ERRO AO TENTAR UTILIZAR PRIVILEGIOS DE "+cargo);
		}finally {
			em.close();
		}
	}
	
	public void alterarGralAcesso(String login,Cargo oldCargo,Cargo newCargo)throws DaoException{
		try {
			em = ConnectionFactory.getConnection();
			em.getTransaction().begin();
			em.createNativeQuery("alter group "+newCargo+" add user "+login).executeUpdate();
			em.createNativeQuery("alter group "+oldCargo+" drop user "+login).executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw new DaoException("ERRO AO ALTERAR PRIVILEGIOS DE USUÃ?RIO");
		}finally {
			em.close();
		}		
	}
	
	/**
	 * utlizado pelo dono da conta
	 */
	public void editaSenha(Funcionario funcionario,String login ,String novaSenha) throws DaoException{
		try{
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery("ALTER ROLE "+login+" WITH PASSWORD '"+novaSenha+"'");
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new DaoException("ERRO AO EDITAR SENHA");
		}finally {
			em.close();
		}
	}

}
