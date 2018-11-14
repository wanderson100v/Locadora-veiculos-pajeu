package business;

import java.util.List;

import dao.DaoLocacao;
import dao.IDaoLocacao;
import entidade.Locacao;
import excecoes.BoException;
import excecoes.DaoException;

public class BoLocacao implements IBoLocacao {
	private IDaoLocacao daoLocacao = new DaoLocacao();
	
	@Override
	public void cadastrarEditar(Locacao entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoLocacao.editar(entidade);
			}else {
				daoLocacao.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Locacao entidade) throws BoException {
		try {
			daoLocacao.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Locacao buscarID(Long id) throws BoException {
		try {
			return daoLocacao.buscarId(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Locacao> buscarAll() throws BoException {
		try {
			return daoLocacao.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
