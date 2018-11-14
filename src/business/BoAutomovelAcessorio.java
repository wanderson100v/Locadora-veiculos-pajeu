package business;

import java.util.List;

import dao.DaoAutomovelAcessorio;
import dao.IDaoAutomovelAcessorio;
import entidade.AutomovelAcessorio;
import excecoes.BoException;
import excecoes.DaoException;

public class BoAutomovelAcessorio implements IBoAutomovelAcessorio{
	private IDaoAutomovelAcessorio daoAutomovelAcessorio = new DaoAutomovelAcessorio();
	
	@Override
	public void cadastrarEditar(AutomovelAcessorio entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoAutomovelAcessorio.editar(entidade);
			}else {
				daoAutomovelAcessorio.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}		
	}

	@Override
	public void excluir(AutomovelAcessorio entidade) throws BoException {
		try {
			daoAutomovelAcessorio.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}		
	}

	@Override
	public AutomovelAcessorio buscarID(Long id) throws BoException {
		try {
			return daoAutomovelAcessorio.buscarId(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<AutomovelAcessorio> buscarAll() throws BoException {
		try {
			return daoAutomovelAcessorio.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
