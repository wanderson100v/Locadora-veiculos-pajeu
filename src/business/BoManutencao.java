package business;

import java.util.List;
import java.util.Map;

import dao.DaoManutencao;
import dao.IDaoManutencao;
import entidade.Manutencao;
import excecoes.BoException;
import excecoes.DaoException;

public class BoManutencao implements IBoManutencao {
	private IDaoManutencao daoManutencao = new DaoManutencao();
	private static IBoManutencao instance;
	
	private BoManutencao() {}
	
	public static IBoManutencao getInstance() {
		if(instance == null)
			instance = new BoManutencao();
		return instance;
	}
	
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

	@Override
	public List<Manutencao> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoManutencao.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	@Override
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws BoException {
		try {
			return daoManutencao.buscaPorBuscaAbrangente(busca, restricoes);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public int checarManutencao() throws BoException {
		try {
			return daoManutencao.checarManutencao();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
