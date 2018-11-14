package business;

import java.util.List;

import dao.DaoAcessorio;
import dao.IDaoAcessorio;
import entidade.Acessorio;
import excecoes.BoException;
import excecoes.DaoException;

public class BoAcessorio implements IBoAcessorio{
	private IDaoAcessorio daoAcessorio = new DaoAcessorio();
	
	@Override
	public void cadastrarEditar(Acessorio entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoAcessorio.editar(entidade);
			}else {
				daoAcessorio.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Acessorio entidade) throws BoException {
		try {
			daoAcessorio.excluir(entidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		} 
	}

	@Override
	public Acessorio buscarID(Long id) throws BoException {
		try {
			return daoAcessorio.buscarId(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> buscarAll() throws BoException {
		try {
			return daoAcessorio.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
