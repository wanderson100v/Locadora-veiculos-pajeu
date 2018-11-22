package business;

import java.util.List;

import dao.DaoFilial;
import dao.IDaoFilial;
import entidade.Filial;
import excecoes.BoException;
import excecoes.DaoException;

public class BoFilial implements IBoFilial{
	private IDaoFilial daoFilial = new DaoFilial();
	private static IBoFilial instance;
	
	private BoFilial() {}
	
	public static IBoFilial getInstance() {
		if(instance == null)
			instance = new BoFilial();
		return instance;
	}
	
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
			try {
				daoFilial.excluir(entidade);
			} catch (DaoException e) {
				entidade.setAtivo(false);
				daoFilial.editar(entidade);
				throw new BoException(e.getMessage());
			}
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Filial buscarID(Long id) throws BoException {
		try {
			return daoFilial.buscarID(id);
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

	@Override
	public List<Filial> buscarPorExemplo(Filial exemploEntidade) throws BoException {
		try {
			return daoFilial.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
