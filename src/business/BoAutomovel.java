package business;

import java.util.List;

import dao.DaoAutomovel;
import dao.IDaoAutomovel;
import entidade.Automovel;
import excecoes.BoException;
import excecoes.DaoException;

public class BoAutomovel implements IBoAutomovel {
	private static IBoAutomovel instance;
	private IDaoAutomovel daoAutomovel = new DaoAutomovel();
	private IBoCategoriaVeiculo boCategoriaVeiculo = BoCategoriaVeiculo.getInstance();
	
	private BoAutomovel() {}
	
	public static IBoAutomovel getInstance() {
		if(instance == null)
			instance = new BoAutomovel();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(Automovel entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoAutomovel.editar(entidade);
			}else {
				boCategoriaVeiculo.categorizarAutomovel(entidade);
				daoAutomovel.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Automovel entidade) throws BoException {
		try {
			try {
				daoAutomovel.excluir(entidade);
			} catch (DaoException e) {
				entidade.setAtivo(false);
				daoAutomovel.editar(entidade);
				throw new BoException(e.getMessage());
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		} 
	}

	@Override
	public Automovel buscarID(Long id) throws BoException {
		try {
			return daoAutomovel.buscarID(id);
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

	@Override
	public List<Automovel> buscarPorExemplo(Automovel exemploEntidade) throws BoException {
		try {
			return daoAutomovel.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
