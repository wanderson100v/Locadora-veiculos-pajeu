package mode.business;

import java.util.List;

import model.dao.DaoCaminhonetaCarga;
import model.dao.IDaoCaminhonetaCarga;
import model.entidade.CaminhonetaCarga;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.excecoes.ValidarException;

public class BoCaminhonetaCarga implements IBoCaminhonetaCarga{
	private static IBoCaminhonetaCarga instance;
	private IDaoCaminhonetaCarga daoCaminhonetaCarga = new DaoCaminhonetaCarga();
	
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
				entidade.setCategoriaVeiculo(BoCategoriaVeiculo.getInstance().categorizarCaminhonetaCarga(entidade));
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
		}catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				entidade.setAtivo(false);
				cadastrarEditar(entidade);
				throw new BoException("IMPOSSIBILIDADE DE EXLUS√ÉO : H√? REGISTROS DEPENDENTES, VE√?CULO PASSOU A SER INATIVO");
			}
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

	@Override
	public List<CaminhonetaCarga> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoCaminhonetaCarga.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
}
