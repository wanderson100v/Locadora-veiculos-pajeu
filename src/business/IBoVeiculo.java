package business;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import excecoes.BoException;

public interface IBoVeiculo {
	
	public int totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws BoException ;
}
