package business;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.BoException;

public interface IBoVeiculo {
	
	public long totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws BoException ;
	public long totalManutencoesPententes(Veiculo veiculo) throws BoException;
}
