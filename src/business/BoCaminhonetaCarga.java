package business;

import java.util.List;

import dao.DaoCaminhonetaCarga;
import dao.IDaoCaminhonetaCarga;
import entidade.CaminhonetaCarga;
import enumeracoes.TipoAcionamentoEmbreagem;
import enumeracoes.TipoCombustivel;
import excecoes.BoException;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class BoCaminhonetaCarga implements IBoCaminhonetaCarga{
	private static IBoCaminhonetaCarga instance;
	private IDaoCaminhonetaCarga daoCaminhonetaCarga = new DaoCaminhonetaCarga();
	
	private BoCaminhonetaCarga() {}
	
	public static IBoCaminhonetaCarga getInstance() {
		if(instance == null)
			instance = new BoCaminhonetaCarga();
		return instance;
	}
	
	public static void main(String[] args) {
			ConnectionFactory.setUser("postgres","admin");
			
			CaminhonetaCarga caminhonetaCarga = new CaminhonetaCarga(false, true, "dasd-b", "rosa", "tabu", "ffabuster", "dasd-13", "gdfsg-dsa",20.4f,TipoCombustivel.GASOLINA_ETANOL,3000,2009,2012,4,1, null,null,15.3f, 11.9f,12.f,TipoAcionamentoEmbreagem.HIDRAULICO,12,21);
			try {
				getInstance().cadastrarEditar(caminhonetaCarga);
			} catch (BoException e) {
				e.printStackTrace();
			}
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
			try {
				daoCaminhonetaCarga.excluir(entidade);
			} catch (DaoException e) {
				entidade.setAtivo(false);
				daoCaminhonetaCarga.editar(entidade);
				throw new BoException(e.getMessage());
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());	
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

}
