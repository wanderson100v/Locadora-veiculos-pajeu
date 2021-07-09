package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.entidade.CategoriaVeiculo;
import model.entidade.Filial;
import model.entidade.Veiculo;

public interface IDaoVeiculo extends IDao<Veiculo>{
	String TOTAL_DISPONIVEL = "veiculo.totalDisponivel";
	String TOTAL_MANUTENCAO_PENDENTE = "veiculo.totalManutencaoPendente";
	
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException;
	
	public long totalManutencaoPendente(Veiculo veiculo)throws DaoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Long categoriaVeiculoId , Veiculo veiculo)throws DaoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Veiculo veiculo)throws DaoException;
	
}
