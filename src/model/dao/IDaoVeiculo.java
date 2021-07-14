package model.dao;

import java.util.List;

import model.excecoes.DaoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Veiculo;

public interface IDaoVeiculo extends IDao<Veiculo>{

	String TOTAL_DISPONIVEL = "select count(*) from "
			+ "Veiculo as veiculo inner join veiculo.filial as filial "
			+ "left join veiculo.categoriaVeiculo as categoriaVeiculo"
			+ " where veiculo.ativo = true and locado = false and veiculo.filial = :filial and veiculo.categoriaVeiculo = :categoria";
	String TOTAL_MANUTENCAO_PENDENTE = "select count(*) from "
			+ "Manutencao as m inner join m.veiculo as v where v = :veiculo and m.estadoManutencao = PENDENTE";
	
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws DaoException;
	
	public long totalManutencaoPendente(Veiculo veiculo)throws DaoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Long categoriaVeiculoId , Veiculo veiculo)throws DaoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Veiculo veiculo)throws DaoException;
	
}
