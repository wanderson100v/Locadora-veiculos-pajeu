package business;

import java.util.List;

import dao.DaoCategoriaVeiculo;
import dao.IDaoCategoriaVeiculo;
import entidade.CategoriaVeiculo;
import excecoes.BoException;
import excecoes.DaoException;

public class BoCategoriaVeiculo implements IBoCategoriaVeiculo{
	private IDaoCategoriaVeiculo daoCategoriaVeiculo = new DaoCategoriaVeiculo();
	private static IBoCategoriaVeiculo instance;
	
	private BoCategoriaVeiculo() {}
	
	public static IBoCategoriaVeiculo getInstance() {
		if(instance == null)
			instance = new BoCategoriaVeiculo();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(CategoriaVeiculo entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoCategoriaVeiculo.editar(entidade);
			}else {
				daoCategoriaVeiculo.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(CategoriaVeiculo entidade) throws BoException {
		try {
			daoCategoriaVeiculo.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public CategoriaVeiculo buscarID(Long id) throws BoException {
		try {
			return daoCategoriaVeiculo.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<CategoriaVeiculo> buscarAll() throws BoException {
		try {
			return daoCategoriaVeiculo.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<CategoriaVeiculo> buscarPorExemplo(CategoriaVeiculo exemploEntidade) throws BoException {
		try {
			return daoCategoriaVeiculo.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
