package model.business;

import java.util.List;

import model.excecoes.BoException;
import model.vo.Automovel;
import model.vo.CaminhonetaCarga;
import model.vo.CategoriaVeiculo;

public interface IBoCategoriaVeiculo extends IBussines<CategoriaVeiculo> {
	
	public CategoriaVeiculo categorizarCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException;
	
	public CategoriaVeiculo categorizarAutomovel(Automovel automovel) throws BoException;
	
	public List<CategoriaVeiculo> categoriasSuperiorCaminhonetaCarga(CaminhonetaCarga caminhonetaCarga) throws BoException;
	
	public List<CategoriaVeiculo> categoriasSuperiorAutomovel(Automovel automovel) throws BoException;	
	
}
