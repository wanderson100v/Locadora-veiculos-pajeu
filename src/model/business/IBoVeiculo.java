package model.business;

import java.util.List;

import model.excecoes.BoException;
import model.vo.CategoriaVeiculo;
import model.vo.Filial;
import model.vo.Veiculo;

public interface IBoVeiculo extends IBussines<Veiculo>{
	
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws BoException ;
	
	public long totalManutencoesPententes(Veiculo veiculo) throws BoException;
	
	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, Long categoriaVeiculoId, String dadoVeiculo) throws BoException;

	public List<Veiculo> buscarVeiculosDisponivel(Long filialId, String dadoVeiculo) throws BoException;
}
