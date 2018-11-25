package dao;

import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import excecoes.DaoException;

public interface IDaoCategoriaVeiculo extends IDao<CategoriaVeiculo>{
	String CATEGORIZAR_CAMINHONETA_CARGA = "categorizarCaminhonetaCarga";
	String CATEGORIZAR_CAMINHONETA_PASSAGEIRO = "categorizarCaminhonetaPassageiro";
	String CATEGORIZAR_AUTOMOVEL_PEQUENO= "categorizarAutomovelPequeno";
	
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public CategoriaVeiculo categorizarCaminhonetaPassageiro(Automovel automovel) throws DaoException;
	
	public CategoriaVeiculo categorizarAutomovelPequeno(Automovel automovel) throws DaoException;

}