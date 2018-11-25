package business;

import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import excecoes.BoException;

public interface IBoCategoriaVeiculo extends IBussines<CategoriaVeiculo> {
	
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException;
	
	public CategoriaVeiculo categorizarAutomovel(Automovel automovel) throws BoException;
	
}
