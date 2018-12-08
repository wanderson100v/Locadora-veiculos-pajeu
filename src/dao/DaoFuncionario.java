package dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.DaoException;
import sql.ConnectionFactory;

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
			throw new DaoException("OCORREU UM ERRO AO EXCLUIR FUNCIONARIO, CONTATE O ADM");
		}finally {
			em.close();
		}
	}
	
	@Override
	public List<Funcionario> buscaPorBusca(Funcionario funcionario) throws DaoException {
		try {
			em = ConnectionFactory.getConnection();
			TypedQuery<Funcionario> typedQuery = em.createNamedQuery(BUSCA_POR_BUSCA, Funcionario.class);
			typedQuery.setParameter("nome","%"+funcionario.getNome()+"%");
			typedQuery.setParameter("cpf","%"+funcionario.getCpf()+"%");
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO BUSCAR FUNCIONARIOS POR BUSCA - CONTATE ADM");
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
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException("ERRO AO REQUISITAR PRIVILEGIOS DE USUARIO");
		}finally {
			if(em != null )
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
			throw new DaoException("ERRO AO ALTERAR PRIVILEGIOS DE USUÁRIO");
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
