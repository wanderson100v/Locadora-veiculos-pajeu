package business;

import java.util.List;
import java.util.Map;

import dao.DaoAutomovel;
import dao.IDaoAutomovel;
import entidade.Automovel;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

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
				entidade.setCategoriaVeiculo(boCategoriaVeiculo.categorizarAutomovel(entidade));
				daoAutomovel.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(Automovel entidade) throws BoException {
		try {
			daoAutomovel.excluir(entidade);
		}catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				entidade.setAtivo(false);
				cadastrarEditar(entidade);
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : HÁ REGISTROS DEPENDENTES, VEÍCULO PASSOU A SER INATIVO");
			}
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
	
	@Override
	public List<Automovel> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoAutomovel.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<Automovel> buscaPorBuscaAbrangente(String busca, Map<String, String> restricoes) throws BoException {
		try {
			return daoAutomovel.buscaPorBuscaAbrangente(busca, restricoes);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
}
