package business;


import java.util.List;

import entidade.Fisico;
import excecoes.BoException;

public interface IBoFisico extends IBussines<Fisico> {
	
	public List<Fisico> buscaPorBusca(Fisico fisico) throws BoException;
	
}
