package business;

import java.util.List;

import entidade.Manutencao;
import excecoes.BoException;

public interface IBoManutencao extends IBussines<Manutencao>{

	public int checarManutencao() throws BoException;
	
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Manutencao manutencao) throws BoException;

}
