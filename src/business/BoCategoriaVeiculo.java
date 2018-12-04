package business;

import java.util.List;

import dao.DaoCategoriaVeiculo;
import dao.IDaoCategoriaVeiculo;
import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import entidade.Veiculo;
import enumeracoes.TamanhoVeiculo;
import enumeracoes.TipoAirBag;
import enumeracoes.TipoAutomovel;
import enumeracoes.TipoCambio;
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
			List<CategoriaVeiculo> categoriasCandidatas = daoCategoriaVeiculo.categorizarCaminhonetaCarga(caminhonetaCarga); 
			if(categoriasCandidatas.isEmpty())
				throw new BoException("Não há categorias cadastradas que satisfação as \nespecificações da camihoneta de carga");
			return categoriasCandidatas.get(0);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public CategoriaVeiculo categorizarAutomovel(Automovel automovel) throws BoException {
		try {
			if(automovel.getTipoAutomovel() == null)
				throw new BoException("Veiculo sem tipo(convencional ou caminhoneta de carga):\n impossibilidade de categorização");
			
			List<CategoriaVeiculo> categoriasCandidatas;
			if(automovel.getTipoAutomovel() == TipoAutomovel.CONVENCIONAL)
				categoriasCandidatas = daoCategoriaVeiculo.categorizarAutomovelPequeno(automovel); 
			else
				categoriasCandidatas = daoCategoriaVeiculo.categorizarCaminhonetaPassageiro(automovel);
			
			if(categoriasCandidatas.isEmpty())
				throw new BoException("Não há categorias cadastradas que satisfação as \nespecificações do automovel");
			
			return categoriasCandidatas.get(0);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void validarCategoria(CategoriaVeiculo categoriaVeiculo) throws BoException {
		Veiculo veiculo = categoriaVeiculo.getVeiculoExemplo();
		if(veiculo != null){
			if(veiculo instanceof CaminhonetaCarga)
				if(BoCaminhonetaCarga.getInstance().buscarPorExemplo((CaminhonetaCarga) veiculo) !=null)
					throw new BoException("Já existe uma categoria de Caminhonetas de Carga semelhante");
			else if(veiculo instanceof Automovel) {
				if(BoAutomovel.getInstance().buscarPorExemplo((Automovel) veiculo) !=null)
					throw new BoException("Já existe uma categoria de Automoveis semelhante");
			}else
				throw new BoException("Veiculo de exemplo invalido");
		}else
			throw new BoException("Nenhum veiculo de exemplo adicionado");
				
		
	}

}
