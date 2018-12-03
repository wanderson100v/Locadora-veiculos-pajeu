package dao;

import javax.persistence.Query;

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
			Query query =em.createNativeQuery("CREATE ROLE "+login+" LOGIN PASSWORD '"+senha+"' IN ROLE "+cargo);
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
