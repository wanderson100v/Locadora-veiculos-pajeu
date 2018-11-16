package business;

import java.util.List;

import dao.DaoFuncionario;
import dao.IDaoFuncionario;
import entidade.Funcionario;
import excecoes.BoException;
import excecoes.DaoException;

public class BoFuncionario implements IBoFuncionario {
	private IDaoFuncionario daoFuncionario = new DaoFuncionario();
	
	@Override
	public void cadastrarEditar(Funcionario entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoFuncionario.editar(entidade);
			}else {
				daoFuncionario.cadastrar(entidade);
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
				daoFuncionario.editar(entidade);
			}else {
				daoFuncionario.excluir(entidade);
			}
		}catch (DaoException e) {
			System.out.println();
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public Funcionario buscarID(Long id) throws BoException {
		try {
			return daoFuncionario.buscarID(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Funcionario> buscarAll() throws BoException {
		try {
			return daoFuncionario.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Funcionario> buscarPorExemplo(Funcionario exemploEntidade) throws BoException {
		try {
			return daoFuncionario.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	

}
