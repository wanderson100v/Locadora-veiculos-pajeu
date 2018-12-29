package dao;

import java.util.List;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.DaoException;

public interface IDaoVeiculo extends IDao<Veiculo>{
	String TOTAL_DISPONIVEL = "veiculo.totalDisponivel";
	String TOTAL_MANUTENCAO_PENDENTE = "veiculo.totalManutencaoPendente";
	
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException;
	
	public long totalManutencaoPendente(Veiculo veiculo)throws DaoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Long categoriaVeiculoId , Veiculo veiculo)throws DaoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Veiculo veiculo)throws DaoException;
	
}
