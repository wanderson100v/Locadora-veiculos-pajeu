package model.business;

import java.util.List;

import model.dao.DaoCategoriaVeiculo;
import model.dao.IDaoCategoriaVeiculo;
import model.enumeracoes.TipoAutomovel;
import model.excecoes.BoException;
import model.excecoes.DaoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;

public class BoCategoriaVeiculo extends BoAdapter<CategoriaVeiculo> implements IBoCategoriaVeiculo{
	private IDaoCategoriaVeiculo daoCategoriaVeiculo = new DaoCategoriaVeiculo();
	
	public BoCategoriaVeiculo() {
		super(new DaoCategoriaVeiculo());
	}
	
	@Override
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException {
		try {
			List<CategoriaVeiculo> categoriasCandidatas = daoCategoriaVeiculo.buscarCategoriasCaminhonetaCarga(caminhonetaCarga); 
			if(categoriasCandidatas.isEmpty())
				throw new BoException("Não há categorias cadastradas que satisfação as especificações da camihoneta de carga informada");
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
				categoriasCandidatas = daoCategoriaVeiculo.buscarCategoriaAutomovelPequeno(automovel); 
			else
				categoriasCandidatas = daoCategoriaVeiculo.buscarCategoriasCaminhonetaPassageiro(automovel);
			
			if(categoriasCandidatas.isEmpty())
				throw new BoException("Não há categorias cadastradas que satisfação as especificações do automovel informado");
			
			return categoriasCandidatas.get(0);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<CategoriaVeiculo> categoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException{
		try {
			List<CategoriaVeiculo> categoriasCandidatas = daoCategoriaVeiculo.buscarCategoriasSuperiorCaminhonetaCarga(caminhonetaCarga); 
			if(categoriasCandidatas.isEmpty())
				throw new BoException("Não há categorias de caminhonetas de carga superiores a especificada");
			return categoriasCandidatas;
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<CategoriaVeiculo> categoriasSuperiorAutomovel(Automovel automovel) throws BoException {
		try {
			if(automovel.getTipoAutomovel() == null)
				throw new BoException("Veiculo sem tipo(convencional ou caminhoneta de carga):\n impossibilidade de categorizaÃ§Ã£o");
			List<CategoriaVeiculo> categoriasSuperiores;
			if(automovel.getTipoAutomovel() == TipoAutomovel.CONVENCIONAL)
				categoriasSuperiores = daoCategoriaVeiculo.buscarCategoriasSuperiorAutomovelPequeno(automovel); 
			else
				categoriasSuperiores = daoCategoriaVeiculo.buscarCategoriasSuperiorCaminhonetaPassageiro(automovel);
			
			if(categoriasSuperiores.isEmpty())
				throw new BoException("Não há categorias de Automovel superiores a especificada");
			
			return categoriasSuperiores;
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}

	@Override
	public List<CategoriaVeiculo> buscaPorBuscaAbrangente(String busca) throws BoException {
		try {
			return daoCategoriaVeiculo.buscaPorBuscaAbrangente(busca);
		}catch (DaoException e) {
			throw new BoException(e.getMessage());
		}
	}
	
	/*
	private void validarCategoria(CategoriaVeiculo categoriaVeiculo) throws BoException {
		Veiculo veiculo = categoriaVeiculo.getVeiculoExemplo();
		if(veiculo != null){
			if(veiculo instanceof CaminhonetaCarga)
				if(BoCaminhonetaCarga.getInstance().buscarPorExemplo((CaminhonetaCarga) veiculo) !=null)
					throw new BoException("JÃ¡ existe uma categoria de Caminhonetas de Carga semelhante");
			else if(veiculo instanceof Automovel) {
				if(BoAutomovel.getInstance().buscarPorExemplo((Automovel) veiculo) !=null)
					throw new BoException("JÃ¡ existe uma categoria de Automoveis semelhante");
			}else
				throw new BoException("Veiculo de exemplo invalido");
		}else
			throw new BoException("Nenhum veiculo de exemplo adicionado");
				
		
	}
	*/

}
