package dao;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.DaoException;

public interface IDaoVeiculo extends IDao<Veiculo>{
	String TOTAL_DISPONIVEL = "totalDiponivel";
	
	public int totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException ;
}
