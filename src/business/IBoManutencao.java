package business;

import entidade.Manutencao;
import excecoes.BoException;

public interface IBoManutencao extends IBussines<Manutencao>{

	public int checarManutencao() throws BoException;

}
