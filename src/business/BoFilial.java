package business;

import java.util.List;

import dao.DaoFilial;
import dao.IDaoFilial;
import entidade.Filial;
import excecoes.BoException;
import excecoes.DaoException;

public class BoFilial implements IBoFilial{
	private IDaoFilial daoFilial = new DaoFilial();
	
	@Override
	public void cadastrarEditar(Filial entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoFilial.editar(entidade);
			}else {
				daoFilial.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Filial entidade) throws BoException {
		try {
			daoFilial.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Filial buscarID(Long id) throws BoException {
		try {
			return daoFilial.buscarId(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Filial> buscarAll() throws BoException {
		try {
			return daoFilial.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
