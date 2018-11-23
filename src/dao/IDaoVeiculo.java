package dao;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.DaoException;

public interface IDaoVeiculo extends IDao<Veiculo>{
	String TOTAL_DISPONIVEL = "totalDiponivel";
	String TOTAL_MANUTENCAO_PENDENTE = "totalManutencaoPendente";
	
	public int totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException;
	
	public int totalManutencaoPendente(Veiculo veiculo)throws DaoException;
}
