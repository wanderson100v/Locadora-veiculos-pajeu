package business;

import java.util.Date;
import java.util.List;

import dao.DaoLocacao;
import dao.IDaoLocacao;
import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Locacao;
import excecoes.BoException;
import excecoes.DaoException;

public class BoLocacao implements IBoLocacao {
	private IDaoLocacao daoLocacao = new DaoLocacao();
	private static IBoLocacao instance;
	
	private BoLocacao() {}
	
	public static IBoLocacao getInstance() {
		if(instance == null)
			instance = new BoLocacao();
		return instance;
	}
	
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
			return daoLocacao.buscarID(id);
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

	@Override
	public List<Locacao> buscarPorExemplo(Locacao exemploEntidade) throws BoException {
		try {
			return daoLocacao.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public int totalLocacoePrevisaoEntrega(Filial filialEntrega, CategoriaVeiculo categoriaVeiculo, Date dataLimite) throws BoException {
		try {
			return daoLocacao.totalLocacoePrevisaoEntrega(filialEntrega, categoriaVeiculo, dataLimite);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

}
