package business;

import java.util.List;

import dao.DaoCategoriaVeiculo;
import dao.IDaoCategoriaVeiculo;
import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import enumeracoes.TipoAutomovel;
import excecoes.BoException;
import excecoes.DaoException;
import sql.ConnectionFactory;

public class BoCategoriaVeiculo implements IBoCategoriaVeiculo{
	private static IBoCategoriaVeiculo instance;
	private IDaoCategoriaVeiculo daoCategoriaVeiculo = new DaoCategoriaVeiculo();
	
	private BoCategoriaVeiculo() {}
	
	public static IBoCategoriaVeiculo getInstance() {
		if(instance == null)
			instance = new BoCategoriaVeiculo();
		return instance;
	}
	
	public static void main(String[] args) {
		CategoriaVeiculo categoriaVeiculo = new CategoriaVeiculo();
		categoriaVeiculo.setHorasLimpesa(3);
		categoriaVeiculo.setHorasRevisao(48);
		categoriaVeiculo.setQuilometragemRevisao(6000);
		categoriaVeiculo.setTipo("CCS");
		categoriaVeiculo.setDescricao("Caminhonetas de carga simples ");
		categoriaVeiculo.setValorDiaria(100f);
		CaminhonetaCarga caminhonetaCarga = new CaminhonetaCarga(0f, 0f, 0f, 0, 0);
		categoriaVeiculo.setVeiculoExemplo(caminhonetaCarga);
		
		CategoriaVeiculo categoriaVeiculo2 = new CategoriaVeiculo();
		categoriaVeiculo2.setHorasLimpesa(3);
		categoriaVeiculo2.setHorasRevisao(48);
		categoriaVeiculo2.setQuilometragemRevisao(6000);
		categoriaVeiculo2.setTipo("CCM");
		categoriaVeiculo2.setDescricao("Caminhonetas de carga marrumeno ");
		categoriaVeiculo2.setValorDiaria(100f);
		CaminhonetaCarga caminhonetaCarga2 = new CaminhonetaCarga(10f, 10f, 10f, 10, 10);
		categoriaVeiculo2.setVeiculoExemplo(caminhonetaCarga2);
		
		try {
			ConnectionFactory.setUser("postgres","admin");
			ConnectionFactory.getConnection().close();;
			getInstance().cadastrarEditar(categoriaVeiculo);
			getInstance().cadastrarEditar(categoriaVeiculo2);
		} catch (BoException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void cadastrarEditar(CategoriaVeiculo entidade) throws BoException {
		try {
			if(entidade.getId() != null) {
				daoCategoriaVeiculo.editar(entidade);
			}else {
				validarCategoria(entidade);
				daoCategoriaVeiculo.cadastrar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(CategoriaVeiculo entidade) throws BoException {
		try {
			daoCategoriaVeiculo.excluir(entidade);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());	
		}
	}

	@Override
	public CategoriaVeiculo buscarID(Long id) throws BoException {
		try {
			return daoCategoriaVeiculo.buscarID(id);
		} catch (DaoException e) {
			throw new BoException(e.getMessage());		
		}
	}

	@Override
	public List<CategoriaVeiculo> buscarAll() throws BoException {
		try {
			return daoCategoriaVeiculo.buscarAll();
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<CategoriaVeiculo> buscarPorExemplo(CategoriaVeiculo exemploEntidade) throws BoException {
		try {
			return daoCategoriaVeiculo.buscarPorExemplo(exemploEntidade);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException {
		try {
			return daoCategoriaVeiculo.categorizarCaminhonetaCarga(caminhonetaCarga);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public CategoriaVeiculo categorizarAutomovel(Automovel automovel) throws BoException {
		try {
			if(automovel.getTipoAutomovel() == null){
				throw new BoException("Veiculo sem tipo: impossibilidade de categorização!");
			}
			if(automovel.getTipoAutomovel() == TipoAutomovel.CONVENCIONAL)
				return daoCategoriaVeiculo.categorizarAutomovelPequeno(automovel);
			return daoCategoriaVeiculo.categorizarCaminhonetaPassageiro(automovel);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void validarCategoria(CategoriaVeiculo categoriaVeiculo) throws BoException {
		if(BoCaminhonetaCarga.getInstance().buscarPorExemplo((CaminhonetaCarga) categoriaVeiculo.getVeiculoExemplo()) != null){
			throw new BoException("O já existe uma categoria de veiculo semelhante");
		}
	}

}
