package business;

import java.util.List;

import entidade.Filial;
import excecoes.BoException;

public interface IBoFilial extends IBussines<Filial>{
	
	public List<Filial> buscaPorBusca(String busca) throws BoException;
	
}
