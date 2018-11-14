package business;

import java.util.List;

import dao.DaoCaminhonetaCarga;
import dao.IDaoCaminhonetaCarga;
import entidade.CaminhonetaCarga;
import excecoes.BoException;
import excecoes.DaoException;

public class BoCaminhonetaCarga implements IBoCaminhonetaCarga{
	private IDaoCaminhonetaCarga daoCaminhonetaCarga = new DaoCaminhonetaCarga();
	
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
			daoCaminhonetaCarga.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public CaminhonetaCarga buscarID(Long id) throws BoException {
		try {
			return daoCaminhonetaCarga.buscarId(id);
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

}
