package business;

import java.util.List;

import dao.DaoAutomovel;
import dao.IDaoAutomovel;
import entidade.Automovel;
import excecoes.BoException;
import excecoes.DaoException;

public class BoAutomovel implements IBoAutomovel {
	private IDaoAutomovel daoAutomovel = new DaoAutomovel();
	
	@Override
	public void cadastrarEditar(Automovel entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoAutomovel.editar(entidade);
			}else {
				daoAutomovel.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Automovel entidade) throws BoException {
		try {
			daoAutomovel.excluir(entidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		} 
	}

	@Override
	public Automovel buscarID(Long id) throws BoException {
		try {
			return daoAutomovel.buscarId(id);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Automovel> buscarAll() throws BoException {
		try {
			return daoAutomovel.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
