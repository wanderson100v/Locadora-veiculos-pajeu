package business;

import java.util.List;

import dao.DaoManutencao;
import dao.IDaoManutencao;
import entidade.Manutencao;
import excecoes.BoException;
import excecoes.DaoException;

public class BoManutencao implements IBoManutencao {
	private IDaoManutencao daoManutencao = new DaoManutencao();
	
	@Override
	public void cadastrarEditar(Manutencao entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoManutencao.editar(entidade);
			}else {
				daoManutencao.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Manutencao entidade) throws BoException {
		try {
			daoManutencao.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public Manutencao buscarID(Long id) throws BoException {
		try {
			return daoManutencao.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<Manutencao> buscarAll() throws BoException {
		try {
			return daoManutencao.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Manutencao> buscarPorExemplo(Manutencao exemploEntidade) throws BoException {
		try {
			return daoManutencao.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
