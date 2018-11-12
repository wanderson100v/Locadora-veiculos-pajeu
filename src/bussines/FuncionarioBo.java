package bussines;

import java.util.List;

import dao.Dao;
import entidade.Funcionario;
import excecoes.BoException;
import excecoes.DaoException;

public class FuncionarioBo implements IBussines<Funcionario> {
	public static IBussines<Funcionario> instance;
	private Dao<Funcionario> funcionarioDao;
	
	public static IBussines<Funcionario> getInstance() {
		if(instance == null)
			instance = new FuncionarioBo();
		return instance;
	}
	
	public FuncionarioBo() {
		funcionarioDao = new Dao<>(Funcionario.class);
	}
	
	@Override
	public void cadastrarEditar(Funcionario entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				funcionarioDao.editar(entidade);
			}else {
				funcionarioDao.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Funcionario entidade) throws BoException {
		try {
			if(entidade.getLocacoes() != null || entidade.getReservas()!= null) {
				entidade.setAtivo(false);
				funcionarioDao.editar(entidade);
			}else {
				funcionarioDao.excluir(entidade);
			}
		}catch (DaoException e) {
			System.out.println();
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Funcionario buscarID(Long id) throws BoException {
		try {
			return funcionarioDao.buscarId(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Funcionario> buscarAll() throws BoException {
		try {
			return funcionarioDao.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
