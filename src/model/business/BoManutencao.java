package model.business;

import java.util.List;

import model.dao.DaoManutencao;
import model.dao.IDaoManutencao;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Manutencao;

public class BoManutencao extends BoAdapter<Manutencao> implements IBoManutencao {
	
	private IDaoManutencao daoManutencao;
	
	public BoManutencao() {
		super(new DaoManutencao());
		this.daoManutencao = (IDaoManutencao) daoEntidade;
	}
	
	@Override
	public int checarManutencao() throws BoException {
		try {
			return daoManutencao.checarManutencao();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Manutencao manutencao) throws BoException {
		try {
			return daoManutencao.buscaPorBuscaAbrangente(busca,manutencao);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
}
