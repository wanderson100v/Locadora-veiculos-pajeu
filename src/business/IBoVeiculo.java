package business;

import entidade.CategoriaVeiculo;
import entidade.Filial;
import entidade.Veiculo;
import excecoes.BoException;

public interface IBoVeiculo {
	
	public int totalVeiculoDisponivel(Filial filial, CategoriaVeiculo categoria)throws BoException ;
	public int totalManutencoesPententes(Veiculo veiculo) throws BoException;
}
