package business;

import java.util.List;

import dao.DaoCategoriaVeiculo;
import dao.IDaoCategoriaVeiculo;
import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import entidade.Veiculo;
import enumeracoes.TipoAutomovel;
import excecoes.BoException;
import excecoes.DaoException;
import excecoes.ValidarException;

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
				Veiculo veiculoExemplo = entidade.getVeiculoExemplo();
				entidade.setVeiculoExemplo(null);
				daoCategoriaVeiculo.cadastrar(entidade);
				veiculoExemplo.setCategoriaVeiculo(entidade);
				BoVeiculo.getInstance().cadastrarEditar(veiculoExemplo);
				entidade.setVeiculoExemplo(veiculoExemplo);
				daoCategoriaVeiculo.editar(entidade);
			}
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public void excluir(CategoriaVeiculo entidade) throws BoException {
		Veiculo veiculo = entidade.getVeiculoExemplo();
		try {
			entidade.setVeiculoExemplo(null);
			if(veiculo!=null) {
				daoCategoriaVeiculo.editar(entidade);
				BoVeiculo.getInstance().exluir(veiculo);
			}
			daoCategoriaVeiculo.excluir(entidade);
		} catch (DaoException e) {
			try {
				Util.validarCausaInicial(e,"","not-null","violates foreign key");
				throw new BoException(e.getMessage());
			}catch (ValidarException ValidarException) {
				if(veiculo!=null) {
					veiculo.setId(null);
					BoVeiculo.getInstance().cadastrarEditar(veiculo);
					entidade.setVeiculoExemplo(veiculo);
					cadastrarEditar(entidade);
				}
				throw new BoException("IMPOSSIBILIDADE DE EXLUSÃO : HÁ VEICULOS NA CATEGORIA");
			}
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

	@Override
	public List<CategoriaVeiculo> buscaPorBusca(String busca) throws BoException {
		try {
			CategoriaVeiculo categoriaVeiculo = new CategoriaVeiculo();
			categoriaVeiculo.setTipo(busca);
			categoriaVeiculo.setDescricao(busca);
			try {
				categoriaVeiculo.setHorasLimpesa(Integer.parseInt(busca));
				categoriaVeiculo.setHorasRevisao(Integer.parseInt(busca));
				categoriaVeiculo.setValorDiaria(Float.parseFloat(busca));
				categoriaVeiculo.setQuilometragemRevisao(Integer.parseInt(busca));
			}catch (NumberFormatException e) {
				categoriaVeiculo.setHorasLimpesa(0);
				categoriaVeiculo.setHorasRevisao(0);
				categoriaVeiculo.setValorDiaria(0f);
				categoriaVeiculo.setQuilometragemRevisao(0);
			}
			return daoCategoriaVeiculo.buscaPorBusca(categoriaVeiculo);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	private void validarCategoria(CategoriaVeiculo categoriaVeiculo) throws BoException {
		Veiculo veiculo = categoriaVeiculo.getVeiculoExemplo();
		if(veiculo != null){
			/*if(veiculo instanceof CaminhonetaCarga)
				if(BoCaminhonetaCarga.getInstance().buscarPorExemplo((CaminhonetaCarga) veiculo) !=null)
					throw new BoException("Já existe uma categoria de Caminhonetas de Carga semelhante");
			else if(veiculo instanceof Automovel) {
				if(BoAutomovel.getInstance().buscarPorExemplo((Automovel) veiculo) !=null)
					throw new BoException("Já existe uma categoria de Automoveis semelhante");
			}else
				throw new BoException("Veiculo de exemplo invalido");*/
		}else
			throw new BoException("Nenhum veiculo de exemplo adicionado");
				
		
	}
}
