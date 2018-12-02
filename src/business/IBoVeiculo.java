package business;

import java.util.List;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.BoException;

public interface IBoVeiculo {
	
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws BoException ;
	
	public long totalManutencoesPententes(Veiculo veiculo) throws BoException;
	
	public List<Veiculo> buscarPorExemplo(Veiculo veiculo) throws BoException;
	
}
