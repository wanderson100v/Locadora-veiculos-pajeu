package business;

import java.time.LocalDateTime;
import java.util.List;

import dao.DaoManutencao;
import dao.IDaoManutencao;
import entidade.Manutencao;
import enumeracoes.EstadoManutencao;
import enumeracoes.TipoManutencao;
import excecoes.BoException;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class BoManutencao implements IBoManutencao {
	private IDaoManutencao daoManutencao = new DaoManutencao();
	private static IBoManutencao instance;
	
	private BoManutencao() {}
	
	public static IBoManutencao getInstance() {
		if(instance == null)
			instance = new BoManutencao();
		return instance;
	}
	
	public static void main(String[] args) {
		try {
			ConnectionFactory.setUser("w12745141422","123456");
			Manutencao m = new Manutencao();
			m.setCusto(10.0f);
			m.setEstadoManutencao(EstadoManutencao.PENDENTE);
			m.setDataHoraInicio(LocalDateTime.now());
			m.setTipoManuntencao(TipoManutencao.LIMPEZA);
			m.setVeiculo(BoAutomovel.getInstance().buscarID(new Long(105)));
			getInstance().cadastrarEditar(m);
		} catch (BoException e) {
			e.printStackTrace();
		}
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

}
