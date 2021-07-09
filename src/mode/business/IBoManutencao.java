package mode.business;

import java.util.List;

import model.excecoes.BoException;
import model.entidade.Manutencao;

public interface IBoManutencao extends IBussines<Manutencao>{

	public int checarManutencao() throws BoException;
	
	public List<Manutencao> buscaPorBuscaAbrangente(String busca, Manutencao manutencao) throws BoException;

}
