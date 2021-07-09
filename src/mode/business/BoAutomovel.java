package mode.business;

import java.util.List;

import model.dao.DaoAutomovel;
import model.dao.IDaoAutomovel;
import model.entidade.Automovel;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.excecoes.ValidarException;

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
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : H�? REGISTROS DEPENDENTES, VE�?CULO PASSOU A SER INATIVO");
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
}
