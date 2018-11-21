package dao;

import javax.persistence.Query;

import entidade.Funcionario;
import enumeracoes.Cargo;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class DaoFuncionario extends Dao<Funcionario> implements IDaoFuncionario{
	
	public DaoFuncionario() {
		super(Funcionario.class);
	}
	
	public void cadastrar(Funcionario funcionario ,String senha, Cargo cargo) throws DaoException {
		try{
			em = ConnectionFactory.getConnection();
			Query query =em.createNativeQuery("CREATE ROLE "+funcionario.getCpf()+" LOGIN PASSWORD '"+senha+"' IN ROLE "+cargo);
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

	@Override
	public void excluir(Funcionario funcionario) throws DaoException {
		try{
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery("DROP ROLE "+funcionario.getCpf());
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
	
	public void editaSenha(Funcionario funcionario, String novaSenha) throws DaoException{
		try{
			em = ConnectionFactory.getConnection();
			Query query = em.createNativeQuery("ALTER ROLE "+funcionario.getCpf()+" WITH PASSWORD '"+novaSenha+"'");
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
