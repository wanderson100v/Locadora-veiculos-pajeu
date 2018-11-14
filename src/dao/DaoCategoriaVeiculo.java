package dao;

import entidade.CategoriaVeiculo;

public class DaoCategoriaVeiculo extends Dao<CategoriaVeiculo> implements IDaoCategoriaVeiculo  {

	public DaoCategoriaVeiculo() {
		super(CategoriaVeiculo.class);
	}

}
