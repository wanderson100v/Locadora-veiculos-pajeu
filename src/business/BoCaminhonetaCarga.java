package business;

import java.util.List;

import dao.DaoCaminhonetaCarga;
import dao.IDaoCaminhonetaCarga;
import entidade.CaminhonetaCarga;
import excecoes.BoException;
import excecoes.DaoException;

public class BoCaminhonetaCarga implements IBoCaminhonetaCarga{
	private IDaoCaminhonetaCarga daoCaminhonetaCarga = new DaoCaminhonetaCarga();
	private static IBoCaminhonetaCarga instance;
	
	private BoCaminhonetaCarga() {}
	
	public static IBoCaminhonetaCarga getInstance() {
		if(instance == null)
			instance = new BoCaminhonetaCarga();
		return instance;
	}
	
	@Override
	public void cadastrarEditar(CaminhonetaCarga entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoCaminhonetaCarga.editar(entidade);
			}else {
				daoCaminhonetaCarga.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(CaminhonetaCarga entidade) throws BoException {
		try {
			try {
				daoCaminhonetaCarga.excluir(entidade);
			} catch (DaoException e) {
				entidade.setAtivo(false);
				daoCaminhonetaCarga.editar(entidade);
				throw new BoException(e.getMessage());
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public CaminhonetaCarga buscarID(Long id) throws BoException {
		try {
			return daoCaminhonetaCarga.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<CaminhonetaCarga> buscarAll() throws BoException {
		try {
			return daoCaminhonetaCarga.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<CaminhonetaCarga> buscarPorExemplo(CaminhonetaCarga exemploEntidade) throws BoException {
		try {
			return daoCaminhonetaCarga.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
