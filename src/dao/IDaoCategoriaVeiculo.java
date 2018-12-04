package dao;

import java.util.List;

import entidade.Automovel;
import entidade.CaminhonetaCarga;
import entidade.CategoriaVeiculo;
import excecoes.DaoException;

public interface IDaoCategoriaVeiculo extends IDao<CategoriaVeiculo>{
	String CATEGORIZAR_CAMINHONETA_CARGA = "categoriaVeiculo.categorizarCaminhonetaCarga";
	String CATEGORIZAR_CAMINHONETA_PASSAGEIRO = "categoriaVeiculo.categorizarCaminhonetaPassageiro";
	String CATEGORIZAR_AUTOMOVEL_PEQUENO= "categoriaVeiculo.categorizarAutomovelPequeno";
	
	public List<CategoriaVeiculo> categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws DaoException;
	
	public List<CategoriaVeiculo> categorizarCaminhonetaPassageiro(Automovel automovel) throws DaoException;
	
	public List<CategoriaVeiculo> categorizarAutomovelPequeno(Automovel automovel) throws DaoException;

}