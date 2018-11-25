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
	private IBoCategoriaVeiculo boCategoriaVeiculo = BoCategoriaVeiculo.getInstance();
	
	private BoCaminhonetaCarga() {}
	
	public static IBoCaminhonetaCarga getInstance() {
		if(instance == null)
			instance = new BoCaminhonetaCarga();
		return instance;
	}
	
	public static void main(String[] args) {
			ConnectionFactory.setUser("postgres","admin");
			CaminhonetaCarga caminhonetaCarga = new CaminhonetaCarga(false, true, "kdas-a", "verde", "citroem", "mutilazer", "13123-dsad", "dasd-1321",31.f,TipoCombustivel.ETANOL_DIESEL,100,2012,2013,2,3, null,null,10.5f, 13.4f,14.f,TipoAcionamentoEmbreagem.MANUAL,20,30);
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
				entidade.setCategoriaVeiculo(boCategoriaVeiculo.categorizarCaminhonetaCarga(entidade));
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
	
	public void categorizar(CaminhonetaCarga caminhonetaCarga) {
		// buscar veiculos de exemplo que atendam as caracteristicas da caminhoneta em questão
		// selecionar o qual há mais acessórios
		// pegar sua categoria e atribuir a camihoneta em questão
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
