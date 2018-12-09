package business;

import java.util.List;

import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import excecoes.BoException;

public interface IBoCategoriaVeiculo extends IBussines<CategoriaVeiculo> {
	
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException;
	
	public CategoriaVeiculo categorizarAutomovel(Automovel automovel) throws BoException;
	
	public List<CategoriaVeiculo> buscaPorBusca(String busca) throws BoException ;
	
}
